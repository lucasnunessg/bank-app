package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.AccountDto;
import vestido_bank.VestidoBank.Controller.Dto.ClientAndCorrentDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteDto;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Entity.Account;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Service.AccountService;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;
import java.util.List;

@RestController
@RequestMapping("/clients-bank/accounts")
public class AccountController {

  ClientService clientService;
  ContaCorrenteService contaCorrenteService;
  AccountService accountService;

  @Autowired
  public AccountController(ClientService clientService, ContaCorrenteService contaCorrenteService, AccountService accountService) {
    this.clientService = clientService;
    this.contaCorrenteService = contaCorrenteService;
    this.accountService = accountService;
  }

  @PostMapping("/{clientId}/conta-corrente")
  @ResponseStatus(HttpStatus.CREATED)
  public ContaCorrenteDto criandoConta(
      @PathVariable Long clientId,
      @RequestBody ContaCorrenteCreateDto contaCorrenteCreateDto) {

    Client client = clientService.getById(clientId);

    // Criar a nova conta a partir do DTO
    ContaCorrente novaConta = contaCorrenteCreateDto.toEntity(client);

    // Chamar o serviço para salvar a conta
    novaConta = contaCorrenteService.criarContaCorrente(novaConta);

    // Retornar o DTO da conta criada
    return ContaCorrenteDto.fromEntity(novaConta);
  }

  @PostMapping("/{clientId}/conta-corrente/{contaCorrenteId}/depositar")
  @ResponseStatus(HttpStatus.CREATED)
  public ClientAndCorrentDto depositar(@PathVariable Long clientId, @PathVariable Long contaCorrenteId, @RequestBody DepositAndSakeDto deposit) {

    float valor = deposit.valor();

    Client client = clientService.getById(clientId);
    if(client == null) {
      throw new ClientNotFoundException("Não encontrado");
    }
    ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteById(contaCorrenteId);
    if(contaCorrente == null) {
      throw new ContaCorrentNotFoundException("Não encontrada");
    }

    contaCorrente.depositar(valor);
    contaCorrenteService.salvar(contaCorrente);

    return ClientAndCorrentDto.fromEntities(client, contaCorrente);
  }


  @PutMapping("/{clientId}/conta-corrente/{contaCorrenteId}/sacar")
  public ClientAndCorrentDto sacar(@PathVariable Long clientId, @PathVariable Long contaCorrenteId, @RequestBody DepositAndSakeDto sake) {
    Client client = clientService.getById(clientId);

    float valor = sake.valor();

    if(client == null) {
      throw new ClientNotFoundException("Não encontrado!");
    }

    ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteById(contaCorrenteId);
    if(contaCorrente == null) {
      throw new ContaCorrentNotFoundException("Não encontrado!");
    }

    contaCorrente.sacar(valor);
    contaCorrenteService.salvar(contaCorrente);
    return ClientAndCorrentDto.fromEntities(client, contaCorrente);
  }

  @GetMapping
  public List<AccountDto> getAllAcconts() throws ClientNotFoundException{
    List<Account> accounts = accountService.getAllContasCorrentes();
    return accounts.stream()
        .map(AccountDto::fromEntity)
        .toList();
  }


}
