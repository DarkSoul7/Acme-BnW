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
import domain.Bet;
import domain.Market;
import domain.MarketType;
import domain.Match;

@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MarketServiceTest extends AbstractTest {

	@Autowired
	private MarketService	marketService;

	@Autowired
	private MatchService	matchService;


	/***
	 * Register teams
	 * 1� Good test -> expected: market registered
	 * 2� Bad test -> cannot register market without title
	 * 3� Bad test -> cannot register market without fee
	 */

	@Test
	public void registerMarketDriver() {
		final Object[][] testingData = {
				// actor, title, fee, idMatch expected exception
				{"manager1", MarketType.LOCALVICTORY, 10.0, 112, null},
				{"manager1", null, 30.0, 112, ConstraintViolationException.class},
				{"manager1", MarketType.TIE, null, 112, IllegalArgumentException.class}};

		for (int i = 0; i < testingData.length; i++) {
			this.registerMarketTemplated((String) testingData[i][0], (MarketType) testingData[i][1],
					(Double) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerMarketTemplated(String principal, MarketType type, Double fee, Integer matchId,
			Class<?> expectedException) {
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
	 * 1� Good test -> expected: market edited
	 * 2� Bad test -> cannot edit market with negative fee
	 * 3� Bad test -> cannot edit market without fee
	 */

	@Test
	public void editMarketDriver() {
		final Object[][] testingData = {
				// actor, marketId, fee, expected exception
				{"manager1", 119, 10., null},
				{"manager1", 119, -20., IllegalArgumentException.class},
				{"manager1", 119, null, IllegalArgumentException.class}};

		for (int i = 0; i < testingData.length; i++) {
			this.editMarketTemplated((String) testingData[i][0], (int) testingData[i][1],
					(Double) testingData[i][2], (Class<?>) testingData[i][3]);
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
	 * 1� Good test -> expected: market deleted
	 * 2� Bad test -> an customer cannot delete market
	 * 3� Bad test -> an admin cannot delete market
	 */

	@Test
	public void deleteMarketDriver() {
		final Object[][] testingData = {
				// actor , expected exception
				{"manager1", null},
				{"customer1", IllegalArgumentException.class},
				{"admin", IllegalArgumentException.class}};

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMarketTemplated((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void deleteMarketTemplated(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			final Match match = this.matchService.findOne(112);
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
}
