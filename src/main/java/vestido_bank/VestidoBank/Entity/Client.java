package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "client")
public class Client implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String cpf;
  private String contact;
  private String address;
  private String email;
  private String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
  private List<ContaCorrente> contaCorrente;

  @OneToMany(mappedBy = "client")
  private List<Transaction> transactions;


  @OneToMany(mappedBy = "client")
  private List<ContaPoupanca> contaPoupanca;


  public Client(String name, String cpf, String contact, String address, String email,
      String password) {
    this.name = name;
    this.cpf = cpf;
    this.contact = contact;
    this.address = address;
    this.email = email;
    this.password = password;
  }

  public Client() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return "";
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public List<ContaCorrente> getContaCorrente() {
    return contaCorrente;
  }

  public void setContaCorrente(List<ContaCorrente> contaCorrente) {
    this.contaCorrente = contaCorrente;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<ContaPoupanca> getContaPoupanca() {
    return contaPoupanca;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public void setContaPoupanca(List<ContaPoupanca> contaPoupanca) {
    this.contaPoupanca = contaPoupanca;


  }
}
