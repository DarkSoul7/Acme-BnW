package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Match;
import repositories.MatchRepository;

@Service
@Transactional
public class MatchService {

	// Managed repository

	@Autowired
	private MatchRepository matchRepository;

	// Supported services

	public MatchService() {
			super();
		}

	public Match create() {
		return new Match();
	}

	public Collection<Match> findAll() {
		return this.matchRepository.findAll();
	}

	public Match findOne(final int matchId) {
		return this.matchRepository.findOne(matchId);

	}

	public void save(final Match match) {
		this.matchRepository.save(match);
	}

	public void delete(final Match match) {
		this.matchRepository.delete(match);
	}
}
