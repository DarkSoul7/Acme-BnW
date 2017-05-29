package forms;

import java.util.Date;

public class MatchForm {

	//Attributes

	private Date	startMoment;
	private Date	endMoment;
	private int		id;
	private int		idTeamLocal;
	private int		idTeamVisitor;
	private int		idFixture;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getIdTeamLocal() {
		return idTeamLocal;
	}

	public void setIdTeamLocal(int idTeamLocal) {
		this.idTeamLocal = idTeamLocal;
	}

	public int getIdTeamVisitor() {
		return idTeamVisitor;
	}

	public void setIdTeamVisitor(int idTeamVisitor) {
		this.idTeamVisitor = idTeamVisitor;
	}

	public int getIdFixture() {
		return idFixture;
	}

	public void setIdFixture(int idFixture) {
		this.idFixture = idFixture;
	}

}
