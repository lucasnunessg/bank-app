package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public record ContaCorrenteDto(float saldo, float limite, LocalDateTime data_criacao, Client client) {
  public static ContaCorrenteDto fromEntity(ContaCorrente contaCorrente) {
    return new ContaCorrenteDto(
        contaCorrente.getSaldo(),
        contaCorrente.getLimite(),
        contaCorrente.getData_criacao(),
        contaCorrente.getClient()
    );
  }

}
