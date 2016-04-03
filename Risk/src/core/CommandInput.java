/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */
package core;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class CommandInput extends JPanel{

	protected static JTextField commandInputWindow = new JTextField(15);
	private static JButton enterButton = new JButton("ENTER");

	private static LinkedList<String> commandBuffer = new LinkedList<String>();
	private static final int FONT_SIZE = 14;

	protected JScrollPane scrollPane;

	public static boolean inputUpdated = false;

	static JTextPane outputWindow = new JTextPane();

	static String currentPlayer = "";
	public static String player1 = ""; //Holds player 1 name
	public static String player2 = ""; //Holds player 2 name

	static StyledDocument doc = outputWindow.getStyledDocument();
	static Style style = outputWindow.addStyle("Style", null);
	static String checkIfDieEqual = "NO";

	//Holds colours for players
	public static Color player1Colour = Color.BLUE;
	public static Color player2Colour = Color.MAGENTA;
	public static Color currentPlayerColour = Color.BLACK;

	public CommandInput() {

		class AddActionListener implements ActionListener {
			public void actionPerformed(ActionEvent event)	{
				synchronized (commandBuffer) {
					commandBuffer.add(commandInputWindow.getText());
					commandInputWindow.setText("");
					commandBuffer.notify();
				}
				return;
			}
		}
		ActionListener listener = new AddActionListener();
		commandInputWindow.addActionListener(listener);
		enterButton.addActionListener(listener);
		commandInputWindow.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
		setLayout(new BorderLayout());
		add(commandInputWindow, BorderLayout.CENTER);

		outputWindow.setEditable(false);
		scrollPane=new JScrollPane(outputWindow);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(250, 480));
		scrollPane.setMinimumSize(new Dimension(10, 10));

		// The above is setting up the scroll bar and adding the output window to it 

		DefaultCaret caret = (DefaultCaret)outputWindow.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Adding the button and the action listener to the enter key and 
		// the update caret follows the scroll bar to the bottom of the screen


		JPanel finishedPanel = new JPanel();

		finishedPanel.setLayout(new BorderLayout());
		finishedPanel.add(scrollPane, BorderLayout.SOUTH);
		finishedPanel.add(enterButton, BorderLayout.EAST);
		finishedPanel.add(commandInputWindow);

		//adds all elements to the final panel and gets it ready to be aded to the frame in main.
		add(finishedPanel);

		return;
	}

	public static void run(){ //runs the start of the game, usernames entered, first player to go decided using DiceRoll class.
		appendStringTo("Enter Username for Player 1: \n", Color.BLACK);

		while(player1.length() < 3 || player1.length() > 10){
			ErrorHandling.Player1UsernameChecks(); //Error handling checks in ErrorHandling class to keep commandInput tidy
		}

		appendStringTo("Enter Username for Player 2: \n", Color.BLACK);	

		while((player2.length() < 3 || player2.length() > 10)){
			ErrorHandling.Player2UsernameChecks(); //Error handling checks in ErrorHandling class to keep commandInput tidy
			if(player2.equalsIgnoreCase(player1)){
				appendStringTo("That username has already been taken, please enter another name: \n", Color.RED);
				player2 = "";
			}
		}
	}

	public static String getCommand() { //This function waits for the user to enter a string before taking the result in the text field
		String command;
		synchronized (commandBuffer) {
			while (commandBuffer.isEmpty()) {
				try {
					commandBuffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			command = commandBuffer.pop();
		}
		return command; //returns the user input (very important funcion)
	}

	public static void updatePlayerColour(String currentPlayer){ //updates the current player colour in sync with the current player updation
		if(currentPlayer == player1){
			currentPlayerColour = player1Colour;
		}
		else {
			currentPlayerColour = player2Colour;
		}
	}

	//Function to place armies/reinforcements at the start of the game
	public static void placeUnits(String currentPlayer) {

		updatePlayerColour(currentPlayer);
		int currentUnits;
		if(currentPlayer == player1){
			currentUnits = Data.PLAYER_1_ARMIES;
		}
		else {
			currentUnits = Data.PLAYER_2_ARMIES;
		}

		if(currentPlayer.compareTo(player1)==0 && currentUnits ==0 ){ //if its player one and he/she has no units left ..
			currentPlayer=player2;
			placeUnits(currentPlayer);
			currentUnits = Data.PLAYER_2_ARMIES;
			if(currentUnits == 0){
				Data.keepPlacingUnits = false;
			}
			else{
				placeUnits(currentPlayer);
			}
		}
		else if(currentPlayer.compareTo(player2)==0 && currentUnits == 0){ //if its player two and he/she has no units left ..
			currentPlayer=player1;
			currentUnits = Data.PLAYER_1_ARMIES;
			if(currentUnits == 0){
				Data.keepPlacingUnits = false;
			}
			else{
				placeUnits(currentPlayer);
			}
		}
		else if(currentUnits!=0){
			appendStringTo(currentPlayer + ", please type the country name to place units in: \n", Color.BLACK);

			if(Data.PLAYER_1_ARMIES != 0){


				if(currentPlayer.compareTo(player1)==0){
					String country = getCommand();
					boolean validCountry = countryCheck(country);
					if(validCountry){
						ErrorHandling.P1checkTerritories(country);
					}
					else{
						appendStringTo("Invalid input. Try again.\n",Color.RED);
						placeUnits(currentPlayer);
					}
				}
			}

			if(Data.PLAYER_2_ARMIES != 0){
				if(currentPlayer.compareTo(player2)==0){
					String country = getCommand();
					boolean validCountry = countryCheck(country);
					if(validCountry){
						ErrorHandling.P2checkTerritories(country);
					}
					else{
						appendStringTo("Invalid input. Try again.\n",Color.RED);
						placeUnits(currentPlayer);
					}
				}
			}	
		}
	}

	//checks if the country name or abbreviation exists 
	public static boolean countryCheck(String country) {
		int count = 0;
		for(int i=0;i<42;i++){
			if(country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 || country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation())==0){
				count++;
			}
		}
		if(count==0){
			return false;
		}
		else{
			return true;
		}
	}

	//Function places the neutrals armies
	public static void placeNeutrals(String currentPlayer, String Neutral) {
		appendStringTo(currentPlayer + ", which " + Neutral+ " country do you want to place units in\n", Color.BLACK);
		String country = getCommand();
		Boolean isCountry = countryCheck(country);
		if(isCountry){
			while(Data.neutralsFilled==false){
				NeutralChecks(country, Neutral);
				Screen.mainFrame.repaint();
			}
		}
		else{
			appendStringTo("That is not a country. Try again.\n", Color.RED);
			placeNeutrals(currentPlayer, Neutral);
		}
	}

	//Function allows player 1 & 2 to assign neutral armies as specified in the brief for sprint 3
	private static void NeutralChecks(String country, String currentNeutral) {
		for(int i=0;i<42;i++){
			if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0 
					|| country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getAbbreviation()) == 0){

				if(currentNeutral.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerName)==0){
					CommandInput.appendStringTo(CommandInput.currentPlayer + ", please type the number of armies to place: \n", Color.BLACK);
					String numToPlace = CommandInput.getCommand();

					int numReinforcementsToPlace = 0;

					if (numToPlace !=null && !"".equals(numToPlace) ){
						try {
							numReinforcementsToPlace = Integer.parseInt(numToPlace);
						} catch (NumberFormatException e) {
							CommandInput.appendStringTo("This is not a number.\n", Color.RED);
							NeutralChecks(country, currentNeutral);
						}
					}

					int currentNeutralArmies = 0;

					//current neutral armies variable is set based on which neutral is the current neutral!
					if(currentNeutral.compareToIgnoreCase("Neutral 1")==0){
						currentNeutralArmies = Data.NEUTRAL_1_ARMIES;
					}
					if(currentNeutral.compareToIgnoreCase("Neutral 2")==0){
						currentNeutralArmies = Data.NEUTRAL_2_ARMIES;
					}
					if(currentNeutral.compareToIgnoreCase("Neutral 3")==0){
						currentNeutralArmies = Data.NEUTRAL_3_ARMIES;
					}
					if(currentNeutral.compareToIgnoreCase("Neutral 4")==0){
						currentNeutralArmies = Data.NEUTRAL_4_ARMIES;
					}
					if(currentNeutralArmies>=0){
						if(numReinforcementsToPlace <= currentNeutralArmies && numReinforcementsToPlace >= 0 && numReinforcementsToPlace <= 3){
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+numReinforcementsToPlace);
							CommandInput.appendStringTo((Deck.countriesAfterShuffle[i].getName()+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);

							String nextNeutral = "";
							//Series of if statements corresponding to each neutral player. Calculates reinforcement placings etc.
							if(currentNeutral.compareToIgnoreCase("Neutral 1")==0){
								Data.NEUTRAL_1_ARMIES-=numReinforcementsToPlace;
								CommandInput.appendStringTo((currentNeutral + " now has "+ Data.NEUTRAL_1_ARMIES + " armies left.\n"), Color.BLUE);
								nextNeutral = "Neutral 2";

							}
							else if(currentNeutral.compareToIgnoreCase("Neutral 2")==0){
								Data.NEUTRAL_2_ARMIES-=numReinforcementsToPlace;
								CommandInput.appendStringTo((currentNeutral + " now has "+ Data.NEUTRAL_2_ARMIES + " armies left.\n"), Color.BLUE);
								nextNeutral = "Neutral 3";
							}
							else if(currentNeutral.compareToIgnoreCase("Neutral 3")==0){
								Data.NEUTRAL_3_ARMIES-=numReinforcementsToPlace;
								CommandInput.appendStringTo((currentNeutral + " now has "+ Data.NEUTRAL_3_ARMIES + " armies left.\n"), Color.BLUE);
								nextNeutral = "Neutral 4";
							}
							else if(currentNeutral.compareToIgnoreCase("Neutral 4")==0){
								Data.neutralsFilled = true;
								Data.NEUTRAL_4_ARMIES-=numReinforcementsToPlace;
								CommandInput.appendStringTo((currentNeutral + " now has "+ Data.NEUTRAL_4_ARMIES + " armies left.\n"), Color.BLUE);
								Screen.mainFrame.repaint();
								if(currentPlayer.compareTo(player1)==0){
									currentPlayer=player2;
									currentUnits = Data.PLAYER_2_ARMIES;
									if(currentUnits!=0){
										placeUnits(currentPlayer);
									}
									else{
										break;
									}
								}
								if(currentPlayer.compareTo(player2)==0){
									currentPlayer=player1;
									currentUnits = Data.PLAYER_1_ARMIES;
									if(currentUnits!=0){
										placeUnits(currentPlayer);
									}
									else{
										break;
									}
								}
							}
							Screen.mainFrame.repaint();
							if(currentNeutral.compareToIgnoreCase("Neutral 4")!=0){
								placeNeutrals(currentPlayer, nextNeutral);
							}
						}
						else{
							CommandInput.appendStringTo("Invalid input.\n", Color.RED);
							if(currentNeutralArmies==0){
								CommandInput.appendStringTo(currentNeutral + " has no armies left, please enter 0 to proceed.\n", Color.RED);
							}
							NeutralChecks(country, currentNeutral);
						}
					}
					else{
						CommandInput.appendStringTo(currentNeutral + " has no armies left, please enter 0 to proceed.\n", Color.RED);
						placeNeutrals(currentPlayer, currentNeutral);
					}
				}
				else{
					CommandInput.appendStringTo(currentNeutral+" does not own this country\n", Color.RED);
					placeNeutrals(currentPlayer, currentNeutral);
				}
			}
		}
	}

	//getters for player 1 string, player 2 string, player 1 colour and player 2 colour.
	public static String getPlayer1() {
		return player1;
	}
	public static String getPlayer2(){
		return player2;
	}
	public static Color getPlayer1Colour(){
		return player1Colour;
	}
	public static Color getPlayer2Colour(){
		return player2Colour;
	}

	public static void randomPlayerGenerator(String player1, String player2){ // random generator for who plays first.

		DiceRoll bothPlayers = new DiceRoll();
		bothPlayers.rollDice();

		appendStringTo(player1 + " rolled a " + DiceRoll.getPlayer1RollValue() + "\n", Color.BLACK);
		appendStringTo(player2 + " rolled a " + DiceRoll.getPlayer2RollValue() + "\n", Color.BLACK);

		checkIfDieEqual = "NO";

		if(bothPlayers.rollWinnerOutcome() == "1" ){ //If player 1 wins ..
			appendStringTo((player1) + " to go first.\n", player1Colour);
			currentPlayer = player1;
		}

		if(bothPlayers.rollWinnerOutcome() == "2" ){ //If player 2 wins .. 
			appendStringTo((player2) + " to go first.\n", player2Colour);
			currentPlayer = player2;
		}

		if(bothPlayers.rollWinnerOutcome() == "3"){ //If the two die's rolled were equal .. 
			appendStringTo((player1) + "'s and " + (player2) + "'s Die rolls were equal, Re-Roll required: \n", Color.RED);
			checkIfDieEqual = "YES";
		}
	}

	public static void appendStringTo(String s, Color c){ //append string function called every time a message is printed to the text field
		//Add the ">" too each line in Dark Gray.
		StyleConstants.setForeground(style, Color.DARK_GRAY);
		try {
			doc.insertString(doc.getLength(), "> ", style);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Add the actual text the user inputs with the Colour you want
		StyleConstants.setForeground(style, c);
		try {
			doc.insertString(doc.getLength(), s,style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}