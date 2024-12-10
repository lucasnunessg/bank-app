package vestido_bank.VestidoBank.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vestido_bank.VestidoBank.Entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
List<Client> findByEmail(String email);
boolean existsByName(String name);
boolean existsByemail(String email);
}
