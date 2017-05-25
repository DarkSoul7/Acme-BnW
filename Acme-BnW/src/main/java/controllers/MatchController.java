
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Match;
import services.MatchService;

@RequestMapping(value = "/match")
@Controller
public class MatchController extends AbstractController {

	//Related services

	@Autowired
	private MatchService matchService;


	//Constructor

	public MatchController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Match> matches;

		matches = matchService.findAll();

		result = new ModelAndView("match/list");
		result.addObject("matches", matches);
		result.addObject("requestURI", "match/list.do");

		return result;
	}

	@RequestMapping(value = "/listByFixture", method = RequestMethod.GET)
	public ModelAndView listByFixture(@RequestParam int fixtureId) {
		ModelAndView result;
		Collection<Match> matches;

		matches = matchService.matchesOfFixture(fixtureId);

		result = new ModelAndView("match/list");
		result.addObject("matches", matches);
		result.addObject("requestURI", "match/listByFixture.do");

		return result;
	}

}
