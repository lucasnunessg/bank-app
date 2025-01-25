package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.Transaction;

public record TransactionDto(
    Long contaDestinoId,
    String nomeDonoDestino,
    Long contaOrigemId,
    String nomeDonoOrigem,
    float valor,
    LocalDateTime dataHora,
    String descricao,
    float saldoRestante
) {

  public static TransactionDto fromEntity(Transaction transaction) {
    return new TransactionDto(
        // Conta Destino
        transaction.getContaCorrenteDestino() != null
            ? transaction.getContaCorrenteDestino().getId()
            : (transaction.getContaPoupancaDestino() != null
                ? transaction.getContaPoupancaDestino().getId()
                : null),
        transaction.getContaCorrenteDestino() != null && transaction.getContaCorrenteDestino().getClient() != null
            ? transaction.getContaCorrenteDestino().getClient().getName()
            : (transaction.getContaPoupancaDestino() != null && transaction.getContaPoupancaDestino().getClient() != null
                ? transaction.getContaPoupancaDestino().getClient().getName()
                : null),
        // Conta Origem
        transaction.getContaCorrenteOrigem() != null
            ? transaction.getContaCorrenteOrigem().getId()
            : (transaction.getContaPoupancaOrigem() != null
                ? transaction.getContaPoupancaOrigem().getId()
                : null),
        transaction.getContaCorrenteOrigem() != null && transaction.getContaCorrenteOrigem().getClient() != null
            ? transaction.getContaCorrenteOrigem().getClient().getName()
            : (transaction.getContaPoupancaOrigem() != null && transaction.getContaPoupancaOrigem().getClient() != null
                ? transaction.getContaPoupancaOrigem().getClient().getName()
                : null),
        // Outros campos
        transaction.getValor(),
        transaction.getData_hora() != null ? transaction.getData_hora() : LocalDateTime.now(),
        transaction.getDescricao(),

        transaction.getContaCorrenteOrigem() != null
            ? transaction.getContaCorrenteOrigem().getSaldo()
            : (transaction.getContaPoupancaOrigem() != null
                ? transaction.getContaPoupancaOrigem().getSaldo()
                : 0.0f)

    );
  }
}
