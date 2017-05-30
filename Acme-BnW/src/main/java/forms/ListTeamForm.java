
package forms;

import domain.Team;

public class ListTeamForm {

	//Attributes

	private String	name;
	private String	shield;
	private int		id;
	private Boolean	favourite;


	public ListTeamForm(Team team, Boolean favourite) {
		super();

		this.setId(team.getId());

		this.name = team.getName();
		this.shield = team.getShield();
		this.favourite = favourite;

	}

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

	public String getShield() {
		return shield;
	}

	public void setShield(String shield) {
		this.shield = shield;
	}

	public Boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}

}
