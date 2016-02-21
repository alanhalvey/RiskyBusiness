package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Deck {
	

	
	
protected int MAX_CARD_AMOUNT = 42;
	    
	    
	    private String[] temp = new String[MAX_CARD_AMOUNT];
	    private int cardUsed;
	    ArrayList<String> deck = new ArrayList<>(42);
	    String card = new String();
	    
	    public void getCards(){
	    	
	    	
	    	for(int i=1; i<MAX_CARD_AMOUNT;i++){
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
		
		test.getCards();
		test.shuffle();
		for(int i =0;i<42;i++){
		System.out.println(test.deal());
		System.out.println(test.cardsLeft());
		}

	}

}
