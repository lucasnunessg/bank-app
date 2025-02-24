package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import vestido_bank.VestidoBank.Exceptions.DepositInvalid;
import vestido_bank.VestidoBank.Exceptions.SakeInvalid;

@Entity
@Table(name = "conta_poupanca")
public class ContaPoupanca {

  @Id
  @GeneratedValue
  private Long id;
  private float saldo;
  private float rendimentoMensal;
  private LocalDateTime data_criacao;
  private LocalDateTime dataUltimaAtt;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public ContaPoupanca(float saldo, float rendimentoMensal, LocalDateTime data_criacao,
      Client client) {
    this.saldo = saldo;
    this.rendimentoMensal = rendimentoMensal;
    this.data_criacao = LocalDateTime.now();
    this.client = client;
    this.dataUltimaAtt = LocalDateTime.now();
  }

  public ContaPoupanca() {

  }

  public float aplicarRendimento() {
    LocalDateTime agora = LocalDateTime.now();

    if (this.dataUltimaAtt == null) {
      throw new IllegalStateException("Data da última atualização não pode ser nula.");
    }

    // Calcula o número de dias desde a última atualização
    long diasUltimaAtualizacao = Duration.between(this.dataUltimaAtt, agora).toDays();

    // Converte os valores para BigDecimal
    BigDecimal saldoAtual = BigDecimal.valueOf(this.saldo);
    BigDecimal rendimentoMensal = BigDecimal.valueOf(this.rendimentoMensal);
    BigDecimal diasNoMes = BigDecimal.valueOf(30);

    BigDecimal rendimentoDiario = BigDecimal.valueOf(Math.pow(1 + rendimentoMensal.doubleValue(), 1.0 / 30) - 1);

    // Calcula o rendimento total proporcional aos dias
    BigDecimal rendimentoTotal = saldoAtual.multiply(rendimentoDiario)
        .multiply(BigDecimal.valueOf(diasUltimaAtualizacao));

    // Atualiza o saldo
    this.saldo = saldoAtual.add(rendimentoTotal).floatValue();

    this.dataUltimaAtt = agora;

    return this.saldo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public float getSaldo() {
    return saldo;
  }

  public void setSaldo(float saldo) {
    this.saldo = saldo;
  }

  public float getRendimentoMensal() {
    return rendimentoMensal;
  }

  public void setRendimentoMensal(float rendimentoMensal) {
    this.rendimentoMensal = rendimentoMensal;
  }

  public LocalDateTime getDataUltimaAtt() {
    return dataUltimaAtt;
  }

  public void setDataUltimaAtt(LocalDateTime dataUltimaAtt) {
    this.dataUltimaAtt = dataUltimaAtt;
  }

  public LocalDateTime getData_criacao() {
    return data_criacao;
  }

  public void setData_criacao(LocalDateTime data_criacao) {
    this.data_criacao = data_criacao;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void depositar(float valor) {
    if (valor < 0) {
      throw new DepositInvalid("Valor de depósito não pode ser 0");
    }
    this.saldo += valor;
  }

  public void saque(float valor) {
    if (valor > this.saldo || valor <= 0) {
      throw new SakeInvalid("Não permitido");
    }
    this.saldo -= valor;

  }


}
