
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//C3
	@Query("select distinct c from Category c join c.fixtures f join f.matches m join m.markets mk where mk.bets.size = (select max(mk2.bets.size)from Category c2 join c2.fixtures f2 join f2.matches m2 join m2.markets mk2)")
	Category categoryMoreBets();

	//C4
	@Query("select distinct c from Category c join c.fixtures f join f.matches m join m.markets mk where mk.bets.size = (select min(mk2.bets.size)from Category c2 join c2.fixtures f2 join f2.matches m2 join m2.markets mk2)")
	Category categoryLessBets();
}
