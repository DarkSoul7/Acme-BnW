
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Bet;
import repositories.BetRepository;

@Service
@Transactional
public class BetService {

	//Managed repository

	@Autowired
	private BetRepository betRepository;


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

}
