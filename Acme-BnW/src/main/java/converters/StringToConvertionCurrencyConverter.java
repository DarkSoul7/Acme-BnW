
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import services.ConvertionCurrencyService;
import domain.ConvertionCurrency;

public class StringToConvertionCurrencyConverter implements Converter<String, ConvertionCurrency> {

	@Autowired
	private ConvertionCurrencyService	convertionCurrencyService;


	@Override
	public ConvertionCurrency convert(final String text) {
		ConvertionCurrency result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.convertionCurrencyService.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
