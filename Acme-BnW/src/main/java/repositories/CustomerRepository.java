
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Team;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int id);

	@Query("select c from Customer c where c.userAccount.username = ?1")
	Customer findByUserName(String username);

	@Query("select t from Team t join t.customers c where c.id = ?1")
	Collection<Team> getFavouriteTeams(int customerId);

}
