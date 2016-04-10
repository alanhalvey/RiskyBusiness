/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */

package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class TerritoryCard {
	private String countryName;
	private String cardType;
	private Player cardOwner;
	private static Boolean areCardsShuffled = false;
	
	public static ArrayList<TerritoryCard> territoryCardsShuffled = new ArrayList<TerritoryCard>();
	
	
	String card = new String();
	static int MAX_CARD_AMOUNT = 42;
	
	
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
	
	




public static void  FillTerritoryCards(){
	Player z = new Player ("temp", Color.WHITE, 0, 0, false);
	for(int i = 0;i<MAX_CARD_AMOUNT;i++){
		territoryCardsShuffled.add(new TerritoryCard(Data.COUNTRY_NAMES[i],Data.CARD_TYPE[i],z));
		System.out.println(territoryCardsShuffled.get(i).getCardName());
		}
	
}





	

public static void shuffleTerrirtoryDeck(){
	Collections.shuffle(territoryCardsShuffled);	
	setAreCardsShuffled(true);
}
public static Boolean getAreCardsShuffled() {
	return areCardsShuffled;
}
public static void setAreCardsShuffled(Boolean areCardsShuffled) {
	TerritoryCard.areCardsShuffled = areCardsShuffled;
}
}