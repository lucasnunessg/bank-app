package vestido_bank.VestidoBank.Controller.Dto;

import vestido_bank.VestidoBank.Entity.Client;

public record ClientDto(Long id, String name, String contact, String address, String email) {

  public static ClientDto fromEntity(Client client) {
    return new ClientDto(
        client.getId(),
        client.getName(),
        client.getContact(),
        client.getAddress(),
        client.getEmail()
    );
  }
}
