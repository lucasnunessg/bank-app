package vestido_bank.VestidoBank.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vestido_bank.VestidoBank.Entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
boolean existeByname(String name);
boolean existeByemail(String email);

}
