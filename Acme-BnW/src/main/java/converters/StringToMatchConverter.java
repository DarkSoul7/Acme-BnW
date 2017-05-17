
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Match;
import repositories.MatchRepository;

public class StringToMatchConverter implements Converter<String, Match> {

	@Autowired
	private MatchRepository matchRepository;


	@Override
	public Match convert(String text) {
		Match result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = matchRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
