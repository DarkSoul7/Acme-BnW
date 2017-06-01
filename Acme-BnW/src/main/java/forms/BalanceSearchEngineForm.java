
package forms;

public class BalanceSearchEngineForm {

	//Attributes
	private String	name;
	private String	surname;
	private String	nid;


	//Constructor

	public BalanceSearchEngineForm() {
		super();
	}

	//Getter & setter

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(final String nid) {
		this.nid = nid;
	}
}
