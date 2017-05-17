
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Manager;

public class ManagerToStringConverter implements Converter<Manager, String> {

	@Override
	public String convert(Manager manager) {
		String result;

		if (manager == null) {
			result = null;
		} else {
			result = String.valueOf(manager.getId());
		}

		return result;
	}

}
