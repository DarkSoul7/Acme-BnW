package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

	@Query("select m from Match m where m.fixture.id=?1")
	public Collection<Match> matchesOfFixture(int id);

}
