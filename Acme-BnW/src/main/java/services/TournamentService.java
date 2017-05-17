package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Tournament;
import repositories.TournamentRepository;

@Service
@Transactional
public class TournamentService {

	// Managed repository

	@Autowired
	private TournamentRepository tournamentRepository;

	// Supported services

	public TournamentService() {
			super();
		}

	public Tournament create() {
		return new Tournament();
	}

	public Collection<Tournament> findAll() {
		return this.tournamentRepository.findAll();
	}

	public Tournament findOne(final int tournamentId) {
		return this.tournamentRepository.findOne(tournamentId);

	}

	public void save(final Tournament tournament) {
		this.tournamentRepository.save(tournament);
	}

	public void delete(final Tournament tournament) {
		this.tournamentRepository.delete(tournament);
	}
}
