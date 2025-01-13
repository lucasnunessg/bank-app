package middlewares;

import org.springframework.stereotype.Component;
import vestido_bank.VestidoBank.Exceptions.InvalidPassword;

@Component
public class RegexPassword {

  public void validatePassword(String password) {
    String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

    if(password == null) {
      throw new InvalidPassword("Erro");
    }
    if(!password.matches(regex)) {
      throw new InvalidPassword("Erro");
    }
  }
}
