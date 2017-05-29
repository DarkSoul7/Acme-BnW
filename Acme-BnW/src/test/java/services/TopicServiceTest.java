
package services;

import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Message;
import domain.Punctuation;
import domain.Topic;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TopicServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TopicService	topicService;

	@Autowired
	private CustomerService	customerService;


	/***
	 * Register topic
	 * 1º Good test -> expected: topic registered
	 * 2º Bad test -> cannot register topic without title
	 * 3º Bad test -> cannot register topic an admin
	 */

	@Test
	public void registerTopicDriver() {
		final Object[][] testingData = {
			//actor, title, description, expected exception
			{
				"customer1", "title", "description", null
			}, {
				"customer1", "", "description", ConstraintViolationException.class
			}, {
				"admin", "title", "description", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerTopicTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void registerTopicTemplated(String principal, String title, String description, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Topic topic = new Topic();

			topic.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			topic.setTitle(title);
			topic.setDescription(description);
			topic.setMessages(new ArrayList<Message>());
			topic.setPunctuations(new ArrayList<Punctuation>());
			topic.setCustomer(customerService.findByPrincipal());

			topicService.save(topic);

			this.unauthenticate();
			this.topicService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit topic
	 * 1º Good test -> expected: topic edit
	 * 2º Bad test -> cannot edit topic without description
	 * 3º Bad test -> A customer don't owner topic cannot edit
	 */

	@Test
	public void editTopicDriver() {
		final Object[][] testingData = {
			//actor, title, description, expected exception
			{
				"customer1", "description", 120, null
			}, {
				"customer1", "", 120, ConstraintViolationException.class
			}, {
				"customer2", "description", 120, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editTopicTemplated((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void editTopicTemplated(String principal, String description, int topicId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Topic topic = topicService.findOne(topicId);

			topic.setDescription(description);

			topicService.save(topic);

			this.unauthenticate();
			this.topicService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
