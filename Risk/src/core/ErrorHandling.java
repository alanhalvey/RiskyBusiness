/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;

import javax.swing.text.StyleConstants;

public class ErrorHandling {
	
	public static void Player1UsernameChecks(){ //performs error checks on user input
		
		CommandInput.player1 = CommandInput.getCommand();
		CommandInput.appendStringTo(CommandInput.player1 + "\n", CommandInput.player1Colour);
		
		if (CommandInput.player1.length() < 3){ //Why does this if statement not work more than once?
			CommandInput.appendStringTo("Your username is too short, please enter a new name: \n", Color.RED);
		}
		
		if (CommandInput.player1.length() > 10){
			CommandInput.appendStringTo("You username is too long, please enter a new name: \n", Color.RED);
		}
		
		CommandInput.commandInputWindow.setText("");		
		System.out.println("Player 1 = " + CommandInput.player1);
	}

	public static void Player2UsernameChecks() {
			
		CommandInput.player2 = CommandInput.getCommand();
		CommandInput.appendStringTo(CommandInput.player2 + "\n", CommandInput.player2Colour);	

		if(CommandInput.player1 == CommandInput.player2){
			CommandInput.appendStringTo("your username is already taken, please enter a new name: \n", Color.RED);
		}
		if(CommandInput.player2.length() < 3){
			CommandInput.appendStringTo("Your username is too short, please enter a new name: \n", Color.RED);
		}
		if (CommandInput.player2.length() > 10){
			CommandInput.appendStringTo("You username is too long, please enter a new name: \n", Color.RED);			
		}
		
		CommandInput.commandInputWindow.setText("");
		System.out.println("Player 2 = " + CommandInput.player2);
	
		StyleConstants.setForeground(CommandInput.style, Color.blue);
}

	public static void P1checkTerritories(String country){
		for(int i=0;i<42;i++){
			if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
					|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

				if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
					int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
					Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+3);
					CommandInput.appendStringTo((country+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
					Data.PLAYER_1_ARMIES-=3;
					CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Data.PLAYER_1_ARMIES + " units left.\n"), CommandInput.currentPlayerColour);
					CommandInput.currentPlayer = CommandInput.player2;
				}
				else{
					CommandInput.appendStringTo("You do not own this country\n", Color.RED);
					CommandInput.placeUnits(CommandInput.player1);
				}
			}
		}
	}	
	
public static void P2checkTerritories(String country){
	for(int i=0;i<42;i++){
		if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){

			if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){

				int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
				Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+3);
				CommandInput.appendStringTo((country+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
				Data.PLAYER_2_ARMIES-=3;
				CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Data.PLAYER_2_ARMIES + " units left.\n"), CommandInput.currentPlayerColour);
				CommandInput.currentPlayer = CommandInput.player1;
			}
			else{
				CommandInput.appendStringTo("You do not own this country\n", Color.RED);
				CommandInput.placeUnits(CommandInput.player2);
				}
			}
		}
	}	
}