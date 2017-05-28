
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

import domain.Market;
import domain.Match;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MatchServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MatchService	matchService;

	@Autowired
	private TeamService		teamService;

	@Autowired
	private FixtureService	fixtureService;


	/***
	 * Register match
	 * 1� Good test -> expected: match registered
	 * 2� Bad test -> cannot register match where endMoment before startMoment
	 * 3� Bad test -> cannot register match where starMoment before date actually
	 */

	@Test
	public void registerMatchDriver() {
		final Object[][] testingData = {
			//actor, startMoment, endMoment, idTeamLocal , idTeamVisitor, idFixture ,expected exception
			{
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), 91, 92, 98, null
			}, {
				"manager1", new DateTime().plusHours(3).toDate(), new DateTime().plusHours(1).toDate(), 91, 92, 98, IllegalArgumentException.class
			}, {
				"manager1", new DateTime().plusHours(-1).toDate(), new DateTime().plusHours(-2).toDate(), 91, 92, 98, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMatchTemplated((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	protected void registerMatchTemplated(String principal, Date startMoment, Date endMoment, int idTeamLocal, int idTeamVisitor, int idFixture, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Match match = new Match();
			match.setLocalGoal(0);
			match.setVisitorGoal(0);
			match.setMarkets(new ArrayList<Market>());
			match.setStartMoment(startMoment);
			match.setEndMoment(endMoment);
			match.setVisitorTeam(teamService.findOne(idTeamVisitor));
			match.setLocalTeam(teamService.findOne(idTeamLocal));
			match.setFixture(fixtureService.findOne(idFixture));

			matchService.save(match);
			this.unauthenticate();
			this.matchService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit match
	 * 1� Good test -> expected: match edited
	 * 2� Bad test -> cannot edited match where endMoment before startMoment
	 * 3� Bad test -> cannot edited match where starMoment before date actually
	 * 4� Bad test -> cannot edit local goal negative
	 * 5� Bad test -> cannot edit visitor goal negative
	 */

	@Test
	public void editMatchDriver() {
		final Object[][] testingData = {
			//actor,idMatch,expected exception
			{
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), 2, 1, 99, null
			}, {
				"manager1", new DateTime().plusHours(3).toDate(), new DateTime().plusHours(1).toDate(), 2, 1, 99, IllegalArgumentException.class
			}, {
				"manager1", new DateTime().plusHours(-1).toDate(), new DateTime().plusHours(-2).toDate(), 2, 1, 99, IllegalArgumentException.class
			}, {
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), -1, 1, 99, ConstraintViolationException.class
			}, {
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), 2, -2, 99, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editMatchTemplated((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}

	protected void editMatchTemplated(String principal, Date startMoment, Date endMoment, int idMatch, int localGoal, int visitorGoal, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Match match = matchService.findOne(idMatch);
			match.setStartMoment(startMoment);
			match.setEndMoment(endMoment);
			match.setLocalGoal(localGoal);
			match.setVisitorGoal(visitorGoal);
			matchService.save(match);
			this.unauthenticate();
			this.matchService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete match
	 * 1� Good test -> expected: match deleted
	 * 2� Bad test -> an customer cannot delete match
	 * 3� Bad test -> an admin cannot delete match
	 */

	@Test
	public void deleteTeamDriver() {
		final Object[][] testingData = {
			//actor, matchId , expected exception
			{
				"manager1", 99, null
			}, {
				"customer1", 99, IllegalArgumentException.class
			}, {
				"admin", 99, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMatchTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteMatchTemplated(String principal, int idMatch, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Match match = matchService.findOne(idMatch);

			matchService.delete(match);
			this.unauthenticate();
			this.matchService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}