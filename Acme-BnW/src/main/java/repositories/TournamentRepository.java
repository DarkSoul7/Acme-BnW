
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

}
