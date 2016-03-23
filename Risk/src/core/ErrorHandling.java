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


		StyleConstants.setForeground(CommandInput.style, Color.blue);
	}

	public static void P1checkTerritories(String country){
		if(Data.PLAYER_1_ARMIES!=0){
			for(int i=0;i<42;i++){
				if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
						|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of armies to place: \n", Color.BLACK);
						String numToPlace = CommandInput.getCommand();
						int numReinforcementsToPlace = Integer.parseInt(numToPlace);
						if(numReinforcementsToPlace <= Data.PLAYER_1_ARMIES){
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							Data.PLAYER_1_ARMIES-=numReinforcementsToPlace;
							CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Data.PLAYER_1_ARMIES + " armies left.\n"), Color.BLUE);
							Data.neutralsFilled = false;
							CommandInput.placeNeutrals(CommandInput.currentPlayer, "Neutral 1");
							if(Data.PLAYER_2_ARMIES!=0){
								CommandInput.currentPlayer = CommandInput.player2;
							}
							else{
								CommandInput.currentPlayer = CommandInput.player1;
							}

						}
						else{
							CommandInput.appendStringTo("You do not have enough armies to place that many.\n", Color.RED);
							P1checkTerritories(country);
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

	public static void P2checkTerritories(String country){
		if(Data.PLAYER_2_ARMIES!=0){
			for(int i=0;i<42;i++){
				if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
						|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of armies to place: \n", Color.BLACK);
						String numToPlace = CommandInput.getCommand();
						int numReinforcementsToPlace = Integer.parseInt(numToPlace);
						if(numReinforcementsToPlace <= Data.PLAYER_2_ARMIES){
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							Data.PLAYER_2_ARMIES-=numReinforcementsToPlace;
							CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Data.PLAYER_2_ARMIES + " armies left.\n"), Color.BLUE);
							Data.neutralsFilled = false;
							CommandInput.placeNeutrals(CommandInput.currentPlayer, "Neutral 1");
							if(Data.PLAYER_1_ARMIES!=0){
								CommandInput.currentPlayer = CommandInput.player1;
							}
							else{
								CommandInput.currentPlayer = CommandInput.player2;
							}

						}
						else{
							CommandInput.appendStringTo("You do not have enough armies to place that many.\n", Color.RED);
							P2checkTerritories(country);
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

	public static void placeReinforcementsErrorChecksP1(String country){
		if(Gameplay.reinforcementsLeft(CommandInput.player1)==false){
			Gameplay.placeReinforcements(CommandInput.player2);
		}
		else{
			for(int i=0;i<42;i++){
				if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
						|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

					if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
						CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of Reinforcements to place: \n", Color.BLACK);
						String numToPlace = CommandInput.getCommand();
						int numReinforcementsToPlace = Integer.parseInt(numToPlace);
						if(numReinforcementsToPlace <= Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements){
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							for(int k = 0;k<42;k++){
								if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[k].getOccupyingPlayer().playerName)==0){
									Deck.countriesAfterShuffle[k].getOccupyingPlayer().numReinforcements-=numReinforcementsToPlace;
								}
							}
							CommandInput.appendStringTo((CommandInput.currentPlayer + " now has "+ Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements + " Reinforcements left.\n"), Color.BLUE);
							CommandInput.currentPlayer = CommandInput.player2;

						}
						else{
							CommandInput.appendStringTo("You do not have enough Reinforcements to place that many.\n", Color.RED);
							placeReinforcementsErrorChecksP1(country);
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
	public static void placeReinforcementsErrorChecksP2(String country){

		for(int i=0;i<42;i++){
			if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
					|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

				if(CommandInput.currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
					CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of Reinforcements to place: \n", Color.BLACK);
					String numToPlace = CommandInput.getCommand();
					int numReinforcementsToPlace = Integer.parseInt(numToPlace);
					if(numReinforcementsToPlace <= Deck.countriesAfterShuffle[i].getOccupyingPlayer().numReinforcements){
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
						CommandInput.currentPlayer = CommandInput.player1;
					}
					else{
						CommandInput.appendStringTo("You do not have enough Reinforcements to place that many.\n", Color.RED);
						placeReinforcementsErrorChecksP2(country);
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
