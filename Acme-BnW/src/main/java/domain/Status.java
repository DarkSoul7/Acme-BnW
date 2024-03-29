package domain;

public enum Status {

	PENDING("PENDING", "Pending", "Pendiente"),
	SUCCESSFUL("SUCCESSFUL", "Successful", "Ganada"),
	FAILED("FAILED", "Failed", "Perdida");

	private final String	constant;
	private final String	name;
	private final String	spanishName;


	private Status(String constant, String name, String spanishName) {
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
