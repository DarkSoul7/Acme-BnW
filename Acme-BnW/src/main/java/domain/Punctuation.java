
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"customer_id", "topic_id"
	})
})
public class Punctuation extends DomainEntity {

	private Integer	stars;


	@NotNull
	@Range(min = 0, max = 5)
	public Integer getStars() {
		return this.stars;
	}

	public void setStars(final Integer stars) {
		this.stars = stars;
	}


	//Relationships
	private Customer	customer;
	private Topic		topic;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@ManyToOne(optional = false)
	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(final Topic topic) {
		this.topic = topic;
	}

}
