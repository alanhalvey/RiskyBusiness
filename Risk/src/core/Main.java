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
		boolean flag = true;

		while(flag){
			//if the both users have put in their name
			if ((CommandInput.getPlayer2().length() >= 3) && (CommandInput.getPlayer2().length() <= 10)){
				Screen.mainFrame.repaint();
				Deck.shuffledDeck();
				flag = false;
			}	
		}

		CommandInput.appendStringTo(CommandInput.player1 + " owns the following countries: \n" + Deck.player1Countries + "\n", CommandInput.player1Colour);
		CommandInput.appendStringTo(CommandInput.player2 + " owns the following countries: \n" + Deck.player2Countries + "\n", CommandInput.player2Colour);

		CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		while(CommandInput.checkIfDieEqual == "YES"){
			CommandInput.randomPlayerGenerator(CommandInput.player1, CommandInput.player2);
		}
		
		Gameplay.combat(CommandInput.currentPlayer);

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

		Gameplay.calculateReinforcements();
		while(Gameplay.reinforcementsLeft(CommandInput.player1)==true || Gameplay.reinforcementsLeft(CommandInput.player2)==true){
			Gameplay.placeReinforcements(CommandInput.currentPlayer);
			Screen.mainFrame.repaint();
		}

		while(true){
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies from\n", Color.RED);
			String takeArmies = CommandInput.getCommand();
			CommandInput.appendStringTo(Gameplay.setFromAbbreviation(takeArmies)+"\n", CommandInput.currentPlayerColour);
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter country to fortify armies to\n", Color.RED);
			String putArmies = CommandInput.getCommand();
			CommandInput.appendStringTo(Gameplay.setFromAbbreviation(putArmies)+"\n", CommandInput.currentPlayerColour);
			CommandInput.appendStringTo(CommandInput.currentPlayer+" Enter amount of armies to fortify\n", Color.RED);
			int toMove = Integer.parseInt(CommandInput.getCommand());
			CommandInput.appendStringTo( toMove+ " armies to fortify\n", Color.RED);
			takeArmies = Gameplay.setFromAbbreviation(takeArmies);
			putArmies = Gameplay.setFromAbbreviation(putArmies);

			if(putArmies!=null && takeArmies!=null){
				Country takingFrom = Gameplay.setCountry(takeArmies);
				Country puttingTo = Gameplay.setCountry(putArmies);
				Gameplay.Fortify(takingFrom, puttingTo, toMove );
				Screen.mainFrame.repaint();


				if(CommandInput.currentPlayer.compareTo(CommandInput.player1)==0){
					CommandInput.currentPlayer = CommandInput.player2;
				}
				else if(CommandInput.currentPlayer.compareTo(CommandInput.player2)==0){
					CommandInput.currentPlayer = CommandInput.player1;
				}
			}
			
			else{
				CommandInput.appendStringTo("You have entered invalid details, please try again.\n\n", Color.RED);
			}

		}


	}
}