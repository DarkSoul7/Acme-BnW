
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
import domain.Customer;
import domain.Match;
import domain.Team;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TeamServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TeamService	teamService;


	/***
	 * Register teams
	 * 1� Good test -> expected: team registered
	 * 2� Bad test -> cannot register team without name
	 * 3� Bad test -> cannot register team without shield
	 */

	@Test
	public void registerTeamDriver() {
		final Object[][] testingData = {
			//actor, name, shield, expected exception
			{
				"manager1", "name", "https://c1.staticflickr.com/9/8108/8531201674_60519d433a.jpg", null
			}, {
				"manager1", "", "https://c1.staticflickr.com/9/8108/8531201674_60519d433a.jpg", ConstraintViolationException.class
			}, {
				"manager1", "name", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerTeamTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void registerTeamTemplated(final String principal, final String name, final String shield, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Team team = new Team();
			team.setName(name);
			team.setShield(shield);
			team.setCustomers(new ArrayList<Customer>());
			team.setLocalMatches(new ArrayList<Match>());
			team.setVisitorMatches(new ArrayList<Match>());

			this.teamService.save(team);
			this.unauthenticate();
			this.teamService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit teams
	 * 1� Good test -> expected: team edited
	 * 2� Bad test -> cannot edit team without name
	 * 3� Bad test -> cannot edit team without shield
	 */

	@Test
	public void editTeamDriver() {
		final Object[][] testingData = {
			//actor, teamId ,name, shield, expected exception
			{
				"manager1", 108, "name", "https://c1.staticflickr.com/9/8108/8531201674_60519d433a.jpg", null
			}, {
				"manager1", 108, "", "https://c1.staticflickr.com/9/8108/8531201674_60519d433a.jpg", ConstraintViolationException.class
			}, {
				"manager1", 108, "name", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editTeamTemplated((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void editTeamTemplated(final String principal, final int teamId, final String name, final String shield, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Team team = this.teamService.findOne(teamId);
			team.setName(name);
			team.setShield(shield);

			this.teamService.save(team);
			this.unauthenticate();
			this.teamService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete teams
	 * 1� Good test -> expected: team deleted
	 * 2� Bad test -> an customer cannot delete teams
	 * 3� Bad test -> an admin cannot delete teams
	 */

	@Test
	public void deleteTeamDriver() {
		final Object[][] testingData = {
			//actor, teamId , expected exception
			{
				"manager1", 116, null
			}, {
				"customer1", 115, IllegalArgumentException.class
			}, {
				"admin", 115, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteTeamTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteTeamTemplated(final String principal, final int teamId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Team team = this.teamService.findOne(teamId);
			this.teamService.delete(team);
			this.unauthenticate();
			this.teamService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}
