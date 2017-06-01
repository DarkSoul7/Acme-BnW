
package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.MarketService;
import services.MatchService;
import domain.Market;
import domain.MarketType;
import domain.Match;
import forms.MarketForm;
import forms.MarketResponseForm;

@RequestMapping(value = "/market")
@Controller
public class MarketController extends AbstractController {

	//Related services

	@Autowired
	private MarketService	marketService;

	@Autowired
	private MatchService	matchService;


	//Constructor

	public MarketController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/listByMatch", method = RequestMethod.GET)
	public ModelAndView listByMatch(@RequestParam final int matchId, @RequestParam(required = false) final String errorMessage, @RequestParam(required = false) final String successMessage) {
		ModelAndView result;
		Match match;
		Collection<Market> markets;

		match = this.matchService.findOne(matchId);
		markets = this.marketService.marketsOfMatches(matchId);

		result = new ModelAndView("market/list");
		result.addObject("markets", markets);
		result.addObject("match", match);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
		result.addObject("requestURI", "markets/listByMatch.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer marketId, @RequestParam(required = false) final String errorMessage,
		@RequestParam(required = false) final String successMessage) {
		ModelAndView result;
		Collection<Market> markets;
		final Date currentDate = new Date();

		if (marketId != null) {
			markets = new ArrayList<Market>();
			markets.add(this.marketService.findOne(marketId));
		} else {
			markets = this.marketService.findAll();
		}

		result = new ModelAndView("market/list");
		result.addObject("markets", markets);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
		result.addObject("currentDate", currentDate);
		result.addObject("requestURI", "market/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		final MarketForm marketForm = this.marketService.create();
		result = this.createModelAndView(marketForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int marketId) {
		ModelAndView result = new ModelAndView();

		final Market market = this.marketService.findOne(marketId);
		final MarketForm marketForm = this.marketService.toFormObject(market);
		result = this.editModelAndView(marketForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MarketForm marketForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Market market = new Market();
		Boolean incorrectMatch = false;
		market = this.marketService.reconstruct(marketForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(marketForm);
		} else {
			try {
				final Date now = new Date();
				if (market.getMatch().getEndMoment().before(now)) {
					incorrectMatch = true;
					throw new IllegalArgumentException();
				}

				this.marketService.save(market);
				result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + market.getMatch().getId());
				result.addObject("successMessage", "market.register.success");
			} catch (final Throwable oops) {
				if (incorrectMatch == true) {
					result = this.createModelAndView(marketForm, "market.match.error");
				} else {
					result = this.createModelAndView(marketForm, "market.register.error");
				}

			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final MarketForm marketForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Market market;
		Boolean incorrectMatch = false;

		market = this.marketService.reconstruct(marketForm, binding);
		if (binding.hasErrors()) {
			result = this.editModelAndView(marketForm);
		} else {
			try {
				final Date now = new Date();
				if (market.getMatch().getEndMoment().before(now)) {
					incorrectMatch = true;
					throw new IllegalArgumentException();
				}

				this.marketService.save(market);
				result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + market.getMatch().getId());
				result.addObject("successMessage", "market.edit.success");
			} catch (final Throwable oops) {
				if (incorrectMatch == true) {
					result = this.createModelAndView(marketForm, "market.match.error");
				} else {
					result = this.createModelAndView(marketForm, "market.register.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int marketId) {
		ModelAndView result = new ModelAndView();
		Market market;
		String errorMessage = null;
		String successMessage = null;

		try {
			market = this.marketService.findOne(marketId);
			this.marketService.delete(market);

			successMessage = "market.delete.success";
		} catch (final Throwable e) {
			errorMessage = "market.delete.error";
		}

		result = new ModelAndView("redirect:/market/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final MarketForm marketForm) {
		return this.createModelAndView(marketForm, null);
	}

	protected ModelAndView createModelAndView(final MarketForm marketForm, final String message) {
		ModelAndView result;
		Collection<Match> matches;
		Collection<MarketType> marketTypes;

		matches = this.matchService.findAll();
		marketTypes = Arrays.asList(MarketType.values());

		result = new ModelAndView("market/register");
		result.addObject("marketForm", marketForm);
		result.addObject("matches", matches);
		result.addObject("marketTypes", marketTypes);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "market/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(final MarketForm marketForm) {
		return this.editModelAndView(marketForm, null);
	}

	protected ModelAndView editModelAndView(final MarketForm marketForm, final String errorMessage) {
		ModelAndView result;
		Match match;

		match = this.matchService.findOne(marketForm.getIdMatch());

		result = new ModelAndView("market/edit");
		result.addObject("marketForm", marketForm);
		result.addObject("match", match);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "market/edit.do");

		return result;
	}

	// AJAX

	@RequestMapping(value = "/updateFees", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Collection<MarketResponseForm> updateFees(@RequestParam(required = false) final Integer matchId) {
		Collection<MarketResponseForm> result;

		if (matchId == null) {
			result = this.marketService.findAllMarketsFees();
		} else {
			result = this.marketService.findAllMarketsFeesFromMatch(matchId);
		}

		return result;
	}

}
