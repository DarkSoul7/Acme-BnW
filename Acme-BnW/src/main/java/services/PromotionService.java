package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Promotion;
import repositories.PromotionRepository;

@Service
@Transactional
public class PromotionService {

	// Managed repository

	@Autowired
	private PromotionRepository promotionRepository;

	// Supported services

	public PromotionService() {
		super();
	}

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
}
