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

	public static String player1 = "";
	public static String player2 = "";
	static StyledDocument doc = outputWindow.getStyledDocument();
	static Style style = outputWindow.addStyle("Style", null);
	static int i=0; //iterator for going thru the two players
	static int check = 0;
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

		appendStringTo("Enter Username for Player 1: \n", Color.RED);

		while(player1.length() < 3 || player1.length() > 10){
			Player1UsernameChecks();
		}

		appendStringTo("Enter Username for Player 2: \n", Color.BLUE);	

		while((player2.length() < 3 || player2.length() > 10)){
			Player2UsernameChecks();
		}
		
		System.out.println(player1 + "\n");
		System.out.println(player2 + "\n");
		
		randomPlayerGenerator(player1, player2);
		while(check == 1){
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

	public static void Player1UsernameChecks(){ //performs error checks on user input
	
		player1 = getCommand();
		appendStringTo(player1 + "\n", Color.BLACK);
		
		if (player1.length() < 3){ //Why does this if statement not work more than once?
			appendStringTo("Your username is too short, please enter a new name: \n", Color.GREEN);
		}
		
		if (player1.length() > 10){
			appendStringTo("You username is too long, please enter a new name: \n", Color.GREEN);
		}
		
		commandInputWindow.setText("");		
		System.out.println("Player 1 = " + player1);

	}

	public static void Player2UsernameChecks() {
			
		player2 = getCommand();

		appendStringTo(player2 + "\n", Color.BLACK);	

		if(player1 == player2){
			appendStringTo("your username is already taken, please enter a new name: \n", Color.GREEN);
		}
		
		if(player2.length() < 3){
			appendStringTo("Your username is too short, please enter a new name: \n", Color.GREEN);
		}
		if (player2.length() > 10){
			appendStringTo("You username is too long, please enter a new name: \n", Color.GREEN);			
		}
		
		commandInputWindow.setText("");
		
		System.out.println("Player 2 = " + player2);
	
		StyleConstants.setForeground(style, Color.blue);
		
}
	
	public static String getPlayer1() {
		return player1;
	}

	public static String getPlayer2(){
		return player2;
	}

	public static void randomPlayerGenerator(String player1, String player2){
		/*
		 * random generator for who plays first.
		 */
		DiceRoll bothPlayers = new DiceRoll();
		bothPlayers.rollDice();
		
		appendStringTo(player1 + " rolled a " + bothPlayers.getPlayer1RollValue() + "\n", Color.ORANGE);
		appendStringTo(player2 + " rolled a " + bothPlayers.getPlayer2RollValue() + "\n", Color.PINK);

		check = 2;
		
		if(bothPlayers.rollWinnerOutcome() == "1" ){
			appendStringTo((player1) + " to go first.\n", Color.RED);
		}
		
		if(bothPlayers.rollWinnerOutcome() == "2" ){
			appendStringTo((player2) + " to go first.\n", Color.BLUE);
		}
		
		if(bothPlayers.rollWinnerOutcome() == "3"){
			appendStringTo((player1) + "'s and " + (player2) + "'s Die rolls were equal, Re-Roll required: \n", Color.BLACK);
			check = 1;
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