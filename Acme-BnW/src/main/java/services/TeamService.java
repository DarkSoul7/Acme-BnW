package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Team;
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

	public Team create() {
		return new Team();
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
}
