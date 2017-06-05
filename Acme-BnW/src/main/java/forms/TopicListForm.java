package forms;

import java.util.Date;

import domain.Customer;

public class TopicListForm {

	private int			id;
	private String		title;
	private String		description;
	private Date		creationMoment;
	private Long		starsNumber;
	private Customer	customer;


	public TopicListForm() {
		super();
	}

	public TopicListForm(int id, String title, String description, Date creationMoment, Long starsNumber, Customer customer) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.creationMoment = creationMoment;
		this.starsNumber = starsNumber;
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public Long getStarsNumber() {
		return starsNumber;
	}

	public void setStarsNumber(Long starsNumber) {
		this.starsNumber = starsNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
