
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.WelcomeOfferRepository;
import domain.WelcomeOffer;

@Service
@Transactional
public class WelcomeOfferService {

	// Managed repository

	@Autowired
	private WelcomeOfferRepository	welcomeOfferRepository;


	// Supported services

	//Constructor

	public WelcomeOfferService() {
		super();
	}

	//Simple CRUD methods

	public WelcomeOffer create() {
		return new WelcomeOffer();
	}

	public Collection<WelcomeOffer> findAll() {
		return this.welcomeOfferRepository.findAll();
	}

	public WelcomeOffer findOne(final int welcomeOfferId) {
		return this.welcomeOfferRepository.findOne(welcomeOfferId);

	}

	public void save(final WelcomeOffer welcomeOffer) {
		this.welcomeOfferRepository.save(welcomeOffer);
	}

	public void delete(final WelcomeOffer welcomeOffer) {
		this.welcomeOfferRepository.delete(welcomeOffer);
	}

	//Other business methods

	public WelcomeOffer getActive() {
		return this.welcomeOfferRepository.getActive();
	}
}
