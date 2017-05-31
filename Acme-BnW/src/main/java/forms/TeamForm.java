package forms;

public class TeamForm {

	//Attributes

	private String	name;
	private String	shield;
	private int		id;


	public TeamForm() {
		super();
	}

	public TeamForm(String name, String shield, int id) {
		this.name = name;
		this.shield = shield;
		this.id = id;
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

}
