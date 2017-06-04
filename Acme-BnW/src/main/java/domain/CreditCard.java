
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	holderName;
	private Brand	brandName;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	cvv;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	public Brand getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final Brand brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Range(min = 2017, max = 3000)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 900)
	public Integer getCvv() {
		return this.cvv;
	}

	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}

}
