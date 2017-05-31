package forms;

import domain.Team;

public class ListTeamForm extends TeamForm {

	//Attributes

	private Boolean	favourite;


	public ListTeamForm(Team team, Boolean favourite) {
		super(team.getName(), team.getShield(), team.getId());
		this.favourite = favourite;
	}

	public Boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}

}
