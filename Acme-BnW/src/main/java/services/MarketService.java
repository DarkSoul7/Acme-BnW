package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Market;
import repositories.MarketRepository;

@Service
@Transactional
public class MarketService {

	// Managed repository

	@Autowired
	private MarketRepository marketRepository;

	// Supported services

	public MarketService() {
		super();
	}

	public Market create() {
		return new Market();
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
		this.marketRepository.delete(market);
	}
}
