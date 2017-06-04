
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

import utilities.AbstractTest;
import domain.Fixture;
import domain.Match;

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
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 105, null
			}, {
				"manager1", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 105, ConstraintViolationException.class
			}, {
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(1).toDate(), 105, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerFixtureTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (int) testingData[i][4],
				(Class<?>) testingData[i][5]);
		}
	}

	protected void registerFixtureTemplated(final String principal, final String title, final Date startMoment, final Date endMoment, final int idCategory, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Fixture fixture = new Fixture();
			fixture.setTitle(title);
			fixture.setStartMoment(startMoment);
			fixture.setEndMoment(endMoment);
			fixture.setCategory(this.categoryService.findOne(idCategory));
			fixture.setMatches(new ArrayList<Match>());

			this.fixtureService.save(fixture);
			this.unauthenticate();
			this.fixtureService.flush();
		} catch (final Throwable oops) {
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
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 117, null
			}, {
				"manager1", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(4).toDate(), 117, ConstraintViolationException.class
			}, {
				"manager1", "title1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(1).toDate(), 118, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editFixtureTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void editFixtureTemplated(final String principal, final String title, final Date startMoment, final Date endMoment, final int idFixture, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Fixture fixture = this.fixtureService.findOne(idFixture);
			fixture.setTitle(title);
			fixture.setStartMoment(startMoment);
			fixture.setEndMoment(endMoment);

			this.fixtureService.save(fixture);
			this.unauthenticate();
			this.fixtureService.flush();
		} catch (final Throwable oops) {
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
				"manager1", 117, null
			}, {
				"customer", 118, IllegalArgumentException.class
			}, {
				"admin", 119, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteFixtureTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteFixtureTemplated(final String principal, final int idFixture, final Class<?> expectedException) {
		Class<?> caught = null;
		Fixture fixture;
		try {
			fixture = this.fixtureService.findOne(idFixture);
			fixture = new Fixture();
			fixture.setTitle("title 300");
			fixture.setStartMoment(new DateTime().plusDays(2).toDate());
			fixture.setEndMoment(new DateTime().plusDays(4).toDate());
			fixture.setMatches(new ArrayList<Match>());
			fixture.setCategory(this.categoryService.findAll().iterator().next());
			this.fixtureService.save(fixture);
			this.authenticate(principal);

			this.fixtureService.delete(fixture);
			this.unauthenticate();
			this.fixtureService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
