package vestido_bank.VestidoBank.Controller;

import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vestido_bank.VestidoBank.Controller.Dto.ClientDto;
import vestido_bank.VestidoBank.Controller.Dto.CreateClientDto;
import vestido_bank.VestidoBank.Controller.Dto.UpdatePasswordDto;
import vestido_bank.VestidoBank.Entity.Client;

import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;

import vestido_bank.VestidoBank.Exceptions.InvalidPassword;
import vestido_bank.VestidoBank.Exceptions.NameOrEmailDuplicateException;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Service.ClientService;
import java.util.List;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;

@RestController
@RequestMapping("/clients-bank")
public class ClientController {

  ClientService clientService;
  ContaCorrenteService contaCorrenteService;
  ClientRepository clientRepository;

  @Autowired
  public ClientController(ClientService clientService, ContaCorrenteService contaCorrenteService,
      ClientRepository clientRepository) {
    this.clientService = clientService;
    this.contaCorrenteService = contaCorrenteService;
    this.clientRepository = clientRepository;
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
  public ClientDto createClient(@RequestBody @Valid CreateClientDto createClientDto)
      throws ClientNotFoundException, NameOrEmailDuplicateException, InvalidPassword {
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

  @PostMapping("/{id}/update-password")
  public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody
  UpdatePasswordDto updatePasswordDto) {
    clientService.updatePassword(id, updatePasswordDto.newPassword());
    return ResponseEntity.ok("Senha alterada com sucesso.");
  }

  @PostMapping("/{id}/upload-photo")
  public ResponseEntity<String> uploadClientPhoto(@PathVariable Long id, @RequestParam("file")
  MultipartFile file) throws IOException {
    Client client = clientService.getById(id);
    if (client == null) {
      throw new ClientNotFoundException("Não encontrado");
    }

    String uploadDir = "uploads/client-photos";

    File dir = new File(uploadDir);
    if (!dir.exists()) {
      dir.mkdirs();
    }

    String fileName = id + "-" + file.getOriginalFilename();
    Path path = Paths.get(uploadDir, fileName);

    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    String fotoUrl = "/uploas/client-photos/" + fileName;
    client.setImgUrl(fotoUrl);
    clientRepository.save(client);
    return ResponseEntity.ok("Foto carregada com sucesso!");
  }


}
