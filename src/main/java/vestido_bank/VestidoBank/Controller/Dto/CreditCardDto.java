package vestido_bank.VestidoBank.Controller.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import vestido_bank.VestidoBank.Entity.CreditCard;

public record CreditCardDto(Long id, BigDecimal limite, BigDecimal faturaAtual,
                            LocalDate dataVencimento, String status, ClientDto clientDto) {

  public static CreditCardDto fromEntity(CreditCard creditCard) {
    ClientDto clientDto1 =
        creditCard.getClient() != null ? ClientDto.fromEntity(creditCard.getClient()) : null;
    return new CreditCardDto(
        creditCard.getId(),
        creditCard.getLimite(),
        creditCard.getFaturaAtual(),
        creditCard.getDataVencimento(),
        creditCard.getStatus(),
        clientDto1
    );
  }
}
