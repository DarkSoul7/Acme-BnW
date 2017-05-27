
package forms;

public class ResultForm {

	//Attributes

	private int	idMatch;
	private int	localGoal;
	private int	visitorGoal;


	//Constructor

	public ResultForm() {
		super();
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public int getLocalGoal() {
		return localGoal;
	}

	public void setLocalGoal(int localGoal) {
		this.localGoal = localGoal;
	}

	public int getVisitorGoal() {
		return visitorGoal;
	}

	public void setVisitorGoal(int visitorGoal) {
		this.visitorGoal = visitorGoal;
	}

}
