package vestido_bank.VestidoBank.Controller.Dto;

import vestido_bank.VestidoBank.Entity.Client;

public record ClientDto(String name, String contact) {

  public static ClientDto fromEntity(Client client) {
    return new ClientDto(
        client.getName(),
        client.getContact()
    );
  }
}
