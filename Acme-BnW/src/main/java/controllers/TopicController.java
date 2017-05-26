
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.TopicService;
import domain.Customer;
import domain.Topic;
import forms.TopicForm;

@Controller
@RequestMapping("/topic")
public class TopicController extends AbstractController {

	//Related services

	@Autowired
	private TopicService	topicService;

	@Autowired
	private CustomerService	customerService;


	//Constructor

	public TopicController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		final Collection<Topic> topics = this.topicService.findAll();

		final Customer customer = this.customerService.findByPrincipal();

		result = new ModelAndView("topic/listAll");
		result.addObject("topics", topics);
		result.addObject("RequestURI", "topic/listAll.do");
		result.addObject("customerId", customer.getId());

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
	@RequestMapping(value = "/save", method = RequestMethod.POST)
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
}
