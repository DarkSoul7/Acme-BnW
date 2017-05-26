
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PunctuationRepository;
import domain.Customer;
import domain.Punctuation;
import domain.Topic;

@Service
@Transactional
public class PunctuationService {

	// Managed repository

	@Autowired
	private PunctuationRepository	punctuationRepository;

	// Supported services

	@Autowired
	private TopicService			topicService;

	@Autowired
	private CustomerService			customerService;


	public PunctuationService() {
		super();
	}

	public Punctuation create(final Topic topic) {
		final Customer customer = this.customerService.findByPrincipal();
		final Punctuation result = new Punctuation();
		result.setCustomer(customer);
		result.setTopic(topic);

		return result;
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

	//Other business services

	public void givePunctuation(final Punctuation punctuation, final int topicId) {
		final Topic topic = this.topicService.findOne(topicId);
		final Customer customer = this.customerService.findByPrincipal();
		punctuation.setTopic(topic);
		punctuation.setCustomer(customer);
		this.save(punctuation);

	}

	public Punctuation findByTopicAndCustomer(final Topic topic, final Customer customer) {
		return this.punctuationRepository.findByTopicAndCustomer(topic.getId(), customer.getId());
	}
}
