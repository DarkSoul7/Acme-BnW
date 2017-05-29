package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BetService;
import services.MarketService;

import com.google.common.base.Splitter;

import domain.Bet;
import domain.Market;

@RequestMapping(value = "/bet")
@Controller
public class BetController extends AbstractController {

	//Related services

	@Autowired
	private BetService		betService;

	@Autowired
	private MarketService	marketService;


	//Constructor

	public BetController() {
		super();
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ModelAndView history() {
		ModelAndView result;
		Collection<Bet> bets;

		bets = this.betService.findAllByCustomer();

		result = new ModelAndView("bet/history");
		result.addObject("bets", bets);
		result.addObject("requestURI", "bet/history.do");

		return result;
	}

	@RequestMapping(value = "/pendingBets", method = RequestMethod.GET)
	public ModelAndView pendingBets(@RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Bet> bets;

		bets = this.betService.findAllPendingByCustomer();

		result = new ModelAndView("bet/pendingBets");
		result.addObject("bets", bets);
		result.addObject("successMessage", successMessage);
		result.addObject("requestURI", "bet/pendingBets.do");

		return result;
	}

	@RequestMapping(value = "/showSelection", method = RequestMethod.GET)
	public ModelAndView showSelection(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Bet> bets;

		bets = this.betService.findAllSelectedByCustomer();

		result = new ModelAndView("bet/showSelection");
		result.addObject("bets", bets);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
		result.addObject("requestURI", "bet/showSelection.do");

		return result;
	}

	@RequestMapping(value = "/simpleBet", method = RequestMethod.GET)
	public ModelAndView simpleBet(Integer matchId, Integer marketId, Double quantity, Integer betId) {
		ModelAndView result;
		Bet bet;
		Market market;

		try {
			market = this.marketService.findOne(marketId);
			if (betId != null) {
				bet = this.betService.completeSelectedBet(betId, quantity, market);
			} else {
				bet = this.betService.createSimple(quantity, market);
			}

			this.betService.save(bet, true);

			result = new ModelAndView("redirect:/bet/pendingBets.do");
			result.addObject("successMessage", "bet.simple.success");
		} catch (IllegalStateException e) {
			result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + matchId);
			result.addObject("errorMessage", "bet.balance.error");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + matchId);
			result.addObject("errorMessage", "bet.simple.error");
		}

		return result;
	}

	@RequestMapping(value = "/multipleBet", method = RequestMethod.GET)
	public ModelAndView multipleBet(String betsIdsStr, Double quantity) {
		ModelAndView result;
		List<String> betsIds = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(betsIdsStr);

		if (betsIds.size() == 0) {
			result = new ModelAndView("redirect:/bet/showSelection.do");
			result.addObject("errorMessage", "bet.multiple.size.error");
		} else {
			try {
				this.betService.saveMultipleBet(betsIds, quantity);

				result = new ModelAndView("redirect:/bet/pendingBets.do");
				result.addObject("successMessage", "bet.multiple.success");
			} catch (IllegalArgumentException e) {
				result = new ModelAndView("redirect:/bet/showSelection.do");
				result.addObject("errorMessage", "bet.sameMatch.error");
			} catch (IllegalStateException e) {
				result = new ModelAndView("redirect:/bet/showSelection.do");
				result.addObject("errorMessage", "bet.balance.error");
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/bet/showSelection.do");
				result.addObject("errorMessage", "bet.multiple.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/multipleBetDetails", method = RequestMethod.GET)
	public ModelAndView multipleBetDetails(Integer betId) {
		ModelAndView result;
		Bet multipleBet;

		multipleBet = this.betService.findOne(betId);

		result = new ModelAndView("bet/multipleBetDetails");
		result.addObject("multipleBet", multipleBet);
		result.addObject("requestURI", "bet/multipleBetDetails.do");

		return result;
	}

	@RequestMapping(value = "/addSelection", method = RequestMethod.GET)
	public ModelAndView addSelection(Integer matchId, Integer marketId) {
		ModelAndView result;
		Bet bet;
		Market market;

		try {
			market = this.marketService.findOne(marketId);
			bet = this.betService.createDefault(market);
			this.betService.save(bet, true);

			result = new ModelAndView("redirect:/bet/showSelection.do");
			result.addObject("successMessage", "bet.addSelection.success");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + matchId);
			result.addObject("errorMessage", "bet.addSelection.error");
		}

		return result;
	}

	@RequestMapping(value = "/removeSelection", method = RequestMethod.GET)
	public ModelAndView removeSelection(Integer matchId, Integer betId) {
		ModelAndView result;
		String errorMessage;
		String successMessage;

		try {
			this.betService.delete(betId);

			errorMessage = null;
			successMessage = "bet.removeSelection.success";
		} catch (Throwable oops) {
			errorMessage = "bet.addSelection.error";
			successMessage = null;
		}

		result = new ModelAndView("redirect:/bet/showSelection.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	//Ancillary methods

	protected ModelAndView listModelAndView(boolean isHistory) {
		return this.listModelAndView(isHistory, null, null);
	}

	protected ModelAndView listModelAndView(boolean isHistory, String errorMessage, String successMessage) {
		ModelAndView result;
		String viewName;
		Collection<Bet> bets;

		if (isHistory) {
			viewName = "history";
			bets = this.betService.findAllByCustomer();
		} else {
			viewName = "pendingBets";
			bets = this.betService.findAllPendingByCustomer();
		}

		result = new ModelAndView("bet/" + viewName);
		result.addObject("bets", bets);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

}