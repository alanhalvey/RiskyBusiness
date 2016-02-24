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
		System.out.println("Player 1 = " + CommandInput.player1);
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
		System.out.println("Player 2 = " + CommandInput.player2);
	
		StyleConstants.setForeground(CommandInput.style, Color.blue);
}
	


}
