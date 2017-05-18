
package forms;

import javax.validation.constraints.Min;

public class BalanceForm {

	//Attributes

	private Double	balance;


	//Constructor

	public BalanceForm() {
		super();
	}

	//Getter & setter

	@Min(1)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(final Double balance) {
		this.balance = balance;
	}

}
