
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TopicRepository;
import domain.Customer;
import domain.Message;
import domain.Punctuation;
import domain.Topic;
import forms.TopicForm;

@Service
@Transactional
public class TopicService {

	// Managed repository

	@Autowired
	private TopicRepository			topicRepository;

	// Supported services

	@Autowired
	private Validator				validator;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PunctuationService		punctuationService;

	@Autowired
	private MessageService			messageService;


	//Constructor
	public TopicService() {
		super();
	}

	public TopicForm create() {
		final TopicForm result = new TopicForm();
		return result;
	}

	public Collection<Topic> findAll() {
		return this.topicRepository.findAll();
	}

	public Topic findOne(final int topicId) {
		return this.topicRepository.findOne(topicId);

	}

	public void save(final Topic topic) {
		this.topicRepository.save(topic);
	}

	public void delete(final Topic topic) {
		this.administratorService.findByPrincipal();
		this.messageService.deleteAll(topic.getMessages());
		this.punctuationService.deleteAll(topic.getPunctuations());
		this.topicRepository.delete(topic);
	}

	//Other business methods

	public Topic reconstruct(final TopicForm topicForm, final BindingResult binding) {
		Assert.notNull(topicForm);

		Topic result = new Topic();
		if (topicForm.getId() != 0)
			result = this.findOne(topicForm.getId());
		else {
			final Customer customer = this.customerService.findByPrincipal();
			result.setTitle(topicForm.getTitle());
			result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			result.setCustomer(customer);
			result.setMessages(new ArrayList<Message>());
			result.setPunctuations(new ArrayList<Punctuation>());
		}

		result.setDescription(topicForm.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public TopicForm toFormObject(final Topic topic) {
		final TopicForm result = this.create();
		result.setDescription(topic.getDescription());
		result.setTitle(topic.getTitle());
		result.setId(topic.getId());
		return result;
	}

	public Collection<Topic> findAllOrderByStars() {
		final Collection<Topic> all = this.findAll();
		final Collection<Topic> result = this.topicRepository.getTopicsOrderByStars();

		all.removeAll(result);
		result.addAll(all);

		return result;
	}

	//DashBoard

	//B.3

	//a)

	public Integer getMaxTopicsByCustomer() {
		return this.topicRepository.getMaxTopicsByCustomer();
	}

	//b)
	public Integer getMinTopicsByCustomer() {
		return this.topicRepository.getMinTopicsByCustomer();
	}

	//c)
	public Double getTopicAvgByCustomers() {
		return this.topicRepository.getTopicAvgByCustomers();
	}

	//B.4

	//a)
	public Integer getMaxMessagesByCustomer() {
		return this.topicRepository.getMaxMessagesByCustomer();
	}

	//b)
	public Integer getMinMessagesByCustomer() {
		return this.topicRepository.getMinMessagesByCustomer();
	}

	//c)
	public Double getMessageAvgByCustomers() {
		return this.topicRepository.getMessageAvgByCustomers();
	}
}
