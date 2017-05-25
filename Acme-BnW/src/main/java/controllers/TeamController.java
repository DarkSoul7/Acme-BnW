
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Team;
import services.TeamService;

@RequestMapping(value = "/team")
@Controller
public class TeamController extends AbstractController {

	//Related services

	@Autowired
	private TeamService teamService;


	//Constructor

	public TeamController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Team> teams;

		teams = teamService.findAll();

		result = new ModelAndView("team/list");
		result.addObject("teams", teams);
		result.addObject("requestURI", "team/list.do");

		return result;
	}

}
