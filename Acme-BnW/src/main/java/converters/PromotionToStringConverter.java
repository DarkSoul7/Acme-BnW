
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Promotion;

public class PromotionToStringConverter implements Converter<Promotion, String> {

	@Override
	public String convert(Promotion promotion) {
		String result;

		if (promotion == null) {
			result = null;
		} else {
			result = String.valueOf(promotion.getId());
		}

		return result;
	}

}
