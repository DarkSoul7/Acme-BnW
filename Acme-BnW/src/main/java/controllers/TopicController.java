
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TopicService;
import domain.Topic;

@Controller
@RequestMapping("/topic")
public class TopicController extends AbstractController {

	//Related services

	@Autowired
	private TopicService	topicService;


	//Constructor

	public TopicController() {
		super();
	}

	//Listing
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		final Collection<Topic> topics = this.topicService.findAll();

		result = new ModelAndView("topic/listAll");
		result.addObject("topics", topics);
		result.addObject("RequestURI", "topic/listAll.do");

		return result;
	}

}
