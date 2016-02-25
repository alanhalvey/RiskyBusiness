/*
 * Alan Halvey -
 * Alan Holmes -
 * Greg Sloggett - 14522247
 * 
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
	public static String player1 = "";
	public static String player2 = "";

	static StyledDocument doc = outputWindow.getStyledDocument();
	static Style style = outputWindow.addStyle("Style", null);
	static String checkIfDieEqual = "NO";

	public static Color player1Colour = Color.BLUE;
	public static Color player2Colour = Color.GREEN;
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
		scrollPane.setPreferredSize(new Dimension(250, 240));
		scrollPane.setMinimumSize(new Dimension(10, 10));

		/*
		 * The above is setting up the scroll bar and adding the output window to it
		 */

		DefaultCaret caret = (DefaultCaret)outputWindow.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		/*
		 * Adding the button and the action listener to the enter key and
		 * th update caret follows the scroll barto the bottom of the screen
		 */

		JPanel finishedPanel = new JPanel();

		finishedPanel.setLayout(new BorderLayout());
		finishedPanel.add(scrollPane, BorderLayout.SOUTH);
		finishedPanel.add(enterButton, BorderLayout.EAST);
		finishedPanel.add(commandInputWindow);
		/*
		 * adds all elements to the final panel and gets it ready to be aded to the frame in main.
		 */
		add(finishedPanel);

		return;
	}

	public static void run(){
		appendStringTo("Enter Username for Player 1: \n", Color.BLACK);

		while(player1.length() < 3 || player1.length() > 10){
			ErrorHandling.Player1UsernameChecks();
		}

		appendStringTo("Enter Username for Player 2: \n", Color.BLACK);	

		while((player2.length() < 3 || player2.length() > 10)){
			ErrorHandling.Player2UsernameChecks();
			if(player2.equalsIgnoreCase(player1)){
				appendStringTo("That username has already been taken, please enter another name: \n", Color.RED);
				player2 = "";
			}
		}



		randomPlayerGenerator(player1, player2);
		while(checkIfDieEqual == "YES"){
			randomPlayerGenerator(player1, player2);
		}
	}

	public static String getCommand() {
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
		return command;
	}

	public static String placeUnits(String currentPlayer) {
		boolean flag = false;

		


			if(currentPlayer == player1){
				currentPlayerColour = player1Colour;
			}
			else {
				currentPlayerColour = player2Colour;
			}

			appendStringTo(currentPlayer + ", please type the country name to place units in: \n", currentPlayerColour);
			if(Data.PLAYER_1_ARMIES != 0){
			if(currentPlayer.compareTo(player1)==0){
				String country = getCommand();
				for(int i=0;i<42;i++){
					if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){

						if(currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer())==0){
							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+3);
							appendStringTo((country+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							flag = true;
							Data.PLAYER_1_ARMIES-=3;
							appendStringTo((currentPlayer + " now has "+ Data.PLAYER_1_ARMIES + " units left.\n"), currentPlayerColour);
							CommandInput.currentPlayer = player2;
							return player2;

						}
						else{
							appendStringTo("You do not own this country\n", Color.RED);
							placeUnits(player1);
						}
					}
				}
			}

			if(currentPlayer.compareTo(player2)==0 && (flag == false)){
				String country = getCommand();
				for(int i=0;i<42;i++){
					if (country.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getName())==0){

						if(currentPlayer.compareToIgnoreCase(Deck.countriesAfterShuffle[i].getOccupyingPlayer())==0){

							int currentUnits = Deck.countriesAfterShuffle[i].getPlayerArmies();
							Deck.countriesAfterShuffle[i].setPlayerArmies(currentUnits+3);
							appendStringTo((country+" now has "+Deck.countriesAfterShuffle[i].getPlayerArmies() + " units\n"), Color.BLACK);
							Data.PLAYER_2_ARMIES-=3;
							appendStringTo((currentPlayer + " now has "+ Data.PLAYER_2_ARMIES + " units left.\n"), currentPlayerColour);
							CommandInput.currentPlayer = player1;
							return player1;

						}
						else{
							appendStringTo("You do not own this country\n", Color.RED);
							placeUnits(player2);
						}
					}

				}
			}
			return currentPlayer;
		}
		else{
	
		}
		return "";
		}


		public static void placeNeutrals() {
			for(int z=0;z<42;z++){
				System.out.println(Deck.countriesAfterShuffle[z].getOccupyingPlayer());
				if(Deck.countriesAfterShuffle[z].getOccupyingPlayer().compareTo("Neutral 1")==0){
					int units = Deck.countriesAfterShuffle[z].getPlayerArmies();
					Deck.countriesAfterShuffle[z].setPlayerArmies(units+1);
					Data.NEUTRAL_1_ARMIES-=1;
				}
				else if(Deck.countriesAfterShuffle[z].getOccupyingPlayer().compareTo("Neutral 2")==0){
					int units = Deck.countriesAfterShuffle[z].getPlayerArmies();
					Deck.countriesAfterShuffle[z].setPlayerArmies(units+1);
					Data.NEUTRAL_2_ARMIES-=1;
				}
				else if(Deck.countriesAfterShuffle[z].getOccupyingPlayer().compareTo("Neutral 3")==0){
					int units = Deck.countriesAfterShuffle[z].getPlayerArmies();
					Deck.countriesAfterShuffle[z].setPlayerArmies(units+1);
					Data.NEUTRAL_3_ARMIES-=1;
				}
				else if(Deck.countriesAfterShuffle[z].getOccupyingPlayer().compareTo("Neutral 4")==0){
					int units = Deck.countriesAfterShuffle[z].getPlayerArmies();
					Deck.countriesAfterShuffle[z].setPlayerArmies(units+1);
					Data.NEUTRAL_4_ARMIES-=1;
				}
			}
		}


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

		public static void randomPlayerGenerator(String player1, String player2){
			/*
			 * random generator for who plays first.
			 */
			DiceRoll bothPlayers = new DiceRoll();
			bothPlayers.rollDice();

			appendStringTo(player1 + " rolled a " + bothPlayers.getPlayer1RollValue() + "\n", player1Colour);
			appendStringTo(player2 + " rolled a " + bothPlayers.getPlayer2RollValue() + "\n", player2Colour);

			checkIfDieEqual = "NO";

			if(bothPlayers.rollWinnerOutcome() == "1" ){
				appendStringTo((player1) + " to go first.\n", player1Colour);
				currentPlayer = player1;
			}

			if(bothPlayers.rollWinnerOutcome() == "2" ){
				appendStringTo((player2) + " to go first.\n", player2Colour);
				currentPlayer = player2;
			}

			if(bothPlayers.rollWinnerOutcome() == "3"){
				appendStringTo((player1) + "'s and " + (player2) + "'s Die rolls were equal, Re-Roll required: \n", Color.RED);
				checkIfDieEqual = "YES";
			}
		}

		public static void appendStringTo(String s, Color c){
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