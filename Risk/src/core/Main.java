/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */
package core;
import java.awt.Color;
import java.io.IOException;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException{
		
		Screen screen = new Screen();
		CommandInput.run();
		DisplayInfo();
		//Gameplay.combat(CommandInput.currentPlayer);
		Screen.mainFrame.repaint();
		//PlaceUnits();
		Gameplay.calculateReinforcements();
		
		while(!(Data.Player1Wins || Data.Player2Wins)){
			TurnSequence();
			ChangePlayers();
		}
	}

	//Changes the player name in the currentPlayer variable, which is used throughout game.
	private static void ChangePlayers() {
		
		if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
			CommandInput.currentPlayer = CommandInput.player2;
		}
		else{
			CommandInput.currentPlayer = CommandInput.player1;
		}
	}

	//Displays the usernames and 1st player to start game. 
	private static void DisplayInfo() {
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
	}

/*	private static void PlaceUnits() {
		while(Data.unitsLeft==true){
			if(Data.PLAYER_1_ARMIES != 0 || Data.PLAYER_2_ARMIES != 0){
				CommandInput.placeUnits(CommandInput.currentPlayer);
				Screen.mainFrame.repaint();
			}
			else{
				CommandInput.appendStringTo("No units left to place\n", Color.RED);
				Data.unitsLeft=false;
			}
		}
	}*/

	//Sequence of functions for each player turn. 
	private static void TurnSequence() {
		System.out.println(Deck.countriesAfterShuffle[0].getName() + Deck.countriesAfterShuffle[0].getOccupyingPlayer().playerName + " " +Deck.countriesAfterShuffle[0].getOccupyingPlayer().numTerritories);
		PlaceReinforcements();
		Combat();
		Fortify();
	}

	//If current player has reinforcements left and it is his/her go, then they place their reinforcements.
	private static void PlaceReinforcements() {
		if(Gameplay.reinforcementsLeft(CommandInput.player1)==true && CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
			Gameplay.placeReinforcements(CommandInput.currentPlayer);
		}
		else if(Gameplay.reinforcementsLeft(CommandInput.player2)==true && CommandInput.currentPlayer.compareTo(CommandInput.player2)==0){
			Gameplay.placeReinforcements(CommandInput.currentPlayer);
		}
		else if(Gameplay.reinforcementsLeft(CommandInput.player1)!=true && CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
			CommandInput.appendStringTo(CommandInput.currentPlayer+", you have no reinforcements left. Skipping placing reinforcements.\n", CommandInput.currentPlayerColour);
		}
		else if(Gameplay.reinforcementsLeft(CommandInput.player2)!=true && CommandInput.currentPlayer.compareTo(CommandInput.player2)==0){
			CommandInput.appendStringTo(CommandInput.currentPlayer+", you have no reinforcements left. Skipping placing reinforcements.\n", CommandInput.currentPlayerColour);
		}
		Screen.mainFrame.repaint();
	}

	//Combat function allows attacking player to attack adjacent countries, based on a dice roll. #Risky
	private static void Combat() {
		CommandInput.appendStringTo("Would you like to combat? (Y/N)\n", Color.RED);
		//If user enters no, he skips combat, moving onto Foritfy.
		String choice = CommandInput.getCommand();
		
		if(choice.compareToIgnoreCase("Y")==0){
			Gameplay.combat(CommandInput.currentPlayer);
		}
		else if (choice.compareToIgnoreCase("N")==0){
			CommandInput.appendStringTo("You have skipped combat\n", Color.RED);
		}
		else{
			CommandInput.appendStringTo("Invalid input. Try again.\n", Color.RED);
			Combat();
		}
	}
	
	//Fortify function allows a player with adjacent countries to move armies between their territories. Only once per turn.
	private static void Fortify() {
		CommandInput.appendStringTo("Would you like to Fortify? (Y/N)\n", Color.RED);
		//If user enters no, he skips fortify, moving onto the next players go.
		String choice = CommandInput.getCommand();

		if(choice.compareToIgnoreCase("Y")==0){
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies from\n", Color.RED);
			String takeArmies = CommandInput.getCommand();
			CommandInput.appendStringTo(Gameplay.setFromAbbreviation(takeArmies)+"\n", CommandInput.currentPlayerColour);
			
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies to\n", Color.RED);
			String putArmies = CommandInput.getCommand();
			CommandInput.appendStringTo(Gameplay.setFromAbbreviation(putArmies)+"\n", CommandInput.currentPlayerColour);
			
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter amount of armies to fortify\n", Color.RED);
			int toMove = Integer.parseInt(CommandInput.getCommand());
			CommandInput.appendStringTo( toMove+ " armies to fortify\n", Color.RED);
			
			takeArmies = Gameplay.setFromAbbreviation(takeArmies);
			putArmies = Gameplay.setFromAbbreviation(putArmies);

			if(putArmies!=null && takeArmies!=null){
				Country takingFrom = Gameplay.setCountry(takeArmies);
				Country puttingTo = Gameplay.setCountry(putArmies);
				Gameplay.Fortify(takingFrom, puttingTo, toMove );
				Screen.mainFrame.repaint();
			}

			else{
				CommandInput.appendStringTo("You have entered invalid details, please try again.\n\n", Color.RED);
			}
		}
		else if (choice.compareToIgnoreCase("N")==0){
			CommandInput.appendStringTo("You have skipped Fortify.\n", Color.RED);
		}
		else{
			CommandInput.appendStringTo("Invalid input. Try again.", Color.red);
		}
	}
}
