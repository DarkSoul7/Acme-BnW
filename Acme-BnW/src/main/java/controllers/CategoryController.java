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
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/listByTournament", method = RequestMethod.GET)
	public ModelAndView listByTournament(@RequestParam int tournamentId, @RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.categoriesOfTournament(tournamentId);

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/listOfTournament.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		CategoryForm categoryForm = this.categoryService.create();
		result = this.createModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int categoryId) {
		ModelAndView result = new ModelAndView();

		Category category = this.categoryService.findOne(categoryId);
		CategoryForm categoryForm = this.categoryService.toFormObject(category);
		result = this.editModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Category category = new Category();

		category = this.categoryService.reconstruct(categoryForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(categoryForm);
		} else {
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do");
				result.addObject("successMessage", "category.register.success");
			} catch (Throwable oops) {
				result = this.createModelAndView(categoryForm, "category.register.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid CategoryForm categoryForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Category category = new Category();

		category = this.categoryService.reconstruct(categoryForm, binding);
		if (binding.hasErrors()) {
			result = this.editModelAndView(categoryForm);
		} else {
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do");
				result.addObject("successMessage", "category.edit.success");
			} catch (Throwable oops) {
				result = this.editModelAndView(categoryForm, "category.edit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int categoryId) {
		ModelAndView result = new ModelAndView();

		Category category = this.categoryService.findOne(categoryId);
		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/list.do");
			result.addObject("successMessage", "category.delete.success");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/category/list.do");
			result.addObject("errorMessage", "category.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(CategoryForm categoryForm) {
		return this.createModelAndView(categoryForm, null);
	}

	protected ModelAndView createModelAndView(CategoryForm categoryForm, String errorMessage) {
		ModelAndView result;

		Collection<Tournament> tournaments = this.tournamentService.findAll();
		result = new ModelAndView("category/register");
		result.addObject("categoryForm", categoryForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("tournaments", tournaments);
		result.addObject("requestURI", "category/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(CategoryForm categoryForm) {
		return this.editModelAndView(categoryForm, null);
	}

	protected ModelAndView editModelAndView(CategoryForm categoryForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("categoryForm", categoryForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("requestURI", "category/edit.do");

		return result;
	}

}
