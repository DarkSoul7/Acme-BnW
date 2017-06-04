
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Punctuation;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PunctuationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PunctuationService	punctuationService;


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
				"customer3", 3, 153, null
			}, {
				"customer3", -2, 153, ConstraintViolationException.class
			}, {
				"admin", 2, 151, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerPunctuationTemplated((String) testingData[i][0], (Integer) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void registerPunctuationTemplated(final String principal, final Integer stars, final int topicId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Punctuation punctuation = new Punctuation();
			punctuation.setStars(stars);

			this.punctuationService.givePunctuation(punctuation, topicId);

			this.punctuationService.flush();
		} catch (final Throwable oops) {
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
				"customer1", 154, null
			}, {
				"customer3", 155, IllegalArgumentException.class
			}, {
				"admin", 159, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deletePunctuationTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deletePunctuationTemplated(final String principal, final int punctuationId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Punctuation punctuation = this.punctuationService.findOne(punctuationId);

			this.punctuationService.delete(punctuation);

			this.unauthenticate();
			this.punctuationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}
