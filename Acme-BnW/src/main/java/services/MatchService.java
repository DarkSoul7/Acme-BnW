
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

import domain.Fixture;
import domain.Market;
import domain.Match;
import domain.Team;
import forms.MatchForm;
import repositories.MatchRepository;

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
		Assert.isTrue(match.getEndMoment().before(new Date()));
		this.matchRepository.save(match);
	}

	public void delete(final Match match) {
		Assert.isTrue(match.getEndMoment().before(new Date()));
		this.matchRepository.delete(match);
	}


	@Autowired
	private Validator validator;


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
}
