package vestido_bank.VestidoBank.Controller.Dto;

import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public record ClientAndCorrentDto(ClientDto client, ContaCorrenteDto contaCorrente) {

  public static ClientAndCorrentDto fromEntities(Client client, ContaCorrente contaCorrente) {
    ClientDto clientDto = ClientDto.fromEntity(client);
    ContaCorrenteDto contaCorrenteDto = ContaCorrenteDto.fromEntity(contaCorrente);
    return new ClientAndCorrentDto(clientDto, contaCorrenteDto);
  }
}
