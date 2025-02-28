package vestido_bank.VestidoBank.Controller.Dto;

import java.math.BigDecimal;

public record PagamentoFaturaResponse(
    Long cartaoDeCreditoId,
    BigDecimal faturaAtual,
    BigDecimal saldoContaPoupanca,
    String mensagem
) {
}