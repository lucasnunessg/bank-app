package vestido_bank.VestidoBank.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import vestido_bank.VestidoBank.Controller.Dto.PagamentoFaturaResponse;
import vestido_bank.VestidoBank.Controller.Dto.TransactionWithCreditCardDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ConnectionFailedException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.InvalidTransaction;
import vestido_bank.VestidoBank.Exceptions.SaldoInsuficienteException;
import vestido_bank.VestidoBank.Exceptions.TransactionNotFound;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;
import vestido_bank.VestidoBank.Repository.ContaPoupancaRepository;
import vestido_bank.VestidoBank.Repository.CreditCardRepository;
import vestido_bank.VestidoBank.Repository.TransactionsRepository;

@Service
public class TransactionService {

  ContaPoupancaRepository contaPoupancaRepository;
  ContaCorrenteRepository contaCorrenteRepository;
  TransactionsRepository transactionsRepository;
  ClientRepository clientRepository;
  CreditCardRepository creditCardRepository;

  @Autowired
  public TransactionService(ContaPoupancaRepository contaPoupancaRepository,
      ContaCorrenteRepository contaCorrenteRepository,
      TransactionsRepository transactionsRepository, ClientRepository clientRepository,
      CreditCardRepository creditCardRepository) {
    this.contaPoupancaRepository = contaPoupancaRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
    this.transactionsRepository = transactionsRepository;
    this.clientRepository = clientRepository;
    this.creditCardRepository = creditCardRepository;
  }

  public List<Transaction> getAllTransactions() {
    return transactionsRepository.findAll();
  }

  public List<Transaction> getAllTransactionsById(Long clientId) {
    Optional<Client> client = clientRepository.findById(clientId);
    if (client.isEmpty()) {
      throw new ClientNotFoundException("Não encontrado");
    }
    return transactionsRepository.findByClient_Id(clientId);
  }

  public List<TransactionWithCreditCardDto> getAllTransactionsWithCreditCard() {
    List<Transaction> transactions = creditCardRepository.findAllWithCreditCard();

    return transactions.stream()
        .map(this::toTransactionWithCreditCardDto)
        .collect(Collectors.toList());
  }

  private TransactionWithCreditCardDto toTransactionWithCreditCardDto(Transaction transaction) {
    Long creditCardId = transaction.getCreditCardOrigem() != null
        ? transaction.getCreditCardOrigem().getId()
        : transaction.getCreditCardDestino().getId();

    BigDecimal faturaAtual = transaction.getCreditCardOrigem() != null
        ? transaction.getCreditCardOrigem().getFaturaAtual()
        : transaction.getCreditCardDestino().getFaturaAtual();

    return new TransactionWithCreditCardDto(
        transaction.getId(),
        creditCardId,
        new BigDecimal(transaction.getValor()),
        transaction.getData_hora(),
        transaction.getDescricao(),
        faturaAtual
    );
  }


  public Transaction getTransactionById(Long id) {
    Optional<Transaction> transaction = transactionsRepository.findById(id);
    if (transaction.isEmpty()) {
      throw new TransactionNotFound("Não encontrada");
    }
    return transaction.get();
  }

  @Transactional
  public Transaction createTransactionWithPoupancaAndCorrente(Long contaPoupancaId,
      Long contaCorrenteId,
      float valor, Transaction transaction) {

    try {
      Optional<ContaPoupanca> contaPoupanca = contaPoupancaRepository.findById(contaPoupancaId);
      if (contaPoupanca.isEmpty()) {
        throw new ContaPoupancaNotFoundException("Não encontrada");
      }

      Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(contaCorrenteId);
      if (contaCorrente.isEmpty()) {
        throw new ContaCorrentNotFoundException("Não encontrada");
      }

      ContaPoupanca contaPoupanca1 = contaPoupanca.get();
      ContaCorrente contaCorrente1 = contaCorrente.get();

      if (contaPoupanca1.getSaldo() < valor) {
        throw new InvalidTransaction("Não é possível concluir a transação");
      }
      contaPoupanca1.setSaldo(contaPoupanca1.getSaldo() - valor);
      contaCorrente1.setSaldo(contaCorrente1.getSaldo() + valor);

      transaction.setContaPoupancaOrigem(contaPoupanca1);
      transaction.setContaCorrenteDestino(contaCorrente1);
      transaction.setValor(valor);
      transaction.setData_hora(LocalDateTime.now());
      transaction.setDescricao("Transferência entre conta poupança e conta corrente!");

      return transactionsRepository.save(transaction);
    } catch (ResourceAccessException e) {
      throw new ConnectionFailedException(
          "Ocorreu um erro em sua solicitação, tente novamente mais tarde");
    }


  }



  @Transactional
  public PagamentoFaturaResponse pagarFaturaComSaldo(Client client, ContaPoupanca contaPoupanca, CreditCard creditCard, float valor, String descricao) {
    if (contaPoupanca.getSaldo() < valor) {
      throw new SaldoInsuficienteException("Saldo insuficiente na conta poupança");
    }

    contaPoupanca.setSaldo(contaPoupanca.getSaldo() - valor);
    contaPoupancaRepository.save(contaPoupanca);

    creditCard.setFaturaAtual(creditCard.getFaturaAtual().subtract(new BigDecimal(valor)));
    creditCardRepository.save(creditCard);

    Transaction transacao = new Transaction(
        client,
        null,
        null,
        creditCard,
        null,
        null,
        contaPoupanca,
        valor,
        LocalDateTime.now(),
        descricao,
        contaPoupanca.getSaldo()
    );
    transactionsRepository.save(transacao);

    return new PagamentoFaturaResponse(
        creditCard.getId(),
        creditCard.getFaturaAtual(),
        new BigDecimal(contaPoupanca.getSaldo()),
        "Pagamento realizado com sucesso!"
    );
  }


}
