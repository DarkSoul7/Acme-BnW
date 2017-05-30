
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Message;
import domain.Team;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int id);

	@Query("select c from Customer c where c.userAccount.username = ?1")
	Customer findByUserName(String username);

	@Query("select t from Team t join t.customers c where c.id = ?1")
	Collection<Team> getFavouriteTeams(int customerId);

	//Dashboard B

	//B.1
	@Query("select count(c) from Customer c where c.isDisabled = true")
	public Integer getAutoExclusionNumber();

	//B.2
	@Query("select sum(c.banNum) from Customer c")
	public Integer getBanNumber();
	
	//Dashboard A
	
	//A2
	@Query("select c from Customer c where c.messages.size = (select max(c2.messages.size) from Customer c2)")
	public Collection<Customer> customerWithMoreMessages();
}
