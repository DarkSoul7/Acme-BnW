package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Fixture;
import repositories.FixtureRepository;

@Service
@Transactional
public class FixtureService {

	// Managed repository

	@Autowired
	private FixtureRepository fixtureRepository;

	// Supported services

	public FixtureService() {
		super();
	}

	public Fixture create() {
		return new Fixture();
	}

	public Collection<Fixture> findAll() {
		return this.fixtureRepository.findAll();
	}

	public Fixture findOne(final int fixtureId) {
		return this.fixtureRepository.findOne(fixtureId);

	}

	public void save(final Fixture fixture) {
		this.fixtureRepository.save(fixture);
	}

	public void delete(final Fixture fixture) {
		this.fixtureRepository.delete(fixture);
	}
}
