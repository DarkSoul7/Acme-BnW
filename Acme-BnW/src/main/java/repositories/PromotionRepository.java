package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

	@Query("select m.promotion from Team t join t.customers c join t.localMatches vm join vm.markets m where c.id = ?1 and m.promotion.endMoment > NOW() and m.promotion.cancel = false")
	public Collection<Promotion> getLocalFavouriteTeamPromotions(int customerId);

	@Query("select m.promotion from Team t join t.customers c join t.visitorMatches vm join vm.markets m where c.id = ?1 and m.promotion.endMoment > NOW() and m.promotion.cancel = false")
	public Collection<Promotion> getVisitorFavouriteTeamPromotions(int customerId);
}
