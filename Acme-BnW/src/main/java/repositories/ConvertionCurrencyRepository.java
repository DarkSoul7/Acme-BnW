
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ConvertionCurrency;
import domain.Currency;

@Repository
public interface ConvertionCurrencyRepository extends JpaRepository<ConvertionCurrency, Integer> {

	@Query("select cc from ConvertionCurrency cc where cc.currency = ?1")
	public ConvertionCurrency findOne(Currency currency);
}
