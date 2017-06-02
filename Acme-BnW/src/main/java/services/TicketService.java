
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TicketRepository;
import domain.ConvertionCurrency;
import domain.Currency;
import domain.Customer;
import domain.Ticket;

@Service
@Transactional
public class TicketService {

	// Managed repository

	@Autowired
	private TicketRepository			ticketRepository;

	// Supported services
	@Autowired
	private CustomerService				customerService;

	@Autowired
	private ConvertionCurrencyService	convertionCurrencyService;


	//Constructor

	public TicketService() {
		super();
	}

	public Ticket create() {
		final Ticket result = new Ticket();
		final Customer customer = this.customerService.findByPrincipal();
		result.setCustomer(customer);

		return result;
	}

	public Collection<Ticket> findAll() {
		return this.ticketRepository.findAll();
	}

	public Ticket findOne(final int ticketId) {
		return this.ticketRepository.findOne(ticketId);

	}

	public void save(final Ticket ticket) {
		Assert.notNull(ticket);
		final Customer customer = this.customerService.findByPrincipal();
		Assert.isTrue(ticket.getCustomer().equals(customer));

		this.ticketRepository.save(ticket);
	}

	@Deprecated
	public void delete(final Ticket ticket) {
		this.ticketRepository.delete(ticket);
	}

	//Other business services

	public Ticket getDefaultEnglishExtractionTicket(final double amount, final Currency currency) {
		final Ticket result = this.create();

		result.setDescription("This ticket be like an invoice to get evidence the extracted money. " + "This ticket has been auto-generated by the system.");
		result.setMoment(new Date());
		result.setTitle("Extraction ticket: " + amount + " vistual credits");

		//Conversi�n de moneda
		final ConvertionCurrency convertionCurrency = this.convertionCurrencyService.findOne(currency);
		final Double convertedAmount = amount / convertionCurrency.getConvertionAmount();
		result.setConvertedMoney(convertedAmount);
		result.setAmount(amount);
		result.setCurrency(currency.getEnglishName());

		return result;
	}
	public Ticket getDefaultSpanishExtractionTicket(final double amount, final Currency currency) {
		final Ticket result = this.create();

		result.setDescription("�sto es una factura que sirve a modo de resguardo del dinero extra�do. " + "�sta factura ha sido autogenerada por el sistema.");
		result.setMoment(new Date());
		result.setTitle("Cantidad extra�da: " + amount + " vistual credits");

		final ConvertionCurrency convertionCurrency = this.convertionCurrencyService.findOne(currency);
		final Double convertedAmount = amount / convertionCurrency.getConvertionAmount();
		result.setConvertedMoney(convertedAmount);
		result.setAmount(amount);
		result.setCurrency(currency.getSpanishName());

		return result;
	}
}
