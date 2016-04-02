package core;

import java.util.ArrayList;

public class TerritoryCard {
	private String countryName;
	private String cardType;
	private Player cardOwner;
	
	public static TerritoryCard[] territoryCardsBeforeShuffle = new TerritoryCard[42];
	
	public static TerritoryCard[] territoryCardsAfterShuffle = new TerritoryCard[42];
	ArrayList<String> Territorydeck = new ArrayList<>(42);
	String card = new String();
	int MAX_CARD_AMOUNT = 42;
	
	
	public TerritoryCard(String countryName, String cardName, Player cardOwner){
		this.countryName=countryName;
		this.cardType=cardName;
		this.cardOwner=cardOwner;
	}


	public void setCountryName(String countryName){
		this.countryName = countryName;
	}
	
	
	public void setCardName(String cardName){
		this.cardType = cardName;
	}
	public void setCardOwner(Player cardOwner){
		this.cardOwner = cardOwner;
	}
	
	public String getCountryName(){
		return countryName;
	}
	public String getCardName(){
		return cardType;
	}
	public Player getPlayer(){
		return cardOwner;
	}
	
	


private void getTerritoryCards(){
	for(int i=0;i<MAX_CARD_AMOUNT;i++){
		Territorydeck.add(Data.CARD_TYPE[i]);
	}
}


public String TerritorycardsLeft() {
	return Territorydeck.size()+1 + " Cards left";
}

public String dealTerritoryCards() {
	if(Territorydeck.isEmpty()== true){
		System.out.println("Deck is now empty");
	}

	else{   
		int j=0; 
		card = Territorydeck.get(j);
		Territorydeck.remove(j);
	}
	return card;	
}


private void FillTerritoryCards(){
	Player z =null;
	for(int i = 0;i<MAX_CARD_AMOUNT;i++){
		territoryCardsBeforeShuffle[i] = new TerritoryCard(Data.COUNTRY_NAMES[i],Data.CARD_TYPE[i],z);
	}
}


public static void shuffledTerrirtoryDeck() {
	
	
	

}
}
