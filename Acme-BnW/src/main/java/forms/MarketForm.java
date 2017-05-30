package forms;

import domain.MarketType;

public class MarketForm {

	//Attributes

	private MarketType	type;
	private Double		fee;
	private int			id;
	private int			idMatch;


	public MarketType getType() {
		return type;
	}

	public void setType(MarketType type) {
		this.type = type;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

}
