
package services;

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
import domain.Promotion;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PromotionServiceTest extends AbstractTest {

	@Autowired
	private PromotionService	promotionService;

	@Autowired
	private MarketService		marketService;


	/***
	 * Register promotions
	 * 1º Good test -> expected: promotion registered
	 * 2º Bad test -> cannot register promotion without title
	 * 3º Bad test -> cannot register category with fee negative
	 */

	@Test
	public void registerPromotionDriver() {
		final Object[][] testingData = {
			// actor, title, description,fee,startMoment,endMoment,idMarket,expected exception
			{
				"manager1", "Title", "Description1", 10.0, new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), 124, null
			}, {
				"manager1", "", "Description1", 10.0, new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), 124, ConstraintViolationException.class
			}, {
				"manager1", "Title", "Description1", -10.0, new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), 124, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.registerPromotionTemplated((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (Date) testingData[i][4], (Date) testingData[i][5], (int) testingData[i][6],
				(Class<?>) testingData[i][7]);
		}
	}

	protected void registerPromotionTemplated(String principal, String title, String description, Double fee, Date startMoment, Date endMoment, int idMarket, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Market market = marketService.findOne(idMarket);

			Promotion promotion = new Promotion();
			promotion.setTitle(title);
			promotion.setStartMoment(startMoment);
			promotion.setDescription(description);
			promotion.setEndMoment(endMoment);
			promotion.setFee(fee);
			promotion.setMarket(market);
			promotion.setCancel(false);

			promotionService.save(promotion);
			this.unauthenticate();
			this.promotionService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Edit promotions
	 * 1º Good test -> expected: promotion edit
	 * 2º Bad test -> cannot edit promotion without description
	 * 3º Bad test -> cannot edit promotion with startMoment past
	 */

	@Test
	public void editPromotionDriver() {
		final Object[][] testingData = {
			// actor, description,startMoment,endMoment,idPromotion,expected exception
			{
				"manager1", "Description1", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), 159, null
			}, {
				"manager1", "", new DateTime().plusDays(2).toDate(), new DateTime().plusDays(10).toDate(), 159, ConstraintViolationException.class
			}, {
				"manager1", "Description1", new DateTime().plusDays(-2).toDate(), new DateTime().plusDays(10).toDate(), 159, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.editPromotionTemplated((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void editPromotionTemplated(String principal, String description, Date startMoment, Date endMoment, int idPromotion, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);

			Promotion promotion = promotionService.findOne(idPromotion);
			promotion.setStartMoment(startMoment);
			promotion.setDescription(description);
			promotion.setEndMoment(endMoment);

			promotionService.save(promotion);
			this.unauthenticate();
			this.promotionService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

	/***
	 * Cancel promotion
	 * 1º Good test -> expected: promotion cancelled
	 * 2º Bad test -> an admin cannot cancel promotion
	 * 3º Bad test -> cannot cancel if promotion was cancel before
	 */

	@Test
	public void cancelPromotionDriver() {
		final Object[][] testingData = {
			//actor, promotionId , expected exception
			{
				"manager1", 159, null
			}, {
				"admin", 162, IllegalArgumentException.class
			}, {
				"manager1", 162, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.cancelPromotionTemplated((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void cancelPromotionTemplated(String principal, int promotionId, Class<?> expectedException) {
		Class<?> caught = null;

		try {
			this.authenticate(principal);
			Promotion promotion = promotionService.findOne(promotionId);
			promotionService.cancel(promotion);
			this.unauthenticate();
			this.promotionService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expectedException, caught);
	}

}
