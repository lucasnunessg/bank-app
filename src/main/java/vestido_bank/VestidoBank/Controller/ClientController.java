package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import vestido_bank.VestidoBank.Controller.Dto.ClientAndCorrentDto;
import vestido_bank.VestidoBank.Controller.Dto.ClientDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.CreateClientDto;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientDuplicateException;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.EmailDuplicateException;
import vestido_bank.VestidoBank.Exceptions.NameOrEmailDuplicateException;
import vestido_bank.VestidoBank.Service.ClientService;
import java.util.List;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;

@RestController
@RequestMapping("/clients-bank")
public class ClientController {

  ClientService clientService;
  ContaCorrenteService contaCorrenteService;

  @Autowired
  public ClientController(ClientService clientService, ContaCorrenteService contaCorrenteService) {
    this.clientService = clientService;
    this.contaCorrenteService = contaCorrenteService;
  }

  @GetMapping
  public List<ClientDto> getAllClients() {
    List<Client> clients = clientService.getAllClients();
    return clients.stream().map(ClientDto::fromEntity)
        .toList();
  }

  @GetMapping("/{id}")
  public ClientDto getClientByid(@PathVariable Long id) {
    return ClientDto.fromEntity(clientService.getById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ClientDto createClient(@RequestBody CreateClientDto createClientDto)
      throws ClientNotFoundException, NameOrEmailDuplicateException {
    return ClientDto.fromEntity(clientService.createClient(createClientDto.toEntity()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteById(@PathVariable Long id) throws ClientNotFoundException {
    clientService.deleteById(id);

    return ResponseEntity.ok("Usuário deletado com sucesso!");
  }

  @PutMapping("/{id}")
  public ClientDto updateClient(@PathVariable Long id,
      @RequestBody CreateClientDto createClientDto) {
    return ClientDto.fromEntity(clientService.updateClient(id, createClientDto.toEntity()));
  }


}
