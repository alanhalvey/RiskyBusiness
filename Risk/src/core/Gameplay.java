package core;

import java.awt.Color;

public class Gameplay {
	int numP1Territories;
	int numP2Territories;
	int numN1Territories;
	int numN2Territories;
	int numN3Territories;
	int numN4Territories;

	private void placeReinforcements(String currentPlayer){
		calculateReinforcements();
		String numToPlace;
		CommandInput.appendStringTo(currentPlayer + ", please type the country name to place Reinforcements in: \n", Color.BLACK);
		if(numP1Territories != 0){
			if(currentPlayer.compareTo(CommandInput.player1)==0){
				String country = CommandInput.getCommand();
				ErrorHandling.P1checkTerritories(country);
			}
			CommandInput.appendStringTo(currentPlayer + ", please type the number of Reinforcements to place: \n", Color.BLACK);
			numToPlace = CommandInput.getCommand();
			int numReinforcementsToPlace = Integer.parseInt(numToPlace);
		}
		
		
		
		
		


	}

	private void calculateReinforcements() {
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
