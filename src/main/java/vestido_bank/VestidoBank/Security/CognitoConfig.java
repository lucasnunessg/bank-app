package vestido_bank.VestidoBank.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class CognitoConfig {

  @Bean
  public CognitoIdentityProviderClient cognitoIdentityProviderClient() {
    return CognitoIdentityProviderClient.builder().build();
  }
}
