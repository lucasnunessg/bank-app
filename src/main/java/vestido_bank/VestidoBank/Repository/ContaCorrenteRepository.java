package vestido_bank.VestidoBank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

  List<ContaCorrente> findByClientId(Long clientId);
}
