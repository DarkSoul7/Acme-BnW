
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Market;

public class MarketToStringConverter implements Converter<Market, String> {

	@Override
	public String convert(Market market) {
		String result;

		if (market == null) {
			result = null;
		} else {
			result = String.valueOf(market.getId());
		}

		return result;
	}

}
