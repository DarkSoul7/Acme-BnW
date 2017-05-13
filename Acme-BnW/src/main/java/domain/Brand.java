package domain;

public enum Brand {

	VISA("VISA"), MASTERCARD("MASTERCARD"), DISCOVER("DISCOVER"),
	DINNERS("DINNERS"), AMEX("AMEX");

	private final String	name;


	private Brand(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
