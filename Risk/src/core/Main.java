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

	static String choice = "";

	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException{
		Screen screen = new Screen();
		CommandInput.run();
		TurnSequence.TurnSequencePreparations();
		//TurnSequence.PlaceUnits();
		
		
		for(int i =0;i<42;i++){
			if(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName.compareTo(CommandInput.player1)!=0){
				Deck.countriesAfterShuffle[i].setPlayerArmies(10);
			}
		}
		while(!(Data.Player1Wins || Data.Player2Wins)){
			TurnSequence.TurnSequence();
			TurnSequence.ChangePlayers();
		}
		if(Data.Player1Wins = true){
			
			TurnSequence.DisplayWinner(CommandInput.getPlayer1());
		}
		if(Data.Player2Wins = true){
			TurnSequence.DisplayWinner(CommandInput.getPlayer1());
		}
	}
}

	