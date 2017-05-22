
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getShield() {
		return this.shield;
	}

	public void setShield(final String shield) {
		this.shield = shield;
	}


	//Relationships
	private Collection<Customer>	customers;
	private Collection<Match>		visitorMatches;
	private Collection<Match>		localMatches;


	@Valid
	@ManyToMany
	@JoinTable(name = "favouriteTeams_customer", joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "customer_id",
		referencedColumnName = "id"))
	public Collection<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(final Collection<Customer> customers) {
		this.customers = customers;
	}

	@Valid
	@OneToMany(mappedBy = "visitorTeam")
	public Collection<Match> getVisitorMatches() {
		return this.visitorMatches;
	}

	public void setVisitorMatches(final Collection<Match> visitorMatches) {
		this.visitorMatches = visitorMatches;
	}

	@Valid
	@OneToMany(mappedBy = "localTeam")
	public Collection<Match> getLocalMatches() {
		return this.localMatches;
	}

	public void setLocalMatches(final Collection<Match> localMatches) {
		this.localMatches = localMatches;
	}

}
