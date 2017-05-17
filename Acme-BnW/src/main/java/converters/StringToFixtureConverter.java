
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Fixture;
import repositories.FixtureRepository;

public class StringToFixtureConverter implements Converter<String, Fixture> {

	@Autowired
	private FixtureRepository fixtureRepository;


	@Override
	public Fixture convert(String text) {
		Fixture result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = fixtureRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
