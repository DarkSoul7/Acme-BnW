
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {

	//C1
	@Query("select max(b.quantity) from Bet b")
	Double maxQuantityForBet();

	@Query("select min(b.quantity) from Bet b")
	Double minQuantityForBet();

	@Query("select avg(b.quantity) from Bet b")
	Double avgQuantityForBet();
}
