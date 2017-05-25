
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Fixture;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Integer> {

	@Query("select f from Fixture f where f.category.id=?1")
	Collection<Fixture> fixturesOfCategory(int id);
}
