
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Team extends DomainEntity {

	private String	name;
	private String	shield;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getShield() {
		return shield;
	}

	public void setShield(String shield) {
		this.shield = shield;
	}


	//Relationships
	private Customer			customer;
	private Collection<Match>	visitorMatches;
	private Collection<Match>	localMatches;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Valid
	@OneToMany(mappedBy = "visitorTeam")
	public Collection<Match> getVisitorMatches() {
		return visitorMatches;
	}

	public void setVisitorMatches(Collection<Match> visitorMatches) {
		this.visitorMatches = visitorMatches;
	}

	@Valid
	@OneToMany(mappedBy = "localTeam")
	public Collection<Match> getLocalMatches() {
		return localMatches;
	}

	public void setLocalMatches(Collection<Match> localMatches) {
		this.localMatches = localMatches;
	}

}
