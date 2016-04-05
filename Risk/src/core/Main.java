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
		for(int i=0; i<1; i++){
			TurnSequence.Combat();
		}
		TurnSequence.PlaceUnits();
		while(!(Data.Player1Wins || Data.Player2Wins)){
			TurnSequence.TurnSequence();
			TurnSequence.ChangePlayers();
		}
		if(Data.Player1Wins){
			TurnSequence.DisplayWinner(CommandInput.getPlayer1());
		}
		if(Data.Player2Wins){
			TurnSequence.DisplayWinner(CommandInput.getPlayer1());
		}
	}
}

	