package vestido_bank.VestidoBank.Controller;

import java.util.List;
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
import vestido_bank.VestidoBank.Controller.Dto.ClientAndCorrentDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteDto;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Controller.Dto.ResponseAccountSaldo;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.DepositInvalid;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;

@RestController
@RequestMapping("/conta-corrente")
public class ContaCorrenteController {

  ContaCorrenteService contaCorrenteService;
  ClientService clientService;

  @Autowired
  public ContaCorrenteController(ContaCorrenteService contaCorrenteService,
      ClientService clientService) {
    this.contaCorrenteService = contaCorrenteService;
    this.clientService = clientService;
  }

  @GetMapping
  public List<ContaCorrenteDto> getAllAccounts() {
    List<ContaCorrente> conta = contaCorrenteService.getAllContasCorrentes();
    return conta.stream().map(ContaCorrenteDto::fromEntity).toList();
  }

  @GetMapping("/{clientId}/saldo")
  public ResponseAccountSaldo getSaldoAccount(@PathVariable Long clientId) {
    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Não encontrado!");
    }
    ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteById(clientId);
    if (contaCorrente == null) {
      throw new ContaPoupancaNotFoundException("Não encontrada!");
    }

    return new ResponseAccountSaldo(contaCorrente.getSaldo());
  }

  @PostMapping("/{clientId}/create-account")
  @ResponseStatus(HttpStatus.CREATED)
  public ContaCorrenteDto criandoConta(
      @PathVariable Long clientId,
      @RequestBody ContaCorrenteCreateDto contaCorrenteCreateDto) {

    Client client = clientService.getById(clientId);

    ContaCorrente novaConta = contaCorrenteCreateDto.toEntity(client);

    novaConta = contaCorrenteService.criarContaCorrente(novaConta);

    return ContaCorrenteDto.fromEntity(novaConta);
  }

  @PostMapping("/{contaCorrenteId}/client/{clientId}/depositar")
  @ResponseStatus(HttpStatus.CREATED)
  public ClientAndCorrentDto depositar(@PathVariable Long clientId,
      @PathVariable Long contaCorrenteId, @RequestBody DepositAndSakeDto deposit)
      throws DepositInvalid {

    float valor = deposit.valor();

    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Não encontrado");
    }
    ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteById(contaCorrenteId);
    if (contaCorrente == null) {
      throw new ContaCorrentNotFoundException("Não encontrada");
    }

    contaCorrente.depositar(valor);
    contaCorrenteService.salvar(contaCorrente);

    return ClientAndCorrentDto.fromEntities(client, contaCorrente);
  }

  @PutMapping("/{contaCorrenteId}/client/{clientId}/sacar")

  public ClientAndCorrentDto sacar(@PathVariable Long clientId, @PathVariable Long contaCorrenteId,
      @RequestBody DepositAndSakeDto sake) {
    Client client = clientService.getById(clientId);

    float valor = sake.valor();

    if (client == null) {
      throw new ClientNotFoundException("Não encontrado!");
    }

    ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteById(contaCorrenteId);
    if (contaCorrente == null) {
      throw new ContaCorrentNotFoundException("Não encontrado!");
    }

    contaCorrente.sacar(valor);
    contaCorrenteService.salvar(contaCorrente);
    return ClientAndCorrentDto.fromEntities(client, contaCorrente);
  }


}
