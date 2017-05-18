
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WelcomeOffer;

@Repository
public interface WelcomeOfferRepository extends JpaRepository<WelcomeOffer, Integer> {

	@Query("select wo from WelcomeOffer wo where wo.openPeriod < current_Date and wo.endPeriod > current_Date")
	public WelcomeOffer getActive();
}
