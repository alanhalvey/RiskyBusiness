/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;
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

		CommandInput.appendStringTo(CommandInput.player1 + " owns the following countries: \n" + Deck.player1Countries + "\n", CommandInput.player1Colour);
		CommandInput.appendStringTo(CommandInput.player2 + " owns the following countries: \n" + Deck.player2Countries + "\n", CommandInput.player2Colour);
		
		CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		while(CommandInput.checkIfDieEqual == "YES"){
			CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		}
		
		
		while(Data.unitsLeft==true){
			if(flag==false){
				CommandInput.placeNeutrals();
				Screen.mainFrame.repaint();
				flag=true;
			}
			if(Data.PLAYER_1_ARMIES !=0 || Data.PLAYER_2_ARMIES !=0){
				CommandInput.placeUnits(CommandInput.currentPlayer);
				Screen.mainFrame.repaint();
			}
			else{
				CommandInput.appendStringTo("No units left to place", Color.RED);
				Data.unitsLeft=false;
			}
		}
	}
}