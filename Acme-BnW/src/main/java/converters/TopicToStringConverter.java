
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Topic;

public class TopicToStringConverter implements Converter<Topic, String> {

	@Override
	public String convert(Topic topic) {
		String result;

		if (topic == null) {
			result = null;
		} else {
			result = String.valueOf(topic.getId());
		}

		return result;
	}

}
