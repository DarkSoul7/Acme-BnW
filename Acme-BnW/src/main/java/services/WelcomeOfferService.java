
package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WelcomeOfferRepository;
import domain.Customer;
import domain.WelcomeOffer;
import forms.WelcomeOfferForm;

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

	public WelcomeOfferForm create() {
		return new WelcomeOfferForm();
	}

	public Collection<WelcomeOffer> findAll() {
		return this.welcomeOfferRepository.findAll();
	}

	public WelcomeOffer findOne(final int welcomeOfferId) {
		return this.welcomeOfferRepository.findOne(welcomeOfferId);

	}

	public void save(final WelcomeOffer welcomeOffer) {
		Assert.notNull(welcomeOffer);
		final Collection<WelcomeOffer> welcomeOffersInDates = this.welcomeOfferRepository.getOffersInDates(welcomeOffer.getOpenPeriod(), welcomeOffer.getEndPeriod());
		if (welcomeOffer.getId() != 0 && welcomeOffersInDates.size() == 1) {
			final WelcomeOffer wo = welcomeOffersInDates.iterator().next();
			if (wo.getId() != welcomeOffer.getId()) {
				Assert.isTrue(welcomeOffersInDates.isEmpty());
			}

		} else {
			Assert.isTrue(welcomeOffersInDates.isEmpty());
		}

		this.welcomeOfferRepository.save(welcomeOffer);
	}

	public void delete(final WelcomeOffer welcomeOffer) {
		Assert.isTrue(welcomeOffer.getCustomers().size() == 0);
		this.welcomeOfferRepository.delete(welcomeOffer);
	}

	//Other business methods

	public WelcomeOffer getActive() {
		return this.welcomeOfferRepository.getActive();
	}


	@Autowired
	private Validator	validator;


	public WelcomeOffer reconstruct(final WelcomeOfferForm welcomeOfferForm, final BindingResult binding) throws ParseException {
		WelcomeOffer result = new WelcomeOffer();
		Date openPeriod = null;
		Date endPeriod = null;

		if (welcomeOfferForm.getId() == 0) {
			final Collection<Customer> customers = new ArrayList<>();
			result.setCustomers(customers);

		} else {
			result = this.findOne(welcomeOfferForm.getId());
		}

		if (!welcomeOfferForm.getOpenPeriod().isEmpty() && !welcomeOfferForm.getEndPeriod().isEmpty()) {
			final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			openPeriod = df.parse(welcomeOfferForm.getOpenPeriod());
			endPeriod = df.parse(welcomeOfferForm.getEndPeriod());
		}

		result.setTitle(welcomeOfferForm.getTitle());
		result.setOpenPeriod(openPeriod);
		result.setEndPeriod(endPeriod);
		result.setAmount(welcomeOfferForm.getAmount());
		result.setExtractionAmount(welcomeOfferForm.getExtractionAmount());

		this.validator.validate(result, binding);

		return result;
	}

	public WelcomeOfferForm toFormObject(final WelcomeOffer welcomeOffer) {
		final WelcomeOfferForm result = this.create();
		final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		result.setAmount(welcomeOffer.getAmount());
		result.setTitle(welcomeOffer.getTitle());
		result.setId(welcomeOffer.getId());
		result.setExtractionAmount(welcomeOffer.getExtractionAmount());

		final String openPeriod = df.format(welcomeOffer.getOpenPeriod());
		final String endPeriod = df.format(welcomeOffer.getEndPeriod());
		result.setOpenPeriod(openPeriod);
		result.setEndPeriod(endPeriod);

		return result;
	}

	public void flush() {
		this.welcomeOfferRepository.flush();

	}
}
