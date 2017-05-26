
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

import domain.Category;
import domain.Fixture;
import forms.FixtureForm;
import services.CategoryService;
import services.FixtureService;

@RequestMapping(value = "/fixture")
@Controller
public class FixtureController extends AbstractController {

	//Related services

	@Autowired
	private FixtureService	fixtureService;

	@Autowired
	private CategoryService	categoryService;


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

	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET)
	public ModelAndView listByCategory(@RequestParam int categoryId) {
		ModelAndView result;
		Collection<Fixture> fixtures;

		fixtures = fixtureService.fixturesOfCategory(categoryId);

		result = new ModelAndView("fixture/list");
		result.addObject("fixtures", fixtures);
		result.addObject("requestURI", "fixture/listByCategory.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		FixtureForm fixtureForm = fixtureService.create();
		result = this.createModelAndView(fixtureForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int fixtureId) {
		ModelAndView result = new ModelAndView();

		Fixture fixture = fixtureService.findOne(fixtureId);
		FixtureForm fixtureForm = fixtureService.toFormObject(fixture);
		result = this.createEditModelAndView(fixtureForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FixtureForm fixtureForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Fixture fixture = new Fixture();

		fixture = fixtureService.reconstruct(fixtureForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(fixtureForm);
		} else {
			try {
				fixtureService.save(fixture);
				result = new ModelAndView("redirect:/fixture/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(fixtureForm, "fixture.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid FixtureForm fixtureForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Fixture fixture = new Fixture();

		fixture = fixtureService.reconstruct(fixtureForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(fixtureForm);
		} else {
			try {
				fixtureService.save(fixture);
				result = new ModelAndView("redirect:/fixture/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(fixtureForm, "fixture.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int fixtureId) {
		ModelAndView result = new ModelAndView();

		Fixture fixture = fixtureService.findOne(fixtureId);
		try {
			fixtureService.delete(fixture);
			result = new ModelAndView("redirect:/fixture/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/fixture/list.do");
			result.addObject("errorMessage", "fixture.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final FixtureForm fixtureForm) {
		return this.createModelAndView(fixtureForm, null);
	}

	protected ModelAndView createModelAndView(final FixtureForm fixtureForm, final String message) {
		ModelAndView result;

		Collection<Category> categories = categoryService.findAll();
		result = new ModelAndView("fixture/register");
		result.addObject("fixtureForm", fixtureForm);
		result.addObject("categories", categories);
		result.addObject("message", message);
		result.addObject("RequestURI", "fixture/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixtureForm fixtureForm) {
		return this.createEditModelAndView(fixtureForm, null);
	}

	protected ModelAndView createEditModelAndView(final FixtureForm fixtureForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("fixture/edit");
		result.addObject("fixtureForm", fixtureForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "fixture/edit.do");

		return result;
	}

}
