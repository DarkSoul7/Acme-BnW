package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Ticket;
import repositories.TicketRepository;

@Service
@Transactional
public class TicketService {

	// Managed repository

	@Autowired
	private TicketRepository ticketRepository;

	// Supported services

	public TicketService() {
		super();
	}

	public Ticket create() {
		return new Ticket();
	}

	public Collection<Ticket> findAll() {
		return this.ticketRepository.findAll();
	}

	public  Ticket findOne(final int ticketId) {
		return this.ticketRepository.findOne(ticketId);

	}

	public void save(final  Ticket  ticket) {
		this.ticketRepository.save(ticket);
	}

	public void delete(final  Ticket ticket) {
		this.ticketRepository.delete(ticket);
	}
}
