
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Customer;
import domain.Message;
import domain.Topic;
import forms.MessageForm;
import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	// Managed repository

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private TopicService			topicService;

	@Autowired
	private AdministratorService	administratorService;


	// Supported services

	public MessageService() {
		super();
	}

	public MessageForm create() {
		final MessageForm result = new MessageForm();
		return result;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);

	}

	public void save(final Message message) {
		Assert.isTrue(message.getCustomer().getId() == customerService.findByPrincipal().getId());
		this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		administratorService.findByPrincipal();
		this.messageRepository.delete(message);
	}

	public void deleteAll(final Collection<Message> messages) {
		this.messageRepository.delete(messages);
	}

	public Message reconstruct(final MessageForm messageForm, final BindingResult binding) {
		Assert.notNull(messageForm);

		Message result = new Message();
		if (messageForm.getId() != 0)
			result = this.findOne(messageForm.getId());
		else {
			final Customer customer = this.customerService.findByPrincipal();
			final Topic topic = this.topicService.findOne(messageForm.getTopicId());
			result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
			result.setTopic(topic);
			result.setCustomer(customer);
			result.setOrder(topic.getMessages().size() + 1);
		}

		result.setTitle(messageForm.getTitle());
		result.setDescription(messageForm.getDescription());

		this.validator.validate(result, binding);

		return result;
	}
	public MessageForm toFormObject(final Message message) {
		final MessageForm result = this.create();
		result.setDescription(message.getDescription());
		result.setTitle(message.getTitle());
		result.setId(message.getId());
		result.setTopicId(message.getTopic().getId());
		return result;
	}

	public Collection<Message> getMessagesByTopic(final Topic topic) {
		return this.messageRepository.getMessagesByTopic(topic.getId());
	}

	public void flush() {
		messageRepository.flush();

	}
}
