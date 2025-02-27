package vestido_bank.VestidoBank.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaPoupancaDto;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ConnectionFailedException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.CreditCardNotFoundExceptions;
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

  public Transaction pagarFaturaComSaldo(Long clientId, Long contaPoupancaId,
      Long cartaoDeCreditoId, float valor) {
    Optional<Client> client = clientRepository.findById(clientId);
    if (client.isEmpty()) {
      throw new ClientNotFoundException("Não0 foi possível encontrar cliente");
    }
    Optional<ContaPoupanca> contaPoupanca = contaPoupancaRepository.findById(contaPoupancaId);
    if (contaPoupanca.isEmpty()) {
      throw new ContaCorrentNotFoundException("Não encontrado");
    }
    Optional<CreditCard> creditCard = creditCardRepository.findById(cartaoDeCreditoId);
    if (creditCard.isEmpty()) {
      throw new CreditCardNotFoundExceptions("Não encontrado");
    }

    ContaPoupanca contaPoupanca1 = contaPoupanca.get();

    if (contaPoupanca1.getSaldo() < valor) {
      throw new SaldoInsuficienteException("Valor não suficiente");
    }

    if (valor <= 0) {
      throw new IllegalArgumentException("O valor deve ser positivo.");
    }

    contaPoupanca1.setSaldo(contaPoupanca1.getSaldo() - valor);
    contaPoupancaRepository.save(contaPoupanca1);

    CreditCard creditCard1 = creditCard.get();

    creditCard1.setFaturaAtual(creditCard1.getFaturaAtual().subtract(new BigDecimal(valor)));
    creditCardRepository.save(creditCard1);

    Transaction transacao = new Transaction(
            client.get(),
            null,
            null,
        creditCard1,
            null,
            null,
        contaPoupanca1,
            valor,
            LocalDateTime.now(),
            "Pagamento de fatura do cartão de crédito",
            contaPoupanca1.getSaldo()
        );

    return transactionsRepository.save(transacao);

  }


}
