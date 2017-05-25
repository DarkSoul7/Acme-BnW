
package domain;

public enum Brand {

	VISA("VISA", "VISA"), MASTERCARD("MASTERCARD", "MASTERCARD"), DISCOVER("DISCOVER", "DISCOVER"), DINNERS("DINNERS", "DINNERS"), AMEX("AMEX", "AMEX");

	private final String	name;
	private final String	spanishName;


	private Brand(String name, String spanishName) {
		this.name = name;
		this.spanishName = spanishName;
	}

	public String getName() {
		return name;
	}

	public String getSpanishName() {
		return spanishName;
	}

}
