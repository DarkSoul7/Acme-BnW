
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Tournament;
import services.TournamentService;

@RequestMapping(value = "/tournament")
@Controller
public class TournamentController extends AbstractController {

	//Related services

	@Autowired
	private TournamentService tournamentService;


	//Constructor

	public TournamentController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tournament> tournaments;

		tournaments = tournamentService.findAll();

		result = new ModelAndView("tournament/list");
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "tournament/list.do");

		return result;
	}

}