package core;

public class TerritoryCard {
	private String countryName;
	private String cardType;
	private Player cardOwner;
	
	
	public TerritoryCard(String countryName, String cardName){
		this.countryName=countryName;
		this.cardType=cardName;
	}


	public void setCountryName(String countryName){
		this.countryName = countryName;
	}
	
	
	public void setCardName(String cardName){
		this.cardType = cardName;
	}
	
	public String getCountryName(){
		return countryName;
	}
	public String getCardName(){
		return cardType;
	}
	
}
