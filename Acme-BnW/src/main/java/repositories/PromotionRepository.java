
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

}
