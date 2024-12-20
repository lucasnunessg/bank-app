package vestido_bank.VestidoBank.Controller;

import javax.security.auth.login.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.AccountDto;
import vestido_bank.VestidoBank.Controller.Dto.ClientAndCorrentDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteCreateDto;
import vestido_bank.VestidoBank.Controller.Dto.ContaCorrenteDto;
import vestido_bank.VestidoBank.Controller.Dto.DepositAndSakeDto;
import vestido_bank.VestidoBank.Entity.Account;
import vestido_bank.VestidoBank.Entity.Client;
import vestido_bank.VestidoBank.Entity.ContaCorrente;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Service.AccountService;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;
import java.util.List;

@RestController
@RequestMapping("/clients-bank/accounts")
public class AccountController {

  ClientService clientService;
  ContaCorrenteService contaCorrenteService;
  AccountService accountService;

  @Autowired
  public AccountController(ClientService clientService, ContaCorrenteService contaCorrenteService, AccountService accountService) {
    this.clientService = clientService;
    this.contaCorrenteService = contaCorrenteService;
    this.accountService = accountService;
  }










}
