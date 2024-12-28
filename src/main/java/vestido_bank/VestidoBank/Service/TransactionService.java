package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Exceptions.ConnectionFailedException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.InvalidTransaction;
import vestido_bank.VestidoBank.Exceptions.TransactionNotFound;
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;
import vestido_bank.VestidoBank.Repository.ContaPoupancaRepository;
import vestido_bank.VestidoBank.Repository.TransactionsRepository;

@Service
public class TransactionService {

  ContaPoupancaRepository contaPoupancaRepository;
  ContaCorrenteRepository contaCorrenteRepository;
  TransactionsRepository transactionsRepository;

  @Autowired
  public TransactionService(ContaPoupancaRepository contaPoupancaRepository,
      ContaCorrenteRepository contaCorrenteRepository,
      TransactionsRepository transactionsRepository) {
    this.contaPoupancaRepository = contaPoupancaRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
    this.transactionsRepository = transactionsRepository;
  }

  public List<Transaction> getAllTransactions() {
    return transactionsRepository.findAll();
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
      transaction.setContaCorrenteOrigem(contaCorrente1);
      transaction.setValor(valor);
      transaction.setData_hora(LocalDateTime.now());
      transaction.setDescricao("Transferência entre conta poupança e conta corrente!");

      return transactionsRepository.save(transaction);
    } catch (ResourceAccessException e) {
      throw new ConnectionFailedException("Ocorreu um erro em sua solicitação, tente novamente mais tarde");
    }


  }

}
