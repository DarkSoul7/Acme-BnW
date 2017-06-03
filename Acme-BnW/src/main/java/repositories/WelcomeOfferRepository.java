package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WelcomeOffer;

@Repository
public interface WelcomeOfferRepository extends JpaRepository<WelcomeOffer, Integer> {

	@Query("select wo from WelcomeOffer wo where current_date between wo.openPeriod and wo.endPeriod")
	public WelcomeOffer getActive();

	@Query("select w from WelcomeOffer w where (w.openPeriod >= ?1 and w.endPeriod <= ?2) or (?1 between w.openPeriod and w.endPeriod) or (?2 between w.openPeriod and w.endPeriod)")
	public Collection<WelcomeOffer> getOffersInDates(Date openPeriod, Date endPeriod);
}
