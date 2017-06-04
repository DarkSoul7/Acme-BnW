
package forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Coordinates;
import domain.CreditCard;
import domain.Customer;

public class CustomerForm {

	//Attributes

	private int			id;
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		nid;
	private Coordinates	coordinates;
	private Date		birthDate;
	private CreditCard	creditCard;
	private Double		balance;
	private Boolean		finishedOffer;
	private String		username;
	private String		password;
	private String		repeatPassword;
	private boolean		acceptCondition;
	private boolean		overAge;
	private Double		earnings;
	private Double		losses;


	//Constructor

	public CustomerForm() {
		super();
	}

	public CustomerForm(final Customer customer, final Double earnings, final Double losses) {
		super();
		this.id = customer.getId();
		this.name = customer.getName();
		this.surname = customer.getSurname();
		this.email = customer.getEmail();
		this.phone = customer.getPhone();
		this.nid = customer.getNid();
		this.coordinates = customer.getCoordinates();
		this.birthDate = customer.getBirthDate();
		this.creditCard = customer.getCreditCard();
		this.balance = customer.getBalance();
		this.finishedOffer = customer.getFinishedOffer();
		this.username = customer.getUserAccount().getUsername();
		this.earnings = earnings;
		this.losses = losses;
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "((\\+|00)\\d{2,4}(\\s)?)?\\d{9,13}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNid() {
		return this.nid;
	}

	public void setNid(final String nid) {
		this.nid = nid;
	}

	@NotNull
	@Valid
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(final Double balance) {
		this.balance = balance;
	}

	public Boolean getFinishedOffer() {
		return this.finishedOffer;
	}

	public void setFinishedOffer(final Boolean finishedOffer) {
		this.finishedOffer = finishedOffer;
	}

	@NotBlank
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	@AssertTrue
	public boolean isAcceptCondition() {
		return this.acceptCondition;
	}

	public void setAcceptCondition(final boolean acceptCondition) {
		this.acceptCondition = acceptCondition;
	}

	public boolean isOverAge() {
		return this.overAge;
	}

	public void setOverAge(final boolean overAge) {
		this.overAge = overAge;
	}

	public Double getEarnings() {
		return this.earnings;
	}

	public void setEarnings(final Double earnings) {
		this.earnings = earnings;
	}

	public Double getLosses() {
		return this.losses;
	}

	public void setLosses(final Double losses) {
		this.losses = losses;
	}

	@NotBlank
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
