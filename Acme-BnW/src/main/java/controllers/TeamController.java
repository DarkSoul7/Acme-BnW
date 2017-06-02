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

import services.CustomerService;
import services.TeamService;
import domain.Customer;
import domain.Team;
import forms.TeamForm;

@RequestMapping(value = "/team")
@Controller
public class TeamController extends AbstractController {

	//Related services

	@Autowired
	private TeamService		teamService;

	@Autowired
	private CustomerService	customerService;


	//Constructor

	public TeamController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Customer principal;
		Collection<? extends TeamForm> teams;

		try {
			principal = this.customerService.findByPrincipal();
			teams = teamService.findTeamFavourite(principal.getId());
		} catch (Exception e) {
			teams = this.teamService.findAllForms();
		}

		result = new ModelAndView("team/list");
		result.addObject("teams", teams);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
		result.addObject("requestURI", "team/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;

		TeamForm teamForm = teamService.create();
		result = this.createModelAndView(teamForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int teamId) {
		ModelAndView result;

		Team team = teamService.findOne(teamId);
		TeamForm teamForm = teamService.toFormObject(team);
		result = this.editModelAndView(teamForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TeamForm teamForm, BindingResult binding) {
		ModelAndView result;
		Team team = new Team();

		team = teamService.reconstruct(teamForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(teamForm);
		} else {
			try {
				teamService.save(team);
				result = new ModelAndView("redirect:/team/list.do");
				result.addObject("succesMessage", "team.save.success");
			} catch (Throwable oops) {
				result = this.createModelAndView(teamForm, "team.save.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid TeamForm teamForm, BindingResult binding) {
		ModelAndView result;
		Team team;

		team = teamService.reconstruct(teamForm, binding);
		if (binding.hasErrors()) {
			result = this.editModelAndView(teamForm);
		} else {
			try {
				teamService.save(team);
				result = new ModelAndView("redirect:/team/list.do");
				result.addObject("succesMessage", "team.edit.success");
			} catch (Throwable oops) {
				result = this.editModelAndView(teamForm, "team.edit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int teamId) {
		ModelAndView result;
		Team team;

		try {
			team = teamService.findOne(teamId);
			teamService.delete(team);
			result = new ModelAndView("redirect:/team/list.do");
			result.addObject("succesMessage", "team.delete.success");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/team/list.do");
			result.addObject("errorMessage", "team.delete.error");
		}

		return result;
	}

	@RequestMapping(value = "/addFavourite", method = RequestMethod.GET)
	public ModelAndView addFavourite(@RequestParam int teamId) {
		ModelAndView result;
		String errorMessage = null;
		String successMessage = null;

		try {
			this.teamService.addFavourite(teamId);

			successMessage = "team.addFavourite.success";
		} catch (Exception e) {
			errorMessage = "team.addFavourite.error";
		}

		result = new ModelAndView("redirect:/team/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/deleteFavourite", method = RequestMethod.GET)
	public ModelAndView deleteFavourite(@RequestParam int teamId) {
		ModelAndView result;
		String errorMessage = null;
		String successMessage = null;

		try {
			this.teamService.deleteFavourite(teamId);

			successMessage = "team.deleteFavourite.success";
		} catch (Exception e) {
			errorMessage = "team.deleteFavourite.error";
		}

		result = new ModelAndView("redirect:/team/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(TeamForm teamForm) {
		return this.createModelAndView(teamForm, null);
	}

	protected ModelAndView createModelAndView(TeamForm teamForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("team/register");
		result.addObject("teamForm", teamForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("requestURI", "team/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(TeamForm teamForm) {
		return this.editModelAndView(teamForm, null);
	}

	protected ModelAndView editModelAndView(TeamForm teamForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("team/edit");
		result.addObject("teamForm", teamForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("requestURI", "team/edit.do");

		return result;
	}

}
