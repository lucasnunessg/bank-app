package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;


@Entity
public class ContaCorrente extends Account {

  @Id
  @GeneratedValue
  private Long id;
  private float limite;

  @OneToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public ContaCorrente(float saldo, LocalDateTime dataCriacao, float limite, Client client) {
    super(saldo, dataCriacao);
    this.limite = limite;
    this.client = client;
  }

  public ContaCorrente() {
  }


  public float getLimite() {
    return limite;
  }

  public void setLimite(float limite) {
    this.limite = limite;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
