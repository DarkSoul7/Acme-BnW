package domain;

public enum Sport {

	FOOTBALL("FOOTBALL","F�TBOL"), BASKETBALL("BASKETBALL","BALONCESTO");

	private final String	name;
	private final String	spanishName;


	private Sport(String name, String spanishName) {
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
