package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public record ContaCorrenteDto(Long id, float saldo, float limite, LocalDateTime data_criacao, ClientDto clientDto
                               ) {

  public static ContaCorrenteDto fromEntity(ContaCorrente contaCorrente) {

    ClientDto clientDto =
        contaCorrente.getClient() != null ? ClientDto.fromEntity(contaCorrente.getClient()) : null;
    return new ContaCorrenteDto(
        contaCorrente.getId(),
        contaCorrente.getSaldo(),
        contaCorrente.getLimite(),
        contaCorrente.getData_criacao(),
        clientDto

    );
  }
}
