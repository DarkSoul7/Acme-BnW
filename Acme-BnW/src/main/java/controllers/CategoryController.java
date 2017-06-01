
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
import services.TournamentService;
import domain.Category;
import domain.Tournament;
import forms.CategoryForm;

@RequestMapping(value = "/category")
@Controller
public class CategoryController extends AbstractController {

	//Related services

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private TournamentService	tournamentService;


	//Constructor

	public CategoryController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do");
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	@RequestMapping(value = "/listByTournament", method = RequestMethod.GET)
	public ModelAndView listByTournament(@RequestParam final int tournamentId) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.categoriesOfTournament(tournamentId);

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/listOfTournament.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		final CategoryForm categoryForm = this.categoryService.create();
		result = this.createModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result = new ModelAndView();

		final Category category = this.categoryService.findOne(categoryId);
		final CategoryForm categoryForm = this.categoryService.toFormObject(category);
		result = this.createEditModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CategoryForm categoryForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Category category = new Category();

		category = this.categoryService.reconstruct(categoryForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(categoryForm);
		} else {
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(categoryForm, "category.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CategoryForm categoryForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Category category = new Category();

		category = this.categoryService.reconstruct(categoryForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(categoryForm);
		} else {
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(categoryForm, "category.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId) {
		ModelAndView result = new ModelAndView();

		final Category category = this.categoryService.findOne(categoryId);
		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/category/list.do");
			result.addObject("errorMessage", "category.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final CategoryForm categoryForm) {
		return this.createModelAndView(categoryForm, null);
	}

	protected ModelAndView createModelAndView(final CategoryForm categoryForm, final String message) {
		ModelAndView result;

		final Collection<Tournament> tournaments = this.tournamentService.findAll();
		result = new ModelAndView("category/register");
		result.addObject("categoryForm", categoryForm);
		result.addObject("message", message);
		result.addObject("tournaments", tournaments);
		result.addObject("RequestURI", "category/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm) {
		return this.createEditModelAndView(categoryForm, null);
	}

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("categoryForm", categoryForm);
		result.addObject("message", message);
		result.addObject("RequestURI", "category/edit.do");

		return result;
	}

}
