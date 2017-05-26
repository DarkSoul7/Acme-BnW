
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

import domain.Team;
import forms.TeamForm;
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

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		TeamForm teamForm = teamService.create();
		result = this.createModelAndView(teamForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int teamId) {
		ModelAndView result = new ModelAndView();

		Team team = teamService.findOne(teamId);
		TeamForm teamForm = teamService.toFormObject(team);
		result = this.createEditModelAndView(teamForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TeamForm teamForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Team team = new Team();

		team = teamService.reconstruct(teamForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(teamForm);
		} else {
			try {
				teamService.save(team);
				result = new ModelAndView("redirect:/team/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(teamForm, "team.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid TeamForm teamForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Team team = new Team();

		team = teamService.reconstruct(teamForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(teamForm);
		} else {
			try {
				teamService.save(team);
				result = new ModelAndView("redirect:/team/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(teamForm, "team.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int teamId) {
		ModelAndView result = new ModelAndView();

		Team team = teamService.findOne(teamId);
		try {
			teamService.delete(team);
			result = new ModelAndView("redirect:/team/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/team/list.do");
			result.addObject("errorMessage", "team.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final TeamForm teamForm) {
		return this.createModelAndView(teamForm, null);
	}

	protected ModelAndView createModelAndView(final TeamForm teamForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("team/register");
		result.addObject("teamForm", teamForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "team/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final TeamForm teamForm) {
		return this.createModelAndView(teamForm, null);
	}

	protected ModelAndView createEditModelAndView(final TeamForm teamForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("team/edit");
		result.addObject("teamForm", teamForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "team/edit.do");

		return result;
	}

}
