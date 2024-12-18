package vestido_bank.VestidoBank.Entity;

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
public class ContaCorrente extends Account {

  @Id
  @GeneratedValue
  private Long id;
  private float limite;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;

  public ContaCorrente(float saldo, float limite, LocalDateTime dataCriacao, Client client) {
    super(saldo, dataCriacao, client); //sempre q tiver uma classe mae, a filha deve receber todos os parametros da mae pelo "super".
    this.limite = limite;
    this.client = client;
  }

  public ContaCorrente() {
    super(); // Chamar o construtor padrão da classe base
  }


  public float getLimite() {
    return limite;
  }

  public void setLimite(float limite) {
    this.limite = limite;
  }


}
