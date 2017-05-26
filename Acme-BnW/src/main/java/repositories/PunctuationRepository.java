
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Punctuation;

@Repository
public interface PunctuationRepository extends JpaRepository<Punctuation, Integer> {

	@Query("select p from Punctuation p where p.topic.id =?1 and p.customer.id = ?2")
	public Punctuation findByTopicAndCustomer(int topicId, int customerId);
}
