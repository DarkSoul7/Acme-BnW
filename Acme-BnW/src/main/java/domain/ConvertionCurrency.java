
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class ConvertionCurrency extends DomainEntity {

	//Attributes

	private Currency	currency;
	private Double		convertionAmount;


	//Construtor

	public ConvertionCurrency() {
		super();
	}

	//Getter & setter

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public Double getConvertionAmount() {
		return this.convertionAmount;
	}

	public void setConvertionAmount(final Double convertionAmount) {
		this.convertionAmount = convertionAmount;
	}

}
