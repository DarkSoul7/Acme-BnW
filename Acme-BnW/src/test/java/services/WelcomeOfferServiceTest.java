
package services;

import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Customer;
import domain.WelcomeOffer;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WelcomeOfferServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private WelcomeOfferService welcomeOfferService;


	/***
	 * Register welcomeOffer
	 * 1º Good test -> expected: welcomeOffer registered
	 * 2º Bad test -> cannot register welcomeOffer when exists one welcomeOffer
	 * 3º Bad test -> cannot register welcomeOffer without title
	 */

	@Test
	public void registerWelcomeOfferDriver() {
		final Object[][] testingData = {
			//actor, title, openPeriod, endPeriod, amount, extractionAmount,expected exception
			{
				"manager1", "title", new DateTime(2020, 10, 10, 0, 0).toDate(), new DateTime(2022, 10, 10, 0, 0).toDate(), 20.0, 7.0, null
			}, {
				"manager1", "title 24", new DateTime(2018, 10, 10, 0, 0).toDate(), new DateTime(2019, 10, 10, 0, 0).toDate(), 22.0, 9.0, IllegalArgumentException.class
			}, {
				"manager2", "", new DateTime(2023, 10, 10, 0, 0).toDate(), new DateTime(2024, 10, 10, 0, 0).toDate(), 20.0, 7.0, ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerWelcomeOfferTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (Double) testingData[i][4], (Double) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	protected void registerWelcomeOfferTemplated(String principal, String title, Date openPeriod, Date endPeriod, Double amount, Double extractionAmount, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			WelcomeOffer welcomeOffer = new WelcomeOffer();
			welcomeOffer.setTitle(title);
			welcomeOffer.setOpenPeriod(openPeriod);
			welcomeOffer.setEndPeriod(endPeriod);
			welcomeOffer.setAmount(amount);
			welcomeOffer.setExtractionAmount(extractionAmount);
			welcomeOffer.setCustomers(new ArrayList<Customer>());

			welcomeOfferService.save(welcomeOffer);

			this.unauthenticate();
			this.welcomeOfferService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit welcomeOffer
	 * 1º Good test -> expected: welcomeOffer edit
	 * 2º Bad test -> cannot edit welcomeOffer when exists one welcomeOffer
	 * 3º Bad test -> cannot edit welcomeOffer with amount negative
	 */

	@Test
	public void editWelcomeOfferDriver() {
		final Object[][] testingData = {
			//actor,openPeriod, endPeriod, amount,expected exception
			{
				"manager1", new DateTime(2020, 10, 10, 0, 0).toDate(), new DateTime(2022, 10, 10, 0, 0).toDate(), 20.0, 94, null
			}, {
				"manager1", new DateTime(2018, 10, 10, 0, 0).toDate(), new DateTime(2019, 10, 10, 0, 0).toDate(), 22.0, 94, IllegalArgumentException.class
			}, {
				"manager2", new DateTime(2023, 10, 10, 0, 0).toDate(), new DateTime(2024, 10, 10, 0, 0).toDate(), -1.0, 94, ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editWelcomeOfferTemplated((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (Double) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void editWelcomeOfferTemplated(String principal, Date openPeriod, Date endPeriod, Double amount, int welcomeOfferId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			WelcomeOffer welcomeOffer = welcomeOfferService.findOne(welcomeOfferId);
			welcomeOffer.setOpenPeriod(openPeriod);
			welcomeOffer.setEndPeriod(endPeriod);
			welcomeOffer.setAmount(amount);

			welcomeOfferService.save(welcomeOffer);

			this.unauthenticate();
			this.welcomeOfferService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete welcome offer
	 * 1º Good test -> expected: welcomeOffer deleted
	 * 2º Bad test -> cannot delete welcomeOffer with customers
	 * 3º Bad test -> an admin cannot delete welcomeOffer
	 */

	@Test
	public void deleteWelcomeOfferDriver() {
		final Object[][] testingData = {
			//actor, welcomeOfferId , expected exception
			{
				"manager1", 95, null
			}, {
				"manager1", 94, IllegalArgumentException.class
			}, {
				"admin", 94, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteWelcomeOfferTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteWelcomeOfferTemplated(String principal, int welcomeOfferId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			WelcomeOffer welcomeOffer = welcomeOfferService.findOne(welcomeOfferId);
			welcomeOfferService.delete(welcomeOffer);
			this.unauthenticate();
			this.welcomeOfferService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Visualizar oferta de bienvenida
	 * Testing cases:
	 * 1º Good test -> expected: results shown
	 */

	@Test
	public void seeWelcomeOfferDriver() {

		final Object testingData[][] = {
			//principal expected exception
			{
				"customer1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.seeWelcomeOfferTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void seeWelcomeOfferTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			WelcomeOffer welcomeOffer = welcomeOfferService.getActive();

			Assert.notNull(welcomeOffer);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

}
