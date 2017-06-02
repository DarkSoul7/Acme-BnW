
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.ConvertionCurrency;

public class ConvertionCurrencyToStringConverter implements Converter<ConvertionCurrency, String> {

	@Override
	public String convert(final ConvertionCurrency convertionCurrency) {
		String result;

		if (convertionCurrency == null) {
			result = null;
		} else {
			result = String.valueOf(convertionCurrency.getId());
		}

		return result;
	}
}
