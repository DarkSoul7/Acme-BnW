
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.topic.id = ?1 order by m.order asc")
	public Collection<Message> getMessagesByTopic(int topicId);
	
	//Dashboard A
	
	//A3
	@Query("select avg(t.messages.size) from Topic t")
	public Double avgMessagesPerTopic();

}
