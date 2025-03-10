package vestido_bank.VestidoBank.Controller.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionWithCreditCardDto(
    Long id,
    Long creditCardId,
    BigDecimal valor,

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDateTime dataHora,
    String descricao,
    BigDecimal faturaAtual
) {

}