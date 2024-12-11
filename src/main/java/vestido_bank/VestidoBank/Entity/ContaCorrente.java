package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class ContaCorrente {

  @Id
  @GeneratedValue
  private Long id;
  private float saldo;
  private float limite;
  private LocalDateTime data_criacao;

  @OneToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public ContaCorrente(float saldo, float limite, LocalDateTime data_criacao) {
    this.saldo = saldo;
    this.limite = limite;
    this.data_criacao = data_criacao;
  }

  public ContaCorrente(){}

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

  public float getLimite() {
    return limite;
  }

  public void setLimite(float limite) {
    this.limite = limite;
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
