
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Promotion extends DomainEntity {

	private String	title;
	private String	description;
	private Double	fee;
	private Date	startMoment;
	private Date	endMoment;
	private boolean	cancel;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public Double getFee() {
		return this.fee;
	}
	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndMoment() {
		return this.endMoment;
	}
	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	public boolean isCancel() {
		return this.cancel;
	}

	public void setCancel(final boolean cancel) {
		this.cancel = cancel;
	}


	//RelationShip
	private Market	market;


	@Valid
	@OneToOne(optional = false)
	public Market getMarket() {
		return this.market;
	}
	public void setMarket(final Market market) {
		this.market = market;
	}

}
