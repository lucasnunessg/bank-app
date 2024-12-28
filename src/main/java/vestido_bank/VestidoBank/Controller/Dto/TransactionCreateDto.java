package vestido_bank.VestidoBank.Controller.Dto;

import vestido_bank.VestidoBank.Entity.Transaction;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;

public record TransactionCreateDto(
    Long contaPoupancaId,
    Long contaCorrenteId,
    float valor,
    String descricao
) {

  // Converte o DTO em uma entidade Transaction
  public Transaction toEntity(ContaPoupanca contaPoupancaOrigem,
      ContaCorrente contaCorrenteDestino) {
    Transaction transaction = new Transaction();
    transaction.setContaPoupancaOrigem(contaPoupancaOrigem);
    transaction.setContaCorrenteDestino(contaCorrenteDestino);
    transaction.setValor(this.valor);
    transaction.setDescricao(this.descricao);
    transaction.setData_hora(java.time.LocalDateTime.now()); // Define a data/hora atual
    return transaction;
  }
}