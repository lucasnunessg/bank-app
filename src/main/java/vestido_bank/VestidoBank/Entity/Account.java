package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private float saldo;
  private LocalDateTime data_criacao;

  public Account(float saldo, LocalDateTime data_criacao) {
    this.saldo = saldo;
    this.data_criacao = data_criacao;

  }

  public Account() {
    this.saldo = 0;
    this.data_criacao = LocalDateTime.now();
  }

  public void depositar(float valor) {
    if (valor < 0) {
      throw new IllegalArgumentException("O valor do depósito está vazio");
    }
    this.saldo += valor;
  }

  public void sacar(float valor) {
    if (valor <= 0 || valor > this.saldo) {
      throw new IllegalArgumentException("Saldo insuficiente");
    }
    this.saldo -= valor;
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

  public LocalDateTime getData_criacao() {
    return data_criacao;
  }

  public void setData_criacao(LocalDateTime data_criacao) {
    this.data_criacao = data_criacao;
  }
}
