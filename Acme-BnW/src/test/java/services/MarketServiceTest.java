
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Bet;
import domain.Market;
import domain.MarketType;
import domain.Match;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MarketServiceTest extends AbstractTest {

	@Autowired
	private MarketService	marketService;

	@Autowired
	private MatchService	matchService;

	@Autowired
	private BetService		betService;


	/***
	 * Register teams
	 * 1º Good test -> expected: market registered
	 * 2º Bad test -> cannot register market without title
	 * 3º Bad test -> cannot register market without fee
	 */

	@Test
	public void registerMarketDriver() {
		final Object[][] testingData = {
			// actor, title, fee, idMatch expected exception
			{
				"manager1", MarketType.LOCALVICTORY, 10.0, 125, null
			}, {
				"manager1", null, 30.0, 125, ConstraintViolationException.class
			}, {
				"manager1", MarketType.TIE, null, 125, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMarketTemplated((String) testingData[i][0], (MarketType) testingData[i][1], (Double) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerMarketTemplated(final String principal, final MarketType type, final Double fee, final Integer matchId, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Match match = this.matchService.findOne(matchId);
			final Market market = new Market();
			market.setType(type);
			market.setFee(fee);
			//			market.setPromotions(new ArrayList<Promotion>());
			market.setMatch(match);
			market.setBets(new ArrayList<Bet>());

			market.getMatch().setEndMoment(new DateTime().plusMonths(3).toDate());

			this.marketService.save(market);
			this.unauthenticate();
			this.marketService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit teams
	 * 1º Good test -> expected: market edited
	 * 2º Bad test -> cannot edit market with negative fee
	 * 3º Bad test -> cannot edit market without fee
	 */

	@Test
	public void editMarketDriver() {
		final Object[][] testingData = {
			// actor, marketId, fee, expected exception
			{
				"manager1", 129, 10., null
			}, {
				"manager1", 129, -20.0, IllegalArgumentException.class
			}, {
				"manager1", 129, null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editMarketTemplated((String) testingData[i][0], (int) testingData[i][1], (Double) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	protected void editMarketTemplated(final String principal, final int marketId, final Double fee, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Market market = this.marketService.findOne(marketId);
			market.setFee(fee);
			market.getMatch().setEndMoment(new DateTime().plusMonths(5).toDate());
			this.marketService.save(market);
			this.unauthenticate();
			this.marketService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete markets
	 * 1º Good test -> expected: market deleted
	 * 2º Bad test -> an customer cannot delete market
	 * 3º Bad test -> an admin cannot delete market
	 */

	@Test
	public void deleteMarketDriver() {
		final Object[][] testingData = {
			// actor , expected exception
			{
				"manager1", null
			}, {
				"customer1", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMarketTemplated((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void deleteMarketTemplated(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Match match = this.matchService.findOne(125);
			Market market = new Market();
			market.setType(MarketType.LOCALVICTORY);
			market.setFee(20.);
			market.setMatch(match);
			market.setBets(new ArrayList<Bet>());
			market = this.marketService.save(market);

			this.marketService.delete(market);
			this.unauthenticate();
			this.marketService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
