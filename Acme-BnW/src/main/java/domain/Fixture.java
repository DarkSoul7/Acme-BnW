
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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Fixture extends DomainEntity {

	private String	title;
	private Date	startMoment;
	private Date	endMoment;


	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}
	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}


	//RelationShip
	private Collection<Match>	matches;
	private Category			category;


	@Valid
	@OneToMany(mappedBy = "fixture")
	public Collection<Match> getMatches() {
		return this.matches;
	}
	public void setMatches(final Collection<Match> matches) {
		this.matches = matches;
	}
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(final Category category) {
		this.category = category;
	}

}
