package vestido_bank.VestidoBank.Controller.Dto;

import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Entity.Transaction;

public record TransactionDto(
    String nomeCliente,
    Long contaDestinoId,
    Long contaOrigemId,
    float valor,
    LocalDateTime dataHora,
    String descricao
) {
  public static TransactionDto fromEntity(Transaction transaction) {
    return new TransactionDto(
        transaction.getClient() != null ? transaction.getClient().getName() : null,
        transaction.getContaCorrenteDestino() != null ? transaction.getContaCorrenteDestino().getId() : null,
        transaction.getContaCorrenteOrigem() != null ? transaction.getContaCorrenteOrigem().getId() : null,
        transaction.getValor(),
        transaction.getData_hora() != null ? transaction.getData_hora() : LocalDateTime.now(),
        transaction.getDescricao()
    );
  }
}
