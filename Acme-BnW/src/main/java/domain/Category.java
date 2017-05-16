
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

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String	name;
	private String	description;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	//RelationShip
	private Collection<Fixture>	fixtures;
	private Tournament			tournament;


	@Valid
	@OneToMany(mappedBy = "category")
	public Collection<Fixture> getFixtures() {
		return fixtures;
	}

	public void setFixtures(Collection<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	@Valid
	@ManyToOne(optional = false)
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

}
