package vestido_bank.VestidoBank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vestido_bank.VestidoBank.Entity.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

}
