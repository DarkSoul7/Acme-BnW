
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	//TODO @Min(1,01)
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}


	//RelationShip
	private Collection<Promotion>	promotions;
	private Match					match;
	private Collection<Bet>			bets;


	@Valid
	@OneToMany(mappedBy = "market")
	public Collection<Promotion> getPromotions() {
		return promotions;
	}
	public void setPromotions(Collection<Promotion> promotions) {
		this.promotions = promotions;
	}
	@Valid
	@ManyToOne(optional = false)
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	@Valid
	@OneToMany(mappedBy = "market")
	public Collection<Bet> getBets() {
		return bets;
	}
	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}

}
