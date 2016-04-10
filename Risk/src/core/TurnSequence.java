package core;

import java.awt.Color;

public class TurnSequence {

	public static void ChangePlayers() {
		if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
			CommandInput.currentPlayer = CommandInput.player2;
		}
		else{
			CommandInput.currentPlayer = CommandInput.player1;
		}

	}

	public static void TurnSequencePreparations(){
		DisplayInfo();
		TerritoryCard.FillTerritoryCards();
		TerritoryCard.shuffleTerrirtoryDeck();
	}

	public static void DisplayInfo() {
		boolean flag = true;

		while(flag){
			//if the both users have put in their name
			if ((CommandInput.getPlayer2().length() >= 3) && (CommandInput.getPlayer2().length() <= 10)){

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

	public static int countCards(String currentPlayer) {
		int count = 0;
		for(int i=0;i<TerritoryCard.territoryCardsShuffled.size();i++){
			if(TerritoryCard.territoryCardsShuffled.get(i).getPlayer().playerName.compareTo(currentPlayer)==0){
				count++;
			}
		}
		return count;
	}

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

	public static void Combat() {
		CommandInput.appendStringTo("Would you like to combat? (Y/N)\n", Color.RED);
		String choice = CommandInput.getCommand();
		if(choice.compareToIgnoreCase("Y")==0){
			Combat.combat(CommandInput.currentPlayer);
			if(!(Data.Player1Wins || Data.Player2Wins)){
				CombatAgain();
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
			}
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