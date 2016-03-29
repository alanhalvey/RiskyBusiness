/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */

package core;
import java.awt.Color;
import javax.swing.text.StyleConstants;

//Error handling class allows us to keep important class neat and tidy by dealing with some of the chunky error handling in a seperate class
public class ErrorHandling {

	//Function deals with errors user may input for player 1
	public static void Player1UsernameChecks(){ 

		CommandInput.player1 = CommandInput.getCommand();
		CommandInput.player1 = CommandInput.player1.toUpperCase();
		CommandInput.appendStringTo(CommandInput.player1 + "\n", CommandInput.player1Colour);

		if (CommandInput.player1.length() < 3){ //if username is less than 3 characters. Unacceptable!
			CommandInput.appendStringTo("Your username is too short, please enter a new name: \n", Color.RED);
		}

		if (CommandInput.player1.length() > 10){ //If username is over 10 characters. Ridiculous carry on if someone provokes this error!
			CommandInput.appendStringTo("You username is too long, please enter a new name: \n", Color.RED);
		}

		CommandInput.commandInputWindow.setText("");		

	}

	//Function deals with errors user may input for player 2
	public static void Player2UsernameChecks() {

		CommandInput.player2 = CommandInput.getCommand();
		CommandInput.player2 = CommandInput.player2.toUpperCase();
		CommandInput.appendStringTo(CommandInput.player2 + "\n", CommandInput.player2Colour);	

		if(CommandInput.player1 == CommandInput.player2){ //If the username is already taken by player 1, you is gonna get an error right here!
			CommandInput.appendStringTo("your username is already taken, please enter a new name: \n", Color.RED);
		}
		if(CommandInput.player2.length() < 3){ //if username is less than 3 characters. Unacceptable!
			CommandInput.appendStringTo("Your username is too short, please enter a new name: \n", Color.RED);
		}
		if (CommandInput.player2.length() > 10){ //If username is over 10 characters. Ridiculous carry on if someone provokes this error!
			CommandInput.appendStringTo("You username is too long, please enter a new name: \n", Color.RED);			
		}

		CommandInput.commandInputWindow.setText("");


		StyleConstants.setForeground(CommandInput.style, Color.blue);
	}

	//Function deals with errors that may occur when player 1 is placing their armies
	public static void P1checkTerritories(String country){
		if(Data.PLAYER_1_ARMIES!=0){
			for(int i=0;i<42;i++){
				if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
						|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){ 

					if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of armies to place: \n", Color.BLACK);
						String numToPlace = CommandInput.getCommand();
						int numReinforcementsToPlace = 0;


						if (numToPlace !=null && !"".equals(numToPlace) ){
							try {
								numReinforcementsToPlace = Integer.parseInt(numToPlace);
							} catch (NumberFormatException e) {
								CommandInput.appendStringTo("This is not a number.\n", Color.RED);
								P1checkTerritories(country);
							}
						}



						if(numReinforcementsToPlace <= Data.PLAYER_1_ARMIES){ //If you enter more reinforcements then you actually have, then you ain't getting into this if statement
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							Data.PLAYER_1_ARMIES-=numReinforcementsToPlace;
							CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Data.PLAYER_1_ARMIES + " armies left.\n"), Color.BLUE);
							Screen.mainFrame.repaint();
							Data.neutralsFilled = false;
							CommandInput.placeNeutrals(CommandInput.currentPlayer, "Neutral 1");

							if(Data.PLAYER_2_ARMIES!=0){ //if player 2 has no reinforcements left to place, current player becomes player 2
								CommandInput.currentPlayer = CommandInput.player2;
							}
							else{ //otherwise stays the same
								CommandInput.currentPlayer = CommandInput.player1;
							}


						}
						else{
							CommandInput.appendStringTo("You do not have enough armies to place that many.\n", Color.RED);
							P1checkTerritories(country); //If you enter a number over the armies you have left, the function is called again.
						}
					}
					else{
						CommandInput.appendStringTo("You do not own this country\n", Color.RED);
						CommandInput.placeUnits(CommandInput.player1);
					}
				}
			}
		}
		else{
			CommandInput.currentPlayer = CommandInput.player2;
		}
	}	

	//Function deals with errors that may occur when player 2 is placing their armies
	public static void P2checkTerritories(String country){
		if(Data.PLAYER_2_ARMIES!=0){
			for(int i=0;i<42;i++){
				if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
						|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of armies to place: \n", Color.BLACK);
						String numToPlace = CommandInput.getCommand();
						int numReinforcementsToPlace = 0;

						if (numToPlace !=null && !"".equals(numToPlace) ){
							try {
								numReinforcementsToPlace = Integer.parseInt(numToPlace);
							} catch (NumberFormatException e) {
								CommandInput.appendStringTo("This is not a number.\n", Color.RED);
								P2checkTerritories(country);
							}
						}

						if(numReinforcementsToPlace <= Data.PLAYER_2_ARMIES){ //If you enter more reinforcements then you actually have, then you ain't getting into this if statement
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							Data.PLAYER_2_ARMIES-=numReinforcementsToPlace;
							CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Data.PLAYER_2_ARMIES + " armies left.\n"), Color.BLUE);
							Screen.mainFrame.repaint();
							Data.neutralsFilled = false;
							CommandInput.placeNeutrals(CommandInput.currentPlayer, "Neutral 1");

							if(Data.PLAYER_1_ARMIES!=0){ //if player 2 has no reinforcements left to place, current player becomes player 2
								CommandInput.currentPlayer = CommandInput.player1;
							}
							else{ //otherwise stays the same
								CommandInput.currentPlayer = CommandInput.player2;
							}

						}
						else{ 
							CommandInput.appendStringTo("You do not have enough armies to place that many.\n", Color.RED);
							P2checkTerritories(country); //If you enter a number over the armies you have left, the function is called again.
						}
					}
					else{
						CommandInput.appendStringTo("You do not own this country\n", Color.RED);
						CommandInput.placeUnits(CommandInput.player2);
					}
				}
			}
		}
		else{
			CommandInput.currentPlayer = CommandInput.player1;
		}
	}	

	//Function deals with errors that may occur when player 1 is adding reinforcements at the beginning of the game
	public static void placeReinforcementsErrorChecksP1(String country){

		if(Gameplay.reinforcementsLeft(CommandInput.player1)==false){ //if player 1 hasn't any reinforcements left, player 2 places his reinforcements
			Gameplay.placeReinforcements(CommandInput.player2);
		}
		else{
			for(int i=0;i<42;i++){
				if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
						|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of Reinforcements to place: \n", Color.BLACK);
						String numToPlace = CommandInput.getCommand();	
						CommandInput.appendStringTo(numToPlace+"\n", Color.BLACK);

						int numReinforcementsToPlace = 0;
						if (numToPlace !=null && !"".equals(numToPlace) ){
							try {
								numReinforcementsToPlace = Integer.parseInt(numToPlace);
							} catch (NumberFormatException e) {
								CommandInput.appendStringTo("This is not a number.\n", Color.RED);
								placeReinforcementsErrorChecksP1(country);
							}
						}


						if(numReinforcementsToPlace <= Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements){ //If you enter a number greater then the number of reinforcements you have left you won't enter this if statement 
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);

							for(int k = 0;k<42;k++){
								if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[k].getOccupyingPlayer().playerName)==0){
									Deck.countriesAfterShuffle[k].getOccupyingPlayer().numReinforcements-=numReinforcementsToPlace;
								}
							}
							CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements + " Reinforcements left.\n"), Color.BLUE);

						}
						else{
							CommandInput.appendStringTo("You do not have enough Reinforcements to place that many.\n", Color.RED);
							placeReinforcementsErrorChecksP1(country); //If you enter a number over the number of reinforcements you have left, the function is called again.
						}
					}
					else{
						CommandInput.appendStringTo("You do not own this country\n", Color.RED);
						Gameplay.placeReinforcements(CommandInput.player1);
					}
				}
			}
		}


	}

	//Function deals with errors that may occur when player 2 is adding reinforcements at the beginning of the game
	public static void placeReinforcementsErrorChecksP2(String country){

		for(int i=0;i<42;i++){
			if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
					|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

				if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
					CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of Reinforcements to place: \n", Color.BLACK);
					String numToPlace = CommandInput.getCommand();	
					CommandInput.appendStringTo(numToPlace+"\n", Color.BLACK);
					int numReinforcementsToPlace = 0;
					if (numToPlace !=null && !"".equals(numToPlace) ){
						try {
							numReinforcementsToPlace = Integer.parseInt(numToPlace);
						} catch (NumberFormatException e) {
							CommandInput.appendStringTo("This is not a number.\n", Color.RED);
							placeReinforcementsErrorChecksP1(country);

						}
					}

					if(numReinforcementsToPlace <= Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements){ //If you enter a number greater then the number of reinforcements you have left you won't enter this if statement
						int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
						Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
						CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
						System.out.println(Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements);

						for(int k = 0;k<42;k++){
							if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[k].getOccupyingPlayer().playerName)==0){
								Deck.countriesAfterShuffle[k].getOccupyingPlayer().numReinforcements-=numReinforcementsToPlace;
							}
						}

						System.out.println(Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements);

						CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements + " Reinforcements left.\n"), Color.MAGENTA);
					}
					else{
						CommandInput.appendStringTo("You do not have enough Reinforcements to place that many.\n", Color.RED);
						placeReinforcementsErrorChecksP2(country); //If you enter a number over the number of reinforcements you have left, the function is called again.
					}
				}
				else{
					CommandInput.appendStringTo("You do not own this country\n", Color.RED);
					Gameplay.placeReinforcements(CommandInput.player2);
				}
			}
		}
	}

}
