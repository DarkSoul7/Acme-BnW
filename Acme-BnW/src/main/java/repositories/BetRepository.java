
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {

}
