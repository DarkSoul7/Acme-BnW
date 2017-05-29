package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MatchRepository;
import domain.Fixture;
import domain.Market;
import domain.Match;
import domain.Team;
import forms.MatchForm;
import forms.ResultForm;

@Service
@Transactional
public class MatchService {

	// Managed repository

	@Autowired
	private MatchRepository	matchRepository;

	// Supported services
	@Autowired
	private TeamService		teamService;

	@Autowired
	private FixtureService	fixtureService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private Validator		validator;


	public MatchService() {
		super();
	}

	public MatchForm create() {
		MatchForm result = new MatchForm();

		return result;
	}

	public Collection<Match> findAll() {
		return this.matchRepository.findAll();
	}

	public Match findOne(final int matchId) {
		return this.matchRepository.findOne(matchId);

	}

	public void save(final Match match) {
		Assert.isTrue(match.getEndMoment().after(new Date()));
		Assert.isTrue(match.getEndMoment().after(match.getStartMoment()));
		Assert.isTrue(match.getLocalTeam().getId() != match.getVisitorTeam().getId());
		this.matchRepository.save(match);
	}

	public void delete(final Match match) {
		Assert.isTrue(match.getStartMoment().after(new Date()));
		managerService.findByPrincipal();
		this.matchRepository.delete(match);
	}

	public Match reconstruct(MatchForm matchForm, BindingResult binding) {
		Assert.notNull(matchForm);

		Match result = new Match();
		if (matchForm.getId() != 0) {
			result = this.findOne(matchForm.getId());
		} else {
			Team local = teamService.findOne(matchForm.getIdTeamLocal());
			Team visitor = teamService.findOne(matchForm.getIdTeamVisitor());
			Fixture fixture = fixtureService.findOne(matchForm.getIdFixture());
			result.setFixture(fixture);
			result.setLocalTeam(local);
			result.setVisitorTeam(visitor);
			result.setMarkets(new ArrayList<Market>());
			result.setLocalResult(null);
			result.setVisitorResult(null);
		}

		result.setStartMoment(matchForm.getStartMoment());
		result.setEndMoment(matchForm.getEndMoment());

		this.validator.validate(result, binding);

		return result;
	}

	public MatchForm toFormObject(Match match) {
		MatchForm result = this.create();
		result.setStartMoment(match.getStartMoment());
		result.setEndMoment(match.getEndMoment());
		result.setId(match.getId());
		return result;
	}

	public ResultForm toFormResult(int idMatch) {
		ResultForm result = new ResultForm();
		Match match = findOne(idMatch);

		result.setIdMatch(idMatch);
		result.setLocalGoal(match.getLocalResult());
		result.setVisitorGoal(match.getVisitorResult());

		return result;
	}

	public Collection<Match> matchesOfFixtureNonEnded(int id) {
		return matchRepository.matchesOfFixtureNonEnded(id);
	}

	public Collection<Match> findAllNonEnded() {
		return matchRepository.findAllNonEnded();
	}

	public void editResult(ResultForm resultForm) {
		Assert.notNull(resultForm);
		Match match = this.findOne(resultForm.getIdMatch());
		match.setLocalResult(resultForm.getLocalGoal());
		match.setVisitorResult(resultForm.getVisitorGoal());
		matchRepository.save(match);
	}

	public void flush() {
		matchRepository.flush();
	}
}
