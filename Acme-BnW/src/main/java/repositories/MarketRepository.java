
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {

	@Query("select count(b) from Market m join m.bets b where m.id = ?1 ")
	Integer findExistsCustomerOnMarket(int idMarket);

}
