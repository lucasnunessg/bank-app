package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.Account;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public record AccountDto(float saldo, LocalDateTime dataCriacao, ClientDto clientDto) {

  public static AccountDto fromEntity(Account account) {
    ClientDto clientDto =
        account.getClient() != null ? ClientDto.fromEntity(account.getClient()) : null;
    return new AccountDto(
        account.getSaldo(),
        account.getData_criacao(),
        clientDto
    );
  }

}
