package domain;

public enum MarketType {

	LOCALVICTORY("LOCALVICTORY", "Local victory", "Victoria local"),
	VISITORVICTORY("VISITORVICTORY", "Visitor victory", "Victoria visitante"),
	TIE("TIE", "Tie", "Empate");

	private final String	constant;
	private final String	name;
	private final String	spanishName;


	private MarketType(String constant, String name, String spanishName) {
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
