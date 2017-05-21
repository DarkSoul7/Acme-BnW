
package services;

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

	public BetService() {
		super();
	}

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

	//C2

	//a)
	public Long maxBetsWonPerClients() {
		return this.betRepository.maxBetsWonPerClients().get(0);
	}

	//b)
	public Long minBetsWonPerClients() {
		return this.betRepository.minBetsWonPerClients().get(0);
	}

	//c)
	public Double avgWonBetsPerClients() {
		return this.betRepository.avgWonBetsPerClients();
	}

	//C3

	//a)
	public Long maxBetsLostPerClients() {
		return this.betRepository.maxBetsLostPerClients().get(0);
	}

	//b)
	public Long minBetsLostPerClients() {
		return this.betRepository.minBetsLostPerClients().get(0);
	}

	//c)
	public Double avgLostBetsPerClients() {
		return this.betRepository.avgLostBetsPerClients();
	}

}
