
package forms;

import java.util.Date;

import domain.Sport;

public class TournamentForm {

	//Attributes

	private String	name;
	private String	description;
	private Date	startMoment;
	private Date	endMoment;
	private Sport	sport;
	private int		id;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

}
