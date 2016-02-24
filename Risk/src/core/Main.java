/*
 * Alan Halvey -
 * Alan Holmes -
 * Greg Sloggett - 14522247
 * 
 */

package core;

import java.io.IOException;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException{
		Screen screen = new Screen();
		
		CommandInput.run();
		boolean flag = true;

		while(flag){
			//if the both users have put in their name
			if ((CommandInput.getPlayer2().length() >= 3) && (CommandInput.getPlayer2().length() <= 10)){
				Screen.mainFrame.repaint();
				Deck.shuffledDeck();
				flag = false;
			}	
		}
		
		while(true){
			String next = CommandInput.placeUnits(CommandInput.currentPlayer);
			Screen.mainFrame.repaint();
			CommandInput.placeUnits(next);
			Screen.mainFrame.repaint();
			CommandInput.placeUnits(CommandInput.currentPlayer);
		}
	}
	
}
