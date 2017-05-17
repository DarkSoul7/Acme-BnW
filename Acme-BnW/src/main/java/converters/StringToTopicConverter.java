
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Topic;
import repositories.TopicRepository;

public class StringToTopicConverter implements Converter<String, Topic> {

	@Autowired
	private TopicRepository topicRepository;


	@Override
	public Topic convert(String text) {
		Topic result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = topicRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
