package vestido_bank.VestidoBank.Controller.Dto;

import vestido_bank.VestidoBank.Entity.Transaction;

public record TransferDto(
    Long contaDestinoId,
    String nomeDonoDestino,
    Long contaOrigemId,
    String nomeDonoOrigem,
    float valor,
    String descricao
) {
  public static TransferDto fromTransaction(Transaction transaction) {
    return new TransferDto(
        // Conta Destino
        transaction.getContaPoupancaDestino() != null
            ? transaction.getContaPoupancaDestino().getId()
            : (transaction.getContaCorrenteDestino() != null
                ? transaction.getContaCorrenteDestino().getId()
                : null),
        transaction.getContaPoupancaDestino() != null
            ? transaction.getContaPoupancaDestino().getClient().getName()
            : (transaction.getContaCorrenteDestino() != null
                ? transaction.getContaCorrenteDestino().getClient().getName()
                : null),
        // Conta Origem
        transaction.getContaPoupancaOrigem() != null
            ? transaction.getContaPoupancaOrigem().getId()
            : (transaction.getContaCorrenteOrigem() != null
                ? transaction.getContaCorrenteOrigem().getId()
                : null),
        transaction.getContaPoupancaOrigem() != null
            ? transaction.getContaPoupancaOrigem().getClient().getName()
            : (transaction.getContaCorrenteOrigem() != null
                ? transaction.getContaCorrenteOrigem().getClient().getName()
                : null),
        // Outros campos
        transaction.getValor(),
        transaction.getDescricao()
    );
  }
}
