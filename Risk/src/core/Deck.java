package core;
public class Deck {
	

	
	
protected int MAX_CARD_AMOUNT = 42;
	    
	    
	    private String[] temp = new String[MAX_CARD_AMOUNT];
	    private int cardUsed;
	    

	    

	    public void shuffle() {
	    	
	    	for ( int i=0; i<Data.COUNTRY_NAMES.length-1;i++)
	    	{
	            int rand = (int)(Math.random()*(i+1));
	             temp[i] = Data.COUNTRY_NAMES[i];
	             Data.COUNTRY_NAMES[i] = Data.COUNTRY_NAMES[rand];
	             Data.COUNTRY_NAMES[rand] = temp[i];
	        }
	     
	    }	


public int cardsLeft() {
    return Data.COUNTRY_NAMES.length- cardUsed;
}


public String deal() {
    if (cardUsed == Data.COUNTRY_NAMES.length)
        throw new IllegalStateException("No cards are left in the deck.");
    cardUsed++;
    
    return Data.COUNTRY_NAMES[cardUsed - 1];
   
}



	public static void main(String[] args) {
		Deck test = new Deck();
		
		
		test.shuffle();
		for(int j=0;j<41;j++){
		System.out.println(test.deal());
		}
	}

}
