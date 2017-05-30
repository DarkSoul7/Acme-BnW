
package services;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Message;
import domain.Topic;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private TopicService	topicService;


	/***
	 * Register message
	 * 1º Good test -> expected: message registered
	 * 2º Bad test -> cannot register message without title
	 * 3º Bad test -> cannot register message an admin
	 */

	@Test
	public void registerMessageDriver() {
		final Object[][] testingData = {
			//actor, title, description, topicId ,expected exception
			{
				"customer1", "title", "description", 136, null
			}, {
				"customer1", "", "description", 136, ConstraintViolationException.class
			}, {
				"admin", "title", "description", 136, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMessageTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerMessageTemplated(String principal, String title, String description, int topicId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Message message = new Message();

			Topic topic = topicService.findOne(topicId);

			message.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			message.setTitle(title);
			message.setDescription(description);
			message.setOrder(topic.getMessages().size() + 1);
			message.setCustomer(customerService.findByPrincipal());
			message.setTopic(topic);

			messageService.save(message);

			this.unauthenticate();
			this.messageService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit message
	 * 1º Good test -> expected: message edit
	 * 2º Bad test -> cannot edit message without description
	 * 3º Bad test -> A customer don't owner message cannot edit
	 */

	@Test
	public void editMessageDriver() {
		final Object[][] testingData = {
			//actor, title, description, messageId ,expected exception
			{
				"customer1", "description", 146, null
			}, {
				"customer1", "", 146, ConstraintViolationException.class
			}, {
				"customer2", "description", 146, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editMessageTemplated((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void editMessageTemplated(String principal, String description, int messageId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Message message = messageService.findOne(messageId);

			message.setDescription(description);

			messageService.save(message);

			this.unauthenticate();
			this.topicService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete messages
	 * 1º Good test -> expected: message deleted
	 * 2º Bad test -> an customer cannot delete message
	 * 3º Bad test -> an manager cannot delete message
	 */

	@Test
	public void deleteMessageDriver() {
		final Object[][] testingData = {
			//actor, messageId , expected exception
			{
				"admin", 146, null
			}, {
				"customer1", 147, IllegalArgumentException.class
			}, {
				"manager", 147, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMessageTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteMessageTemplated(String principal, int messageId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Message message = messageService.findOne(messageId);
			messageService.delete(message);
			this.unauthenticate();
			this.messageService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
