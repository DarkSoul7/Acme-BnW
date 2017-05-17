
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Message;
import repositories.MessageRepository;

public class StringToMessageConverter implements Converter<String, Message> {

	@Autowired
	private MessageRepository messageRepository;


	@Override
	public Message convert(String text) {
		Message result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = messageRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
