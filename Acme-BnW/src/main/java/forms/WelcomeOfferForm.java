
package forms;


public class WelcomeOfferForm {

	//Attributes
	private int		id;
	private String	title;
	private String	openPeriod;
	private String	endPeriod;
	private Double	amount;
	private Double	extractionAmount;


	//Constructor
	public WelcomeOfferForm() {
		super();
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getOpenPeriod() {
		return this.openPeriod;
	}

	public void setOpenPeriod(final String openPeriod) {
		this.openPeriod = openPeriod;
	}

	public String getEndPeriod() {
		return this.endPeriod;
	}

	public void setEndPeriod(final String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public Double getExtractionAmount() {
		return this.extractionAmount;
	}

	public void setExtractionAmount(final Double extractionAmount) {
		this.extractionAmount = extractionAmount;
	}

}
