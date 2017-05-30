
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Customer;
import domain.Market;
import domain.Promotion;
import forms.PromotionForm;
import repositories.PromotionRepository;

@Service
@Transactional
public class PromotionService {

	// Managed repository

	@Autowired
	private PromotionRepository	promotionRepository;

	// Supported services

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private MarketService		marketService;

	@Autowired
	private ManagerService		managerService;


	//Constructor

	public PromotionService() {
		super();
	}

	//Simple CRUD methods

	public PromotionForm create() {
		final PromotionForm result = new PromotionForm();

		return result;
	}

	public Collection<Promotion> findAll() {
		return this.promotionRepository.findAll();
	}

	public Promotion findOne(final int promotionId) {
		return this.promotionRepository.findOne(promotionId);

	}

	public void save(final Promotion promotion) {
		Assert.notNull(promotion);
		Assert.isTrue(promotion.getStartMoment().after(new Date()));
		Assert.isTrue(promotion.getEndMoment().after(promotion.getStartMoment()));
		this.promotionRepository.save(promotion);
	}

	public void delete(final Promotion promotion) {
		Assert.notNull(promotion);
		this.promotionRepository.delete(promotion);
	}

	public void cancel(final Promotion promotion) {
		Assert.notNull(promotion);
		Assert.isTrue(!promotion.isCancel());
		promotion.setCancel(true);
		promotionRepository.save(promotion);
	}


	@Autowired
	private Validator validator;


	public Promotion reconstruct(final PromotionForm promotionForm, final BindingResult binding) {
		Assert.notNull(promotionForm);

		Promotion result = new Promotion();
		if (promotionForm.getId() != 0)
			result = this.findOne(promotionForm.getId());
		else {
			final Market market = this.marketService.findOne(promotionForm.getIdMarket());
			result.setMarket(market);
		}

		result.setDescription(promotionForm.getDescription());
		result.setFee(promotionForm.getFee());
		result.setTitle(promotionForm.getTitle());
		result.setEndMoment(promotionForm.getEndMoment());
		result.setStartMoment(promotionForm.getStartMoment());
		result.setCancel(false);

		this.validator.validate(result, binding);

		return result;
	}

	public PromotionForm toFormObject(final Promotion promotion) {
		final PromotionForm result = this.create();
		result.setDescription(promotion.getDescription());
		result.setTitle(promotion.getTitle());
		result.setId(promotion.getId());
		result.setEndMoment(promotion.getEndMoment());
		result.setStartMoment(promotion.getStartMoment());
		result.setFee(promotion.getFee());
		result.setIdMarket(promotion.getId());
		return result;
	}

	//Other business methods

	public Collection<Promotion> getLocalFavouriteTeamPromotions() {
		final Customer customer = this.customerService.findByPrincipal();
		return this.promotionRepository.getLocalFavouriteTeamPromotions(customer.getId());
	}

	public Collection<Promotion> getVisitorFavouriteTeamPromotions() {
		final Customer customer = this.customerService.findByPrincipal();
		return this.promotionRepository.getVisitorFavouriteTeamPromotions(customer.getId());
	}

	public Collection<Promotion> getAllPromotionsCustomizedByCustomer() {
		final Set<Promotion> result = new HashSet<>();
		result.addAll(this.getLocalFavouriteTeamPromotions());
		result.addAll(this.getVisitorFavouriteTeamPromotions());

		return result;
	}

	public void flush() {
		promotionRepository.flush();

	}
}
