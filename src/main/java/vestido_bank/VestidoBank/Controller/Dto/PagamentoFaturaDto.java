package vestido_bank.VestidoBank.Controller.Dto;

import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Entity.CreditCard;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoFaturaDto(
    Long clientId,
    Long contaPoupancaId,
    Long cartaoDeCreditoId,
    float valor,
    String descricao
) {

  public Transaction toEntity(Client client, ContaPoupanca contaPoupanca, CreditCard creditCard) {
    return new Transaction(
        client,
        null,
        null,
        creditCard,
        null,
        null,
        contaPoupanca,
        this.valor,
        LocalDateTime.now(),
        this.descricao,
        contaPoupanca.getSaldo()

    );
  }
}