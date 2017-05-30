package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Market;
import domain.MarketType;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {

	@Query("select count(b) from Market m join m.bets b where m.id = ?1 ")
	public Integer findExistsCustomerOnMarket(int idMarket);

	@Query("select m from Market m where m.match.id=?1")
	public Collection<Market> marketsOfMatches(int id);

	//Marked market
	@Query("select distinct m from Market m where m.bets.size >= all(select m2.bets.size from Market m2)")
	public Collection<Market> getMarkedMarket();

	//Cantidad de apuestas sobre un partido -> número de clientes que han apostado en un partido
	@Query("select sum(m.bets.size) from Market m where m.match.id = ?1")
	public Integer betsInMatchDone(int matchId);

	@Query("select m.type from Market m where m.match.id = ?1")
	public Collection<MarketType> getAllMarketTypesUsedByMatch(int marketId);

}