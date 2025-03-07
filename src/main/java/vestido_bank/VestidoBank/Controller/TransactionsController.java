package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.BuyFaturaResponse;
import vestido_bank.VestidoBank.Controller.Dto.FaturaRequestDto;
import vestido_bank.VestidoBank.Controller.Dto.PagamentoFaturaDto;
import vestido_bank.VestidoBank.Controller.Dto.PagamentoFaturaResponse;
import vestido_bank.VestidoBank.Controller.Dto.TransactionCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.TransactionDto;
import vestido_bank.VestidoBank.Controller.Dto.TransactionWithCreditCardDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.InvalidTransaction;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;
import vestido_bank.VestidoBank.Service.ContaPoupancaService;
import vestido_bank.VestidoBank.Service.CreditCardService;
import vestido_bank.VestidoBank.Service.TransactionService;

@RestController
@RequestMapping("/transactions/accounts")
public class TransactionsController {

  TransactionService transactionService;
  ContaPoupancaService contaPoupancaService;
  ContaCorrenteService contaCorrenteService;
  ClientService clientService;
  CreditCardService creditCardService;

  @Autowired
  public TransactionsController(TransactionService transactionService,
      ContaPoupancaService contaPoupancaService, ContaCorrenteService contaCorrenteService,
      ClientService clientService, CreditCardService creditCardService) {
    this.transactionService = transactionService;
    this.contaPoupancaService = contaPoupancaService;
    this.contaCorrenteService = contaCorrenteService;
    this.clientService = clientService;
    this.creditCardService = creditCardService;
  }

  @GetMapping
  public List<TransactionDto> getAllTransactions() {
    List<Transaction> transactions = transactionService.getAllTransactions();
    return transactions.stream().map(TransactionDto::fromEntity)
        .toList();

  }

  @PostMapping("/{contaOrigemId}/transfer/{contaDestinoId}")
  public ResponseEntity<TransactionDto> transferBetweenAccounts(@PathVariable Long contaOrigemId,
      @PathVariable Long contaDestinoId, @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody
      TransactionCreateDto transactionCreateDto) throws InvalidTransaction {

    ContaPoupanca contaOrigem = contaPoupancaService.getPoupancaById(contaOrigemId);
    if (contaOrigem == null) {
      throw new ContaPoupancaNotFoundException("Não encontrada");
    }

    ContaCorrente contaDestino = contaCorrenteService.getContaCorrenteById(contaDestinoId);
    if (contaDestino == null) {
      throw new ContaCorrentNotFoundException("Não encontrada");
    }

    if (contaOrigem.getSaldo() < transactionCreateDto.valor()) {
      throw new InvalidTransaction("Saldo insuficiente");
    }

    Transaction transaction = transactionCreateDto.toEntity(contaOrigem, contaDestino);
    transaction.setClient(contaOrigem.getClient());

    Transaction createTransaction = transactionService.createTransactionWithPoupancaAndCorrente(
        contaOrigemId, contaDestinoId, transaction.getValor(), transaction);
    return ResponseEntity.ok(TransactionDto.fromEntity(createTransaction));
  }


  @PostMapping("/{clientId}/{contaPoupancaId}/{cartaoDeCreditoId}/payments-with-creditcard")
  public ResponseEntity<PagamentoFaturaResponse> pagarFaturaComSaldo(@PathVariable Long clientId,
      @PathVariable Long contaPoupancaId, @PathVariable Long cartaoDeCreditoId,
      @RequestBody FaturaRequestDto faturaRequestDto) {
    Client client = clientService.getById(clientId);
    ContaPoupanca contaPoupanca = contaPoupancaService.getPoupancaById(contaPoupancaId);
    CreditCard creditCard = creditCardService.findById(cartaoDeCreditoId);
    if (faturaRequestDto.valor() <= 0) {
      throw new IllegalArgumentException("O valor deve ser positivo.");
    }


    PagamentoFaturaResponse response = transactionService.pagarFaturaComSaldo(
        client,
        contaPoupanca,
        creditCard,
        faturaRequestDto.valor(),
        faturaRequestDto.descricao()
    );

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{clientId}/credit")
  public ResponseEntity<List<TransactionWithCreditCardDto>> getTransactionsById(@PathVariable Long clientId) throws ClientNotFoundException {

    List<TransactionWithCreditCardDto> transaction = transactionService.getAllTransactionsByClientId(clientId);
    return ResponseEntity.ok(transaction);
  }

  @GetMapping("/with-credit-card")
  public ResponseEntity<List<TransactionWithCreditCardDto>> getAllTransactionsWithCreditCard() {
    List<TransactionWithCreditCardDto> transactions = transactionService.getAllTransactionsWithCreditCard();
    return ResponseEntity.ok(transactions);
  }

}
