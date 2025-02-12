package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.ResetPasswordDto;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Service.ClientService;

@RestController
@RequestMapping("/reset-password")
public class ResetPassword {

  ClientService clientService;

  @Autowired
  public ResetPassword(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public ResponseEntity<String> recoveryPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
    String token = resetPasswordDto.token();
    String newPassword = resetPasswordDto.password();

    try {
      Client client = clientService.validateResetToken(token);
      String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);

      client.setPassword(hashedPassword);
      client.setResetToken(null);
      client.setTokenExpirity(null);
      clientService.updateClient(client.getId(), client);
      return ResponseEntity.ok("Senha alterada com sucesso");

    }catch(RuntimeException e) {
      return ResponseEntity.badRequest().body("Erro ao alterar senha");
    }
  }

}
