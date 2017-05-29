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

import services.FixtureService;
import services.MatchService;
import services.TeamService;
import domain.Fixture;
import domain.Match;
import domain.Team;
import forms.MatchForm;
import forms.ResultForm;

@RequestMapping(value = "/match")
@Controller
public class MatchController extends AbstractController {

	//Related services

	@Autowired
	private MatchService	matchService;

	@Autowired
	private TeamService		teamService;

	@Autowired
	private FixtureService	fixtureService;


	//Constructor

	public MatchController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Match> matches;

		matches = matchService.findAllNonEnded();

		result = new ModelAndView("match/list");
		result.addObject("matches", matches);
		result.addObject("requestURI", "match/list.do");

		return result;
	}

	@RequestMapping(value = "/listByFixture", method = RequestMethod.GET)
	public ModelAndView listByFixture(@RequestParam int fixtureId) {
		ModelAndView result;
		Collection<Match> matches;

		matches = matchService.matchesOfFixtureNonEnded(fixtureId);

		result = new ModelAndView("match/list");
		result.addObject("matches", matches);
		result.addObject("requestURI", "match/listByFixture.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		MatchForm matchForm = matchService.create();
		result = this.createModelAndView(matchForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int matchId) {
		ModelAndView result = new ModelAndView();

		Match match = matchService.findOne(matchId);
		MatchForm matchForm = matchService.toFormObject(match);
		result = this.createEditModelAndView(matchForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MatchForm matchForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Match match = new Match();

		match = matchService.reconstruct(matchForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(matchForm);
		} else {
			try {
				matchService.save(match);
				result = new ModelAndView("redirect:/match/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(matchForm, "match.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid MatchForm matchForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Match match = new Match();

		match = matchService.reconstruct(matchForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(matchForm);
		} else {
			try {
				matchService.save(match);
				result = new ModelAndView("redirect:/match/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(matchForm, "match.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int matchId) {
		ModelAndView result = new ModelAndView();

		Match match = matchService.findOne(matchId);
		try {
			matchService.delete(match);
			result = new ModelAndView("redirect:/match/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/match/list.do");
			result.addObject("errorMessage", "match.delete.error");
		}

		return result;
	}

	@RequestMapping(value = "/editResult", method = RequestMethod.GET)
	public ModelAndView editResult(@RequestParam int matchId) {

		ModelAndView result;

		ResultForm resultForm = matchService.toFormResult(matchId);
		result = this.editResultModelAndView(resultForm);

		return result;
	}

	@RequestMapping(value = "/editResult", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRes(@Valid ResultForm resultForm, BindingResult binding) {

		ModelAndView result = new ModelAndView();

		if (binding.hasErrors()) {
			result = this.editResultModelAndView(resultForm);
		} else {
			try {
				matchService.editResult(resultForm);
				result = new ModelAndView("redirect:/match/list.do");
			} catch (Throwable oops) {
				result = editResultModelAndView(resultForm, "result.commit.error");
			}
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final MatchForm matchForm) {
		return this.createModelAndView(matchForm, null);
	}

	protected ModelAndView createModelAndView(final MatchForm matchForm, final String message) {
		ModelAndView result;

		Collection<Team> teams = teamService.findAll();
		Collection<Fixture> fixtures = fixtureService.findAll();
		result = new ModelAndView("match/register");
		result.addObject("matchForm", matchForm);
		result.addObject("message", message);
		result.addObject("teams", teams);
		result.addObject("fixtures", fixtures);
		result.addObject("RequestURI", "match/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final MatchForm matchForm) {
		return this.createEditModelAndView(matchForm, null);
	}

	protected ModelAndView createEditModelAndView(final MatchForm matchForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("match/edit");
		result.addObject("matchForm", matchForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "match/edit.do");

		return result;
	}

	protected ModelAndView editResultModelAndView(final ResultForm resultForm) {
		return this.editResultModelAndView(resultForm, null);
	}

	protected ModelAndView editResultModelAndView(final ResultForm resultForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("match/editResult");
		result.addObject("resultForm", resultForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "match/editResult.do");

		return result;
	}

}
