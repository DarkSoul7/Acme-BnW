
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	//Dashboard C

	//8 y 9 a)
	@Query("select t, sum(m.bets.size) from Team t join t.visitorMatches match join match.markets m group by t")
	Collection<Object[]> betsByVisitorTeam();

	//8 y 9 b)
	@Query("select t, sum(m.bets.size) from Team t join t.localMatches match join match.markets m group by t")
	Collection<Object[]> betsByLocalTeam();
}
