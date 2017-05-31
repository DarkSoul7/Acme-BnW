
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import domain.Bet;
import domain.Market;
import domain.MarketType;
import domain.Match;
import domain.Promotion;
import forms.MarketForm;
<<<<<<< HEAD
import forms.MarketResponseForm;
=======
import repositories.MarketRepository;
>>>>>>> branch 'master' of https://github.com/DarkSoul7/Acme-BnW.git

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
	private ManagerService		managerService;

	@Autowired
	private PromotionService	promotionService;

	@Autowired
	private Validator			validator;


	//Constructor
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

	public Market findOne(int marketId) {
		return this.marketRepository.findOne(marketId);

	}

	public Market save(Market market) {
		Assert.isTrue(market.getMatch().getEndMoment().after(new Date()));
		Assert.isTrue(market.getFee() != null && market.getFee().compareTo(1.01) >= 0);
		Market result;
		Collection<MarketType> usedMarketTypes;

		if (market.getId() == 0) {
			//No se puede guardar un mismo market varias veces para un mismo partido
			usedMarketTypes = this.getAllMarketTypesUsedByMatch(market.getMatch().getId());
			Assert.isTrue(!usedMarketTypes.contains(market.getType()));
		}

		result = this.marketRepository.save(market);

		return result;
	}

	public void delete(Market market) {
		Assert.isTrue(market.getMatch().getStartMoment().after(new Date()));
		Assert.isTrue(this.marketRepository.findExistsCustomerOnMarket(market.getId()) == 0);
		this.managerService.findByPrincipal();
		this.marketRepository.delete(market);
	}

	public Market enjoyPromotion(int idPromotion) {
		Promotion promotion = promotionService.findOne(idPromotion);
		Assert.notNull(promotion);
		Assert.isTrue(!promotion.isCancel());
		Assert.isTrue(promotion.getStartMoment().before(new Date()));
		Assert.isTrue(promotion.getEndMoment().after(new Date()));
		return promotion.getMarket();
	}

	//Other business services

	public Market reconstruct(MarketForm marketForm, BindingResult binding) {
		Assert.notNull(marketForm);

		Market result = new Market();
		if (marketForm.getId() != 0)
			result = this.findOne(marketForm.getId());
		else {
			Match match = this.matchService.findOne(marketForm.getIdMatch());
			result.setBets(new ArrayList<Bet>());
			result.setMatch(match);
			result.setType(marketForm.getType());
		}

		result.setFee(marketForm.getFee());

		this.validator.validate(result, binding);

		if (result.getMatch() == null) {
			FieldError fieldError;
			final String[] codes = {
				"javax.validation.constraints.NotNull.message"
			};
			fieldError = new FieldError("marketForm", "idMatch", result.getMatch(), false, codes, null, "");
			binding.addError(fieldError);
		}

		return result;
	}

	public MarketForm toFormObject(Market market) {
		MarketForm result;

		result = this.create();
		result.setFee(market.getFee());
		result.setType(market.getType());
		result.setId(market.getId());
		result.setIdMatch(market.getMatch().getId());

		return result;
	}

	/**
	 * Devuelve las apuestas destacadas (según la lista de equipos favoritos del cliente registrado)
	 * 
	 */
	public Collection<Market> getMarkedMarket() {
		return this.marketRepository.getMarkedMarket();
	}

	public Collection<Market> marketsOfMatches(final int id) {
		return this.marketRepository.marketsOfMatches(id);
	}

	public void flush() {
		this.marketRepository.flush();
	}

	public Integer betsInMatchDone(final Match match) {
		return this.marketRepository.betsInMatchDone(match.getId());
	}

	/***
	 * Recálculo de cuotas de los mercados. El algoritmo se pondrá en marcha atendiendo al umbral.
	 * (populationThreshold: cantidad de apuestas para poner en marcha el algoritmo)
	 * 
	 * Se obtiene una proporción de cuantos usuarios de la aplicación (teniendo en cuenta aquellos que no apuesten
	 * en el partido) van a participar en cada mercado: estimatedPopulation (en tantos de 1)
	 * 
	 * Si el número de participantes en el mercado en cuestión es superior al estimado la cuota desciende dependiendo de
	 * su valor actual y del porcentaje estimado de usuarios que debia participar: newFee
	 * 
	 * @param match
	 *            : partido a actualizar cuotas de mercados)
	 * 
	 * @param populationThreshold
	 *            : umbral para la activación del algoritmo
	 */
	public void updateMarketQuotes(final Match match, final int populationThreshold) {
		Assert.notNull(match);
		final Double marketProportion = 100.0 / match.getMarkets().size();
		final Integer population = this.betsInMatchDone(match);

		if (population > populationThreshold) {
			for (final Market market : match.getMarkets()) {

				if (market.getFee() > 1.01) {
					final Double estimatedPopulation = (marketProportion / market.getFee()) * 0.01; //Pertentage over 1
					final Integer marketPopulation = market.getBets().size();

					if (population * estimatedPopulation < marketPopulation) {
						final Double newFee = market.getFee() - (market.getFee() * (estimatedPopulation * 0.001));

						if (newFee > 1.01) {
							market.setFee(newFee);

						} else {
							market.setFee(1.01);
						}
						this.save(market);
					}
				}

			}
		}

	}

	public Collection<MarketType> getAllMarketTypesUsedByMatch(int matchId) {
		Collection<MarketType> result;

		result = this.marketRepository.getAllMarketTypesUsedByMatch(matchId);

		return result;
	}

	public Collection<MarketResponseForm> findAllMarketsFees() {
		Collection<MarketResponseForm> result;

		result = this.marketRepository.findAllMarketsFees();

		return result;
	}

	public Collection<MarketResponseForm> findAllMarketsFeesFromMatch(int matchId) {
		Collection<MarketResponseForm> result;

		result = this.marketRepository.findAllMarketsFeesFromMatch(matchId);

		return result;
	}
}
