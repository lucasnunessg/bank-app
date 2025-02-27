package vestido_bank.VestidoBank.Controller.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.CreditCard;

public record CreditCardCreateDto(BigDecimal limite, BigDecimal faturaAtual,
                                  LocalDate dataVencimento, String status) {

  public CreditCard toEntity(Client client) {
    return new CreditCard(limite, faturaAtual, dataVencimento, status, client);
  }
}
