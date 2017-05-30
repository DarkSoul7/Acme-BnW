
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

import domain.Fixture;
import domain.Match;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FixtureServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private FixtureService	fixtureService;

	@Autowired
	private CategoryService	categoryService;


	/***
	 * Register fixtures
	 * 1º Good test -> expected: register fixture
	 * 2º Bad test -> cannot register fixture without title
	 * 3º Bad test -> cannot register fixture with startMoment after endMoment
	 */

	@Test
	public void registerFixtureDriver() {
		final Object[][] testingData = {
			//actor, title, startMoment, endMoment, idCategory ,expected exception
			{
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 94, null
			}, {
				"manager1", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 94, ConstraintViolationException.class
			}, {
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(1).toDate(), 94, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerFixtureTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void registerFixtureTemplated(String principal, String title, Date startMoment, Date endMoment, int idCategory, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Fixture fixture = new Fixture();
			fixture.setTitle(title);
			fixture.setStartMoment(startMoment);
			fixture.setEndMoment(endMoment);
			fixture.setCategory(categoryService.findOne(idCategory));
			fixture.setMatches(new ArrayList<Match>());

			fixtureService.save(fixture);
			this.unauthenticate();
			this.fixtureService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit fixtures
	 * 1º Good test -> expected: edit fixture
	 * 2º Bad test -> cannot edit fixture without title
	 * 3º Bad test -> cannot edit fixture with startMoment after endMoment
	 */

	@Test
	public void editFixtureDriver() {
		final Object[][] testingData = {
			//actor, title, startMoment, endMoment, idFixture ,expected exception
			{
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 107, null
			}, {
				"manager1", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 107, ConstraintViolationException.class
			}, {
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(1).toDate(), 107, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editFixtureTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void editFixtureTemplated(String principal, String title, Date startMoment, Date endMoment, int idFixture, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Fixture fixture = fixtureService.findOne(idFixture);
			fixture.setTitle(title);
			fixture.setStartMoment(startMoment);
			fixture.setEndMoment(endMoment);

			fixtureService.save(fixture);
			this.unauthenticate();
			this.fixtureService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete tournament
	 * 1º Good test -> expected: fixture deleted
	 * 2º Bad test -> an customer cannot delete a fixture
	 * 3º Bad test -> an admin cannot delete a fixture
	 */

	@Test
	public void deleteTeamDriver() {
		final Object[][] testingData = {
			//actor, fixtureId , expected exception
			{
				"manager1", 0, null
			}, {
				"customer", 107, IllegalArgumentException.class
			}, {
				"admin", 107, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteFixtureTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteFixtureTemplated(String principal, int idFixture, Class<?> expectedException) {
		Class<?> caught = null;
		Fixture fixture;
		try {
			if (idFixture != 0) {
				fixture = fixtureService.findOne(idFixture);
			} else {
				fixture = new Fixture();
				fixture.setTitle("title 300");
				fixture.setStartMoment(new DateTime().plusDays(2).toDate());
				fixture.setEndMoment(new DateTime().plusDays(4).toDate());
				fixture.setMatches(new ArrayList<Match>());
				fixture.setCategory(categoryService.findAll().iterator().next());
				fixtureService.save(fixture);
			}
			this.authenticate(principal);

			fixtureService.delete(fixture);
			this.unauthenticate();
			this.fixtureService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
