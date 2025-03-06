package vestido_bank.VestidoBank.Repository;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;
import vestido_bank.VestidoBank.Entity.CreditCard;
import vestido_bank.VestidoBank.Entity.Transaction;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

  @Query("SELECT t FROM Transaction t WHERE t.creditCardOrigem IS NOT NULL OR t.creditCardDestino IS NOT NULL")
  List<Transaction> findAllWithCreditCard();

  @Query("SELECT cc.faturaAtual FROM CreditCard cc WHERE cc.client.id = :clientId")
  List<BigDecimal> findFaturasAtuaisByClientId(@Param("clientId") Long clientId);

  List<CreditCard> findByClientId(Long clientId);
}
