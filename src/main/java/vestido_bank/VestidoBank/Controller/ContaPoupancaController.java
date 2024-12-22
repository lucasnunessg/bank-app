package vestido_bank.VestidoBank.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vestido_bank.VestidoBank.Controller.Dto.ContaPoupancaDto;
import vestido_bank.VestidoBank.Entity.ContaPoupanca;
import vestido_bank.VestidoBank.Service.ClientService;
import vestido_bank.VestidoBank.Service.ContaCorrenteService;
import vestido_bank.VestidoBank.Service.ContaPoupancaService;

@RestController
@RequestMapping("/conta-corrente")
public class ContaPoupancaController {

  ContaPoupancaService contaPoupancaService;
  ClientService clientService;

  @Autowired
  public ContaPoupancaController(ContaPoupancaService contaPoupancaService, ClientService clientService){
    this.contaPoupancaService = contaPoupancaService;
    this.clientService = clientService;
  }

  @GetMapping
  public List<ContaPoupancaDto> getAllPoupancas() {
    List<ContaPoupanca> contas = contaPoupancaService.getAllPoupancas();
       return contas.stream().map(ContaPoupancaDto::fromEntity)
        .toList();
  }

}
