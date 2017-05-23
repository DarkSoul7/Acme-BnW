
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class WelcomeOffer extends DomainEntity {

	private String title;
	private Date	openPeriod;
	private Date	endPeriod;
	private Double	amount;
	private Double	extractionAmount;

	
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getOpenPeriod() {
		return openPeriod;
	}

	public void setOpenPeriod(Date openPeriod) {
		this.openPeriod = openPeriod;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

	@NotNull
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@NotNull
	public Double getExtractionAmount() {
		return extractionAmount;
	}

	public void setExtractionAmount(Double extractionAmount) {
		this.extractionAmount = extractionAmount;
	}


	//Relationships
	private Collection<Customer> customers;


	@Valid
	@OneToMany(mappedBy = "welcomeOffer")
	public Collection<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}

}
