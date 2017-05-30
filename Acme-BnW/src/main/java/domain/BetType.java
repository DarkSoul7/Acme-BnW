package domain;

public enum BetType {

	SIMPLE("SIMPLE", "Simple", "Simple"),
	MULTIPLE("MULTIPLE", "Multiple", "Combinada"),
	CHILD("CHILD", "Child", "Hija");

	private final String	constant;
	private final String	name;
	private final String	spanishName;


	private BetType(String constant, String name, String spanishName) {
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
