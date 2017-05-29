
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

import domain.Category;
import domain.Sport;
import domain.Tournament;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TournamentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private TournamentService tournamentService;


	/***
	 * Register tournaments
	 * 1º Good test -> expected: tournament registered
	 * 2º Bad test -> cannot register tournament without name
	 * 3º Bad test -> cannot register tournament without description
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
			this.registerTournamentTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4], (Sport) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	protected void registerTournamentTemplated(String principal, String name, String description, Date startMoment, Date endMoment, Sport sport, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Tournament tournament = new Tournament();
			tournament.setName(name);
			tournament.setDescription(description);
			tournament.setStartMoment(startMoment);
			tournament.setSport(sport);
			tournament.setEndMoment(endMoment);
			tournament.setCategories(new ArrayList<Category>());

			tournamentService.save(tournament);
			this.unauthenticate();
			this.tournamentService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit tournaments
	 * 1º Good test -> expected: tournament edited
	 * 2º Bad test -> cannot edit tournament without name
	 * 3º Bad test -> cannot edit tournament without description
	 */

	@Test
	public void editTournamentDriver() {
		final Object[][] testingData = {
			//actor, name, description,starMoment,endMoment,sport, idTournament, expected exception
			{
				"manager1", "name", "description 1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, 83, null
			}, {
				"manager1", "", "description 1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, 83, ConstraintViolationException.class
			}, {
				"manager1", "name", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), Sport.FOOTBALL, 83, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editTournamentTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Date) testingData[i][4], (Sport) testingData[i][5], (int) testingData[i][6],
				(Class<?>) testingData[i][7]);
		}
	}

	protected void editTournamentTemplated(String principal, String name, String description, Date startMoment, Date endMoment, Sport sport, int idTournament, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Tournament tournament = tournamentService.findOne(idTournament);
			tournament.setName(name);
			tournament.setDescription(description);
			tournament.setStartMoment(startMoment);
			tournament.setSport(sport);
			tournament.setEndMoment(endMoment);

			tournamentService.save(tournament);
			this.unauthenticate();
			this.tournamentService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete tournament
	 * 1º Good test -> expected: tournament deleted
	 * 2º Bad test -> cannot delete tournament with categories
	 * 3º Bad test -> an admin cannot delete a tournament
	 */

	@Test
	public void deleteTeamDriver() {
		final Object[][] testingData = {
			//actor, matchId , expected exception
			{
				"manager1", 0, null
			}, {
				"manager1", 83, IllegalArgumentException.class
			}, {
				"admin", 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteTournamentTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteTournamentTemplated(String principal, int idTournament, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			Tournament tournament;
			if (idTournament != 0) {
				tournament = tournamentService.findOne(idTournament);
			} else {
				tournament = new Tournament();
				tournament.setName("name");
				tournament.setDescription("description");
				tournament.setStartMoment(new DateTime().plusDays(2).toDate());
				tournament.setSport(Sport.FOOTBALL);
				tournament.setEndMoment(new DateTime().plusDays(10).toDate());
				tournament.setCategories(new ArrayList<Category>());
			}
			this.authenticate(principal);
			tournamentService.delete(tournament);
			this.unauthenticate();
			this.tournamentService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
