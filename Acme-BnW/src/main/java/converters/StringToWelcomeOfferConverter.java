
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.WelcomeOffer;
import repositories.WelcomeOfferRepository;

public class StringToWelcomeOfferConverter implements Converter<String, WelcomeOffer> {

	@Autowired
	private WelcomeOfferRepository welcomeOfferRepository;


	@Override
	public WelcomeOffer convert(String text) {
		WelcomeOffer result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = welcomeOfferRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
