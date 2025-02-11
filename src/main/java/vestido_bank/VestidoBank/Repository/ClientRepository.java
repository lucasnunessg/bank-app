package vestido_bank.VestidoBank.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vestido_bank.VestidoBank.Entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Client findByEmail(String email);

  Optional<Client> findByName(String name);


  Optional<Client> findByResetToken(String resetToken);


  boolean existsByName(String name);

  boolean existsByemail(String email);

  boolean existsByEmailOrName(String email, String name);
}
