package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private MarketService	marketService;


	//Constructor

	public BetService() {
		super();
	}

	//Simple CRUD methods

	public Bet createSimple(Double quantity, Market market) {
		Assert.notNull(quantity);
		Assert.isTrue(quantity > 0.0);
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
		result.setType(Type.SIMPLE);
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

	public Bet createMultiple(Double quantity) {
		Assert.notNull(quantity);
		Assert.isTrue(quantity > 0.0);
		Bet result;
		Customer principal;
		Double fee;

		fee = 1.01;
		principal = this.customerService.findByPrincipal();

		result = new Bet();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		result.setCustomer(principal);
		result.setFee(fee);
		result.setMarket(null);
		result.setQuantity(quantity);
		result.setStatus(Status.PENDING);
		result.setType(Type.MULTIPLE);
		result.setCompleted(true);

		return result;
	}

	public Collection<Bet> findAll() {
		return this.betRepository.findAll();
	}

	public Bet findOne(int betId) {
		return this.betRepository.findOne(betId);

	}

	public Bet save(Bet bet, Boolean payment) throws IllegalStateException, IllegalArgumentException {
		Customer principal;
		Double balance;
		Bet result;

		if (!Type.MULTIPLE.equals(bet.getType())) {
			Assert.notNull(bet.getMarket());
			if (new Date().compareTo(bet.getMarket().getMatch().getEndMoment()) >= 0) {
				throw new IllegalArgumentException("BetService - save: El partido ya ha terminado");
			}
		}

		principal = this.customerService.findByPrincipal();
		balance = principal.getBalance();

		if (bet.getCompleted() && !Type.CHILD.equals(bet.getType()) && payment) {
			if (balance.compareTo(bet.getQuantity()) < 0) {
				throw new IllegalStateException("BetService - save: El saldo del cliente es insuficiente");
			} else {
				balance -= bet.getQuantity();
				principal.setBalance(balance);

				this.customerService.save(principal);
			}
		}

		result = this.betRepository.save(bet);

		return result;
	}

	public Collection<Bet> save(Collection<Bet> bets) {
		return this.betRepository.save(bets);
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
		result = this.betRepository.findAllByCustomer(principal.getId(), Type.CHILD);

		return result;
	}

	public Collection<Bet> findAllPendingByCustomer() {
		Collection<Bet> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.betRepository.findAllPendingByCustomer(principal.getId(), Status.PENDING, Type.CHILD);

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

	public Collection<Bet> prepareChildrenBets(List<String> betsIds, Bet parentBet, Double quantity) {
		Collection<Bet> childrenBets;
		Market market;

		childrenBets = this.findAllById(betsIds);

		for (Bet bet : childrenBets) {
			market = this.marketService.findOne(bet.getMarket().getId());
			bet.setCompleted(true);
			bet.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			bet.setFee(market.getFee());
			bet.setParentBet(parentBet);
			bet.setQuantity(quantity);
			bet.setStatus(Status.PENDING);
			bet.setType(Type.CHILD);
		}

		return childrenBets;
	}

	public void saveMultipleBet(List<String> betsIds, Double quantity) throws IllegalStateException, IllegalArgumentException {
		Bet parentBet;
		Collection<Bet> childrenBets;
		List<Integer> matchesIds;
		Market market;
		Double totalFee = 1.0;

		parentBet = this.createMultiple(quantity);
		parentBet = this.save(parentBet, false);

		childrenBets = this.findAllById(betsIds);
		matchesIds = new ArrayList<Integer>();

		for (Bet bet : childrenBets) {
			//En una apuesta múltiple no puede haber más de una apuesta sobre el mismo partido
			Assert.isTrue(!matchesIds.contains(bet.getMarket().getMatch().getId()));
			matchesIds.add(bet.getMarket().getMatch().getId());

			//Se comprueba accediendo a base de datos para estar seguro de que se trabaja con el último valor establecido
			market = this.marketService.findOne(bet.getMarket().getId());
			Assert.notNull(market);
			totalFee *= market.getFee();

			bet.setCompleted(true);
			bet.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			bet.setFee(market.getFee());
			bet.setParentBet(parentBet);
			bet.setQuantity(quantity);
			bet.setStatus(Status.PENDING);
			bet.setType(Type.CHILD);
		}

		totalFee = Double.valueOf(String.format("%.2f", totalFee));
		parentBet.setFee(totalFee);

		childrenBets = this.save(childrenBets);
		parentBet.setChildrenBets(childrenBets);
		this.save(parentBet, true);
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
