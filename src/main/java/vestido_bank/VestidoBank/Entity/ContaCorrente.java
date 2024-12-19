package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
public class ContaCorrente {

  @Id
  @GeneratedValue
  private Long id;
  private float saldo;
  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime data_criacao = LocalDateTime.now();
  private float limite;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;

  public ContaCorrente(float saldo, float limite, LocalDateTime dataCriacao, Client client) {
    this.saldo = saldo;
    this.limite = limite;
    this.data_criacao = LocalDateTime.now();
    this.client = client;
  }

  public ContaCorrente() {
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

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public float getLimite() {
    return limite;
  }

  public void setLimite(float limite) {
    this.limite = limite;
  }


}
