
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
import domain.Tournament;
import forms.CategoryForm;
import services.CategoryService;
import services.TournamentService;

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
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories;

		categories = categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do");

		return result;
	}

	@RequestMapping(value = "/listByTournament", method = RequestMethod.GET)
	public ModelAndView listByTournament(@RequestParam int tournamentId) {
		ModelAndView result;
		Collection<Category> categories;

		categories = categoryService.categoriesOfTournament(tournamentId);

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/listOfTournament.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		CategoryForm categoryForm = categoryService.create();
		result = this.createModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryId) {
		ModelAndView result = new ModelAndView();

		Category category = categoryService.findOne(categoryId);
		CategoryForm categoryForm = categoryService.toFormObject(category);
		result = this.createEditModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Category category = new Category();

		category = categoryService.reconstruct(categoryForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(categoryForm);
		} else {
			try {
				categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(categoryForm, "category.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid CategoryForm categoryForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Category category = new Category();

		category = categoryService.reconstruct(categoryForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(categoryForm);
		} else {
			try {
				categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(categoryForm, "category.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int categoryId) {
		ModelAndView result = new ModelAndView();

		Category category = categoryService.findOne(categoryId);
		try {
			categoryService.delete(category);
			result = new ModelAndView("redirect:/category/list.do");
		} catch (Throwable oops) {
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

		Collection<Tournament> tournaments = tournamentService.findAll();
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
