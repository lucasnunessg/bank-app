package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private float saldo;
  private LocalDateTime data_criacao;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public Account(float saldo, LocalDateTime data_criacao, Client client) {
    this.saldo = saldo;
    this.data_criacao = data_criacao != null ? data_criacao
        : LocalDateTime.now(); // Garantir que a data não seja nula
    this.client = client;

  }

  public Account() {

  }



  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
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
