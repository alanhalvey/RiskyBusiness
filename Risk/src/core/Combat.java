package core;

import java.awt.Color;

public class Combat {
	static int numP1Territories;
	static int numP2Territories;
	static int numN1Territories;
	static int numN2Territories;
	static int numN3Territories;
	static int numN4Territories;


	static int numberOfArmiesInAttackingCountry = 0;

	static String countryToAttackWith = "";
	static String countryToAttack = "";

	static int numberOfUnitsToAttackWith = 0;
	static int numberOfUnitsToDefendWith = 0;
	static int numberOfUnitsToMoveInWith = 0;

	static String attackingPlayer = "";
	static String defendingPlayer = "";
	static String defendingPlayerString = "";

	static Color attackingPlayerColour = Color.ORANGE;
	static Color defendingPlayerColour = Color.ORANGE;

	static Player attackingPlayerP = new Player (attackingPlayer, attackingPlayerColour, getAttackingPlayerTerritoryNumber(attackingPlayer), 0, true);

	//Central point for combat function, calls 8 different sub functions.
	public static void combat(String currentPlayer){
		SortingPlayersForCombat(currentPlayer);

		PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);

		PickCountryToAttack(currentPlayer, attackingPlayer, defendingPlayer);

		AttackingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);

		DefendingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);

		InternalCombatLogic(currentPlayer, attackingPlayer, defendingPlayer, numberOfUnitsToAttackWith, numberOfUnitsToDefendWith);
		
		CheckPlayerEliminated();
	}

	public static void SortingPlayersForCombat(String currentPlayer){

		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
		}
		if(currentPlayer == CommandInput.player2){
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;
		}
		if(currentPlayer == "Neutral 1"){
			attackingPlayer = "Neutral 1";
			attackingPlayerColour = Color.BLACK;
		}
		if(currentPlayer == "Neutral 2"){
			attackingPlayer = "Neutral 2";
			attackingPlayerColour = Color.GREEN;
		}
		if(currentPlayer == "Neutral 3"){
			attackingPlayer = "Neutral 3";
			attackingPlayerColour = Color.RED;
		}
		if(currentPlayer == "Neutral 4"){
			attackingPlayer = "Neutral 4";
			attackingPlayerColour = Color.YELLOW;
		}
	}


	//Function gets player to pick which country he wishes to attack with
	static void PickAttackingCountry(String currentPlayer, String attackingPlayer, String defendingPlayer){
		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}
		int count = 0;
		String didPickOccur = "NO";

		CommandInput.appendStringTo(attackingPlayer + " (ATTACK), Enter country to attack with: \n", Color.BLACK);
		countryToAttackWith = CommandInput.getCommand();

		boolean validCountryCheck = CommandInput.countryCheck(countryToAttackWith);
		if(!validCountryCheck){
			CommandInput.appendStringTo("That's not a country. Try again.\n", Color.RED);
			count++;
		}
		else{
			countryToAttackWith = Gameplay.setFromAbbreviation(countryToAttackWith);

			int NumArmies = 0;
			NumArmies = Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies();

			for(int i=0;i<42;i++){
				if (countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(attackingPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						if(NumArmies <= 1){
							CommandInput.appendStringTo("You cannot attack using a country that contains less than 2 armies, please select another country to attack with: ", Color.RED);
							count++;
						}
						else{
							CommandInput.appendStringTo("You have chose " + countryToAttackWith + " to attack with. \n", attackingPlayerColour);
							didPickOccur = "YES";
						}
					}

					else{
						CommandInput.appendStringTo("You do not own this country\n", Color.RED);
					}
				}
			}

		}

		if(count>0 || didPickOccur == "NO"){
			PickAttackingCountry(currentPlayer, attackingPlayer, defendingPlayer);
		}
	}

	//Allows attacking player to pick which country, adjacent to his attacking country, that he wishes to attack
	static void PickCountryToAttack(String currentPlayer, String attackingPlayer, String defendingPlayer){
		CommandInput.appendStringTo(attackingPlayer + " (ATTACK), Enter country to attack:\n", Color.BLACK);
		countryToAttack = CommandInput.getCommand();
		boolean validCountryCheck = CommandInput.countryCheck(countryToAttack);
		int count = 0;
		String didPickOccur = "NO";

		if(!validCountryCheck){
			CommandInput.appendStringTo("That's not a country. Try again.\n", Color.RED);
			count++;
		}
		else{
			countryToAttack = Gameplay.setFromAbbreviation(countryToAttack);

			for(int i=0;i<42;i++){
				if (countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName()) == 0 || countryToAttack.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){
					int attackingCountryIndex = getIndex(countryToAttackWith);
					if(attackingPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)!=0){
						if(Map.arrayContains(Deck.countriesAfterShuffle[i].getAdjacent(), attackingCountryIndex)){
							CommandInput.appendStringTo("You have chosen to attack " + countryToAttack + "\n", attackingPlayerColour);
							didPickOccur = "YES";
							defendingPlayer = Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName;
							defendingPlayerString = defendingPlayer;

							if(defendingPlayer == CommandInput.player1){
								defendingPlayerColour = CommandInput.player1Colour;
							}
							if(defendingPlayer == CommandInput.player2){
								defendingPlayerColour = CommandInput.player2Colour;							
							}
							if(defendingPlayer == "Neutral 1"){
								defendingPlayerColour = Color.BLACK;
							}
							if(defendingPlayer == "Neutral 2"){
								defendingPlayerColour = Color.GREEN;							
							}
							if(defendingPlayer == "Neutral 3"){
								defendingPlayerColour = Color.RED;
							}
							if(defendingPlayer == "Neutral 4"){
								defendingPlayerColour = Color.YELLOW;
							}
						}
						else{
							CommandInput.appendStringTo("You cannot attack this country, select an adjacent country that you do not own. \n", Color.RED);
						}	
					}
					else{
						CommandInput.appendStringTo("You cannot attack this country, select an adjacent country that you do not own. \n", Color.RED);
					}
				}
			}
		}
		if(didPickOccur == "NO" || count > 0){
			PickCountryToAttack(currentPlayer, attackingPlayer, defendingPlayer);
		}
	}

	//Gets the index of the country 
	static int getIndex(String countryToAttackWith2) {
		int index = -1;

		for(int i=0;i<42;i++){
			if (countryToAttackWith2.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || countryToAttackWith2.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				index = Deck.countriesAfterShuffle[i].getIndex();
				System.out.println(Deck.countriesAfterShuffle[i].getName());
				System.out.println(index);
				System.out.println("\n");
			}
		}
		return index;
	}

	//Fucntion containing some of the battle decisions attacking players make during combat
	static void AttackingPlayerBattleDecisions(String currentPlayer, String attackingPlayer, String defendingPlayer){
		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}

		CommandInput.appendStringTo(attackingPlayer + " (ATTACK), Enter number of units to attack with.\n", Color.BLACK);
		CheckAttackerIntegerErrorInput(attackingPlayer, defendingPlayer);
		int NumArmies = 0;

		NumArmies = Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies();
		System.out.println("" + NumArmies);
		int count = 0;
		if(count == 0){

			if(numberOfUnitsToAttackWith == 0){
				CommandInput.appendStringTo("You must attack with at least one army.\n", Color.RED);
				count++;
			}
			else if(numberOfUnitsToAttackWith >= NumArmies){
				CommandInput.appendStringTo("You do not have a sufficient number of armies for that attack.\n", Color.RED);
				count++;
			}
			else if(numberOfUnitsToAttackWith > 3){
				CommandInput.appendStringTo("You can only attack with a maximum of 3 units.\n", Color.RED);
				count++;
			}
		}
		if(count > 0){
			AttackingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);
		}

		if(count == 0){				
			CommandInput.appendStringTo(attackingPlayer + " uses " + countryToAttackWith + " to attack " + countryToAttack + " and uses " + numberOfUnitsToAttackWith + " armies to battle. \n", attackingPlayerColour);
		}	
	}

	//Fucntion containing some of the battle decisions defending players make during combat
	private static void DefendingPlayerBattleDecisions(String currentPlayer, String attackingPlayer, String defendingPlayer) {
		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}

		int NumArmies = 0;
		int count = 0;
		NumArmies = Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies();
		System.out.println("" + NumArmies);

		if(NumArmies == 1){
			CommandInput.appendStringTo(defendingPlayer + " (DEFEND), you only have one army in " + countryToAttack + ", so you do not get to choose how many armies to defend with:\n", defendingPlayerColour);
			numberOfUnitsToDefendWith = 1;
		}
		else{
			CommandInput.appendStringTo(defendingPlayerString + " (DEFEND), Enter number of units to defend with: \n", Color.BLACK);

			CheckDefenderIntegerErrorInput(attackingPlayer, defendingPlayer);
			System.out.println(" " + numberOfUnitsToDefendWith);

			if(count == 0){
				if(numberOfUnitsToDefendWith < 1){
					CommandInput.appendStringTo("You must defend with at least one army.\n", Color.RED);
					count++;
				}
				else if(numberOfUnitsToDefendWith > 2){
					CommandInput.appendStringTo("You must defend with less than three armies.\n", Color.RED);
					count++;
				}
				else if(numberOfUnitsToDefendWith > NumArmies){
					CommandInput.appendStringTo("You do not have that number of armies.\n", Color.RED);
					count++;
				}
			}
		}
		if(count>0){
			DefendingPlayerBattleDecisions(currentPlayer, attackingPlayer, defendingPlayer);
		}
		if(count==0){
			CommandInput.appendStringTo(defendingPlayerString + ", you have chosen to defend with " + numberOfUnitsToDefendWith + " units. \n", defendingPlayerColour);
		}
	}

	//Contains some of the dice roll combat logic and deals with it accordingly. Uses Dice Roll class.
	private static void InternalCombatLogic(String currentPlayer, String attackingPlayer, String defendingPlayer, int numberOfUnitsToAttackWith, int numberOfUnitsToDefendWith) {
		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}
		DiceRoll.combatDiceRoll(currentPlayer, attackingPlayer, defendingPlayer, numberOfUnitsToAttackWith, numberOfUnitsToDefendWith);
		System.out.println("Attacking"+Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
		System.out.println("Attacking with"+Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName()+ " " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getIndex());
		// indexToAttack = 
		if((DiceRoll.string1 == "0") && DiceRoll.string2 == "-1"){

			CommandInput.appendStringTo(attackingPlayer + " wins one combat.\n" + attackingPlayer + " does not lose an army.\n" + defendingPlayerString + " loses one army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;


			if((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())==0){
				takeOverCountry(attackingPlayer, defendingPlayer, countryToAttackWith, countryToAttack);
			}

			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			  
		}
		else if((DiceRoll.string1 == "1") && DiceRoll.string2 == "-1"){

			CommandInput.appendStringTo(defendingPlayerString + " wins one combat.\n" + defendingPlayerString + " does not lose an army.\n" + attackingPlayer + " loses one army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;

			if((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())==0){
				takeOverCountry(attackingPlayer, defendingPlayer, countryToAttackWith, countryToAttack);
			}

			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			  
		}
		else if((DiceRoll.string1 == "0") && DiceRoll.string2 == "0"){

			CommandInput.appendStringTo( attackingPlayer + " wins both combats.\n" + attackingPlayer + " does not lose an army.\n" + defendingPlayerString + " loses two armies.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-2);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;

			if((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())==0){
				takeOverCountry(attackingPlayer, defendingPlayer, countryToAttackWith, countryToAttack);
			}

			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			  
		}
		else if((DiceRoll.string1 == "1") && DiceRoll.string2 == "1"){

			CommandInput.appendStringTo(defendingPlayerString + " wins both combats.\n" + defendingPlayerString + " does not lose an army.\n" + attackingPlayer + " loses two armies.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-2);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;

			if((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())==0){
				takeOverCountry(attackingPlayer, defendingPlayer, countryToAttackWith, countryToAttack);
			}

			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			  
		}
		else if((DiceRoll.string1 == "0") && DiceRoll.string2 == "1"){

			CommandInput.appendStringTo(attackingPlayer + " and " + defendingPlayerString + " each win and lose a combat and both lose 1 army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-1);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;

			if((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())==0){
				takeOverCountry(attackingPlayer, defendingPlayer, countryToAttackWith, countryToAttack);
			}

			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			  
		}
		else if((DiceRoll.string1 == "1") && DiceRoll.string2 == "0"){

			CommandInput.appendStringTo(attackingPlayer + " and " + defendingPlayerString + " each win and lose a combat and both lose 1 army.\n", Color.BLACK);
			Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())-1);
			Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].setPlayerArmies((Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies())-1);
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies()) ;
			System.out.println(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName() + " now has this many armies: " + Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getPlayerArmies()) ;

			if((Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getPlayerArmies())==0){
				takeOverCountry(attackingPlayer, defendingPlayer, countryToAttackWith, countryToAttack);
			}

			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttack)].getName());
			ReassignArmies(Deck.countriesBeforeShuffle[getIndex(countryToAttackWith)].getName());
			  
		}
		  
	}

	private static void takeOverCountry(String attackingPlayer, String defendingPlayer, String countryToAttackWith, String countryToAttack) {
		for(int i=0;i<42;i++){
			ReassignCountriesArmies(Data.COUNTRY_NAMES[i]);
		}

		attackingPlayerP = new Player(attackingPlayer, attackingPlayerColour, getAttackingPlayerTerritoryNumber(attackingPlayer), 0, true);
		if(attackingPlayerP.playerName == CommandInput.player1){
			attackingPlayerP = Deck.player1;
		}
		if(attackingPlayerP.playerName == CommandInput.player2){
			attackingPlayerP = Deck.player2;
		}
		System.out.println("country to attack occupying player before = " + Deck.countriesAfterShuffle[getIndex(countryToAttack)].getOccupyingPlayer().playerName);


		for(int i=0; i<42; i++){
			if(Deck.countriesAfterShuffle[i].getName().compareTo(countryToAttack)==0 || Deck.countriesAfterShuffle[i].getAbbreviation().compareToIgnoreCase(countryToAttack)==0){
				Deck.countriesAfterShuffle[i].setOccupyingPlayer(attackingPlayerP);
			}
		}

		for(int i=0; i<42; i++){
			if(Deck.countriesAfterShuffle[i].getName().compareTo(countryToAttackWith)==0 || Deck.countriesAfterShuffle[i].getAbbreviation().compareToIgnoreCase(countryToAttackWith)==0){
				numberOfArmiesInAttackingCountry = Deck.countriesAfterShuffle[i].getPlayerArmies();
			}
		}
		System.out.println("number of armies in attacking country = " + numberOfArmiesInAttackingCountry);
		int count = 1;
		while(count > 0){
			CommandInput.appendStringTo("Enter number of units to move in with: ", Color.BLACK);
			CheckTakeOverCountryIntegerErrorInput(attackingPlayer, defendingPlayer);

			System.out.println("number of units to move in with = " + numberOfUnitsToMoveInWith);

			if(numberOfUnitsToMoveInWith >= numberOfArmiesInAttackingCountry){
				CommandInput.appendStringTo("You do not have enough armies for this move. You must have at least 1 remaining army in your country.", Color.RED);
				count = 1;
			}
			else if(numberOfUnitsToMoveInWith < 1){
				CommandInput.appendStringTo("Invalid. Please enter a positive number.", Color.RED);
				count = 1;
			}
			else{
				for(int i=0; i<42; i++){
					if(Deck.countriesAfterShuffle[i].getName().compareTo(countryToAttackWith)==0 || Deck.countriesAfterShuffle[i].getAbbreviation().compareToIgnoreCase(countryToAttackWith)==0){
						Deck.countriesAfterShuffle[i].setPlayerArmies(Deck.countriesAfterShuffle[i].getPlayerArmies()-numberOfUnitsToMoveInWith);
					}
				}
				count = 0;
			}

		}
		Deck.countriesBeforeShuffle[getIndex(countryToAttack)].setPlayerArmies(numberOfUnitsToMoveInWith);
		if(Data.exchangeIndex<Data.NUM_COUNTRIES && !(Data.alreadyExchanged)){
			if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
				TerritoryCard.territoryCardsShuffled.get(Data.exchangeIndex).setCardOwner(Deck.player1);
			}
			if(CommandInput.currentPlayer.compareTo(CommandInput.player2)==0){
				TerritoryCard.territoryCardsShuffled.get(Data.exchangeIndex).setCardOwner(Deck.player2);
			}
			Data.exchangeIndex++;
			Data.alreadyExchanged =true;
			CommandInput.appendStringTo(CommandInput.currentPlayer + ", you have gained a territory card.\n", Color.RED);
			int cards = TurnSequence.countCards(CommandInput.currentPlayer);
			CommandInput.appendStringTo("You now have " + cards + " territory cards.\n." , Color.RED);
		}
		  
	}

	//Deals with incorrect user input for attacking player
	static void CheckTakeOverCountryIntegerErrorInput(String attackingPlayer, String defendingPlayer){
		numberOfUnitsToMoveInWith = -1234;

		while(numberOfUnitsToMoveInWith == -1234 ){
			try{
				numberOfUnitsToMoveInWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n ",Color.BLACK);
				CommandInput.appendStringTo("Please enter how many units you wish to move in with:\n", Color.BLACK);
			}
		}
	}



	private static int getAttackingPlayerTerritoryNumber(String attackingPlayer){
		int attackingPlayerTerritories = 0;
		if(attackingPlayer==CommandInput.player1){
			attackingPlayerTerritories = numP1Territories;
		}
		if(attackingPlayer==CommandInput.player2){
			attackingPlayerTerritories = numP2Territories;
		}

		return attackingPlayerTerritories;
	}



	static void ReassignCountriesArmies(String string) {
		int armiesToPass = 0;
		Player playerToPass = null;
		for(int i=0;i<42;i++){
			if(string.compareTo(Deck.countriesAfterShuffle[i].getName())==0){
				armiesToPass = Deck.countriesAfterShuffle[i].getPlayerArmies();
				playerToPass = Deck.countriesAfterShuffle[i].getOccupyingPlayer();
			}
		}
		for(int i=0;i<42;i++){
			if(string.compareTo(Deck.countriesBeforeShuffle[i].getName())==0){
				Deck.countriesBeforeShuffle[i].setPlayerArmies(armiesToPass);
				Deck.countriesBeforeShuffle[i].setOccupyingPlayer(playerToPass);

			}
		}
	}



	private static void ReassignArmies(String name) {
		int indexForReassignment = 0;
		for(int i=0;i<42;i++){
			if(name.compareTo(Deck.countriesBeforeShuffle[i].getName())==0){
				indexForReassignment=i;
			}
		}

		for(int i=0;i<42;i++){
			if(name.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){
				System.out.println("got here");
				Deck.countriesAfterShuffle[i].setPlayerArmies(Deck.countriesBeforeShuffle[indexForReassignment].getPlayerArmies());
				  
				System.out.println(Deck.countriesAfterShuffle[i].getName() + " now has changed units.");
			}
		}

	}



	//Deals with incorrect user input for attacking player
	static void CheckAttackerIntegerErrorInput(String attackingPlayer, String defendingPlayer){
		numberOfUnitsToAttackWith = -1234;

		while(numberOfUnitsToAttackWith == -1234 ){
			try{
				numberOfUnitsToAttackWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n ",Color.BLACK);
				CommandInput.appendStringTo("Please enter how many units you wish to attack with.\n", Color.BLACK);
			}
		}

	}



	//Deals with incorrect user input for defending player
	static void CheckDefenderIntegerErrorInput(String attackingPlayer, String defendingPlayer){

		numberOfUnitsToDefendWith = -1234;

		while(numberOfUnitsToDefendWith == -1234 ){
			try{
				numberOfUnitsToDefendWith = Integer.parseInt(CommandInput.getCommand());	
			}
			catch(NumberFormatException exception){
				CommandInput.appendStringTo("You entered the number incorrectly. Please enter it in Integer form (e.g 7, 3, 4) \n Please enter how many units you wish to attack with.\n", Color.BLACK);
			}
		}

	}



	//Checks if a player has been eliminated
	static void CheckPlayerEliminated(){
		int count1 = 0;
		int count2 = 0;
		for(int i=0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareTo(Deck.player1.playerName)==0){
				count1++;
			}
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareTo(Deck.player2.playerName)==0){
				count2++;
			}
		}
		if(count1 ==0){
			Data.Player2Wins = true;
		}
		if(count2 ==0){
			Data.Player1Wins = true;
		}

	}	





}
