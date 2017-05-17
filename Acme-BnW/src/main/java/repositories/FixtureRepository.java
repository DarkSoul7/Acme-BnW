
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Fixture;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Integer> {

}
