package domain;

public enum Type {

	SIMPLE("SIMPLE", "SIMPLE"), MULTIPLE("MULTIPLE", "MULTIPLE"), CHILD("CHILD", "HIJA");

	private final String	name;
	private final String	spanishName;


	private Type(String name, String spanishName) {
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
