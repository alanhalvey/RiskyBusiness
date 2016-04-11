/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */

package core;

import java.awt.Color;

public class TurnSequence {
	//Method used to switch players at the end of a turn.
	public static void ChangePlayers() {
		if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
			CommandInput.currentPlayer = CommandInput.player2;
		}
		else{
			CommandInput.currentPlayer = CommandInput.player1;
		}

	}
	
	//Method to display information to the players and prepare territory cards for the game,.
	public static void TurnSequencePreparations(){
		DisplayInfo();
		TerritoryCard.FillTerritoryCards();
		TerritoryCard.shuffleTerrirtoryDeck();
	}
	
	//Displays who will go first in the game
	public static void DisplayInfo() {
		boolean flag = true;

		while(flag){
			//if the both users have put in their name
			if ((CommandInput.getPlayer2().length() >= 3) && (CommandInput.getPlayer2().length() <= 10)){

				Deck.shuffledDeck();
				flag = false;
			}	
		}

		CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		while(CommandInput.checkIfDieEqual == "YES"){
			CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		}

	}
	//Allows players to place up to 3 units at a time in a country
	public static void PlaceUnits() {
		while(Data.unitsLeft==true){
			if(Data.PLAYER_1_ARMIES != 0 || Data.PLAYER_2_ARMIES != 0){
				CommandInput.placeUnits(CommandInput.currentPlayer);

			}
			else{
				CommandInput.appendStringTo("No units left to place\n", Color.RED);
				Data.unitsLeft=false;
			}

		}

	}

	//The turn sequence of the game, with checks to see if a player has won.
	public static void TurnSequence() {
		Gameplay.calculateReinforcements();
		System.out.println(Deck.countriesAfterShuffle[0].getName() + Deck.countriesAfterShuffle[0].getOccupyingPlayer().playerName + " " +Deck.countriesAfterShuffle[0].getOccupyingPlayer().numTerritories);
		while(Gameplay.reinforcementsLeft(CommandInput.currentPlayer)==true){
			PlaceReinforcements();
		}

		Combat();

		if(!(Data.Player1Wins || Data.Player2Wins)){
			
			int check = 0;

			if(Deck.calculateTerritories(CommandInput.currentPlayer) == Deck.calculateArmies(CommandInput.currentPlayer)){
				CommandInput.appendStringTo("You can not fortify as you do not have more than 1 army in any country.\n", Color.RED);
				check=1;
			}
			else{
				Fortify();
			}
			InsigniaExchange();
			Data.alreadyExchanged = false;
		}
	}

	//Allows players to exchange territory cards and get reinforcements in return.
	private static void InsigniaExchange() {
		int counts[] = Gameplay.getCounts();
		int artillary = counts[0];
		int cavalry = counts[1];
		int infantry = counts[2];

		CommandInput.appendStringTo("artillary: "+artillary+ ", cavalry: "+cavalry+", infantry: "+ infantry+"\n", Color.RED);



		int currentPlayerTerritoryCards = countCards(CommandInput.currentPlayer);
		CommandInput.appendStringTo(CommandInput.currentPlayer +",  you have " + currentPlayerTerritoryCards + " territory cards.\n", Color.black);
		if(artillary>= 3 || cavalry>=3 || infantry>=3 || (artillary>=1 && cavalry>=1 && infantry>=1)){
			if(currentPlayerTerritoryCards<3){
				CommandInput.appendStringTo("You don't have enough territory cards to exchange.\n", Color.RED);
			}

			if(currentPlayerTerritoryCards >= 3 && currentPlayerTerritoryCards<5){
				CommandInput.appendStringTo("Would you like to exhange some territory cards? (Y/N)\n", Color.RED);
				String choice = CommandInput.getCommand();
				if(choice.compareToIgnoreCase("Y")==0){
					currentPlayerTerritoryCards =Gameplay.Exchange(CommandInput.currentPlayer, currentPlayerTerritoryCards);
					CommandInput.appendStringTo("You now have " + currentPlayerTerritoryCards + " territory cards left\n", Color.RED);
					Screen.mainFrame.repaint();
				}
				else if(choice.compareToIgnoreCase("N")==0){
					CommandInput.appendStringTo("You have skipped exchange of territory cards.\n", Color.RED);
				}
				else{
					CommandInput.appendStringTo("Invalid input. Try again\n", Color.RED);
					Data.alreadyExchanged = true;
					InsigniaExchange();
				}
			}
			else if (currentPlayerTerritoryCards >= 5){
				CommandInput.appendStringTo("You have greater than 5 territory cards, you must exchange.\n", Color.red);
				currentPlayerTerritoryCards =Gameplay.Exchange(CommandInput.player1, currentPlayerTerritoryCards);
				CommandInput.appendStringTo("You now have " + currentPlayerTerritoryCards + " territory cards left\n", Color.RED);
			}
		}
		else{
			CommandInput.appendStringTo("You don't have enough cards with the required insignias to exchange.\n", Color.RED);
		}



	}

	//Counts the number of territory cards a player has.
	public static int countCards(String currentPlayer) {
		int count = 0;
		for(int i=0;i<TerritoryCard.territoryCardsShuffled.size();i++){
			if(TerritoryCard.territoryCardsShuffled.get(i).getPlayer().playerName.compareTo(currentPlayer)==0){
				count++;
			}
		}
		return count;
	}

	//Allows players to place reinforcements in a country that they own.
	public static void PlaceReinforcements() {
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

	}

	//Allows players to attack neighbouring countries owned by other players
	public static void Combat() {
		CommandInput.appendStringTo("Would you like to combat? (Y/N)\n", Color.RED);
		String choice = CommandInput.getCommand();
		if(choice.compareToIgnoreCase("Y")==0){
			Combat.combat(CommandInput.currentPlayer);
			Screen.mainFrame.repaint();
			if(!(Data.Player1Wins || Data.Player2Wins)){
				CombatAgain();
				Screen.mainFrame.repaint();
			}
		}
		else if (choice.compareToIgnoreCase("N")==0){
			CommandInput.appendStringTo("You have skipped combat\n", Color.RED);
		}
		else{
			CommandInput.appendStringTo("Invalid input. Try again.\n", Color.RED);
			Combat();
		}
	}

	//allows players to combat again after a combat.
	public static void CombatAgain(){

		int check = 0;

		if(Deck.calculateTerritories(CommandInput.currentPlayer) == Deck.calculateArmies(CommandInput.currentPlayer)){
			CommandInput.appendStringTo("You can not combat as you do not have more than 1 army in any country.\n", Color.RED);
			check=1;
		}

		if(check == 0){
			int count = 0;
			CommandInput.appendStringTo("Would you like to combat again? (Y/N)\n", Color.RED);
			String choice = CommandInput.getCommand();
			if(choice.compareToIgnoreCase("Y")==0){
				Combat.combat(CommandInput.currentPlayer);
				count++;
			}
			else if (choice.compareToIgnoreCase("N")==0){
				CommandInput.appendStringTo("You have skipped combat\n", Color.RED);
			}
			else{
				CommandInput.appendStringTo("Invalid input. Try again.\n", Color.RED);
				count++;
			}
			if(count>0 && !(Data.Player1Wins || Data.Player2Wins)){
				CombatAgain();
				Screen.mainFrame.repaint();
			}
		}
	}

	//Allows countries to fortify armies between two neighbouring countries that they own.
	public static void Fortify() {

		String takeArmies=" ";
		String putArmies=" ";

		CommandInput.appendStringTo("Would you like to Fortify? (Y/N)\n", Color.RED);
		String choice = CommandInput.getCommand();

		if(choice.compareToIgnoreCase("Y")==0){
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies from\n", Color.RED);
			do{
				takeArmies = CommandInput.getCommand();
				takeArmies = Gameplay.setFromAbbreviation(takeArmies);
				if(takeArmies == null){
					CommandInput.appendStringTo(CommandInput.currentPlayer+" This is not a country,  Enter country to fortify armies from\n", Color.RED);
				}
				
			}while(takeArmies == null);

			CommandInput.appendStringTo(takeArmies+"\n", CommandInput.currentPlayerColour);
			Country takingFrom = Gameplay.setCountry(takeArmies);
			if(takingFrom == null){
				CommandInput.appendStringTo("This is not a country", Color.black);
			}




			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies to\n", Color.RED);
			do{
				putArmies = CommandInput.getCommand();
				putArmies = Gameplay.setFromAbbreviation(putArmies);
				if(putArmies == null){
					CommandInput.appendStringTo(CommandInput.currentPlayer+" This is not a country,  Enter country to fortify armies to\n", Color.RED);
				}
			}while(putArmies == null);
			CommandInput.appendStringTo(putArmies+"\n", CommandInput.currentPlayerColour);
			Country puttingTo = Gameplay.setCountry(putArmies);

			if(puttingTo == null){
				CommandInput.appendStringTo("This is not a country", Color.black);
			}
		





			String numberToMove = "temp";
			int numToMove = 0;
			boolean gotNumber = false;
			while (numberToMove!=null && gotNumber==false ){
				try {
					CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter amount of armies to fortify\n", CommandInput.currentPlayerColour);
					numberToMove = CommandInput.getCommand();
					numToMove= Integer.parseInt(numberToMove);
					gotNumber=true;
				} catch (NumberFormatException e) {
					CommandInput.appendStringTo("This is not a number. Try again.\n", Color.RED);
				}
			}




			CommandInput.appendStringTo( numToMove+ " armies to fortify\n", Color.RED);
			
			Gameplay.Fortify(takingFrom, puttingTo, numToMove );
			Screen.mainFrame.repaint();
			System.out.println("test");

		}



		else if (choice.compareToIgnoreCase("N")==0){
			CommandInput.appendStringTo("You have skipped Fortify.\n", Color.RED);
		}
		else{
			CommandInput.appendStringTo("Invalid input. Try again.", Color.red);
		}
	}

	//Displays the winner of the game in the case that the other player has no occupied countries left.
	public static void DisplayWinner(String player) {
		Color playerColor;
		if(player.compareTo(CommandInput.getPlayer1())==0){
			playerColor = Color.BLUE;
		}
		else{
			playerColor = Color.MAGENTA;
		}
		CommandInput.appendStringTo("Congratulations, "+player+ ", you have won the game!!!\n", playerColor);
	}

}