
package controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Sport;
import domain.Tournament;
import forms.TournamentForm;
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

	@RequestMapping(value = "/listOfBasketball", method = RequestMethod.GET)
	public ModelAndView listOfBasketball() {
		ModelAndView result;
		Collection<Tournament> tournaments;

		tournaments = tournamentService.listTournamentOfBasketball();

		result = new ModelAndView("tournament/list");
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "tournament/listOfBasketball.do");

		return result;
	}

	@RequestMapping(value = "/listOfFootball", method = RequestMethod.GET)
	public ModelAndView listOfFootball() {
		ModelAndView result;
		Collection<Tournament> tournaments;

		tournaments = tournamentService.listTournamentOfFootball();

		result = new ModelAndView("tournament/list");
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "tournament/listOfFootball.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		TournamentForm tournamentForm = tournamentService.create();
		result = this.createModelAndView(tournamentForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tournamentId) {
		ModelAndView result = new ModelAndView();

		Tournament tournament = tournamentService.findOne(tournamentId);
		TournamentForm tournamentForm = tournamentService.toFormObject(tournament);
		result = this.createEditModelAndView(tournamentForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TournamentForm tournamentForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Tournament tournament = new Tournament();

		tournament = tournamentService.reconstruct(tournamentForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(tournamentForm);
		} else {
			try {
				tournamentService.save(tournament);
				result = new ModelAndView("redirect:/tournament/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(tournamentForm, "tournament.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid TournamentForm tournamentForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Tournament tournament = new Tournament();

		tournament = tournamentService.reconstruct(tournamentForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(tournamentForm);
		} else {
			try {
				tournamentService.save(tournament);
				result = new ModelAndView("redirect:/tournament/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(tournamentForm, "tournament.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int tournamentId) {
		ModelAndView result = new ModelAndView();

		Tournament tournament = tournamentService.findOne(tournamentId);
		try {
			tournamentService.delete(tournament);
			result = new ModelAndView("redirect:/tournament/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/tournament/list.do");
			result.addObject("errorMessage", "tournament.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final TournamentForm tournamentForm) {
		return this.createModelAndView(tournamentForm, null);
	}

	protected ModelAndView createModelAndView(final TournamentForm tournamentForm, final String message) {
		ModelAndView result;

		List<Sport> sports = Arrays.asList(Sport.values());
		result = new ModelAndView("tournament/register");
		result.addObject("tournamentForm", tournamentForm);
		result.addObject("message", message);
		result.addObject("sports", sports);
		result.addObject("RequestURI", "tournament/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final TournamentForm tournamentForm) {
		return this.createEditModelAndView(tournamentForm, null);
	}

	protected ModelAndView createEditModelAndView(final TournamentForm tournamentForm, final String message) {
		ModelAndView result;

		List<Sport> sports = Arrays.asList(Sport.values());
		result = new ModelAndView("tournament/edit");
		result.addObject("tournamentForm", tournamentForm);
		result.addObject("message", message);
		result.addObject("sports", sports);
		result.addObject("RequestURI", "tournament/edit.do");

		return result;
	}

}
