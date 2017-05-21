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

import domain.Category;
import domain.Customer;
import domain.Fixture;
import domain.Message;
import domain.Punctuation;
import domain.Topic;
import domain.Tournament;
import forms.CategoryForm;
import forms.TopicForm;
import repositories.TopicRepository;
import security.Authority;

@Service
@Transactional
public class TopicService {

	// Managed repository

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ActorService actorService;

	// Supported services

	public TopicService() {
		super();
	}

	public TopicForm create() {
		TopicForm result = new TopicForm();
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
		Assert.notNull(actorService.findByPrincipal().getUserAccount().getAuthorities().contains(Authority.ADMIN));
		this.topicRepository.delete(topic);
	}

	public Topic reconstruct(TopicForm topicForm, BindingResult binding) {
		Assert.notNull(topicForm);

		Topic result = new Topic();
		if (topicForm.getId() != 0) {
			result = this.findOne(topicForm.getId());
		} else {
			Customer customer = customerService.findByPrincipal();
			result.setTitle(topicForm.getTitle());
			result.setCreationMoment(new Date(System.currentTimeMillis()-1000));
			result.setCustomer(customer);
			result.setMessages(new ArrayList<Message>());
			result.setPunctuations(new ArrayList<Punctuation>());
		}
		
		result.setDescription(topicForm.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public TopicForm toFormObject(Topic topic) {
		TopicForm result = this.create();
		result.setDescription(topic.getDescription());
		result.setTitle(topic.getTitle());
		result.setId(topic.getId());
		return result;
	}

}
