package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "conta_poupanca")
public class ContaPoupanca {

  @Id
  @GeneratedValue
  private Long id;
  private float saldo;
  private float rendimentoMensal;
  private LocalDateTime data_criacao;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public ContaPoupanca(float saldo, float rendimentoMensal, LocalDateTime data_criacao,
      Client client) {
    this.saldo = saldo;
    this.rendimentoMensal = rendimentoMensal;
    this.data_criacao = data_criacao;
    this.client = client;
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
}