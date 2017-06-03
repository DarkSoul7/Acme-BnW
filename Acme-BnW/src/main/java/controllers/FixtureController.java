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

import services.CategoryService;
import services.FixtureService;
import domain.Category;
import domain.Fixture;
import forms.FixtureForm;

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
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Fixture> fixtures;

		fixtures = fixtureService.findAll();

		result = new ModelAndView("fixture/list");
		result.addObject("fixtures", fixtures);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
		result.addObject("requestURI", "fixture/list.do");

		return result;
	}

	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET)
	public ModelAndView listByCategory(@RequestParam int categoryId, @RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Fixture> fixtures;

		fixtures = fixtureService.fixturesOfCategory(categoryId);

		result = new ModelAndView("fixture/list");
		result.addObject("fixtures", fixtures);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);
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
		result = this.editModelAndView(fixtureForm);
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
				result.addObject("successMessage", "fixture.register.success");
			} catch (Throwable oops) {
				result = this.createModelAndView(fixtureForm, "fixture.register.error");
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
			result = this.editModelAndView(fixtureForm);
		} else {
			try {
				fixtureService.save(fixture);
				result = new ModelAndView("redirect:/fixture/list.do");
				result.addObject("successMessage", "fixture.edit.success");
			} catch (Throwable oops) {
				result = this.editModelAndView(fixtureForm, "fixture.edit.error");
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
			result.addObject("successMessage", "fixture.delete.success");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/fixture/list.do");
			result.addObject("errorMessage", "fixture.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(FixtureForm fixtureForm) {
		return this.createModelAndView(fixtureForm, null);
	}

	protected ModelAndView createModelAndView(FixtureForm fixtureForm, String errorMessage) {
		ModelAndView result;

		Collection<Category> categories = categoryService.findAll();
		result = new ModelAndView("fixture/register");
		result.addObject("fixtureForm", fixtureForm);
		result.addObject("categories", categories);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "fixture/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(FixtureForm fixtureForm) {
		return this.editModelAndView(fixtureForm, null);
	}

	protected ModelAndView editModelAndView(FixtureForm fixtureForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("fixture/edit");
		result.addObject("fixtureForm", fixtureForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "fixture/edit.do");

		return result;
	}

}
