
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "\"Match\"")
public class Match extends DomainEntity {

	private Date	startMoment;
	private Date	endMoment;


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
	private Fixture				fixture;
	private Collection<Market>	markets;
	private Team				visitorTeam;
	private Team				localTeam;


	@Valid
	@ManyToOne(optional = false)
	public Fixture getFixture() {
		return this.fixture;
	}
	public void setFixture(final Fixture fixture) {
		this.fixture = fixture;
	}
	@Valid
	@OneToMany(mappedBy = "match")
	public Collection<Market> getMarkets() {
		return this.markets;
	}
	public void setMarkets(final Collection<Market> markets) {
		this.markets = markets;
	}
	@Valid
	@ManyToOne(optional = false)
	public Team getVisitorTeam() {
		return this.visitorTeam;
	}
	public void setVisitorTeam(final Team visitorTeam) {
		this.visitorTeam = visitorTeam;
	}
	@Valid
	@ManyToOne(optional = false)
	public Team getLocalTeam() {
		return this.localTeam;
	}
	public void setLocalTeam(final Team localTeam) {
		this.localTeam = localTeam;
	}

}
