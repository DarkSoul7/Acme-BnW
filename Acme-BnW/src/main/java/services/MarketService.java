
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

	@Autowired
	private PromotionService	promotionService;


	//Constructor
	public MarketService() {
		super();
	}

	public MarketForm create() {
		final MarketForm result = new MarketForm();

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
		Assert.isTrue(this.marketRepository.findExistsCustomerOnMarket(market.getId()) == 0);
		this.marketRepository.delete(market);
	}


	//Other business services

	@Autowired
	private Validator validator;


	public Market reconstruct(final MarketForm marketForm, final BindingResult binding) {
		Assert.notNull(marketForm);

		Market result = new Market();
		if (marketForm.getId() != 0)
			result = this.findOne(marketForm.getId());
		else {
			final Match match = this.matchService.findOne(marketForm.getIdMatch());
			result.setBets(new ArrayList<Bet>());
			result.setPromotions(new ArrayList<Promotion>());
			result.setMatch(match);
		}

		result.setFee(marketForm.getFee());
		result.setTitle(marketForm.getTitle());

		this.validator.validate(result, binding);

		return result;
	}

	public MarketForm toFormObject(final Market market) {
		final MarketForm result = this.create();
		result.setFee(market.getFee());
		result.setTitle(market.getTitle());
		result.setId(market.getId());
		return result;
	}

	/**
	 * Devuelve las apuestas destacadas (según la lista de equipos favoritos del cliente registrado)
	 * 
	 */
	public Collection<Market> getNotedMarket() {
		final Set<Promotion> promotions = new HashSet<>();
		final Set<Market> result = new HashSet<>();
		final Collection<Promotion> localPromotions = this.promotionService.getLocalFavouriteTeamPromotions();
		final Collection<Promotion> visitorPromotions = this.promotionService.getVisitorFavouriteTeamPromotions();

		promotions.addAll(localPromotions);
		promotions.addAll(visitorPromotions);

		for (final Promotion promotion : promotions)
			result.add(promotion.getMarket());

		return result;
	}

	public Collection<Market> marketsOfMatches(int id) {
		return marketRepository.marketsOfMatches(id);
	}
}
