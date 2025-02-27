package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class CreditCard {

  @Id
  @GeneratedValue
  private Long id;

  private BigDecimal limite;

  private BigDecimal faturaAtual;

  private LocalDate dataVencimento;

  private String status;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @OneToMany(mappedBy = "creditCardOrigem")
  private List<Transaction> creditCardOrigem;

  @OneToMany(mappedBy = "creditCardDestino")
  private List<Transaction> creditCardDestino;


  public CreditCard(BigDecimal limite, BigDecimal faturaAtual, LocalDate dataVencimento,
      String status, Client client) {
    this.limite = limite;
    this.faturaAtual = faturaAtual;
    this.dataVencimento = dataVencimento;
    this.status = status;
    this.client = client;
  }

  public CreditCard() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getLimite() {
    return limite;
  }

  public void setLimite(BigDecimal limite) {
    this.limite = limite;
  }

  public BigDecimal getFaturaAtual() {
    return faturaAtual;
  }

  public void setFaturaAtual(BigDecimal faturaAtual) {
    this.faturaAtual = faturaAtual;
  }

  public LocalDate getDataVencimento() {
    return dataVencimento;
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<Transaction> getCreditCardOrigem() {
    return creditCardOrigem;
  }

  public void setCreditCardOrigem(
      List<Transaction> creditCardOrigem) {
    this.creditCardOrigem = creditCardOrigem;
  }

  public List<Transaction> getCreditCardDestino() {
    return creditCardDestino;
  }

  public void setCreditCardDestino(
      List<Transaction> creditCardDestino) {
    this.creditCardDestino = creditCardDestino;
  }
}
