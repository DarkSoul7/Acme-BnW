
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private Date		birthDate;
	private CreditCard	creditCard;
	private Double		balance;
	private Boolean		finishedOffer;


	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Boolean getFinishedOffer() {
		return finishedOffer;
	}

	public void setFinishedOffer(Boolean finishedOffer) {
		this.finishedOffer = finishedOffer;
	}


	//RelationShips
	private Collection<Topic>		topics;
	private Collection<Punctuation>	punctuacions;
	private Collection<Ticket>		tickets;
	private Collection<Message>		messages;
	private Collection<Bet>			bets;
	private Collection<Team>		teams;
	private WelcomeOffer			welcomeOffer;


	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Collection<Topic> topics) {
		this.topics = topics;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Punctuation> getPunctuacions() {
		return punctuacions;
	}

	public void setPunctuacions(Collection<Punctuation> punctuacions) {
		this.punctuacions = punctuacions;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Collection<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Bet> getBets() {
		return bets;
	}

	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}

	@Valid
	@ManyToMany()
	public Collection<Team> getTeams() {
		return teams;
	}

	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}

	@Valid
	@ManyToOne(optional = true)
	public WelcomeOffer getWelcomeOffer() {
		return welcomeOffer;
	}

	public void setWelcomeOffer(WelcomeOffer welcomeOffer) {
		this.welcomeOffer = welcomeOffer;
	}

}
