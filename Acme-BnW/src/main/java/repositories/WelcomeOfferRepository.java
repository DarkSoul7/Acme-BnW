
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.WelcomeOffer;

@Repository
public interface WelcomeOfferRepository extends JpaRepository<WelcomeOffer, Integer> {

}
