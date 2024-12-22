package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;

public record ContaPoupancaCreateDto(float saldo, float rendimentoMensal) {

  public ContaPoupanca toEntity(Client client) {
    return new ContaPoupanca(saldo, rendimentoMensal, LocalDateTime.now(), client);
  }

}
