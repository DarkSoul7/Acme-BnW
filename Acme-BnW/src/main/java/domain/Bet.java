package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Bet extends DomainEntity {

	private Double	quantity;
	private Double	fee;
	private Date	creationMoment;
	private Type	type;
	private Status	status;
	private Boolean	completed;


	@NotNull
	@DecimalMin("0.01")
	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final Double quantity) {
		this.quantity = quantity;
	}

	@NotNull
	@DecimalMin("1.01")
	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@Past
	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@NotNull
	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	@NotNull
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	@NotNull
	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}


	//Relationships
	private Customer		customer;
	private Bet				parentBet;
	private Collection<Bet>	childrenBets;
	private Market			market;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Bet getParentBet() {
		return this.parentBet;
	}

	public void setParentBet(final Bet parentBet) {
		this.parentBet = parentBet;
	}

	@Valid
	@OneToMany(mappedBy = "parentBet")
	public Collection<Bet> getChildrenBets() {
		return this.childrenBets;
	}

	public void setChildrenBets(final Collection<Bet> childrenBets) {
		this.childrenBets = childrenBets;
	}

	@Valid
	@ManyToOne(optional = true)
	public Market getMarket() {
		return this.market;
	}

	public void setMarket(final Market market) {
		this.market = market;
	}

}
