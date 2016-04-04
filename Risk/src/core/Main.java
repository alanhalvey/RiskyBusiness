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
		DisplayInfo();

		/*
		for(int i=0; i<7; i++){
			Gameplay.combat(CommandInput.currentPlayer);
			ChangePlayers();
		}
		*/


		//Gameplay.combat(CommandInput.currentPlayer);
		TerritoryCard.FillTerritoryCards();
		Gameplay.calculateReinforcements();
		//PlaceUnits();
		TerritoryCard.shuffleTerrirtoryDeck();
		while(!(Data.Player1Wins || Data.Player2Wins)){
			Gameplay.calculateReinforcements();
			TurnSequence();
			ChangePlayers();
		}

	}

	private static void ChangePlayers() {
		if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
			CommandInput.currentPlayer = CommandInput.player2;
		}
		else{
			CommandInput.currentPlayer = CommandInput.player1;
		}

	}

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

		//CommandInput.appendStringTo(CommandInput.player1 + " owns the following countries: \n" + Deck.player1Countries + "\n", Color.BLACK);
		//CommandInput.appendStringTo(CommandInput.player2 + " owns the following countries: \n" + Deck.player2Countries + "\n", Color.BLACK);

		CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		while(CommandInput.checkIfDieEqual == "YES"){
			CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		}

	}

	private static void PlaceUnits() {
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

	}

	private static void TurnSequence() {
		System.out.println(Deck.countriesAfterShuffle[0].getName() + Deck.countriesAfterShuffle[0].getOccupyingPlayer().playerName + " " +Deck.countriesAfterShuffle[0].getOccupyingPlayer().numTerritories);

		PlaceReinforcements();
		//Combat();
		//Fortify();
		InsigniaExchange();
	}

	private static void InsigniaExchange() {
		if(Data.exchangeIndex<Data.NUM_COUNTRIES){
			if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
				TerritoryCard.territoryCardsShuffled.get(Data.exchangeIndex).setCardOwner(Deck.player1);
			}
			if(CommandInput.currentPlayer.compareTo(CommandInput.player2)==0){
				TerritoryCard.territoryCardsShuffled.get(Data.exchangeIndex).setCardOwner(Deck.player2);
			}
			Data.exchangeIndex++;
		}


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
				if(choice.compareTo("Y")==0){
					currentPlayerTerritoryCards =Gameplay.Exchange(CommandInput.currentPlayer, currentPlayerTerritoryCards);
					CommandInput.appendStringTo("You now have " + currentPlayerTerritoryCards + " territory cards left\n", Color.RED);
				}
				else if(choice.compareTo("N")==0){
					CommandInput.appendStringTo("You have skipped exchange of territory cards.\n", Color.RED);
				}
				else{
					CommandInput.appendStringTo("Invalid input. Try again\n", Color.RED);
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

	private static int countCards(String currentPlayer) {
		int count = 0;
		for(int i=0;i<TerritoryCard.territoryCardsShuffled.size();i++){
			if(TerritoryCard.territoryCardsShuffled.get(i).getPlayer().playerName.compareTo(currentPlayer)==0){
				count++;
			}
		}
		return count;
	}

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

	private static void Combat() {
		CommandInput.appendStringTo("Would you like to combat? (Y/N)\n", Color.RED);
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
	public static void Fortify() {
		CommandInput.appendStringTo("Would you like to Fortify? (Y/N)\n", Color.RED);
		String choice = CommandInput.getCommand();

		if(choice.compareToIgnoreCase("Y")==0){
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies from\n", Color.RED);
			String takeArmies = CommandInput.getCommand();
			CommandInput.appendStringTo(Gameplay.setFromAbbreviation(takeArmies)+"\n", CommandInput.currentPlayerColour);
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies to\n", Color.RED);
			String putArmies = CommandInput.getCommand();
			CommandInput.appendStringTo(Gameplay.setFromAbbreviation(putArmies)+"\n", CommandInput.currentPlayerColour);


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
			takeArmies = Gameplay.setFromAbbreviation(takeArmies);
			putArmies = Gameplay.setFromAbbreviation(putArmies);

			if(putArmies!=null && takeArmies!=null){
				System.out.println("test");
				Country takingFrom = Gameplay.setCountry(takeArmies);
				Country puttingTo = Gameplay.setCountry(putArmies);
				System.out.println(takingFrom.getName() + puttingTo.getName());
				Gameplay.Fortify(takingFrom, puttingTo, numToMove );
				System.out.println("test");
				Screen.mainFrame.repaint();
			}

			else{
				CommandInput.appendStringTo("You have entered invalid details, please try again.\n\n", Color.RED);
				Fortify();
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
