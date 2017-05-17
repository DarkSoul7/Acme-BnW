
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Promotion;
import repositories.PromotionRepository;

public class StringToPromotionConverter implements Converter<String, Promotion> {

	@Autowired
	private PromotionRepository promotionRepository;


	@Override
	public Promotion convert(String text) {
		Promotion result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = promotionRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
