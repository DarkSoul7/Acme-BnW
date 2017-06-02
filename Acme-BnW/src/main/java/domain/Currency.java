
package domain;

public enum Currency {

	EURO("EURO", "Euro", "Euro"), USD("USD", "USD", "USD"), LB("LB", "LB", "LB");

	private String	constant;
	private String	englishName;
	private String	spanishName;


	private Currency(final String constant, final String englishName, final String spanishName) {
		this.constant = constant;
		this.englishName = englishName;
		this.spanishName = spanishName;
	}

	public String getConstant() {
		return this.constant;
	}

	public void setConstant(final String constant) {
		this.constant = constant;
	}

	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}

	public String getSpanishName() {
		return this.spanishName;
	}

	public void setSpanishName(final String spanishName) {
		this.spanishName = spanishName;
	}

}
