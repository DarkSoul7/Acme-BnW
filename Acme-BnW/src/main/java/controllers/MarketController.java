
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Market;
import domain.Match;
import forms.MarketForm;
import services.MarketService;
import services.MatchService;

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
	public ModelAndView listByMatch(@RequestParam int matchId) {
		ModelAndView result;
		Collection<Market> markets;

		markets = marketService.marketsOfMatches(matchId);

		result = new ModelAndView("market/list");
		result.addObject("markets", markets);
		result.addObject("requestURI", "markets/listByMatch.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Market> markets;

		markets = marketService.findAll();

		result = new ModelAndView("market/list");
		result.addObject("markets", markets);
		result.addObject("requestURI", "market/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		MarketForm marketForm = marketService.create();
		result = this.createModelAndView(marketForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int marketId) {
		ModelAndView result = new ModelAndView();

		Market market = marketService.findOne(marketId);
		MarketForm marketForm = marketService.toFormObject(market);
		result = this.createEditModelAndView(marketForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MarketForm marketForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Market market = new Market();

		market = marketService.reconstruct(marketForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(marketForm);
		} else {
			try {
				marketService.save(market);
				result = new ModelAndView("redirect:/market/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(marketForm, "market.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid MarketForm marketForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Market market = new Market();

		market = marketService.reconstruct(marketForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(marketForm);
		} else {
			try {
				marketService.save(market);
				result = new ModelAndView("redirect:/market/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(marketForm, "market.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int marketId) {
		ModelAndView result = new ModelAndView();

		Market market = marketService.findOne(marketId);
		try {
			marketService.delete(market);
			result = new ModelAndView("redirect:/market/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/market/list.do");
			result.addObject("errorMessage", "market.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final MarketForm marketForm) {
		return this.createModelAndView(marketForm, null);
	}

	protected ModelAndView createModelAndView(final MarketForm marketForm, final String message) {
		ModelAndView result;

		Collection<Match> matches = matchService.findAll();
		result = new ModelAndView("market/register");
		result.addObject("marketForm", marketForm);
		result.addObject("message", message);
		result.addObject("matches", matches);
		result.addObject("RequestURI", "market/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final MarketForm marketForm) {
		return this.createEditModelAndView(marketForm, null);
	}

	protected ModelAndView createEditModelAndView(final MarketForm marketForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("market/edit");
		result.addObject("marketForm", marketForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "market/edit.do");

		return result;
	}

}
