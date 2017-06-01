
package forms;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BalanceForm {

	//Attributes

	private Double	balance;


	//Constructor

	public BalanceForm() {
		super();
	}

	//Getter & setter

	@DecimalMin(value = "1.0")
	@NotNull
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(final Double balance) {
		this.balance = balance;
	}

}
