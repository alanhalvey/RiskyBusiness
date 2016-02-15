package core;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FillInput2 extends JPanel implements ActionListener {

	protected static JTextField command;
	protected static JTextArea a;
	protected JScrollPane scrollPane;
	public static boolean inputUpdated = false;
		
	String player1 = "";
	String player2 = "";
	
	int i=0;

	public FillInput2() {
		super(new GridBagLayout());
		setSize(300, 300);

		command = new JTextField(15);
		a = new JTextArea(10, 10);
		scrollPane = new JScrollPane(a);
		a.setEditable(false);
		JButton button = new JButton("ENTER");
		button.addActionListener(this);
		command.addActionListener(this);  //Allows enter press on keyboard

		scrollPane.add(a);

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.NONE;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(command, c);
		add(button, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.0;
		add(scrollPane, c);

		JPanel input = new JPanel();
		// BorderLayout layout = new BorderLayout();
		// command.setLocation(1000, 300);
		// a.setLocation(1000, 300);

		input.setLayout(new BorderLayout());
		input.add(a, BorderLayout.SOUTH);
		input.add(button, BorderLayout.EAST);
		input.add(command);

		add(input);
	}
	
	public void actionPerformed(ActionEvent e) {
			
	/*	Data.currentInput = command.getText();
		command.selectAll();
		inputUpdated = true;
		a.setText(command.getText());
		System.out.println(Data.currentInput);
	*/ //Previous FillInput2 code in this method
		
		if(i==0){
			player1 = command.getText();
			getPlayer1(player1);
		}
		
		if(i==1){
			player2 = command.getText();
			getPlayer2(player2);
		}
		
		i++;
		
		//Data.currentInput = command.getText();
		a.append(">" + command.getText() + "\n");
		
		command.setText(" ");
		if(i==1){
			a.append("\nEnter username for player 2: \n");
		}
		
		if(i==2){
			RollDice(player1, player2);
		}
		
	}
	
	public static void run(){
		a.append("Enter username for player 1: \n");
	}

	public static String getPlayer1(String player1) {
					
		System.out.println("Player 1 username: " + player1 + "\n");
		return player1;
	}

	public static String getPlayer2(String player2){
		
		System.out.println("Player 2 username: " + player2 + "\n");	
		return player2;
	}
	
	public static void RollDice(String player1, String player2){
		
		DiceRoll players = new DiceRoll();
		
		if(players.getRoll() == "1" ){
			a.append("\n" + getPlayer1(player1) + " to go first.");
		}
		else{
			a.append("\n" + getPlayer2(player2) + " to go first.");
		}
	}
}

