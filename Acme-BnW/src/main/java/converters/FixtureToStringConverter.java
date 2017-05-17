
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Fixture;

public class FixtureToStringConverter implements Converter<Fixture, String> {

	@Override
	public String convert(Fixture fixture) {
		String result;

		if (fixture == null) {
			result = null;
		} else {
			result = String.valueOf(fixture.getId());
		}

		return result;
	}

}
