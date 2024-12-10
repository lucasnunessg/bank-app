package vestido_bank.VestidoBank.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client {

  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String cpf;
  private String contact;
  private String address;
  private String email;
  private String password;

  public Client(String name, String cpf, String contact, String address, String email, String password) {
    this.name = name;
    this.cpf = cpf;
    this.contact = contact;
    this.address = address;
    this.email = email;
    this.password = password;
  }
  public Client(){}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
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
}
