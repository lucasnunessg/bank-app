package vestido_bank.VestidoBank.Service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

import java.util.HashMap;
import java.util.Map;


@Service
public class CognitoAuthService {

  private final CognitoIdentityProviderClient cognitoClient;

  private final String clientId = "6pmsvi95k18spdutge3ilt1jlu";
  private final String clientSecret = "r6omjrbktp3pm4g6fbj9542ljdip5up81c63ktgml1hublud3su";
  private final String region = "sa-east-1";

  public CognitoAuthService() {
    this.cognitoClient = CognitoIdentityProviderClient.builder()
        .region(Region.of(region))
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build();
  }

  public String authenticate(String email, String password) {
    String secretHash = calculateSecretHash(clientId, clientSecret, email);

    // Cria os parâmetros para autenticação
    Map<String, String> authParams = new HashMap<>();
    authParams.put("USERNAME", email);
    authParams.put("PASSWORD", password);
    authParams.put("SECRET_HASH", secretHash);

    // Constrói a requisição para autenticação
    InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
        .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
        .authParameters(authParams)
        .clientId(clientId)
        .build();

    InitiateAuthResponse response = cognitoClient.initiateAuth(authRequest);

    return response.authenticationResult().idToken();
  }

  private String calculateSecretHash(String clientId, String clientSecret, String username) {
    try {
      String message = username + clientId;
      javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
      mac.init(new javax.crypto.spec.SecretKeySpec(clientSecret.getBytes(), "HmacSHA256"));
      byte[] hmac = mac.doFinal(message.getBytes());
      return java.util.Base64.getEncoder().encodeToString(hmac);
    } catch (Exception e) {
      throw new RuntimeException("Failed to calculate secret hash", e);
    }
  }
}