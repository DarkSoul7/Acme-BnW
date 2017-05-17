
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

}
