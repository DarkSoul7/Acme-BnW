
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
import domain.Category;
import domain.Sport;
import domain.Tournament;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TournamentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TournamentService	tournamentService;


	/***
	 * Register tournaments
	 * 1� Good test -> expected: tournament registered
	 * 2� Bad test -> cannot register tournament without name
	 * 3� Bad test -> cannot register tournament without description
	 */

	@Test
	public void registerTournamentDriver() {
		final Object[][] testingData = {
			//actor, name, description,starMoment,endMoment,sport, expected exception
			{
				"manager1", "name", "description 1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, null
			}, {
				"manager1", "", "description 1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, ConstraintViolationException.class
			}, {
				"manager1", "name", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerTournamentTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4],
				(Sport) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	protected void registerTournamentTemplated(final String principal, final String name, final String description, final Date startMoment, final Date endMoment, final Sport sport,
		final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Tournament tournament = new Tournament();
			tournament.setName(name);
			tournament.setDescription(description);
			tournament.setStartMoment(startMoment);
			tournament.setSport(sport);
			tournament.setEndMoment(endMoment);
			tournament.setCategories(new ArrayList<Category>());

			this.tournamentService.save(tournament);
			this.unauthenticate();
			this.tournamentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit tournaments
	 * 1� Good test -> expected: tournament edited
	 * 2� Bad test -> cannot edit tournament without name
	 * 3� Bad test -> cannot edit tournament without description
	 */

	@Test
	public void editTournamentDriver() {
		final Object[][] testingData = {
			//actor, name, description,starMoment,endMoment,sport, idTournament, expected exception
			{
				"manager1", "name", "description 1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, 102, null
			}, {
				"manager1", "", "description 1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, 102, ConstraintViolationException.class
			}, {
				"manager1", "name", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, 102, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editTournamentTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4],
				(Sport) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
		}
	}

	protected void editTournamentTemplated(final String principal, final String name, final String description, final Date startMoment, final Date endMoment, final Sport sport,
		final int idTournament, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Tournament tournament = this.tournamentService.findOne(idTournament);
			tournament.setName(name);
			tournament.setDescription(description);
			tournament.setStartMoment(startMoment);
			tournament.setSport(sport);
			tournament.setEndMoment(endMoment);

			this.tournamentService.save(tournament);
			this.unauthenticate();
			this.tournamentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete tournament
	 * 1� Good test -> expected: tournament deleted
	 * 2� Bad test -> cannot delete tournament with categories
	 * 3� Bad test -> an admin cannot delete a tournament
	 */

	@Test
	public void deleteTeamDriver() {
		final Object[][] testingData = {
			//actor, matchId , expected exception
			{
				"manager1", 0, null
			}, {
				"manager1", 102, IllegalArgumentException.class
			}, {
				"admin", 103, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteTournamentTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteTournamentTemplated(final String principal, final int idTournament, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			Tournament tournament;
			if (idTournament != 0) {
				tournament = this.tournamentService.findOne(idTournament);
			} else {
				tournament = new Tournament();
				tournament.setName("name");
				tournament.setDescription("description");
				tournament.setStartMoment(new DateTime().plusDays(2).toDate());
				tournament.setSport(Sport.FOOTBALL);
				tournament.setEndMoment(new DateTime().plusDays(10).toDate());
				tournament.setCategories(new ArrayList<Category>());
				this.tournamentService.save(tournament);
			}
			this.authenticate(principal);
			this.tournamentService.delete(tournament);
			this.unauthenticate();
			this.tournamentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
