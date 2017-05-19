
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import forms.CustomerForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService	customerService;


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
}
