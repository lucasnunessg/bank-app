package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.AuthDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public String login(@RequestBody AuthDto authDto) {
    UsernamePasswordAuthenticationToken emailPassword =
        new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());

    Authentication auth = authenticationManager.authenticate(emailPassword);

    return "Login realizado com sucesso. Bem vindo(a) %s".formatted(auth.getName());
  }

}
