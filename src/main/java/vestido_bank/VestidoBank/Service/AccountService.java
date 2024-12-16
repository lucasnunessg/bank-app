package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteDto;
import vestido_bank.VestidoBank.Entity.Account;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
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

  public ContaCorrente criarContaCorrent(Long clientId,
      ContaCorrenteCreateDto contaCorrenteCreateDto) {
    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));


    ContaCorrente contaCorrente = contaCorrenteCreateDto.toEntity(client);
    contaCorrente.setClient(client);
    return accountRepository.save(contaCorrente);

  }

  public ContaCorrente depositar(Long clientId, Long contaCorrenteId, float valor) {
    Optional<Client> client = clientRepository.findById(clientId);
    if (client.isEmpty()) {
      throw new ClientNotFoundException("Cliente não encontrado");
    }

    Optional<Account> account = accountRepository.findById(contaCorrenteId);
    if (account.isEmpty()) {
      throw new ContaCorrentNotFoundException("Conta Corrente não encontrada");
    }

    if (valor <= 0) {
      throw new IllegalArgumentException("O valor do depósito deve ser positivo");
    }

    if (account.get() instanceof ContaCorrente) {
      ContaCorrente conta = (ContaCorrente) account.get();
      conta.depositar(valor);
      accountRepository.save(conta);
      return conta;
    } else {
      throw new IllegalArgumentException("A conta não é do tipo ContaCorrente");
    }
  }





}
