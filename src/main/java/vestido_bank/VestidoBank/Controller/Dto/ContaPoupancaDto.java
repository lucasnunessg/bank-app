package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;

public record ContaPoupancaDto(float saldo, float rendimentoMensal, LocalDateTime data_criacao, ClientDto clientDto) {
public static ContaPoupancaDto fromEntity(ContaPoupanca contaPoupanca) {
  ClientDto clientDto =
  contaPoupanca.getClient() != null ? ClientDto.fromEntity(contaPoupanca.getClient()) : null;
  return new ContaPoupancaDto(
      contaPoupanca.getSaldo(),
      contaPoupanca.getRendimentoMensal(),
      contaPoupanca.getData_criacao(),
      clientDto
  );
}

}
