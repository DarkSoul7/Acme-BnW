
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PromotionRepository;
import domain.Customer;
import domain.Promotion;

@Service
@Transactional
public class PromotionService {

	// Managed repository

	@Autowired
	private PromotionRepository	promotionRepository;

	// Supported services

	@Autowired
	private CustomerService		customerService;


	//Constructor

	public PromotionService() {
		super();
	}

	//Simple CRUD methods

	public Promotion create() {
		return new Promotion();
	}

	public Collection<Promotion> findAll() {
		return this.promotionRepository.findAll();
	}

	public Promotion findOne(final int promotionId) {
		return this.promotionRepository.findOne(promotionId);

	}

	public void save(final Promotion promotion) {
		this.promotionRepository.save(promotion);
	}

	public void delete(final Promotion promotion) {
		this.promotionRepository.delete(promotion);
	}

	//Other business methods

	Collection<Promotion> getLocalFavouriteTeamPromotions() {
		final Customer customer = this.customerService.findByPrincipal();
		return this.promotionRepository.getLocalFavouriteTeamPromotions(customer.getId());
	}

	Collection<Promotion> getVisitorFavouriteTeamPromotions() {
		final Customer customer = this.customerService.findByPrincipal();
		return this.promotionRepository.getVisitorFavouriteTeamPromotions(customer.getId());
	}

	Collection<Promotion> getAllPromotionsCustomizedByCustomer() {
		final Set<Promotion> result = new HashSet<>();
		result.addAll(this.getLocalFavouriteTeamPromotions());
		result.addAll(this.getVisitorFavouriteTeamPromotions());

		return result;
	}
}
