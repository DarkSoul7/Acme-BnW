
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

import utilities.AbstractTest;
import domain.Message;
import domain.Punctuation;
import domain.Topic;

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

	protected void registerTopicTemplated(final String principal, final String title, final String description, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Topic topic = new Topic();

			topic.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			topic.setTitle(title);
			topic.setDescription(description);
			topic.setMessages(new ArrayList<Message>());
			topic.setPunctuations(new ArrayList<Punctuation>());
			topic.setCustomer(this.customerService.findByPrincipal());

			this.topicService.save(topic);

			this.unauthenticate();
			this.topicService.flush();
		} catch (final Throwable oops) {
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
			//actor, description, topicId expected exception
			{
				"customer2", "description", 151, null
			}, {
				"customer2", "", 151, ConstraintViolationException.class
			}, {
				"customer1", "description", 151, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editTopicTemplated((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void editTopicTemplated(final String principal, final String description, final int topicId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Topic topic = this.topicService.findOne(topicId);

			topic.setDescription(description);

			this.topicService.save(topic);

			this.unauthenticate();
			this.topicService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete topics
	 * 1º Good test -> expected: topic deleted
	 * 2º Bad test -> an customer cannot delete topic
	 * 3º Bad test -> an manager cannot delete topic
	 */

	@Test
	public void deleteTopicDriver() {
		final Object[][] testingData = {
			//actor, topicId , expected exception
			{
				"admin", 151, null
			}, {
				"customer1", 152, IllegalArgumentException.class
			}, {
				"manager", 152, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteTopicTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteTopicTemplated(final String principal, final int topicId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Topic topic = this.topicService.findOne(topicId);
			this.topicService.delete(topic);
			this.unauthenticate();
			this.topicService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
