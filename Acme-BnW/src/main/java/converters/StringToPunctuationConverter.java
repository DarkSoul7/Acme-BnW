
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Punctuation;
import repositories.PunctuationRepository;

public class StringToPunctuationConverter implements Converter<String, Punctuation> {

	@Autowired
	private PunctuationRepository punctuationRepository;


	@Override
	public Punctuation convert(String text) {
		Punctuation result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = punctuationRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
