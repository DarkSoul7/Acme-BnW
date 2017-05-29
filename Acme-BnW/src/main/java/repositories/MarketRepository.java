
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {

	@Query("select count(b) from Market m join m.bets b where m.id = ?1 ")
	Integer findExistsCustomerOnMarket(int idMarket);

	@Query("select m from Market m where m.match.id=?1")
	Collection<Market> marketsOfMatches(int id);

	//Marked market
	@Query("select distinct m from Market m where m.bets.size >= all(select m2.bets.size from Market m2)")
	Collection<Market> getMarkedMarket();
}
