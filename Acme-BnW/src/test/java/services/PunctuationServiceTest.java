
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Punctuation;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PunctuationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PunctuationService punctuationService;


	/***
	 * Register punctuation
	 * 1º Good test -> expected: punctuation registered
	 * 2º Bad test -> cannot register punctuation with stars negative
	 * 3º Bad test -> cannot register punctuation an admin
	 */

	@Test
	public void registerPunctuationDriver() {
		final Object[][] testingData = {
			//actor, starts, topicId ,expected exception
			{
				"customer2", 3, 137, null
			}, {
				"customer2", -2, 137, ConstraintViolationException.class
			}, {
				"admin", 2, 137, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerPunctuationTemplated((String) testingData[i][0], (Integer) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void registerPunctuationTemplated(String principal, Integer stars, int topicId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Punctuation punctuation = new Punctuation();
			punctuation.setStars(stars);

			punctuationService.givePunctuation(punctuation, topicId);

			this.punctuationService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete punctuation
	 * 1º Good test -> expected: punctuation deleted
	 * 2º Bad test -> A customer don't owner punctuation cannot delete
	 * 3º Bad test -> an admin cannot delete punctuation
	 */

	@Test
	public void deletePunctuationDriver() {
		final Object[][] testingData = {
			//actor, punctuationId ,expected exception
			{
				"customer1", 139, null
			}, {
				"customer2", 141, IllegalArgumentException.class
			}, {
				"admin", 141, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deletePunctuationTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deletePunctuationTemplated(String principal, int punctuationId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Punctuation punctuation = punctuationService.findOne(punctuationId);

			punctuationService.delete(punctuation);

			this.unauthenticate();
			this.punctuationService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}
