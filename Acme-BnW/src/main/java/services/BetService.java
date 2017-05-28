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
		result.setCompleted(true);

		return result;
	}

	public Bet createDefault(Market market) {
		Assert.notNull(market);
		Bet result;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		result = new Bet();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		result.setCustomer(principal);
		result.setFee(market.getFee());
		result.setMarket(market);
		result.setQuantity(0.01);
		result.setStatus(Status.PENDING);
		result.setType(Type.SIMPLE);
		result.setCompleted(false);

		return result;
	}

	public Collection<Bet> findAll() {
		return this.betRepository.findAll();
	}

	public Bet findOne(int betId) {
		return this.betRepository.findOne(betId);

	}

	public void save(Bet bet) throws IllegalStateException {
		Customer principal;
		Double balance;

		principal = this.customerService.findByPrincipal();
		balance = principal.getBalance();

		if (bet.getCompleted()) {
			if (balance.compareTo(bet.getQuantity()) < 0) {
				throw new IllegalStateException("BetService - save: El saldo del cliente es insuficiente");
			} else {
				balance -= bet.getQuantity();
				principal.setBalance(balance);

				this.customerService.save(principal);
			}
		}

		this.betRepository.save(bet);
	}

	public void delete(Bet bet) {
		Assert.isTrue(!bet.getCompleted());
		this.betRepository.delete(bet);
	}

	public void delete(int betId) {
		Bet bet;

		bet = this.findOne(betId);

		this.delete(bet);
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

	public Collection<Bet> findAllSelectedByCustomer() {
		Collection<Bet> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.betRepository.findAllSelectedByCustomer(principal.getId());

		return result;
	}

	public Collection<Bet> findAllById(Collection<String> ids) {
		Collection<Bet> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.betRepository.findAllById(ids, principal.getId());

		Assert.isTrue(result.size() == ids.size());

		return result;
	}

	public Bet completeSelectedBet(int betId, Double quantity, Market market) {
		Bet result;

		result = this.findOne(betId);
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		result.setCompleted(true);
		result.setFee(market.getFee());
		result.setQuantity(quantity);

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
		ArrayList<Long> result = this.betRepository.minBetsWonPerClients();
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
		ArrayList<Long> result = this.betRepository.maxBetsLostPerClients();
		if (!result.isEmpty()) {
			return result.get(0);
		} else {
			return 0L;
		}
	}

	//b)
	public Long minBetsLostPerClients() {
		ArrayList<Long> result = this.betRepository.minBetsLostPerClients();
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
