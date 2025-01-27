package vestido_bank.VestidoBank.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vestido_bank.VestidoBank.Entity.Client;

@Service
public class TokenService {

  private final Algorithm algorithm;
  private final ClientService clientService;


  public Client clientId(Long id) {
    return clientService.getById(id);
  }

  public TokenService(@Value("${api.security.token.secret}") String secret,
      ClientService clientService) {
    this.algorithm = Algorithm.HMAC256(secret);
    this.clientService = clientService;
  }

  public String generateToken(String email, Long clientId, String name) {
    return JWT.create()
        .withSubject(email)
        .withClaim("name", name)
        .withExpiresAt(generateExpiration())
        .withClaim("clientId", clientId)
        .sign(algorithm);
  }

  private Instant generateExpiration() {
    return Instant.now()
        .plus(2, ChronoUnit.HOURS);
  }

  public String validateToken(String token) {
    return JWT.require(algorithm)
        .build()
        .verify(token)
        .getSubject();
  }

}
