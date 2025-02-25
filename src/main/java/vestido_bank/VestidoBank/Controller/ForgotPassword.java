package vestido_bank.VestidoBank.Controller;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.EmailDto;
import vestido_bank.VestidoBank.Controller.Dto.UserNameDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.SendEmailRecoveryPassword;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPassword {

  SendEmailRecoveryPassword sendEmailRecoveryPassword;
  ClientService clientService;

  @Autowired
  public ForgotPassword(SendEmailRecoveryPassword sendEmailRecoveryPassword,
      ClientService clientService) {
    this.sendEmailRecoveryPassword = sendEmailRecoveryPassword;
    this.clientService = clientService;
  }

  @PostMapping
  public ResponseEntity<String> forgotPassword(@RequestBody @Valid EmailDto emailDto) {
    String email = emailDto.email();
    Client clientOptional = clientService.findByEmail(email);
    if (clientOptional == null) {
      return ResponseEntity.badRequest().body("Não encontrado");
    }

    Client client = clientOptional;

    String token = UUID.randomUUID().toString();

    clientService.savedResetToken(client.getId(), token);

    String resetLink = "http://localhost:5173/reset-password?token=" + token;

    String subject = "Redefinição de senha";
    String message = "Olá, " + client.getName() + ",\n\n" +
        "Clique no link abaixo para redefinir sua senha:\n" + resetLink + "\n\n" +
        "Se você não solicitou esta ação, por favor ignore este e-mail.";

    sendEmailRecoveryPassword.sendEmail(client.getEmail(), subject, message);

    return ResponseEntity.ok(
        "Link para redefinição de senha enviado com sucesso para o e-mail registrado.");
  }


}


