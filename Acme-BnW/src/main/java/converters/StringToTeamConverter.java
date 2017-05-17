
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Team;
import repositories.TeamRepository;

public class StringToTeamConverter implements Converter<String, Team> {

	@Autowired
	private TeamRepository teamRepository;


	@Override
	public Team convert(String text) {
		Team result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = teamRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
