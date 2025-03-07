package vestido_bank.VestidoBank.Controller.Dto;

import java.math.BigDecimal;

public record BuyFaturaResponse(Long cartaoId,
                                BigDecimal faturaAtual,
                                String mensagem) {

}
