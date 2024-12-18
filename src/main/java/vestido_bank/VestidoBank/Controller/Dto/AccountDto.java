package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.Account;

public record AccountDto(float saldo, LocalDateTime dataCriacao, ClientDto clientDto) {
  public static AccountDto fromEntity(Account account) {
    return new AccountDto(
        account.getSaldo(),
        account.getData_criacao(),
        ClientDto.fromEntity(account.getClient())
    );
  }
}
