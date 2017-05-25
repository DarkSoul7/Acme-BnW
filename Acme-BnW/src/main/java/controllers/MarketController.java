
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Market;
import services.MarketService;

@RequestMapping(value = "/market")
@Controller
public class MarketController extends AbstractController {

	//Related services

	@Autowired
	private MarketService marketService;


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

}
