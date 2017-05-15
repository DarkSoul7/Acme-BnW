
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Bet extends DomainEntity {

	private Double quantity;
	private Double fee;
	private Date creationMoment;
	private Type type;
	private Status status;
	
	
	@NotNull
	//TODO @Min(0,01)
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@NotNull
	//TODO @Min(0,01)
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Past
	@NotNull
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@NotNull
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@NotNull
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	//Relationships
	private Customer customer;
	private Bet parentBet;
	private Collection<Bet> childrenBets;
	private Market market;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Bet getParentBet() {
		return parentBet;
	}

	public void setParentBet(Bet parentBet) {
		this.parentBet = parentBet;
	}

	@Valid
	@OneToMany(mappedBy = "parentBet")
	public Collection<Bet> getChildrenBets() {
		return childrenBets;
	}

	public void setChildrenBets(Collection<Bet> childrenBets) {
		this.childrenBets = childrenBets;
	}

	@Valid
	@ManyToOne(optional = false)
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}
	
	
	
	

}
