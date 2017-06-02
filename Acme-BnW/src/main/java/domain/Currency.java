
package domain;

public enum Currency {

	EURO("EURO", "Euro", "Euro"), USD("USD", "USD", "USD"), POUND("LB", "LB", "LB");

	private String	currencyName;
	private String	englishName;
	private String	spanishName;


	private Currency(final String currencyName, final String englishName, final String spanishName) {
		this.currencyName = currencyName;
		this.englishName = englishName;
		this.spanishName = spanishName;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(final String currencyName) {
		this.currencyName = currencyName;
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
