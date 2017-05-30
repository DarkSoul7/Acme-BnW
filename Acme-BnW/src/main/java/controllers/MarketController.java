package controllers;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MarketService;
import services.MatchService;
import domain.Market;
import domain.MarketType;
import domain.Match;
import forms.MarketForm;

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
	public ModelAndView listByMatch(@RequestParam int matchId, @RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
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
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Market> markets;

		markets = marketService.findAll();

		result = new ModelAndView("market/list");
		result.addObject("markets", markets);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
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
		result = this.editModelAndView(marketForm);
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
				result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + market.getMatch().getId());
				result.addObject("successMessage", "market.register.success");
			} catch (Throwable oops) {
				result = this.createModelAndView(marketForm, "market.register.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid MarketForm marketForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Market market;

		market = marketService.reconstruct(marketForm, binding);
		if (binding.hasErrors()) {
			result = this.editModelAndView(marketForm);
		} else {
			try {
				marketService.save(market);
				result = new ModelAndView("redirect:/market/listByMatch.do?matchId=" + market.getMatch().getId());
				result.addObject("successMessage", "market.edit.success");
			} catch (Throwable oops) {
				result = this.editModelAndView(marketForm, "market.edit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int marketId) {
		ModelAndView result = new ModelAndView();
		Market market;
		String errorMessage = null;
		String successMessage = null;

		try {
			market = marketService.findOne(marketId);
			marketService.delete(market);

			successMessage = "market.delete.success";
		} catch (Throwable e) {
			errorMessage = "market.delete.error";
		}

		result = new ModelAndView("redirect:/market/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(MarketForm marketForm) {
		return this.createModelAndView(marketForm, null);
	}

	protected ModelAndView createModelAndView(MarketForm marketForm, String message) {
		ModelAndView result;
		Collection<Match> matches;
		Collection<MarketType> marketTypes;

		matches = matchService.findAll();
		marketTypes = Arrays.asList(MarketType.values());

		result = new ModelAndView("market/register");
		result.addObject("marketForm", marketForm);
		result.addObject("matches", matches);
		result.addObject("marketTypes", marketTypes);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "market/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(MarketForm marketForm) {
		return this.editModelAndView(marketForm, null);
	}

	protected ModelAndView editModelAndView(MarketForm marketForm, String errorMessage) {
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

}
