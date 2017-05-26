
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sport;
import domain.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

	@Query("select t from Tournament t where t.sport=0")
	Collection<Tournament> listTournamentOfFootball();

	@Query("select t from Tournament t where t.sport=1")
	Collection<Tournament> listTournamentOfBasketball();

	//C6
	@Query("select distinct c from Category c join c.fixtures f join f.matches m join m.markets mk where mk.bets.size = (select max(mk2.bets.size)from Category c2 join c2.fixtures f2 join f2.matches m2 join m2.markets mk2)")
	Sport SportMoreBets();

	//C7
	@Query("select distinct c from Category c join c.fixtures f join f.matches m join m.markets mk where mk.bets.size = (select min(mk2.bets.size)from Category c2 join c2.fixtures f2 join f2.matches m2 join m2.markets mk2)")
	Sport SportLessBets();
}
