package forms;

public class ResultForm {

	//Attributes

	private int		idMatch;
	private Integer	localResult;
	private Integer	visitorResult;


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

	public Integer getLocalResult() {
		return localResult;
	}

	public void setLocalResult(Integer localResult) {
		this.localResult = localResult;
	}

	public Integer getVisitorResult() {
		return visitorResult;
	}

	public void setVisitorResult(Integer visitorResult) {
		this.visitorResult = visitorResult;
	}

}
