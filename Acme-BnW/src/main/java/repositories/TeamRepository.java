
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Team;
import forms.ListTeamForm;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	@Query("select new forms.ListTeamForm(t,case when (exists(select 1 from Customer c where t in (c.favouriteTeams))) then true else false end) from Team t")
	public Collection<ListTeamForm> findTeamFavourite(int customerId);

	//Dashboard C

	//8 y 9 a)
	@Query("select t, sum(m.bets.size) from Team t join t.visitorMatches match join match.markets m group by t")
	Collection<Object[]> betsByVisitorTeam();

	//8 y 9 b)
	@Query("select t, sum(m.bets.size) from Team t join t.localMatches match join match.markets m group by t")
	Collection<Object[]> betsByLocalTeam();
	
	//Dashboard A
	
	//A1
	@Query("select sum(c.favouriteTeams.size)*1.0/count(c) from Customer c")
	public Double avgFavouritTeamPerCustomer();
}
