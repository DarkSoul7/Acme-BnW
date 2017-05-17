
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Category;
import repositories.CategoryRepository;

public class StringToCategoryConverter implements Converter<String, Category> {

	@Autowired
	private CategoryRepository categoryRepository;


	@Override
	public Category convert(String text) {
		Category result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = categoryRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
