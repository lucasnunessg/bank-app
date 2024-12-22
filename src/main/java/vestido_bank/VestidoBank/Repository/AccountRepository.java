package vestido_bank.VestidoBank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import vestido_bank.VestidoBank.Entity.Account;
import vestido_bank.VestidoBank.Entity.ContaCorrente;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
