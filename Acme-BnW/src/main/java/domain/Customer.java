
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
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
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
	private Boolean		overAge;
	private Boolean		activeWO;
	private Boolean		isDisabled;
	private int			banNum;


	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Min(0)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(final Double balance) {
		this.balance = balance;
	}

	public Boolean getFinishedOffer() {
		return this.finishedOffer;
	}

	public void setFinishedOffer(final Boolean finishedOffer) {
		this.finishedOffer = finishedOffer;
	}

	@Transient
	@AssertTrue
	public Boolean getOverAge() {
		return this.overAge;
	}

	public void setOverAge(final Boolean overAge) {
		this.overAge = overAge;
	}

	public Boolean getActiveWO() {
		return this.activeWO;
	}

	public void setActiveWO(final Boolean activeWO) {
		this.activeWO = activeWO;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public int getBanNum() {
		return banNum;
	}

	public void setBanNum(int banNum) {
		this.banNum = banNum;
	}





	//RelationShips
	private Collection<Topic>		topics;
	private Collection<Punctuation>	punctuations;
	private Collection<Ticket>		tickets;
	private Collection<Message>		messages;
	private Collection<Bet>			bets;
	private Collection<Team>		favouriteTeams;
	private WelcomeOffer			welcomeOffer;


	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(final Collection<Topic> topics) {
		this.topics = topics;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Punctuation> getPunctuations() {
		return this.punctuations;
	}

	public void setPunctuations(final Collection<Punctuation> punctuations) {
		this.punctuations = punctuations;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(final Collection<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Bet> getBets() {
		return this.bets;
	}

	public void setBets(final Collection<Bet> bets) {
		this.bets = bets;
	}

	@Valid
	@ManyToMany
	public Collection<Team> getFavouriteTeams() {
		return this.favouriteTeams;
	}

	public void setFavouriteTeams(final Collection<Team> favouriteTeams) {
		this.favouriteTeams = favouriteTeams;
	}

	@Valid
	@ManyToOne(optional = true)
	public WelcomeOffer getWelcomeOffer() {
		return this.welcomeOffer;
	}

	public void setWelcomeOffer(final WelcomeOffer welcomeOffer) {
		this.welcomeOffer = welcomeOffer;
	}

}
