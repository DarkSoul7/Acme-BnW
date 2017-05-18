
package forms;

import java.util.Date;

import domain.Coordinates;
import domain.CreditCard;

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
	private String		userName;
	private String		password;
	private String		repeatPassword;
	private boolean		acceptCondition;
	private boolean		overAge;


	//COnstructor

	public CustomerForm() {
		super();
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(final String nid) {
		this.nid = nid;
	}

	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

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

}
