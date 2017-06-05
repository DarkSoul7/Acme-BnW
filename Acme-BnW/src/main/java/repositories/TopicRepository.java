package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Topic;
import forms.TopicListForm;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

	@Query("select new forms.TopicListForm(t.id, t.title, t.description, t.creationMoment, 0L, t.customer) from Topic t where t.punctuations.size = 0")
	public Collection<TopicListForm> findAllWithoutStars();

	@Query("select new forms.TopicListForm(t.id, t.title, t.description, t.creationMoment, sum(p.stars), t.customer) from Topic t join t.punctuations p group by t order by sum(p.stars) desc")
	public Collection<TopicListForm> getTopicsOrderByStars();

	//DashBoard
	//B.3

	//a)
	@Query("select max(c.topics.size) from Customer c")
	Integer getMaxTopicsByCustomer();

	//b)
	@Query("select min(c.topics.size) from Customer c")
	Integer getMinTopicsByCustomer();

	//c)
	@Query("select sum(c.topics.size)*1.0/count(c) from Customer c")
	Double getTopicAvgByCustomers();

	//B.4

	//a)
	@Query("select max(c.messages.size) from Customer c")
	Integer getMaxMessagesByCustomer();

	//b)
	@Query("select min(c.messages.size) from Customer c")
	Integer getMinMessagesByCustomer();

	//c)
	@Query("select sum(c.messages.size)*1.0/count(c) from Customer c")
	Double getMessageAvgByCustomers();
}
