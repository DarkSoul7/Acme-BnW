
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Match extends DomainEntity {

	private Date startMoment;
	private Date endMoment;
	
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	public Date getEndMoment() {
		return endMoment;
	}
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	
	//RelationShip
	private Fixture fixture;
	private Collection<Market> markets;
	private Team visitorTeam;
	private Team localTeam;

	@Valid
	@ManyToOne(optional=false)
	public Fixture getFixture() {
		return fixture;
	}
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
	@Valid
	@OneToMany(mappedBy="match")
	public Collection<Market> getMarkets() {
		return markets;
	}
	public void setMarkets(Collection<Market> markets) {
		this.markets = markets;
	}
	@Valid
	@ManyToOne(optional=false)
	public Team getVisitorTeam() {
		return visitorTeam;
	}
	public void setVisitorTeam(Team visitorTeam) {
		this.visitorTeam = visitorTeam;
	}
	@Valid
	@ManyToOne(optional=false)
	public Team getLocalTeam() {
		return localTeam;
	}
	public void setLocalTeam(Team localTeam) {
		this.localTeam = localTeam;
	}
	
	
	
	
}
