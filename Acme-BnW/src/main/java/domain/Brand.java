package domain;

public enum Brand {

	VISA("VISA", "VISA", "VISA"),
	MASTERCARD("MASTERCARD", "MASTERCARD", "MASTERCARD"),
	DISCOVER("DISCOVER", "DISCOVER", "DISCOVER"),
	DINNERS("DINNERS", "DINNERS", "DINNERS"),
	AMEX("AMEX", "AMEX", "AMEX");

	private final String	constant;
	private final String	name;
	private final String	spanishName;


	private Brand(String constant, String name, String spanishName) {
		this.constant = constant;
		this.name = name;
		this.spanishName = spanishName;
	}

	public String getConstant() {
		return constant;
	}

	public String getName() {
		return name;
	}

	public String getSpanishName() {
		return spanishName;
	}

}
