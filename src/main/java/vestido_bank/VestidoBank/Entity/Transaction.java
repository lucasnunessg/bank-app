package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "conta_corrente_origem_id")
  private ContaCorrente contaCorrenteOrigem;

  @ManyToOne
  @JoinColumn(name = "conta_corrente_destino_id")
  private ContaCorrente contaCorrenteDestino;

  @ManyToOne
  @JoinColumn(name = "conta_poupanca_destino_id")
  private ContaPoupanca contaPoupancaDestino;

  @ManyToOne
  @JoinColumn(name = "conta_poupanca_origem_id")
  private ContaPoupanca contaPoupancaOrigem;

  @ManyToOne
  @JoinColumn(name = "credit_card_origem_id")
  private CreditCard creditCardOrigem;

  @ManyToOne
  @JoinColumn(name = "credit_card_destino_id")
  private CreditCard creditCardDestino;

  private float valor;

  private LocalDateTime data_hora;

  private String descricao;

  private float saldoRestante;


  public Transaction() {
  }

  public Transaction(Client client, ContaCorrente contaCorrenteOrigem,
      ContaCorrente contaCorrenteDestino, CreditCard creditCardOrigem, CreditCard creditCardDestino,
      ContaPoupanca contaPoupancaDestino, ContaPoupanca contaPoupancaOrigem, float valor,
      LocalDateTime data_hora, String descricao, float saldoRestante) {
    this.client = client;
    this.contaCorrenteOrigem = contaCorrenteOrigem;
    this.contaCorrenteDestino = contaCorrenteDestino;
    this.contaPoupancaDestino = contaPoupancaDestino;
    this.contaPoupancaOrigem = contaPoupancaOrigem;
    this.valor = valor;
    this.data_hora = data_hora;
    this.descricao = descricao;
    this.creditCardOrigem = creditCardOrigem;
    this.creditCardDestino = creditCardDestino;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ContaCorrente getContaCorrenteOrigem() {
    return contaCorrenteOrigem;
  }

  public void setContaCorrenteOrigem(ContaCorrente contaCorrenteOrigem) {
    this.contaCorrenteOrigem = contaCorrenteOrigem;
  }

  public ContaCorrente getContaCorrenteDestino() {
    return contaCorrenteDestino;
  }

  public void setContaCorrenteDestino(ContaCorrente contaCorrenteDestino) {
    this.contaCorrenteDestino = contaCorrenteDestino;
  }

  public void setClient(Client client) {
    this.client = client;
  }


  public ContaPoupanca getContaPoupancaDestino() {
    return contaPoupancaDestino;
  }

  public void setContaPoupancaDestino(ContaPoupanca contaPoupancaDestino) {
    this.contaPoupancaDestino = contaPoupancaDestino;
  }

  public ContaPoupanca getContaPoupancaOrigem() {
    return contaPoupancaOrigem;
  }

  public void setContaPoupancaOrigem(ContaPoupanca contaPoupancaOrigem) {
    this.contaPoupancaOrigem = contaPoupancaOrigem;
  }

  public CreditCard getCreditCardOrigem() {
    return creditCardOrigem;
  }

  public void setCreditCardOrigem(CreditCard creditCardOrigem) {
    this.creditCardOrigem = creditCardOrigem;
  }

  public CreditCard getCreditCardDestino() {
    return creditCardDestino;
  }

  public void setCreditCardDestino(CreditCard creditCardDestino) {
    this.creditCardDestino = creditCardDestino;
  }

  public float getValor() {
    return valor;
  }

  public void setValor(float valor) {
    this.valor = valor;
  }

  public LocalDateTime getData_hora() {
    return data_hora;
  }

  public void setData_hora(LocalDateTime data_hora) {
    this.data_hora = data_hora;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Client getClient() {
    return client;
  }


  public float getSaldoRestante() {
    return saldoRestante;
  }

  public void setSaldoRestante(float saldoRestante) {
    this.saldoRestante = saldoRestante;
  }
}
