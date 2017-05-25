
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Category;
import domain.Fixture;
import domain.Tournament;
import forms.CategoryForm;
import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	//Managed repository

	@Autowired
	private CategoryRepository	categoryRepository;

	//Supported services
	@Autowired
	private TournamentService	tournamentService;


	public CategoryService() {
		super();
	}

	public CategoryForm create() {
		CategoryForm result = new CategoryForm();

		return result;
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);

	}

	public void save(final Category category) {
		this.categoryRepository.save(category);
	}

	public void delete(final Category category) {
		this.categoryRepository.delete(category);
	}

	//C3
	public Category categoryMoreBets() {
		return categoryRepository.categoryMoreBets();
	}

	//C4
	public Category categoryLessBets() {
		return categoryRepository.categoryLessBets();
	}


	@Autowired
	private Validator validator;


	public Category reconstruct(CategoryForm categoryForm, BindingResult binding) {
		Assert.notNull(categoryForm);

		Category result = new Category();
		if (categoryForm.getId() != 0) {
			result = this.findOne(categoryForm.getId());
		} else {
			Tournament tournament = tournamentService.findOne(categoryForm.getIdTournament());
			result.setFixtures(new ArrayList<Fixture>());
			result.setTournament(tournament);
		}

		result.setName(categoryForm.getName());
		result.setDescription(categoryForm.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public CategoryForm toFormObject(Category category) {
		CategoryForm result = this.create();
		result.setDescription(category.getDescription());
		result.setName(category.getName());
		result.setId(category.getId());
		return result;
	}

	public Collection<Category> categoriesOfTournament(int id) {
		return categoryRepository.categoriesOfTournament(id);
	}

}
