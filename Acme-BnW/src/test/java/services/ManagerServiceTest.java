
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Coordinates;
import domain.Manager;
import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ManagerService managerService;


	/***
	 * Register a manager
	 * 1º Good test -> expected manager registered:
	 * 2º Bad test -> cannot register manager with email not pattern
	 * 3º Bad test -> cannot register manager a customer
	 */

	@Test
	public void registerManagerDriver() {

		final Object testingData[][] = {
			// actor name surname email phone nid username password exception
			{
				"admin", "Jesus", "Perez Domingo", "+34 672828282", "jes@gmail.com", "nid1", "manager10", "manager10", null
			}, {
				"admin", "Lucia", "Perez Domingo", "+34 672833123", "blabacar", "nid1", "manager11", "manager11", ConstraintViolationException.class
			//			}, {
			//				"customer1", "Salvador", "Pando Domingo", "+34 672832223", "salvadorpando@gmail.com", "nid4", "manager12", "manager12", IllegalArgumentException.class
			//			}
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registerManagerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void registerManagerTemplate(String principal, String name, String surname, String phone, String email, String nid, String username, String password, Class<?> expectedException) {
		Class<?> caught = null;

		try {

			authenticate(principal);
			Manager manager = new Manager();

			Coordinates coordinates = new Coordinates();
			coordinates.setCity("city");
			coordinates.setCountry("country");
			coordinates.setProvince("province");
			coordinates.setState("state");

			Authority authority;

			authority = new Authority();
			authority.setAuthority(Authority.MANAGER);

			UserAccount userAccount = new UserAccount();

			userAccount.setEnabled(true);
			userAccount.setPassword(password);
			userAccount.setUsername(username);
			userAccount.addAuthority(authority);

			manager.setName(name);
			manager.setSurname(surname);
			manager.setPhone(phone);
			manager.setEmail(email);
			manager.setNid(nid);
			manager.setCoordinates(coordinates);
			manager.setUserAccount(userAccount);

			managerService.save(manager);
			unauthenticate();
			this.managerService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit profile
	 * 1º Good test -> expected: profile manager edit
	 * 2º Bad test -> cannot edit phone blank
	 * 3º Bad test -> cannot edit profile admin
	 */

	@Test
	public void editProfileDriver() {
		final Object[][] testingData = {
			//actor, phone, expected exception
			{
				"manager1", "+34 654234543", null
			}, {
				"manager1", "", ConstraintViolationException.class
			}, {
				"admin", "+34 654234543", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editProfile((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void editProfile(String principal, String phone, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Manager manager = managerService.findByPrincipal();
			manager.setPhone(phone);
			managerService.editProfile(manager);

			this.unauthenticate();
			this.managerService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
