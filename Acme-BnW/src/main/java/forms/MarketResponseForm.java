package forms;

public class MarketResponseForm {

	private Integer	marketId;
	private Double	fee;


	public MarketResponseForm() {
		super();
	}

	public MarketResponseForm(Integer marketId, Double fee) {
		this.marketId = marketId;
		this.fee = fee;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
}
