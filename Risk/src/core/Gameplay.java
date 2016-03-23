package core;

import java.awt.Color;

public class Gameplay {
	static int numP1Territories;
	static int numP2Territories;
	static int numN1Territories;
	static int numN2Territories;
	static int numN3Territories;
	static int numN4Territories;

	static String countryToAttackWith = "";
	static String countryToAttack = "";
	static int numberOfUnitsToAttackWith = 0;


	public static boolean reinforcementsLeft(String player){
		boolean result = true;
		for(int k = 0;k<42;k++){
			if(player.compareToIgnoreCase(Deck.countriesAfterShuffle[k].getOccupyingPlayer().playerName)==0){
				if(Deck.countriesAfterShuffle[k].getOccupyingPlayer().numReinforcements == 0){
					result = false;
				}
			}

		}
		return result;
	}

	static void placeReinforcements(String currentPlayer){
		if(currentPlayer.compareTo(CommandInput.player1)==0){
			if(Gameplay.reinforcementsLeft(CommandInput.player1)==true){
				CommandInput.appendStringTo(currentPlayer + ", please type the country name to place Reinforcements in: \n", Color.BLACK);
				String country = CommandInput.getCommand();
				ErrorHandling.placeReinforcementsErrorChecksP1(country);
			}
			else{
				CommandInput.appendStringTo(currentPlayer + " has no reinforcements left, skipping turn.\n", Color.RED);
				CommandInput.currentPlayer = CommandInput.player2;
				placeReinforcements(CommandInput.currentPlayer);
			}
		}


		if(currentPlayer.compareTo(CommandInput.player2)==0){
			if(Gameplay.reinforcementsLeft(CommandInput.player2)==true){
				CommandInput.appendStringTo(currentPlayer + ", please type the country name to place Reinforcements in: \n", Color.BLACK);
				String country = CommandInput.getCommand();
				ErrorHandling.placeReinforcementsErrorChecksP2(country);
			}
			else{
				CommandInput.appendStringTo(currentPlayer + " has no reinforcements left, skipping turn.\n", Color.RED);
				CommandInput.currentPlayer = CommandInput.player1;
				placeReinforcements(CommandInput.currentPlayer);
			}
		}	
	}

	static void calculateReinforcements() {
		for(int i=0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase(CommandInput.player1)==0){
				numP1Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numP1Territories;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase(CommandInput.player2)==0){
				numP2Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numP2Territories;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 1")==0){
				numN1Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN1Territories;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 2")==0){
				numN2Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN2Territories;

			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 3")==0){
				numN3Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN3Territories;

			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareToIgnoreCase("Neutral 4")==0){
				numN4Territories++;
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories = numN4Territories;

			}


		}
		for(int i = 0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories<=9){
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements = 3;
			}
			else{
				Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements = Deck.countriesAfterShuffle[i].getOccupyingPlayer().numTerritories / 3;
			}
		}

	}





	public static void Fortify(Country takeArmies, Country putArmies, int amountMoved){



		if(takeArmies.getPlayerArmies()<=1){
			System.out.println("You do not have enough armies to do this foritfy");
			Fortify(takeArmies,  putArmies, amountMoved);
		}
		else{
			System.out.println("1");
			for(int k=0; k<= Data.COUNTRY_NAMES.length-1;k++){
				if(takeArmies.getName().compareToIgnoreCase(Data.COUNTRY_NAMES[k])  == 0 || putArmies.getName().compareToIgnoreCase(Data.COUNTRY_NAMES[k]) == 0 ){
					System.out.println("2");



					for(int j =0; j<takeArmies.getAdjacent().length-1; j++){
						System.out.println("3");

						if(takeArmies.getAdjacent()[j]== k && takeArmies.getOccupyingPlayer().playerName.compareTo(putArmies.getOccupyingPlayer().playerName)==0){
							System.out.println("4");

							takeArmies.setPlayerArmies(takeArmies.getPlayerArmies()-amountMoved);
							putArmies.setPlayerArmies(putArmies.getPlayerArmies()+amountMoved);
							CommandInput.appendStringTo(takeArmies.getName() + " now has " + takeArmies.getPlayerArmies() + " units.", Color.RED);
							CommandInput.appendStringTo(putArmies.getName() + " now has " + putArmies.getPlayerArmies() + " units.", Color.RED);
						}


					}
				}
				else{
					System.out.println("this is not a counrty");

				}
			}

		}
	}
	public static void skip(){

		if (CommandInput.currentPlayer.compareToIgnoreCase(CommandInput.player1) == 0){
			CommandInput.currentPlayer= CommandInput.player2;
		}
		else {
			CommandInput.currentPlayer = CommandInput.player1;
		}

	}


	public static String setFromAbbreviation(String country) {
		for(int i = 0;i<42;i++){
			if(country.compareTo(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				return Deck.countriesAfterShuffle[i].getName();
			}
			if(country.compareTo(Deck.countriesAfterShuffle[i].getName())==0){
				return Deck.countriesAfterShuffle[i].getName();
			}

		}
		return null;
	}

	public static Country setCountry(String country) {
		for(int i =0;i<42;i++){
			if (country.compareTo(Deck.countriesAfterShuffle[i].getName())==0){
				return Deck.countriesAfterShuffle[i];
			}
		}
		return null;
	}

	public static void combat(String currentPlayer){

		CommandInput.updatePlayerColour(currentPlayer);
		PickAttackingCountry(currentPlayer);

		CommandInput.updatePlayerColour(currentPlayer);
		PickCountryToAttack(currentPlayer);

		CommandInput.updatePlayerColour(currentPlayer);
		BattleBetweenCountries(currentPlayer);
	}


	static void PickAttackingCountry(String currentPlayer){

		CommandInput.appendStringTo(currentPlayer + ", please enter which of your countries you wish to attack with: \n", Color.BLACK);
		countryToAttackWith = CommandInput.getCommand();

		String didPickOccur = "NO";

		for(int i=0;i<42;i++){
			if (countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

				if(currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
					CommandInput.appendStringTo("You have chose " + countryToAttackWith + " to attack with. \n", CommandInput.currentPlayerColour);
					didPickOccur = "YES";
				}

				else{
					CommandInput.appendStringTo("You do not own this country\n", Color.RED);
				}
			}
		}
		if(didPickOccur == "NO"){
			PickAttackingCountry(currentPlayer);
		}
	}

	static void PickCountryToAttack(String currentPlayer){
		CommandInput.appendStringTo(currentPlayer + ", please enter which country you wish to attack.\n", Color.BLACK);
		countryToAttack = CommandInput.getCommand();

		String didPickOccur = "NO";

		for(int i=0;i<42;i++){
			if (countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName()) == 0 || countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

				if(currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)!=0){
					if(true == true){
						CommandInput.appendStringTo("You have chosen to attack " + countryToAttack + "\n", CommandInput.currentPlayerColour);
						didPickOccur = "YES";
					}
					else{
						CommandInput.appendStringTo("You cannot attack this country, please select an adjacent country that you do not own. \n", Color.RED);
					}	
				}
				else{
					CommandInput.appendStringTo("You cannot attack this country, please select an adjacent country that you do not own. \n", Color.RED);
				}
			}
		}
		if(didPickOccur == "NO"){
			PickCountryToAttack(currentPlayer);
		}
	}

	static void BattleBetweenCountries(String currentPlayer){

		CommandInput.appendStringTo(currentPlayer + ", please enter how many units you wish to attack with.\n", Color.BLACK);

		CheckIntegerErrorInput(currentPlayer);

		//if(currentPlayer.  numberOfUnitsToAttackWith)

		CommandInput.appendStringTo(currentPlayer + " uses " + countryToAttackWith + " to attack " + countryToAttack + " and uses " + numberOfUnitsToAttackWith + " armies to battle. \n", Color.BLACK);

	}

	static void CheckIntegerErrorInput(String currentPlayer){

		numberOfUnitsToAttackWith = 0;

		while(numberOfUnitsToAttackWith == 0 ){
			try{
				numberOfUnitsToAttackWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n Please enter how many units you wish to attack with.\n", Color.BLACK);
			}
		}

	}


}

