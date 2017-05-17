
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Bet;

public class BetToStringConverter implements Converter<Bet, String> {

	@Override
	public String convert(Bet bet) {
		String result;

		if (bet == null) {
			result = null;
		} else {
			result = String.valueOf(bet.getId());
		}

		return result;
	}

}
