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
import vestido_bank.VestidoBank.Controller.Dto.ClientDto;
import vestido_bank.VestidoBank.Controller.Dto.TokenDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Repository.ClientRepository;
import vestido_bank.VestidoBank.Service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }


  @PostMapping("/login")
  public TokenDto loginAuth(@RequestBody AuthDto authDto) {
    UsernamePasswordAuthenticationToken emailPassword =
        new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());

    Authentication auth = authenticationManager.authenticate(emailPassword);

    Client client = (Client) auth.getPrincipal();
    Long clientId = client.getId();
    String name = client.getName();

    String token = tokenService.generateToken(auth.getName(), clientId, name);

    return new TokenDto(token);

  }
}
