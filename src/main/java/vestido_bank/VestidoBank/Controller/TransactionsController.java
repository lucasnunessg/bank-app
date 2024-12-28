package vestido_bank.VestidoBank.Controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.TransactionCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.TransactionDto;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.InvalidTransaction;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;
import vestido_bank.VestidoBank.Service.ContaPoupancaService;
import vestido_bank.VestidoBank.Service.TransactionService;

@RestController
@RequestMapping("/transactions/accounts")
public class TransactionsController {

  TransactionService transactionService;
  ContaPoupancaService contaPoupancaService;
  ContaCorrenteService contaCorrenteService;
  ClientService clientService;

  @Autowired
  public TransactionsController(TransactionService transactionService,
      ContaPoupancaService contaPoupancaService, ContaCorrenteService contaCorrenteService,
      ClientService clientService) {
    this.transactionService = transactionService;
    this.contaPoupancaService = contaPoupancaService;
    this.contaCorrenteService = contaCorrenteService;
    this.clientService = clientService;
  }

  @GetMapping
  public List<TransactionDto> getAllTransactions() {
    List<Transaction> transactions = transactionService.getAllTransactions();
    return transactions.stream().map(TransactionDto::fromEntity)
        .toList();

  }

  @PostMapping("/{contaOrigemId}/transfer/{contaDestinoId}")
  public ResponseEntity<TransactionDto> transferBetweenAccounts(@PathVariable Long contaOrigemId,
      @PathVariable Long contaDestinoId, @RequestBody
  TransactionCreateDto transactionCreateDto) throws InvalidTransaction {

    ContaPoupanca contaOrigem = contaPoupancaService.getPoupancaById(contaOrigemId);
    if (contaOrigem == null) {
      throw new ContaPoupancaNotFoundException("Não encontrada");
    }

    ContaCorrente contaDestino = contaCorrenteService.getContaCorrenteById(contaDestinoId);
    if (contaDestino == null) {
      throw new ContaCorrentNotFoundException("Não encontrada");
    }

    if(contaOrigem.getSaldo() < transactionCreateDto.valor()) {
      throw new InvalidTransaction("Saldo insuficiente");
    }


    Transaction transaction = transactionCreateDto.toEntity(contaOrigem, contaDestino);

    Transaction createTransaction = transactionService.createTransactionWithPoupancaAndCorrente(
        contaOrigemId, contaDestinoId, transaction.getValor(), transaction);
    return ResponseEntity.ok(TransactionDto.fromEntity(createTransaction));
  }

}
