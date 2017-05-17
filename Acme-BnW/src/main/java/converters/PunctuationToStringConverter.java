
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Punctuation;

public class PunctuationToStringConverter implements Converter<Punctuation, String> {

	@Override
	public String convert(Punctuation punctuation) {
		String result;

		if (punctuation == null) {
			result = null;
		} else {
			result = String.valueOf(punctuation.getId());
		}

		return result;
	}

}
