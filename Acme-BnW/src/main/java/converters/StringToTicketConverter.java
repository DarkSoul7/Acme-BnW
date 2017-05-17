
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import domain.Ticket;
import repositories.TicketRepository;

public class StringToTicketConverter implements Converter<String, Ticket> {

	@Autowired
	private TicketRepository ticketRepository;


	@Override
	public Ticket convert(String text) {
		Ticket result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = ticketRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
