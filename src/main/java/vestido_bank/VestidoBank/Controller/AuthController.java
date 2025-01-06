package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.AuthDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Service.CognitoAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final CognitoAuthService cognitoAuthService;

  @Autowired
  public AuthController(CognitoAuthService cognitoAuthService) {
    this.cognitoAuthService = cognitoAuthService;
  }

  @PostMapping("/login")
  public String login(@RequestBody AuthDto authDto) {
    // Autentica o usuário no Cognito e retorna o token JWT
    String jwt = cognitoAuthService.authenticate(authDto.email(), authDto.password());
    return "Seja muito bem vindo(a) " + authDto.email() + " " + jwt;
  }
}