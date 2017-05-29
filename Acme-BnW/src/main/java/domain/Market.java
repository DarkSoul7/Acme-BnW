
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Market extends DomainEntity {

	private String	title;
	private Double	fee;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	//TODO @Min(1,01)
	public Double getFee() {
		return this.fee;
	}
	public void setFee(final Double fee) {
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
	public void setMatch(final Match match) {
		this.match = match;
	}
	@Valid
	@OneToMany(mappedBy = "market")
	public Collection<Bet> getBets() {
		return this.bets;
	}
	public void setBets(final Collection<Bet> bets) {
		this.bets = bets;
	}

}
