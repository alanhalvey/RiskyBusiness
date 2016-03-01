package core;

import java.awt.Color;

public class Gameplay {
	static int numP1Territories;
	static int numP2Territories;
	static int numN1Territories;
	static int numN2Territories;
	static int numN3Territories;
	static int numN4Territories;

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

}
