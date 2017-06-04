
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Bet;
import domain.Market;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BetServiceTest extends AbstractTest {

	//Services under test

	@Autowired
	private BetService		betService;

	@Autowired
	private MarketService	marketService;


	//Tests

	/***
	 * Making a simple bet
	 * 1º Good test -> expected: bet made
	 * 2º Bad test -> an unauthenticated actor cannot make a bet, expected: IllegalArgumentException
	 * 3º Bad test -> an administrator cannot make a bet, expected: IllegalArgumentException
	 */

	@Test
	public void makingSimpleBetDriver() {
		final Object[][] testingData = {
			// actor, expected exception
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.makingSimpleBetTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void makingSimpleBetTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {

			this.authenticate(principal);

			final Market market = this.marketService.findOne(137);
			final Bet bet = this.betService.createSimple(10.3, market);
			this.betService.save(bet, true, false);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}

	//Tests

	/***
	 * Making a multiple bet
	 * 1º Good test -> expected: multiple bet made
	 * 2º Bad test -> an unauthenticated actor cannot make a bet, expected: IllegalArgumentException
	 * 3º Bad test -> an administrator cannot make a bet, expected: IllegalArgumentException
	 */

	@Test
	public void makingMultipleBetDriver() {
		final Object[][] testingData = {
			// actor, expected exception
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.makingMultipleBetTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void makingMultipleBetTemplate(final String principal, final Class<?> expectedException) {
		Class<?> caught = null;

		try {

			this.authenticate(principal);

			final Market market1 = this.marketService.findOne(137);
			final Market market2 = this.marketService.findOne(136);
			final Bet multipleBet = this.betService.createMultiple(3.0);
			final Bet bet1 = this.betService.createDefault(market1);
			final Bet bet2 = this.betService.createDefault(market2);
			final Collection<Bet> childrenBets = new ArrayList<>();
			childrenBets.add(bet1);
			childrenBets.add(bet2);
			multipleBet.setChildrenBets(childrenBets);
			this.betService.save(multipleBet, true, false);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expectedException, caught);
	}
}
