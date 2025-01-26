package vestido_bank.VestidoBank.Controller.Dto;

public record TransactionResponseDto(
    Long id,
    float valor,
    String descricao,
    float saldoAtualizado
) {

}