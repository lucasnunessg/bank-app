package vestido_bank.VestidoBank.Controller;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.ContaPoupancaCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaPoupancaDto;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Controller.Dto.ResponseAccountSaldo;
import vestido_bank.VestidoBank.Controller.Dto.TransactionDto;
import vestido_bank.VestidoBank.Controller.Dto.TransferDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;
import vestido_bank.VestidoBank.Service.ContaPoupancaService;
import vestido_bank.VestidoBank.Service.TransactionService;

@RestController
@RequestMapping("/conta-poupanca")
public class ContaPoupancaController {

  ContaPoupancaService contaPoupancaService;
  ClientService clientService;
  TransactionService transactionService;

  @Autowired
  public ContaPoupancaController(ContaPoupancaService contaPoupancaService,
      ClientService clientService, TransactionService transactionService) {
    this.contaPoupancaService = contaPoupancaService;
    this.clientService = clientService;
    this.transactionService = transactionService;
  }

  @GetMapping
  public List<ContaPoupancaDto> getAllContasPoupancas() {
    List<ContaPoupanca> contas = contaPoupancaService.getAllPoupancas();
    return contas.stream().map(ContaPoupancaDto::fromEntity)
        .toList();
  }

  @GetMapping("/{clientId}/conta/{contaPoupancaId}")
  public ContaPoupancaDto getContaPoupancaById(@PathVariable Long clientId,
      @PathVariable Long contaPoupancaId) {

    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Nao encontrado!");
    }

    ContaPoupanca poupanca = contaPoupancaService.getPoupancaById(contaPoupancaId);
    if (poupanca == null) {
      throw new ContaPoupancaNotFoundException("Não encontrado!");
    }

    return ContaPoupancaDto.fromEntity(poupanca);
  }

  @PostMapping("/{clientId}/create-poupanca")
  @ResponseStatus(HttpStatus.CREATED)
  public ContaPoupancaDto createContaPoupanca(@PathVariable Long clientId,
      @RequestBody ContaPoupancaCreateDto contaPoupancaCreateDto) {
    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Não encontrado");
    }

    ContaPoupanca newConta = contaPoupancaCreateDto.toEntity(client);

    newConta = contaPoupancaService.createContaPoupanca(clientId, newConta);
    return ContaPoupancaDto.fromEntity(newConta);
  }


  @GetMapping("/{clientId}/saldo")
  public ResponseAccountSaldo getSaldoAccount(@PathVariable Long clientId) {
    Client client = clientService.getById(clientId);
        if(client == null) {
          throw new ClientNotFoundException("Não encontrado!");
        }
  ContaPoupanca contaPoupanca = contaPoupancaService.getPoupancaById(clientId);
        if(contaPoupanca == null) {
          throw new ContaPoupancaNotFoundException("Não encontrada!");
        }

        return new ResponseAccountSaldo(contaPoupanca.getSaldo());
  }

  @GetMapping("/{clientId}/transfers/send")
  public List<TransferDto> getTransfersSendAccount(@PathVariable Long clientId) {
    Client client = clientService.getById(clientId);
    if(client == null) {
      throw new ClientNotFoundException("Não encontrado");
    }


    List<Transaction> transaction = transactionService.getAllTransactions();

    return transaction.stream().map(TransferDto::fromTransaction).toList();
      }

  @PostMapping("/{contaPoupancaId}/client/{clientId}/deposit")
  public ResponseEntity<ContaPoupancaDto> deposito(@PathVariable Long contaPoupancaId,
      @PathVariable Long clientId, @RequestBody
  DepositAndSakeDto depositAndSakeDto) {
    float valor = depositAndSakeDto.valor();

    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Não foi possível encontrar cliente!");
    }

    ContaPoupanca contaPoupanca = contaPoupancaService.getPoupancaById(contaPoupancaId);
    if (contaPoupanca == null) {
      throw new ContaPoupancaNotFoundException("Não foi possível encontrar conta poupança!");
    }

    contaPoupanca.depositar(valor);
    contaPoupancaService.salvar(contaPoupanca);
    ContaPoupancaDto contaDto = ContaPoupancaDto.fromEntity(contaPoupanca);
    return ResponseEntity.ok().body(contaDto);
  }

  @PutMapping("/{contaPoupancaId}/client/{clientId}/sake")
  public ResponseEntity<ContaPoupancaDto> saque(@PathVariable Long contaPoupancaId,
      @PathVariable Long clientId, @RequestBody DepositAndSakeDto depositAndSakeDto) {

    float valor = depositAndSakeDto.valor();

    Client client = clientService.getById(clientId);
    if (client == null) {
      throw new ClientNotFoundException("Não encontrado!");
    }

    ContaPoupanca contaPoupanca = contaPoupancaService.getPoupancaById(contaPoupancaId);
    if (contaPoupanca == null) {
      throw new ContaPoupancaNotFoundException("Conta não encontrada");
    }

    contaPoupanca.saque(valor);
    contaPoupancaService.salvar(contaPoupanca);
    ContaPoupancaDto contaPoupancaDto = ContaPoupancaDto.fromEntity(contaPoupanca);
    return ResponseEntity.ok().body(contaPoupancaDto);
  }

}
