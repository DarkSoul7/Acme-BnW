
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

import services.ActorService;
import services.CustomerService;
import services.PunctuationService;
import services.TopicService;
import domain.Actor;
import domain.Customer;
import domain.Punctuation;
import domain.Topic;
import forms.TopicForm;

@Controller
@RequestMapping("/topic")
public class TopicController extends AbstractController {

	//Related services

	@Autowired
	private TopicService		topicService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private PunctuationService	punctuationService;

	@Autowired
	private ActorService		actorService;


	//Constructor

	public TopicController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;
		Boolean admin = false;
		Customer customer = null;
		final Collection<Topic> topics = this.topicService.findAllOrderByStars();

		final Actor actor = this.actorService.findByPrincipal();

		if (actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR")) {
			admin = true;

		} else {
			customer = this.customerService.findByPrincipal();
		}

		result = new ModelAndView("topic/listAll");
		result.addObject("topics", topics);
		result.addObject("RequestURI", "topic/listAll.do");
		if (!admin) {
			result.addObject("customer", customer);
		}

		result.addObject("errorMessage", errorMessage);

		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final TopicForm topicForm = this.topicService.create();

		result = this.createEditModelAndView(topicForm);

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int topicId) {
		ModelAndView result;

		try {
			final Topic topic = this.topicService.findOne(topicId);
			final TopicForm topicForm = this.topicService.toFormObject(topic);

			result = this.createEditModelAndView(topicForm);

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/topic/listAll.do");
			result.addObject("errorMessage", "topic.edit.error");
		}

		return result;
	}

	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TopicForm topicForm, final BindingResult binding) {
		ModelAndView result;
		Topic topic;

		try {
			topic = this.topicService.reconstruct(topicForm, binding);

		} catch (final Throwable e) {
			result = this.createEditModelAndView(topicForm);
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(topicForm);
		else
			try {
				topic = this.topicService.reconstruct(topicForm, binding);

				this.topicService.save(topic);
				result = new ModelAndView("redirect:/topic/listAll.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(topicForm, "topic.save.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int topicId) {
		ModelAndView result;

		try {
			final Topic topic = this.topicService.findOne(topicId);

			this.topicService.delete(topic);
			result = new ModelAndView("redirect:/topic/listAll.do");
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/topic/listAll.do");
			result.addObject("errorMessage", "topic.delete.error");
		}

		return result;
	}

	//Topic's punctuation management

	@RequestMapping(value = "/punctuation/list", method = RequestMethod.GET)
	public ModelAndView punctuationList(@RequestParam final int topicId) {
		ModelAndView result;

		try {
			Boolean givenPunctuation = true;

			final Customer customer = this.customerService.findByPrincipal();
			final Topic topic = this.topicService.findOne(topicId);

			if (this.punctuationService.findByTopicAndCustomer(topic, customer) == null) {
				givenPunctuation = false;
			}
			result = new ModelAndView("topic/punctuation/list");
			result.addObject("punctuations", topic.getPunctuations());
			result.addObject("RequestURI", "topic/punctuation/list.do");
			result.addObject("customerId", customer.getId());
			result.addObject("givenPunctuation", givenPunctuation);
			result.addObject("topicId", topicId);

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/topic/listAll.do");
			result.addObject("errorMessage", "topic.punctuation.list.error");
		}

		return result;
	}

	@RequestMapping(value = "/punctuation/create", method = RequestMethod.GET)
	public ModelAndView punctuationCreate(@RequestParam final int topicId) {
		ModelAndView result;

		try {
			final Topic topic = this.topicService.findOne(topicId);
			final Punctuation punctuation = this.punctuationService.create(topic);

			result = this.createEditPunctuationModelAndView(punctuation);

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/topic/listAll.do");
			result.addObject("errorMessage", "topic.punctuation.create.error");
		}
		return result;
	}

	@RequestMapping(value = "/punctuation/delete", method = RequestMethod.GET)
	public ModelAndView punctuationDelete(@RequestParam final int punctuationId) {
		ModelAndView result;

		try {
			final Customer customer = this.customerService.findByPrincipal();
			final Punctuation punctuation = this.punctuationService.findOne(punctuationId);
			Assert.isTrue(customer.equals(punctuation.getCustomer()));

			this.punctuationService.delete(punctuation);

			result = new ModelAndView("redirect:/topic/listAll.do");

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/topic/listAll.do");
			result.addObject("errorMessage", "topic.punctuation.delete.error");
		}
		return result;
	}

	@RequestMapping(value = "/punctuation/save", method = RequestMethod.POST, params = "save")
	public ModelAndView punctuationSave(@Valid final Punctuation punctuation, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditPunctuationModelAndView(punctuation);
		else
			try {
				this.punctuationService.save(punctuation);
				result = new ModelAndView("redirect:/topic/listAll.do");

			} catch (final Throwable oops) {
				result = this.createEditPunctuationModelAndView(punctuation, "topic.punctuation.save.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final TopicForm topicForm) {
		return this.createEditModelAndView(topicForm, null);
	}

	protected ModelAndView createEditModelAndView(final TopicForm topicForm, final String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("topic/create");
		result.addObject("topicForm", topicForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "topic/save.do");

		return result;
	}

	protected ModelAndView createEditPunctuationModelAndView(final Punctuation punctuation) {
		return this.createEditPunctuationModelAndView(punctuation, null);
	}

	protected ModelAndView createEditPunctuationModelAndView(final Punctuation punctuation, final String message) {
		ModelAndView result;

		result = new ModelAndView("topic/punctuation/create");
		result.addObject("punctuation", punctuation);
		result.addObject("RequestURI", "topic/punctuation/save.do");
		result.addObject("errorMessage", message);

		return result;
	}
}
