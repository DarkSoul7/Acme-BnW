
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Market;
import repositories.MarketRepository;

public class StringToMarketConverter implements Converter<String, Market> {

	@Autowired
	private MarketRepository marketRepository;


	@Override
	public Market convert(String text) {
		Market result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = marketRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
