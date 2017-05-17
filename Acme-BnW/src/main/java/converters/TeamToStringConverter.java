
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Team;

public class TeamToStringConverter implements Converter<Team, String> {

	@Override
	public String convert(Team team) {
		String result;

		if (team == null) {
			result = null;
		} else {
			result = String.valueOf(team.getId());
		}

		return result;
	}

}
