
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Fixture;
import services.FixtureService;

@RequestMapping(value = "/fixture")
@Controller
public class FixtureController extends AbstractController {

	//Related services

	@Autowired
	private FixtureService fixtureService;


	//Constructor

	public FixtureController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Fixture> fixtures;

		fixtures = fixtureService.findAll();

		result = new ModelAndView("fixture/list");
		result.addObject("fixtures", fixtures);
		result.addObject("requestURI", "fixture/list.do");

		return result;
	}

}
