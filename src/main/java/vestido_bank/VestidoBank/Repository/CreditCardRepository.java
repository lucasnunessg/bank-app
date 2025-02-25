package vestido_bank.VestidoBank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vestido_bank.VestidoBank.Entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
