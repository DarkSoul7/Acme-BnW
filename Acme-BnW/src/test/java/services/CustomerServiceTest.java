
package services;

import java.util.ArrayList;
import java.util.Collection;
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

import domain.Bet;
import domain.Brand;
import domain.Coordinates;
import domain.CreditCard;
import domain.Customer;
import domain.Message;
import domain.Punctuation;
import domain.Team;
import domain.Ticket;
import domain.Topic;
import forms.BalanceForm;
import forms.CustomerForm;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private WelcomeOfferService	welcomeOfferService;


	/***
	 * Visualizar una lista con el balance ganancia-perdidas de todos los clientes existentes, pudiendo filtrar por nombre, apellido o DNI
	 * Testing cases:
	 * 1º Good test (without parameters) -> expected: results shown
	 * 2º Good test (with parameters) -> expected: results shown
	 * 3º Good test (with parameters) -> expected: results shown
	 * 4º Bad test; an unauthenticated actor cannot run the searcher -> expected: IllegalArgumentException
	 * 5º Bad test; an actor who is not an administrator run the searcher -> expected: IllegalArgumentException
	 */

	@Test
	public void searchBalanceDriver() {

		final Object testingData[][] = {
			//principal, name, surname, nid, expected exception
			{
				"admin", null, null, null, null
			}, {
				"admin", "Lau", null, null, null
			}, {
				"admin", "Sergio", "Per", null, null
			}, {
				null, "Lau", null, null, IllegalArgumentException.class
			}, {
				"manager1", "Lau", null, null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchBalanceTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void searchBalanceTemplate(final String principal, final String name, final String surname, final String nid, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Collection<CustomerForm> result = this.customerService.getGlobalBalance(name, surname, nid);

			Assert.notNull(result);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Register a customer
	 * 1º Good test -> expected customer registered:
	 * 2º Bad test -> cannot register customer with email not pattern
	 * 3º Bad test -> cannot register customer with birthdate future
	 */

	@Test
	public void registerCustomerDriver() {

		final Object testingData[][] = {
			// name surname email phone nid birthDate username password exception
			{
				"Jesus", "Perez Domingo", "+34 672828282", "jes@gmail.com", "nid1", new DateTime(1989, 10, 10, 00, 00).toDate(), "customer30", "customer30", null
			}, {
				"Lucia", "Perez Domingo", "+34 672833123", "blabacar", "nid1", new DateTime(2017, 12, 12, 00, 00).toDate(), "customer100", "customer100", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerCustomerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Date) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void registerCustomerTemplate(String name, String surname, String phone, String email, String nid, Date birthDate, String username, String password, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			Customer customer = new Customer();

			CreditCard creditCard = new CreditCard();
			creditCard.setBrandName(Brand.VISA);
			creditCard.setCvv(123);
			creditCard.setExpirationMonth(10);
			creditCard.setExpirationYear(2018);
			creditCard.setHolderName("holder");
			creditCard.setNumber("4800553115069231");

			Coordinates coordinates = new Coordinates();
			coordinates.setCity("city");
			coordinates.setCountry("country");
			coordinates.setProvince("province");
			coordinates.setState("state");

			Authority authority;

			authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);

			UserAccount userAccount = new UserAccount();

			userAccount.setEnabled(true);
			userAccount.setPassword(password);
			userAccount.setUsername(username);
			userAccount.addAuthority(authority);

			customer.setName(name);
			customer.setSurname(surname);
			customer.setPhone(phone);
			customer.setBirthDate(birthDate);
			customer.setEmail(email);
			customer.setNid(nid);
			customer.setCoordinates(coordinates);
			customer.setBanNum(0);
			customer.setFinishedOffer(false);
			customer.setActiveWO(null);
			customer.setIsDisabled(false);
			customer.setBalance(0.);
			customer.setOverAge(true);
			customer.setWelcomeOffer(this.welcomeOfferService.getActive());
			customer.setCreditCard(creditCard);
			customer.setUserAccount(userAccount);
			customer.setBets(new ArrayList<Bet>());
			customer.setFavouriteTeams(new ArrayList<Team>());
			customer.setMessages(new ArrayList<Message>());
			customer.setPunctuations(new ArrayList<Punctuation>());
			customer.setTopics(new ArrayList<Topic>());
			customer.setTickets(new ArrayList<Ticket>());

			customerService.save(customer);
			this.customerService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Add balance
	 * 1º Good test -> expected: add balance
	 * 2º Bad test -> cannot add balance manager
	 * 3º Bad test -> cannot add balance admin
	 */

	@Test
	public void addBalanceDriver() {
		final Object[][] testingData = {
			//actor, balance, expected exception
			{
				"customer1", 20.0, null
			}, {
				"manager", 20.0, IllegalArgumentException.class
			}, {
				"admin", 20.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.addBalanceTemplated((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void addBalanceTemplated(String principal, Double balance, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			BalanceForm balanceForm = new BalanceForm();
			balanceForm.setBalance(balance);

			customerService.addBalance(balanceForm);

			this.unauthenticate();
			this.customerService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Extract balance
	 * 1º Good test -> expected: extract balance
	 * 2º Bad test -> cannot extract balance manager
	 * 3º Bad test -> cannot extract more balance he have
	 */

	@Test
	public void extractBalanceDriver() {
		final Object[][] testingData = {
			//actor, balance, expected exception
			{
				"customer1", 20.0, null
			}, {
				"manager1", 20.0, IllegalArgumentException.class
			}, {
				"customer1", 2000.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.extractBalanceTemplated((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void extractBalanceTemplated(String principal, Double balance, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			BalanceForm balanceForm = new BalanceForm();
			balanceForm.setBalance(balance);

			customerService.extractBalance(balanceForm);

			this.unauthenticate();
			this.customerService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}
