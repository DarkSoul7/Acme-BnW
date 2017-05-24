
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BetRepository;
import domain.Bet;

@Service
@Transactional
public class BetService {

	//Managed repository

	@Autowired
	private BetRepository	betRepository;


	//Supported services

	//Constructor

	public BetService() {
		super();
	}

	//Simple CRUD methods

	public Bet create() {
		return new Bet();
	}

	public Collection<Bet> findAll() {
		return this.betRepository.findAll();
	}

	public Bet findOne(final int betId) {
		return this.betRepository.findOne(betId);

	}

	public void save(final Bet bet) {
		this.betRepository.save(bet);
	}

	public void delete(final Bet bet) {
		this.betRepository.delete(bet);
	}

	//Other business service

	//DashBoard

	//C1

	public Double maxQuantityForBet() {
		return this.betRepository.maxQuantityForBet();
	}

	public Double minQuantityForBet() {
		return this.betRepository.minQuantityForBet();
	}

	public Double avgQuantityForBet() {
		return this.betRepository.avgQuantityForBet();
	}

	//C2

	//a)
	public Long maxBetsWonPerClients() {
		final ArrayList<Long> result = this.betRepository.maxBetsWonPerClients();
		if (!result.isEmpty())
			return result.get(0);
		else
			return 0L;
	}

	//b)
	public Long minBetsWonPerClients() {
		final ArrayList<Long> result = this.betRepository.minBetsWonPerClients();
		if (!result.isEmpty())
			return result.get(0);
		else
			return 0L;
	}

	//c)
	public Double avgWonBetsPerClients() {
		return this.betRepository.avgWonBetsPerClients();
	}

	//C3

	//a)
	public Long maxBetsLostPerClients() {
		final ArrayList<Long> result = this.betRepository.maxBetsLostPerClients();
		if (!result.isEmpty())
			return result.get(0);
		else
			return 0L;
	}

	//b)
	public Long minBetsLostPerClients() {
		final ArrayList<Long> result = this.betRepository.minBetsLostPerClients();
		if (!result.isEmpty())
			return result.get(0);
		else
			return 0L;
	}

	//c)
	public Double avgLostBetsPerClients() {
		return this.betRepository.avgLostBetsPerClients();
	}

}
