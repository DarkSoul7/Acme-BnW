
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

import domain.Bet;
import domain.Market;
import domain.Match;
import domain.Promotion;
import forms.MarketForm;
import repositories.MarketRepository;

@Service
@Transactional
public class MarketService {

	// Managed repository

	@Autowired
	private MarketRepository	marketRepository;

	// Supported services
	@Autowired
	private MatchService		matchService;


	public MarketService() {
		super();
	}

	public MarketForm create() {
		MarketForm result = new MarketForm();

		return result;
	}

	public Collection<Market> findAll() {
		return this.marketRepository.findAll();
	}

	public Market findOne(final int marketId) {
		return this.marketRepository.findOne(marketId);

	}

	public void save(final Market market) {
		this.marketRepository.save(market);
	}

	public void delete(final Market market) {
		Assert.isTrue(market.getMatch().getEndMoment().before(new Date()));
		Assert.isTrue(marketRepository.findExistsCustomerOnMarket(market.getId()) == 0);
		this.marketRepository.delete(market);
	}


	@Autowired
	private Validator validator;


	public Market reconstruct(MarketForm marketForm, BindingResult binding) {
		Assert.notNull(marketForm);

		Market result = new Market();
		if (marketForm.getId() != 0) {
			result = this.findOne(marketForm.getId());
		} else {
			Match match = matchService.findOne(marketForm.getIdMatch());
			result.setBets(new ArrayList<Bet>());
			result.setPromotions(new ArrayList<Promotion>());
			result.setMatch(match);
		}

		result.setFee(marketForm.getFee());
		result.setTitle(marketForm.getTitle());

		this.validator.validate(result, binding);

		return result;
	}

	public MarketForm toFormObject(Market market) {
		MarketForm result = this.create();
		result.setFee(market.getFee());
		result.setTitle(market.getTitle());
		result.setId(market.getId());
		return result;
	}

}
