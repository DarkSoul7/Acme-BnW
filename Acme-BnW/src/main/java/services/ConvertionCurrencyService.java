
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ConvertionCurrencyRepository;
import domain.ConvertionCurrency;
import domain.Currency;

@Service
@Transactional
public class ConvertionCurrencyService {

	//Managed repository

	@Autowired
	private ConvertionCurrencyRepository	convertionCurrencyRepository;


	//Constructor
	public ConvertionCurrencyService() {
		super();
	}

	//Simple CRUD methods

	public Collection<ConvertionCurrency> findAll() {
		return this.convertionCurrencyRepository.findAll();
	}

	public ConvertionCurrency findOne(final Currency currency) {
		return this.convertionCurrencyRepository.findOne(currency);
	}

	public ConvertionCurrency findOne(final int currencyId) {
		return this.convertionCurrencyRepository.findOne(currencyId);
	}
}
