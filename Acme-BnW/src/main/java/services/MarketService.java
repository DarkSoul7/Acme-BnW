
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

import repositories.MarketRepository;
import domain.Bet;
import domain.Market;
import domain.Match;
import forms.MarketForm;

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

	public Market save(final Market market) {
		return this.marketRepository.save(market);
	}

	public void delete(final Market market) {
		Assert.isTrue(market.getMatch().getEndMoment().before(new Date()));
		Assert.isTrue(this.marketRepository.findExistsCustomerOnMarket(market.getId()) == 0);
		this.managerService.findByPrincipal();
		this.marketRepository.delete(market);
	}


	//Other business services

	@Autowired
	private Validator	validator;


	public Market reconstruct(final MarketForm marketForm, final BindingResult binding) {
		Assert.notNull(marketForm);

		Market result = new Market();
		if (marketForm.getId() != 0)
			result = this.findOne(marketForm.getId());
		else {
			final Match match = this.matchService.findOne(marketForm.getIdMatch());
			result.setBets(new ArrayList<Bet>());
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
	 * Devuelve las apuestas destacadas (seg�n la lista de equipos favoritos del cliente registrado)
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
	 * Rec�lculo de cuotas de los mercados. El algoritmo se pondr� en marcha atendiendo al umbral.
	 * (populationThreshold: cantidad de apuestas para poner en marcha el algoritmo)
	 * 
	 * Se obtiene una proporci�n de cuantos usuarios de la aplicaci�n (teniendo en cuenta aquellos que no apuesten
	 * en el partido) van a participar en cada mercado: estimatedPopulation (en tantos de 1)
	 * 
	 * Si el n�mero de participantes en el mercado en cuesti�n es superior al estimado la cuota desciende dependiendo de
	 * su valor actual y del porcentaje estimado de usuarios que debia participar: newFee
	 * 
	 * @param match
	 *            : partido a actualizar cuotas de mercados)
	 * 
	 * @param populationThreshold
	 *            : umbral para la activaci�n del algoritmo
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
}
