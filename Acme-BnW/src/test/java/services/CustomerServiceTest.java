
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

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
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

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService				customerService;

	@Autowired
	private WelcomeOfferService			welcomeOfferService;

	@Autowired
	private BalanceSearchEngineService	balanceSearchEngineService;


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

			final Collection<CustomerForm> result = this.balanceSearchEngineService.getGlobalBalance(name, surname, nid);

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
			this.registerCustomerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
				(Date) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void registerCustomerTemplate(final String name, final String surname, final String phone, final String email, final String nid, final Date birthDate, final String username,
		final String password, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			final Customer customer = new Customer();

			final CreditCard creditCard = new CreditCard();
			creditCard.setBrandName(Brand.VISA);
			creditCard.setCvv(123);
			creditCard.setExpirationMonth(10);
			creditCard.setExpirationYear(2018);
			creditCard.setHolderName("holder");
			creditCard.setNumber("4800553115069231");

			final Coordinates coordinates = new Coordinates();
			coordinates.setCity("city");
			coordinates.setCountry("country");
			coordinates.setProvince("province");
			coordinates.setState("state");

			Authority authority;

			authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);

			final UserAccount userAccount = new UserAccount();

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

			this.customerService.save(customer);
			this.customerService.flush();
		} catch (final Throwable oops) {
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

	protected void addBalanceTemplated(final String principal, final Double balance, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final BalanceForm balanceForm = new BalanceForm();
			balanceForm.setBalance(balance);

			this.customerService.addBalance(balanceForm);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
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
				"customer2", 20.0, null
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

	protected void extractBalanceTemplated(final String principal, final Double balance, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final BalanceForm balanceForm = new BalanceForm();
			balanceForm.setBalance(balance);

			this.customerService.extractBalance(balanceForm);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit profile
	 * 1º Good test -> expected: profile customer edit
	 * 2º Bad test -> cannot edit phone blank
	 * 3º Bad test -> cannot edit profile admin
	 */

	@Test
	public void editProfileDriver() {
		final Object[][] testingData = {
			//actor, phone, expected exception
			{
				"customer1", "+34 654234543", null
			}, {
				"customer1", "", ConstraintViolationException.class
			}, {
				"admin", "+34 654234543", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editProfile((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void editProfile(final String principal, final String phone, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Customer customer = this.customerService.findByPrincipal();
			customer.setPhone(phone);
			this.customerService.save(customer);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Active Welcome Offer
	 * 1º Good test -> expected: add amount of welcome offer on balance customer
	 * 2º Bad test -> charge is more little that extract amount
	 * 3º Bad test -> customer have welcome offer join before
	 */

	@Test
	public void activeOfferDriver() {
		final Object[][] testingData = {
			//actor, charge, expected exception
			{
				"customer1", 10.0, null
			}, {
				"customer1", 8.0, IllegalArgumentException.class
			}, {
				"customer1", 10.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.activeOfferTemplated((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void activeOfferTemplated(final String principal, final Double charge, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			this.customerService.activeOffer(charge);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Cancel Welcome Offer
	 * 1º Good test -> expected: Cancel welcome offer customer
	 * 2º Bad test -> Cannot cancel welcome offer if before it was cancel
	 * 3º Bad test -> admin cannot cancel welcome offer of customer
	 */

	@Test
	public void cancelOfferDriver() {
		final Object[][] testingData = {
			//actor, expected exception
			{
				"customer1", null
			}, {
				"customer2", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.cancelOfferTemplated((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void cancelOfferTemplated(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			this.customerService.cancelOffer();

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * AutoExclusion Customer
	 * 1º Good test -> expected: Customer is disabled
	 * 2º Bad test -> manager cannot disable account customer
	 * 3º Bad test -> customer cannot disabled if he is disabled
	 */

	@Test
	public void autoExclusionDriver() {
		final Object[][] testingData = {
			//actor, charge, expected exception
			{
				"customer1", null
			}, {
				"manager1", IllegalArgumentException.class
			}, {
				"customer1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.autoExclusionTemplated((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void autoExclusionTemplated(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			this.customerService.autoExclusion();

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Manage ban Customer
	 * 1º Good test -> expected: Ban/Not ban account customer
	 * 2º Bad test -> manager cannot ban/not ban account customer
	 * 3º Bad test -> customer cannot ban/not ban account customer
	 */

	@Test
	public void managementBanDriver() {
		final Object[][] testingData = {
			//actor, customerId, expected exception
			{
				"admin", 91, null
			}, {
				"manager1", 91, IllegalArgumentException.class
			}, {
				"customer1", 91, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.managementBanTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void managementBanTemplated(final String principal, final int customerId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			this.customerService.managementBan(customerId);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Add team favourite
	 * 1º Good test -> expected: add team a favourite
	 * 2º Bad test -> Cannot add team a favourite if exits in favourites
	 * 3º Bad test -> Admin cannot add team a favourite
	 */

	@Test
	public void addTeamFavouriteDriver() {
		final Object[][] testingData = {
			//actor, idTeam, expected exception
			{
				"customer1", 105, null
			}, {
				"customer1", 105, IllegalArgumentException.class
			}, {
				"admin", 105, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.addTeamFavouriteTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void addTeamFavouriteTemplated(final String principal, final int teamId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			this.customerService.addTeamFavourite(teamId);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * delete team favourite
	 * 1º Good test -> expected: delete team of favourite
	 * 2º Bad test -> Cannot delete team a favourite if don't exits in favourites
	 * 3º Bad test -> Admin cannot delete team of favourite
	 */

	@Test
	public void removeTeamFavouriteDriver() {
		final Object[][] testingData = {
			//actor, idTeam, expected exception
			{
				"customer1", 102, null
			}, {
				"customer1", 102, IllegalArgumentException.class
			}, {
				"admin", 102, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteTeamFavouriteTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteTeamFavouriteTemplated(final String principal, final int teamId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			this.customerService.deleteTeamFavourite(teamId);

			this.unauthenticate();
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
