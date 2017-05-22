
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

	@Query("select p from Team t join t.customers c join t.localMatches vm join vm.markets m join m.promotions p where c.id = ?1")
	Collection<Promotion> getLocalFavouriteTeamPromotions(int customerId);

	@Query("select p from Team t join t.customers c join t.visitorMatches vm join vm.markets m join m.promotions p where c.id = ?1")
	Collection<Promotion> getVisitorFavouriteTeamPromotions(int customerId);
}
