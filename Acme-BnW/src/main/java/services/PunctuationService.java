package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Customer;
import domain.Punctuation;
import domain.Topic;
import repositories.PunctuationRepository;

@Service
@Transactional
public class PunctuationService {

	// Managed repository

	@Autowired
	private PunctuationRepository punctuationRepository;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private CustomerService customerService;

	// Supported services

	public PunctuationService() {
			super();
		}

	public Punctuation create() {
		return new Punctuation();
	}

	public Collection<Punctuation> findAll() {
		return this.punctuationRepository.findAll();
	}

	public Punctuation findOne(final int punctuationId) {
		return this.punctuationRepository.findOne(punctuationId);

	}

	public void save(final Punctuation punctuation) {
		this.punctuationRepository.save(punctuation);
	}

	public void delete(final Punctuation punctuation) {
		this.punctuationRepository.delete(punctuation);
	}
	
	public void givePunctuation(final Punctuation punctuation, int topicId){
		Topic topic = topicService.findOne(topicId);
		Customer customer = customerService.findByPrincipal();
		punctuation.setTopic(topic);
		punctuation.setCustomer(customer);
		this.save(punctuation);
		
	}
}
