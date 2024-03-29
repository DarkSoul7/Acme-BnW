package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Team;
import forms.ListTeamForm;
import forms.TeamForm;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	@Query("select new forms.ListTeamForm(t,case when (exists(select 1 from Team t2 join t2.customers c where t2.id = t.id and c.id = ?1)) then true else false end) from Team t")
	public Collection<ListTeamForm> findTeamFavourite(int customerId);

	@Query("select new forms.TeamForm(t.name, t.shield, t.id) from Team t")
	public Collection<TeamForm> findAllForms();

	//Dashboard C

	//8 y 9 a)
	@Query("select t, sum(m.bets.size) from Team t join t.visitorMatches match join match.markets m group by t")
	Collection<Object[]> betsByVisitorTeam();

	//8 y 9 b)
	@Query("select t, sum(m.bets.size) from Team t join t.localMatches match join match.markets m group by t")
	Collection<Object[]> betsByLocalTeam();

	//Dashboard A

	//A1
	@Query("select sum(t.customers.size)*1.0/(select count(c) from Customer c) from Team t")
	public Double avgFavouritTeamPerCustomer();
}
