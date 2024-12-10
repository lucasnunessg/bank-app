package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.ClientDto;
import vestido_bank.VestidoBank.Controller.Dto.CreateClientDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Service.ClientService;
import java.util.List;

@RestController
@RequestMapping("/clients-bank")
public class ClientController {

  ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
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

  @PostMapping("/{id}")
  public ClientDto createClient(@RequestBody CreateClientDto createClientDto)
      throws ClientNotFoundException {
    return ClientDto.fromEntity(clientService.createClient(createClientDto.toEntity()));
  }

}
