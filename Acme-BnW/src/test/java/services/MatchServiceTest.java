
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
import domain.Market;
import domain.Match;

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
	 * 1º Good test -> expected: match registered
	 * 2º Bad test -> cannot register match where endMoment before startMoment
	 * 3º Bad test -> cannot register match where starMoment before date actually
	 */

	@Test
	public void registerMatchDriver() {
		final Object[][] testingData = {
			//actor, startMoment, endMoment, idTeamLocal , idTeamVisitor, idFixture ,expected exception
			{
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), 108, 109, 117, null
			}, {
				"manager1", new DateTime().plusHours(3).toDate(), new DateTime().plusHours(1).toDate(), 108, 109, 117, IllegalArgumentException.class
			}, {
				"manager1", new DateTime().plusHours(-1).toDate(), new DateTime().plusHours(-2).toDate(), 108, 109, 119, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMatchTemplated((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5],
				(Class<?>) testingData[i][6]);
		}
	}

	protected void registerMatchTemplated(final String principal, final Date startMoment, final Date endMoment, final int idTeamLocal, final int idTeamVisitor, final int idFixture,
		final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Match match = new Match();
			match.setLocalResult(0);
			match.setVisitorResult(0);
			match.setMarkets(new ArrayList<Market>());
			match.setStartMoment(startMoment);
			match.setEndMoment(endMoment);
			match.setSolvedBets(false);
			match.setVisitorTeam(this.teamService.findOne(idTeamVisitor));
			match.setLocalTeam(this.teamService.findOne(idTeamLocal));
			match.setFixture(this.fixtureService.findOne(idFixture));

			this.matchService.save(match);
			this.unauthenticate();
			this.matchService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit match
	 * 1º Good test -> expected: match edited
	 * 2º Bad test -> cannot edited match where endMoment before startMoment
	 * 3º Bad test -> cannot edited match where starMoment before date actually
	 * 4º Bad test -> cannot edit local result negative
	 * 5º Bad test -> cannot edit visitor result negative
	 */

	@Test
	public void editMatchDriver() {
		final Object[][] testingData = {
			//actor,start moment, end moment, local result, visitor result, match id, expected exception
			{
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), 2, 1, 121, null
			}, {
				"manager1", new DateTime().plusHours(3).toDate(), new DateTime().plusHours(1).toDate(), 2, 1, 121, IllegalArgumentException.class
			}, {
				"manager1", new DateTime().plusHours(-1).toDate(), new DateTime().plusHours(-2).toDate(), 2, 1, 121, IllegalArgumentException.class
			}, {
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), -1, 1, 121, ConstraintViolationException.class
			}, {
				"manager1", new DateTime().plusHours(1).toDate(), new DateTime().plusHours(3).toDate(), 2, -2, 121, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editMatchTemplated((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5],
				(Class<?>) testingData[i][6]);
		}
	}

	protected void editMatchTemplated(final String principal, final Date startMoment, final Date endMoment, final Integer localResult, final Integer visitorResult, final int idMatch,
		final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Match match = this.matchService.findOne(idMatch);
			match.setStartMoment(startMoment);
			match.setEndMoment(endMoment);
			match.setLocalResult(localResult);
			match.setVisitorResult(visitorResult);
			this.matchService.save(match);
			this.unauthenticate();
			this.matchService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete match
	 * 1º Good test -> expected: match deleted
	 * 2º Bad test -> an customer cannot delete a match
	 * 3º Bad test -> an administrator cannot delete a match
	 */

	@Test
	public void deleteTeamDriver() {
		final Object[][] testingData = {
			//actor, matchId , expected exception
			{
				"manager1", 125, null
			}, {
				"customer1", 121, IllegalArgumentException.class
			}, {
				"admin", 122, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMatchTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void deleteMatchTemplated(final String principal, final int idMatch, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			final Match match = this.matchService.findOne(idMatch);

			this.matchService.delete(match);
			this.unauthenticate();
			this.matchService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
