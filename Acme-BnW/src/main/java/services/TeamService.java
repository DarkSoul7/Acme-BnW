
package services;

import java.util.ArrayList;
import java.util.Collection;

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
	private TeamRepository teamRepository;


	// Supported services

	public TeamService() {
		super();
	}

	public TeamForm create() {
		TeamForm result = new TeamForm();

		return result;
	}
	public Collection<Team> findAll() {
		return this.teamRepository.findAll();
	}

	public Team findOne(final int teamId) {
		return this.teamRepository.findOne(teamId);

	}

	public void save(final Team team) {
		this.teamRepository.save(team);
	}

	public void delete(final Team team) {
		this.teamRepository.delete(team);
	}


	@Autowired
	private Validator validator;


	public Team reconstruct(TeamForm teamForm, BindingResult binding) {
		Assert.notNull(teamForm);

		Team result = new Team();
		if (teamForm.getId() != 0) {
			result = this.findOne(teamForm.getId());
		} else {
			result.setCustomers(new ArrayList<Customer>());
			result.setLocalMatches(new ArrayList<Match>());
			result.setVisitorMatches(new ArrayList<Match>());
		}

		result.setName(teamForm.getName());
		result.setShield(teamForm.getShield());

		this.validator.validate(result, binding);

		return result;
	}

	public TeamForm toFormObject(Team team) {
		TeamForm result = this.create();
		result.setName(team.getName());
		result.setShield(team.getShield());
		result.setId(team.getId());
		return result;
	}
}
