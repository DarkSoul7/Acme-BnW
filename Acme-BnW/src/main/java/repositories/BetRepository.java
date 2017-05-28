package repositories;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bet;
import domain.Status;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {

	@Query("select b from Bet b where b.customer.id = ?1")
	public Collection<Bet> findAllByCustomer(int customerId);

	@Query("select b from Bet b where b.customer.id = ?1 and b.status = ?2")
	public Collection<Bet> findAllPendingByCustomer(int customerId, Status pendingStatus);

	//C1
	@Query("select max(b.quantity) from Bet b")
	Double maxQuantityForBet();

	@Query("select min(b.quantity) from Bet b")
	Double minQuantityForBet();

	@Query("select avg(b.quantity) from Bet b")
	Double avgQuantityForBet();

	//C2

	@Query("select count(b) from Bet b join b.customer c where b.status=1 group by c order by count(b) desc")
	ArrayList<Long> maxBetsWonPerClients();

	@Query("select count(b) from Bet b join b.customer c where b.status=1 group by c order by count(b) asc")
	ArrayList<Long> minBetsWonPerClients();

	@Query("select count(b)*1.0/(select count(b2) from Bet b2) from Bet b join b.customer c where b.status = 2")
	Double avgWonBetsPerClients();

	//C3
	@Query("select count(b) from Bet b join b.customer c where b.status=2 group by c order by count(b) desc")
	ArrayList<Long> maxBetsLostPerClients();

	@Query("select count(b) from Bet b join b.customer c where b.status=2 group by c order by count(b) asc")
	ArrayList<Long> minBetsLostPerClients();

	@Query("select count(b)*1.0/(select count(b2) from Bet b2) from Bet b join b.customer c where b.status = 2")
	Double avgLostBetsPerClients();
}
