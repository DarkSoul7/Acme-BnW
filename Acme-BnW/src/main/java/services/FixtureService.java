
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

import domain.Category;
import domain.Fixture;
import domain.Match;
import forms.FixtureForm;
import repositories.FixtureRepository;

@Service
@Transactional
public class FixtureService {

	// Managed repository

	@Autowired
	private FixtureRepository	fixtureRepository;

	// Supported services
	@Autowired
	private CategoryService		categoryService;


	public FixtureService() {
		super();
	}

	public FixtureForm create() {
		FixtureForm result = new FixtureForm();

		return result;
	}

	public Collection<Fixture> findAll() {
		return this.fixtureRepository.findAll();
	}

	public Fixture findOne(final int fixtureId) {
		return this.fixtureRepository.findOne(fixtureId);

	}

	public void save(final Fixture fixture) {
		Assert.isTrue(fixture.getEndMoment().before(new Date()));
		this.fixtureRepository.save(fixture);
	}

	public void delete(final Fixture fixture) {
		Assert.isTrue(fixture.getEndMoment().before(new Date()));
		this.fixtureRepository.delete(fixture);
	}


	@Autowired
	private Validator validator;


	public Fixture reconstruct(FixtureForm fixtureForm, BindingResult binding) {
		Assert.notNull(fixtureForm);

		Fixture result = new Fixture();
		if (fixtureForm.getId() != 0) {
			result = this.findOne(fixtureForm.getId());
		} else {
			Category category = categoryService.findOne(fixtureForm.getIdCategory());
			result.setMatches(new ArrayList<Match>());
			result.setCategory(category);
		}

		result.setStartMoment(fixtureForm.getStartMoment());
		result.setEndMoment(fixtureForm.getEndMoment());
		result.setTitle(fixtureForm.getTitle());

		this.validator.validate(result, binding);

		return result;
	}

	public FixtureForm toFormObject(Fixture fixture) {
		FixtureForm result = this.create();
		result.setStartMoment(fixture.getStartMoment());
		result.setEndMoment(fixture.getEndMoment());
		result.setTitle(fixture.getTitle());
		result.setId(fixture.getId());
		return result;
	}

	public Collection<Fixture> fixturesOfCategory(int id) {
		return fixtureRepository.fixturesOfCategory(id);
	}
}
