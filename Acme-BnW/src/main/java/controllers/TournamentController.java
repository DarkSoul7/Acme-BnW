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

import services.TournamentService;
import domain.Sport;
import domain.Tournament;
import forms.TournamentForm;

@RequestMapping(value = "/tournament")
@Controller
public class TournamentController extends AbstractController {

	//Related services

	@Autowired
	private TournamentService	tournamentService;


	//Constructor

	public TournamentController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Tournament> tournaments;

		tournaments = this.tournamentService.findAll();

		result = new ModelAndView("tournament/list");
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "tournament/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/listOfBasketball", method = RequestMethod.GET)
	public ModelAndView listOfBasketball(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Tournament> tournaments;

		tournaments = this.tournamentService.listTournamentOfBasketball();

		result = new ModelAndView("tournament/list");
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "tournament/listOfBasketball.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/listOfFootball", method = RequestMethod.GET)
	public ModelAndView listOfFootball(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Tournament> tournaments;

		tournaments = this.tournamentService.listTournamentOfFootball();

		result = new ModelAndView("tournament/list");
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "tournament/listOfFootball.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		TournamentForm tournamentForm = this.tournamentService.create();
		result = this.createModelAndView(tournamentForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tournamentId) {
		ModelAndView result = new ModelAndView();

		Tournament tournament = this.tournamentService.findOne(tournamentId);
		TournamentForm tournamentForm = this.tournamentService.toFormObject(tournament);
		result = this.editModelAndView(tournamentForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TournamentForm tournamentForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Tournament tournament = new Tournament();

		tournament = this.tournamentService.reconstruct(tournamentForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(tournamentForm);
		} else {
			try {
				this.tournamentService.save(tournament);
				result = new ModelAndView("redirect:/tournament/list.do");
				result.addObject("successMessage", "tournament.register.success");
			} catch (Throwable oops) {
				result = this.createModelAndView(tournamentForm, "tournament.register.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid TournamentForm tournamentForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Tournament tournament = new Tournament();

		tournament = this.tournamentService.reconstruct(tournamentForm, binding);
		if (binding.hasErrors()) {
			result = this.editModelAndView(tournamentForm);
		} else {
			try {
				this.tournamentService.save(tournament);
				result = new ModelAndView("redirect:/tournament/list.do");
				result.addObject("successMessage", "tournament.edit.success");
			} catch (Throwable oops) {
				result = this.editModelAndView(tournamentForm, "tournament.edit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int tournamentId) {
		ModelAndView result = new ModelAndView();

		Tournament tournament = this.tournamentService.findOne(tournamentId);
		try {
			this.tournamentService.delete(tournament);
			result = new ModelAndView("redirect:/tournament/list.do");
			result.addObject("successMessage", "tournament.delete.success");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/tournament/list.do");
			result.addObject("errorMessage", "tournament.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(TournamentForm tournamentForm) {
		return this.createModelAndView(tournamentForm, null);
	}

	protected ModelAndView createModelAndView(TournamentForm tournamentForm, String errorMessage) {
		ModelAndView result;

		List<Sport> sports = Arrays.asList(Sport.values());
		result = new ModelAndView("tournament/register");
		result.addObject("tournamentForm", tournamentForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("sports", sports);
		result.addObject("requestURI", "tournament/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(TournamentForm tournamentForm) {
		return this.editModelAndView(tournamentForm, null);
	}

	protected ModelAndView editModelAndView(TournamentForm tournamentForm, String errorMessage) {
		ModelAndView result;

		List<Sport> sports = Arrays.asList(Sport.values());
		result = new ModelAndView("tournament/edit");
		result.addObject("tournamentForm", tournamentForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("sports", sports);
		result.addObject("requestURI", "tournament/edit.do");

		return result;
	}

}
