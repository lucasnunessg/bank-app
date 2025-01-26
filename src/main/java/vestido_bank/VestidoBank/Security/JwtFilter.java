package vestido_bank.VestidoBank.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.TokenService;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final ClientService clientService;

  @Autowired
  public JwtFilter(TokenService tokenService, ClientService clientService) {
    this.tokenService = tokenService;
    this.clientService = clientService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    Optional<String> token = extractToken(request);

    if (token.isPresent()) {
      String subject = tokenService.validateToken(token.get());
      UserDetails userDetails = clientService.loadUserByUsername(subject);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(
      HttpServletRequest request) { //o request é uma classe q representa uma requisição
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) {
      return Optional.empty();
    }

    return Optional.of(
        authHeader.replace("Bearer ", "")
    );
  }
}
