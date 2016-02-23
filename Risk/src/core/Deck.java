package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Deck {


	public static int z =0;
	public static Country[] countriesBeforeShuffle = new Country[42];
	public static Country[] countriesAfterShuffle = new Country[42];
	protected int MAX_CARD_AMOUNT = 42;


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
		/*
	    	for ( int i=0; i<deck.size();i++)
	    	{
	            int rand = (int)(Math.random()*(i+1));
	             temp[i] = deck.remove(i);
	             deck. = Data.COUNTRY_NAMES[rand];
	             Data.COUNTRY_NAMES[rand] = temp[i];
	        }*/

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



	public static void main(String[] args) {
		Deck test = new Deck();
		PupulateCountries();


		test.getCards();
		test.shuffle();
		for(int i =0;i<42;i++){
			String result= test.deal();
			System.out.println(result);
			Reassign(result);
			System.out.println(test.cardsLeft());
		}
		
		

	}



	private static void PupulateCountries() {
		for(int i=0;i<42;i++){
			countriesBeforeShuffle[i] = new Country(Data.COUNTRY_NAMES[i], "Player", 1, Data.getCountryCoord()[i][0], Data.getCountryCoord()[i][1], Data.ADJACENT[i], Data.CONTINENT_IDS[i] );
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
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(CommandInput.getPlayer1());
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
					
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer(CommandInput.getPlayer2());
					z++;
					break;
				case 2:
				case 8:
				case 14:
				case 20:
				case 26:
				case 32:
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer("Neutral 1");
					z++;
					break;
				case 3:
				case 9:
				case 15:
				case 21:
				case 27:
				case 33:
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer("Neutral 2");
					z++;
					break;
				case 4:
				case 10:
				case 16:
				case 22:
				case 28:
				case 34:
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer("Neutral 3");
					z++;
					break;
				case 5:
				case 11:
				case 17:
				case 23:
				case 29:
				case 35:
					countriesAfterShuffle[z] = new Country( countriesBeforeShuffle[i]);
					countriesAfterShuffle[z].setOccupyingPlayer("Neutral 4");
					z++;
					break;
				}
			}

		}
	}
}
