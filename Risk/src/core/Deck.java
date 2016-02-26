/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Deck {

	public static int u = 0;
	public static int z =0;
	public static Country[] countriesBeforeShuffle = new Country[42];
	public static Country[] countriesAfterShuffle = new Country[42];
	protected int MAX_CARD_AMOUNT = 42;
	public static ArrayList<String> player1Countries = new ArrayList<String>();
	public static ArrayList<String> player2Countries = new ArrayList<String>();

	private String[] temp = new String[MAX_CARD_AMOUNT];
	private int cardUsed;
	ArrayList<String> deck = new ArrayList<>(42);
	String card = new String();

	public void getCards(){

		for(int i=0; i<MAX_CARD_AMOUNT;i++){
			deck.add(Data.COUNTRY_NAMES[i]);
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}	

	public String cardsLeft() {
		return deck.size()+1 + " Cards left";
	}

	public String deal() {
		if(deck.isEmpty()== true){
			System.out.println("Deck is now empty");
		}

		else{   
			int j=0; 
			card = deck.get(j);
			deck.remove(j);
		}
		return card;	
	}

	public static void shuffledDeck() {
		Deck deck = new Deck();
		PupulateCountries();

		deck.getCards();
		deck.shuffle();
		for(int i =0;i<42;i++){
			String result= deck.deal();
			//System.out.println(result);
			Reassign(result);
			//System.out.println(deck.cardsLeft());
		}
		u++;
	}

	private static void PupulateCountries() {
		for(int i=0;i<42;i++){
			Player p = null;
			countriesBeforeShuffle[i] = new Country(Data.COUNTRY_NAMES[i], p, 1, Data.getCountryCoord()[i][0], Data.getCountryCoord()[i][1], Data.ADJACENT[i], Data.CONTINENT_IDS[i], "Abb" );
		}
	}

	private static void Reassign(String result) {
		if(z==42){
			z=0;
		}
		for (int i = 0;i<42;i++){
			if(result.compareTo(countriesBeforeShuffle[i].getName()) == 0){
				switch(z){
				case 0:
				case 6:
				case 12:
				case 18:
				case 24:
				case 30:
				case 36:
				case 38:
				case 40:
					Player p = new Player (CommandInput.getPlayer1(), CommandInput.player1Colour);
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(p);
					if(u==1){
						player1Countries.add(result);
						}
					z++;
					break;
				case 1:
				case 7:
				case 13:
				case 19:
				case 25:
				case 31:
				case 37:
				case 39:
				case 41:
					p = new Player (CommandInput.getPlayer2(), CommandInput.player2Colour);
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(p);
					if(u==1){
					player2Countries.add(result);
					}
					z++;
					break;
				case 2:
				case 8:
				case 14:
				case 20:
				case 26:
				case 32:
					p = new Player("Neutral 1", Color.BLACK);
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(p);
					z++;
					break;
				case 3:
				case 9:
				case 15:
				case 21:
				case 27:
				case 33:
					p = new Player("Neutral 2", Color.GREEN);
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(p);
					z++;
					break;
				case 4:
				case 10:
				case 16:
				case 22:
				case 28:
				case 34:
					p = new Player("Neutral 3", Color.RED);
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(p);
					z++;
					break;
				case 5:
				case 11:
				case 17:
				case 23:
				case 29:
				case 35:
					p = new Player("Neutral 4", Color.YELLOW);
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(p);
					z++;
					break;
				}
			}
		}
	}
}