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

  public ContaCorrente(float saldo, LocalDateTime dataCriacao, float limite) {
    super(saldo, dataCriacao);
    this.limite = limite;
  }

  public ContaCorrente() {
    super(0.0f, LocalDateTime.now());
    this.limite = 10000;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
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
