package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Market extends DomainEntity {

	private MarketType	type;
	private Double		fee;


	@NotNull
	public MarketType getType() {
		return type;
	}

	public void setType(MarketType type) {
		this.type = type;
	}

	@NotNull
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}


	//RelationShip
	private Promotion		promotion;
	private Match			match;
	private Collection<Bet>	bets;


	@Valid
	@OneToOne(mappedBy = "market", optional = true)
	public Promotion getPromotion() {
		return this.promotion;
	}

	public void setPromotion(final Promotion promotion) {
		this.promotion = promotion;
	}

	@Valid
	@ManyToOne(optional = false)
	public Match getMatch() {
		return this.match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Valid
	@OneToMany(mappedBy = "market")
	public Collection<Bet> getBets() {
		return this.bets;
	}

	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}

}
