
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
	 * 2º Bad test -> cannot register welcomeOffer without title
	 * 3º Bad test -> cannot register welcomeOffer when exists one welcomeOffer
	 */

	@Test
	public void registerWelcomeOfferDriver() {
		final Object[][] testingData = {
			//actor, title, openPeriod, endPeriod, amount, extractionAmount,expected exception
			{
				"manager1", "title", new DateTime(2020, 10, 10, 0, 0).toDate(), new DateTime(2022, 10, 10, 0, 0).toDate(), 20.0, 7.0, null
			}, {
				"manager1", "", new DateTime(2023, 10, 10, 0, 0).toDate(), new DateTime(2024, 10, 10, 0, 0).toDate(), 20.0, 7.0, ConstraintViolationException.class
			//			}, {
			//			//				"manager1", "title 24", new DateTime(2018, 10, 10, 0, 0).toDate(), new DateTime(2019, 10, 10, 0, 0).toDate(), 20.0, 7.0, IllegalArgumentException.class
			//			}
			}
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

}
