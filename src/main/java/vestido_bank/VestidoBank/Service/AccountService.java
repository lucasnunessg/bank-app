package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Repository.AccountRepository;
import vestido_bank.VestidoBank.Repository.ClientRepository;

@Service
public class AccountService {

  AccountRepository accountRepository;
  ClientRepository clientRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
    this.accountRepository = accountRepository;
    this.clientRepository = clientRepository;
  }
  public ContaCorrente criarContaCorrent(Long clientId, float saldo, float limite){
    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));

    ContaCorrente contaCorrente = new ContaCorrente(saldo, LocalDateTime.now(), limite, client);

    return accountRepository.save(contaCorrente);

  }


}
