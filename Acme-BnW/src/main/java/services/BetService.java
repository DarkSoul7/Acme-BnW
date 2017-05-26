package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BetRepository;
import domain.Bet;
import domain.Customer;
import domain.Market;
import domain.Status;
import domain.Type;

@Service
@Transactional
public class BetService {

	//Managed repository

	@Autowired
	private BetRepository	betRepository;

	//Supported services

	@Autowired
	private CustomerService	customerService;


	//Constructor

	public BetService() {
		super();
	}

	//Simple CRUD methods

	public Bet create(Double quantity, Type type, Market market) {
		Assert.notNull(quantity);
		Assert.isTrue(quantity > 0.0);
		Assert.notNull(type);
		Assert.notNull(market);
		Bet result;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		result = new Bet();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		result.setCustomer(principal);
		result.setFee(market.getFee());
		result.setMarket(market);
		result.setQuantity(quantity);
		result.setStatus(Status.PENDING);
		result.setType(type);

		return result;
	}

	public Collection<Bet> findAll() {
		return this.betRepository.findAll();
	}

	public Bet findOne(int betId) {
		return this.betRepository.findOne(betId);

	}

	public void save(Bet bet) {
		this.betRepository.save(bet);
	}

	public void delete(Bet bet) {
		this.betRepository.delete(bet);
	}

	//Other business service

	public Collection<Bet> findAllByCustomer() {
		Collection<Bet> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.betRepository.findAllByCustomer(principal.getId());

		return result;
	}

	public Collection<Bet> findAllPendingByCustomer() {
		Collection<Bet> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.betRepository.findAllPendingByCustomer(principal.getId(), Status.PENDING);

		return result;
	}

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
		ArrayList<Long> result = this.betRepository.maxBetsWonPerClients();
		if (!result.isEmpty()) {
			return result.get(0);
		} else {
			return 0L;
		}
	}

	//b)
	public Long minBetsWonPerClients() {
		final ArrayList<Long> result = this.betRepository.minBetsWonPerClients();
		if (!result.isEmpty()) {
			return result.get(0);
		} else {
			return 0L;
		}
	}

	//c)
	public Double avgWonBetsPerClients() {
		return this.betRepository.avgWonBetsPerClients();
	}

	//C3

	//a)
	public Long maxBetsLostPerClients() {
		final ArrayList<Long> result = this.betRepository.maxBetsLostPerClients();
		if (!result.isEmpty()) {
			return result.get(0);
		} else {
			return 0L;
		}
	}

	//b)
	public Long minBetsLostPerClients() {
		final ArrayList<Long> result = this.betRepository.minBetsLostPerClients();
		if (!result.isEmpty()) {
			return result.get(0);
		} else {
			return 0L;
		}
	}

	//c)
	public Double avgLostBetsPerClients() {
		return this.betRepository.avgLostBetsPerClients();
	}

}
