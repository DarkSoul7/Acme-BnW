
package services;

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
import domain.Topic;

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
	 * 1� Good test -> expected: message registered
	 * 2� Bad test -> cannot register message without title
	 * 3� Bad test -> cannot register message an administrator
	 */

	@Test
	public void registerMessageDriver() {
		final Object[][] testingData = {
			//actor, title, description, topicId ,expected exception
			{
				"customer1", "title", "description", 151, null
			}, {
				"customer1", "", "description", 151, ConstraintViolationException.class
			}, {
				"admin", "title", "description", 151, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMessageTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerMessageTemplated(final String principal, final String title, final String description, final int topicId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Message message = new Message();

			final Topic topic = this.topicService.findOne(topicId);

			message.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			message.setTitle(title);
			message.setDescription(description);
			message.setOrder(topic.getMessages().size() + 1);
			message.setCustomer(this.customerService.findByPrincipal());
			message.setTopic(topic);

			this.messageService.save(message);

			this.unauthenticate();
			this.messageService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit message
	 * 1� Good test -> expected: message edit
	 * 2� Bad test -> cannot edit message without description
	 * 3� Bad test -> A customer don't owner message cannot edit
	 */

	@Test
	public void editMessageDriver() {
		final Object[][] testingData = {
			//actor, title, description, messageId ,expected exception
			{
				"customer1", "description", 161, null
			}, {
				"customer1", "", 161, ConstraintViolationException.class
			}, {
				"customer2", "description", 161, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editMessageTemplated((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void editMessageTemplated(final String principal, final String description, final int messageId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Message message = this.messageService.findOne(messageId);

			message.setDescription(description);

			this.messageService.save(message);

			this.unauthenticate();
			this.topicService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete messages
	 * 1� Good test -> expected: message deleted
	 * 2� Bad test -> an customer cannot delete message
	 * 3� Bad test -> an manager cannot delete message
	 */

	@Test
	public void deleteMessageDriver() {
		final Object[][] testingData = {
			//actor, messageId , expected exception
			{
				"admin", 161, null
			}, {
				"customer1", 162, IllegalArgumentException.class
			}, {
				"manager", 163, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMessageTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteMessageTemplated(final String principal, final int messageId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Message message = this.messageService.findOne(messageId);
			this.messageService.delete(message);
			this.unauthenticate();
			this.messageService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
