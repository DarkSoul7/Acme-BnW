
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.MessageService;
import services.PunctuationService;
import services.TopicService;
import domain.Customer;
import domain.Message;
import domain.Topic;
import forms.MessageForm;

@RequestMapping(value = "/message")
@Controller
public class MessageController extends AbstractController {

	//Related services

	@Autowired
	private TopicService		topicService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private PunctuationService	punctuationService;


	//Constructor

	public MessageController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int topicId, @RequestParam(required = false) final String messageError) {
		ModelAndView result;

		try {

			Boolean givenPunctuation = true;

			final Topic topic = this.topicService.findOne(topicId);
			Assert.notNull(topic);
			final Customer customer = this.customerService.findByPrincipal();

			if (this.punctuationService.findByTopicAndCustomer(topic, customer) == null) {
				givenPunctuation = false;
			}

			final Collection<Message> messages = this.messageService.getMessagesByTopic(topic);
			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("topic", topic);
			result.addObject("customerId", customer.getId());
			result.addObject("RequestURI", "message/list.do");
			result.addObject("givenPunctuation", givenPunctuation);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect: /topic/listAll.do");
			result.addObject("errorMessage", "message.list.error");
		}

		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int topicId) {

		ModelAndView result;

		final MessageForm messageForm = this.messageService.create();
		messageForm.setTopicId(topicId);

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId, @RequestParam final int topicId) {
		ModelAndView result;

		try {
			final Message message = this.messageService.findOne(messageId);

			final MessageForm messageForm = this.messageService.toFormObject(message);
			result = this.createEditModelAndView(messageForm);

			final Customer customer = this.customerService.findByPrincipal();
			Assert.isTrue(customer.equals(message.getCustomer()));

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/message/list.do?topicId=" + topicId);
			result.addObject("messageError", "message.edit.error");
		}

		return result;
	}

	//Quote
	@RequestMapping(value = "/quote", method = RequestMethod.GET)
	public ModelAndView quote(@RequestParam final int messageId, @RequestParam final int topicId) {
		ModelAndView result;

		try {
			final Message message = this.messageService.findOne(messageId);

			final MessageForm messageForm = this.messageService.create();
			messageForm.setTitle("(Quote-Cita:" + message.getOrder() + ")");
			messageForm.setTopicId(message.getTopic().getId());
			messageForm.setDescription("''" + message.getDescription() + "''" + "\n");

			result = this.createEditModelAndView(messageForm);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/message/list.do?topicId=" + topicId);
			result.addObject("messageError", "message.quote.error");
		}

		return result;
	}

	//Reply
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int messageId, @RequestParam final int topicId) {
		ModelAndView result;

		try {
			final Message message = this.messageService.findOne(messageId);

			final MessageForm messageForm = this.messageService.create();
			messageForm.setTitle("(Reply-Respuesta:" + message.getOrder() + ")");
			messageForm.setDescription("->" + message.getDescription() + "<-" + "\n");
			messageForm.setTopicId(message.getTopic().getId());

			result = this.createEditModelAndView(messageForm);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/message/list.do?topicId=" + topicId);
			result.addObject("messageError", "message.reply.error");
		}

		return result;
	}

	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final MessageForm messageForm, final BindingResult binding) {
		ModelAndView result;
		Message message;
		try {
			message = this.messageService.reconstruct(messageForm, binding);
		} catch (final Throwable e) {
			result = this.createEditModelAndView(messageForm);
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				message = this.messageService.reconstruct(messageForm, binding);
				this.messageService.save(message);

				result = new ModelAndView("redirect:/message/list.do?topicId=" + messageForm.getTopicId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.save.error");
			}
		return result;

	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		return this.createEditModelAndView(messageForm, null);
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("message/create");
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("topicId", messageForm.getTopicId());
		result.addObject("RequestURI", "message/save.do");

		return result;
	}

}
