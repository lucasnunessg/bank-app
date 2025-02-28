package vestido_bank.VestidoBank.Controller.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionWithCreditCardDto(
    Long id,
    Long creditCardId,
    BigDecimal valor,
    LocalDateTime dataHora,
    String descricao,
    BigDecimal faturaAtual
) {
}