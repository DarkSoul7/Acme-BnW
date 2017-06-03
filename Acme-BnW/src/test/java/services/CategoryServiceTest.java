
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;
import domain.Fixture;
import domain.Tournament;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private TournamentService	tournamentService;


	/***
	 * Register categories
	 * 1º Good test -> expected: category registered
	 * 2º Bad test -> cannot register category without title
	 * 3º Bad test -> cannot register category without fee
	 */

	@Test
	public void registerCategoryDriver() {
		final Object[][] testingData = {
			// actor, name, description, tournamentId,expected exception
			{
				"manager1", "Name1", "Description1", 102, null
			}, {
				"manager1", "", "Description2", 102, ConstraintViolationException.class
			}, {
				"manager1", "Name2", "", 102, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerCategoryTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerCategoryTemplated(final String principal, final String name, final String description, final Integer tournamentId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Tournament tournament = this.tournamentService.findOne(tournamentId);
			final Category category = new Category();
			category.setName(name);
			category.setDescription(description);
			category.setTournament(tournament);
			category.setFixtures(new ArrayList<Fixture>());

			this.categoryService.save(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit categories
	 * 1º Good test -> expected: category edited
	 * 2º Bad test -> cannot edit category without title
	 * 3º Bad test -> cannot edit category without fee
	 */

	@Test
	public void editCategoryDriver() {
		final Object[][] testingData = {
			// actor, categoryId ,name, description, expected exception
			{
				"manager1", 105, "name", "description 1", null
			}, {
				"manager1", 105, "", "description 2", ConstraintViolationException.class
			}, {
				"manager1", 105, "name 1", null, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editCategoryTemplated((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void editCategoryTemplated(final String principal, final int categoryId, final String name, final String description, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Category category = this.categoryService.findOne(categoryId);
			category.setName(name);
			category.setDescription(description);

			this.categoryService.save(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete categories
	 * 1º Good test -> expected: category deleted
	 * 2º Bad test -> an customer cannot delete categories
	 * 3º Bad test -> an administrator cannot delete categories
	 */

	@Test
	public void deletecategoryDriver() {
		final Object[][] testingData = {
			//actor, categoryId, expected exception
			{
				"manager1", 105, null
			}, {
				"customer1", 105, IllegalArgumentException.class
			}, {
				"admin", 105, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deletecategoryTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deletecategoryTemplated(final String principal, final int categoryId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Tournament tournament = this.tournamentService.findOne(102);
			Category category = new Category();
			category.setName("Name 1");
			category.setDescription("Description 1");
			category.setTournament(tournament);
			category.setFixtures(new ArrayList<Fixture>());

			category = this.categoryService.save(category);

			this.categoryService.delete(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}
