
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Customer;
import domain.Match;
import domain.Team;
import forms.TeamForm;
import repositories.TeamRepository;

@Service
@Transactional
public class TeamService {

	// Managed repository

	@Autowired
	private TeamRepository	teamRepository;

	@Autowired
	private ManagerService	managerService;


	// Supported services

	public TeamService() {
		super();
	}

	public TeamForm create() {
		final TeamForm result = new TeamForm();

		return result;
	}
	public Collection<Team> findAll() {
		return this.teamRepository.findAll();
	}

	public Team findOne(final int teamId) {
		return this.teamRepository.findOne(teamId);

	}

	public void save(final Team team) {
		managerService.findByPrincipal();
		this.teamRepository.save(team);
	}

	public void delete(final Team team) {
		managerService.findByPrincipal();
		this.teamRepository.delete(team);
	}


	@Autowired
	private Validator validator;


	public Team reconstruct(final TeamForm teamForm, final BindingResult binding) {
		Assert.notNull(teamForm);

		Team result = new Team();
		if (teamForm.getId() != 0)
			result = this.findOne(teamForm.getId());
		else {
			result.setCustomers(new ArrayList<Customer>());
			result.setLocalMatches(new ArrayList<Match>());
			result.setVisitorMatches(new ArrayList<Match>());
		}

		result.setName(teamForm.getName());
		result.setShield(teamForm.getShield());

		this.validator.validate(result, binding);

		return result;
	}

	public TeamForm toFormObject(final Team team) {
		final TeamForm result = this.create();
		result.setName(team.getName());
		result.setShield(team.getShield());
		result.setId(team.getId());
		return result;
	}

	//Dashboard

	//C (8) y (9)

	/**
	 * Devuelve un lista que contiene:
	 * En primera posicion (0) la lista ordenada descendentemente por los equipos más apostados
	 * En segunda posición (1) la lista ordenada descendentemente por los equipos menos apostados
	 */
	public List<List<Team>> teamStatisticsBets() {

		final List<List<Team>> result = new ArrayList<>();

		final ArrayList<Team> moreBetTeams = new ArrayList<>();
		ArrayList<Team> lessBetTeams;
		final Map<Team, Long> map = new HashMap<Team, Long>();
		final Collection<Object[]> localBets = this.teamRepository.betsByLocalTeam();
		final Collection<Object[]> visitorBets = this.teamRepository.betsByVisitorTeam();

		localBets.addAll(visitorBets);
		for (final Object[] objArray : localBets) {
			final Team team = (Team) objArray[0];
			final Long bets = (Long) objArray[1];

			if (map.containsKey(team)) {
				final Long accumBets = map.get(team);
				map.put(team, bets + accumBets);
			} else
				map.put(team, bets);
		}

		for (final Team team : map.keySet())
			if (moreBetTeams.isEmpty())
				moreBetTeams.add(team);
			else {
				final Team firstPosition = moreBetTeams.get(0);
				if (map.get(team) >= map.get(firstPosition))
					moreBetTeams.add(0, team);
				else
					moreBetTeams.add(team);
			}

		lessBetTeams = new ArrayList<>(moreBetTeams);
		Collections.reverse(lessBetTeams);

		result.add(moreBetTeams);
		result.add(lessBetTeams);

		return result;
	}

	public void flush() {
		teamRepository.flush();
	}
}
