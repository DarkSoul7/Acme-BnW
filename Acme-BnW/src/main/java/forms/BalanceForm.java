
package forms;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import domain.Currency;

public class BalanceForm {

	//Attributes

	private Double		balance;
	private Currency	currency;


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

	@NotNull
	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

}
