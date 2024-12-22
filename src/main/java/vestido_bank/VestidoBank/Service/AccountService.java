package vestido_bank.VestidoBank.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.security.auth.login.AccountNotFoundException;
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
import vestido_bank.VestidoBank.Repository.ContaCorrenteRepository;

@Service
public class AccountService {

  AccountRepository accountRepository;
  ClientRepository clientRepository;
  ContaCorrenteRepository contaCorrenteRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository, ClientRepository clientRepository,
      ContaCorrenteRepository contaCorrenteRepository) {
    this.accountRepository = accountRepository;
    this.clientRepository = clientRepository;
    this.contaCorrenteRepository = contaCorrenteRepository;
  }

  public ContaCorrente criarContaCorrent(Long clientId,
      ContaCorrenteCreateDto contaCorrenteCreateDto) {
    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!"));

    ContaCorrente contaCorrente = contaCorrenteCreateDto.toEntity(client);
    contaCorrente.setClient(client);
    return contaCorrenteRepository.save(contaCorrente);

  }


  public List<ContaCorrente> getAllContasCorrentes() {
    return contaCorrenteRepository.findAll();
  }

  public Account getAccountById(Long clientId, Long accountId) throws AccountNotFoundException {
    // Verifica se o cliente existe
    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado"));

    // Verifica se a conta existe
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada"));

    if (account.getClient() == null) {
      throw new IllegalArgumentException("A conta não possui um cliente");
    }

    // Verifica se a conta pertence ao cliente
    if (!account.getClient().getId().equals(clientId)) {
      throw new IllegalArgumentException("A conta não pertence ao cliente informado");
    }

    return account;
  }


}
