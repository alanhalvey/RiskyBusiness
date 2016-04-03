package core;

import java.util.ArrayList;
import java.util.Collections;

public class TerritoryCard {
	private String countryName;
	private String cardType;
	private Player cardOwner;
	private static Boolean areCardsShuffled = false;
	
	public static ArrayList<TerritoryCard> territoryCardsShuffled = new ArrayList<TerritoryCard>();
	
	
	String card = new String();
	int MAX_CARD_AMOUNT = 42;
	
	
	public TerritoryCard(String countryName, String cardName, Player cardOwner){
		this.countryName=countryName;
		this.cardType=cardName;
		this.cardOwner=cardOwner;
	}
	public TerritoryCard TerritoryCard(Player cardOwner) {
		this.cardOwner=cardOwner;
		return null;
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
	
	




private void  FillTerritoryCards(){
	Player z =null;
	for(int i = 0;i<MAX_CARD_AMOUNT;i++){
		territoryCardsShuffled.add(new TerritoryCard(Data.COUNTRY_NAMES[i],Data.CARD_TYPE[i],z));
		}
	
}




public void dealCard(Player currentOwner){
	int k=0;
		
	if(areCardsShuffled == false){
		FillTerritoryCards();
		shuffleTerrirtoryDeck();
	
	
	if(k<=MAX_CARD_AMOUNT-1){
		
		territoryCardsShuffled.get(k);
		territoryCardsShuffled.add(k, TerritoryCard(currentOwner));
		k++;
	}
	else{
		System.out.println("No cards left.\n");
	}
	}
	else{
		if(k<=MAX_CARD_AMOUNT-1){
			
			territoryCardsShuffled.get(k);
			territoryCardsShuffled.add(k, TerritoryCard(currentOwner));
			k++;
		}
		else{
			System.out.println("No cards left.\n");
		}	
	}
}	
	

private static void shuffleTerrirtoryDeck(){
	
	Collections.shuffle(territoryCardsShuffled);	
	areCardsShuffled=true;
 




}
}