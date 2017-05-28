package services;

import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Bet;
import domain.Market;
import domain.Match;
import domain.Promotion;
import domain.Status;
import domain.Type;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MarketServiceTest extends AbstractTest {

	@Autowired
	private MarketService marketService;

	@Autowired
	private MatchService matchService;

	/***
	 * Register teams 
	 * 1º Good test -> expected: market registered 
	 * 2º Bad test -> cannot register market without title 
	 * 3º Bad test -> cannot register market without fee
	 */

	@Test
	public void registerMarketDriver() {
		final Object[][] testingData = {
				// actor, title, fee, expected exception
				{ "manager1", "title 1", 10.0, 99, null },
				{ "manager1", "", 30.0, 99, ConstraintViolationException.class },
				{ "manager1", "title 2", null, 99, ConstraintViolationException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.registerMarketTemplated((String) testingData[i][0], (String) testingData[i][1],
					(Double) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void registerMarketTemplated(String principal, String title, Double fee, Integer matchId,
			Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Match match = matchService.findOne(matchId);
			Market market = new Market();
			market.setTitle(title);
			market.setFee(fee);
			market.setPromotions(new ArrayList<Promotion>());
			market.setMatch(match);
			market.setBets(new ArrayList<Bet>());

			marketService.save(market);
			this.unauthenticate();
			this.marketService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit teams 
	 * 1º Good test -> expected: market edited 
	 * 2º Bad test -> cannot edit market without title 
	 * 3º Bad test -> cannot edit market without fee
	 */

	@Test
	public void editMarketDriver() {
		final Object[][] testingData = {
				// actor, marketId ,title, fee, expected exception
				{ "manager1", 103, "name", 10., null },
				{ "manager1", 103, "", 20., ConstraintViolationException.class },
				{ "manager1", 103, "title 3", null, ConstraintViolationException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.editMarketTemplated((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2],
					(Double) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	protected void editMarketTemplated(String principal, int marketId, String title, Double fee,
			Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Market market = marketService.findOne(marketId);
			market.setTitle(title);
			market.setFee(fee);

			marketService.save(market);
			this.unauthenticate();
			this.marketService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Delete markets 
	 * 1º Good test -> expected: market deleted
	 *  2º Bad test -> an customer cannot delete market 
	 *  3º Bad test -> an admin cannot delete market
	 */

	@Test
	public void deleteMarketDriver() {
		final Object[][] testingData = {
				// actor , expected exception
				{ "manager1", null }, 
				{ "customer1", IllegalArgumentException.class },
				{ "admin", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			this.deleteMarketTemplated((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void deleteMarketTemplated(String principal, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Match match = matchService.findOne(99);
			Market market = new Market();
			market.setTitle("Title 1");
			market.setFee(20.);
			market.setPromotions(new ArrayList<Promotion>());
			market.setMatch(match);
			market.setBets(new ArrayList<Bet>());
			market = marketService.save(market);
			
			marketService.delete(market);
			this.unauthenticate();
			this.marketService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}
}
