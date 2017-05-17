
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.WelcomeOffer;

public class WelcomeOfferToStringConverter implements Converter<WelcomeOffer, String> {

	@Override
	public String convert(WelcomeOffer welcomeOffer) {
		String result;

		if (welcomeOffer == null) {
			result = null;
		} else {
			result = String.valueOf(welcomeOffer.getId());
		}

		return result;
	}

}
