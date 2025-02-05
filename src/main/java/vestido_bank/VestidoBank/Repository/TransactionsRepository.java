package vestido_bank.VestidoBank.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vestido_bank.VestidoBank.Entity.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByClient_Id(Long clientId);


}
