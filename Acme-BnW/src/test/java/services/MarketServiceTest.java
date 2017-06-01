package services;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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
				"manager1", MarketType.LOCALVICTORY, 10.0, 119, null
		}, {
				"manager1", null, 30.0, 119, ConstraintViolationException.class
		}, {
				"manager1", MarketType.TIE, null, 119, IllegalArgumentException.class
		}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMarketTemplated((String) testingData[i][0], (MarketType) testingData[i][1], (Double) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerMarketTemplated(String principal, MarketType type, Double fee, Integer matchId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Match match = matchService.findOne(matchId);
			Market market = new Market();
			market.setType(type);
			market.setFee(fee);
			//			market.setPromotions(new ArrayList<Promotion>());
			market.setMatch(match);
			market.setBets(new ArrayList<Bet>());

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

	protected void editMarketTemplated(String principal, int marketId, Double fee, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Market market = marketService.findOne(marketId);
			market.setFee(fee);

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
			final Match match = this.matchService.findOne(119);
			Market market = new Market();
			market.setType(MarketType.LOCALVICTORY);
			market.setFee(20.);
			//			market.setPromotions(new ArrayList<Promotion>());
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

	/***
	 * Participar promocion
	 * 1º Good test -> expected: results show
	 * 2º Bad test -> Cannot join promotion cancelled
	 * 3º Bad test -> Cannot join promotion don't start
	 */

	@Test
	public void enjoyPromotionDriver() {

		final Object testingData[][] = {
		//principal, idPromotion,expected exception
		{
				"customer1", 159, null
		}, {
				"customer1", 162, IllegalArgumentException.class
		}, {
				"customer1", 161, IllegalArgumentException.class
		}
		};

		for (int i = 0; i < testingData.length; i++)
			this.enjoyPromotionTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void enjoyPromotionTemplate(final String principal, int idPromotion, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Market market = marketService.enjoyPromotion(idPromotion);

			Assert.notNull(market);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}
}
