package domain;

public enum Sport {

	FOOTBALL("FOOTBALL", "Football", "Fútbol"),
	BASKETBALL("BASKETBALL", "Basketball", "Baloncesto");

	private final String	constant;
	private final String	name;
	private final String	spanishName;


	private Sport(String constant, String name, String spanishName) {
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
