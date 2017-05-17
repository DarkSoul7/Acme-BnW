
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Bet;
import repositories.BetRepository;

public class StringToBetConverter implements Converter<String, Bet> {

	@Autowired
	private BetRepository betRepository;


	@Override
	public Bet convert(String text) {
		Bet result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = betRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
