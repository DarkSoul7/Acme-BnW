
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {

}
