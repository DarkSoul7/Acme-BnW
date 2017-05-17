
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Match;

public class MatchToStringConverter implements Converter<Match, String> {

	@Override
	public String convert(Match match) {
		String result;

		if (match == null) {
			result = null;
		} else {
			result = String.valueOf(match.getId());
		}

		return result;
	}

}
