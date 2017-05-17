
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;
import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	//Managed repository

	@Autowired
	private CategoryRepository categoryRepository;


	//Supported services

	public CategoryService() {
		super();
	}

	public Category create() {
		return new Category();
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

}
