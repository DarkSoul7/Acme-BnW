
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
import domain.Sport;
import domain.Tournament;
import forms.TournamentForm;
import repositories.TournamentRepository;

@Service
@Transactional
public class TournamentService {

	// Managed repository

	@Autowired
	private TournamentRepository	tournamentRepository;

	@Autowired
	private ManagerService			managerService;


	// Supported services

	public TournamentService() {
		super();
	}

	public TournamentForm create() {
		TournamentForm result = new TournamentForm();

		return result;
	}

	public Collection<Tournament> findAll() {
		return this.tournamentRepository.findAll();
	}

	public Tournament findOne(final int tournamentId) {
		return this.tournamentRepository.findOne(tournamentId);

	}

	public void save(final Tournament tournament) {
		Assert.isTrue(tournament.getStartMoment().after(new Date()));
		Assert.isTrue(tournament.getEndMoment().after(tournament.getStartMoment()));
		this.tournamentRepository.save(tournament);
	}

	public void delete(final Tournament tournament) {
		Assert.isTrue(tournament.getCategories().size() == 0);
		managerService.findByPrincipal();
		this.tournamentRepository.delete(tournament);
	}

	public Collection<Tournament> listTournamentOfFootball() {
		return tournamentRepository.listTournamentOfFootball();
	}

	public Collection<Tournament> listTournamentOfBasketball() {
		return tournamentRepository.listTournamentOfBasketball();
	}


	@Autowired
	private Validator validator;


	public Tournament reconstruct(TournamentForm tournamentForm, BindingResult binding) {
		Assert.notNull(tournamentForm);

		Tournament result = new Tournament();
		if (tournamentForm.getId() != 0) {
			result = this.findOne(tournamentForm.getId());
		} else {
			result.setCategories(new ArrayList<Category>());
		}

		result.setName(tournamentForm.getName());
		result.setStartMoment(tournamentForm.getStartMoment());
		result.setDescription(tournamentForm.getDescription());
		result.setEndMoment(tournamentForm.getEndMoment());
		result.setSport(tournamentForm.getSport());

		this.validator.validate(result, binding);

		return result;
	}

	public TournamentForm toFormObject(Tournament tournament) {
		TournamentForm result = this.create();
		result.setName(tournament.getName());
		result.setStartMoment(tournament.getStartMoment());
		result.setDescription(tournament.getDescription());
		result.setEndMoment(tournament.getEndMoment());
		result.setSport(tournament.getSport());
		result.setId(tournament.getId());
		return result;
	}

	public void flush() {
		tournamentRepository.flush();
	}

	//C6
	public Sport SportMoreBets() {
		return tournamentRepository.SportMoreBets();
	}

	//C7
	public Sport SportLessBets() {
		return tournamentRepository.SportLessBets();
	}
}
