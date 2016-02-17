package core;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class CommandInput extends JPanel implements ActionListener {



	protected static JTextField command;
	protected static JTextArea a;
	protected JScrollPane scrollPane;
	public static boolean inputUpdated = false;
	public static String flaps = "";

	static JTextPane edit = new JTextPane();


	public static String player1 = "";
	public static String player2 = "";
	static StyledDocument doc = edit.getStyledDocument();
	static Style style = edit.addStyle("Style", null);
	int i=0;

	public CommandInput() {
		super(new GridBagLayout());
		setSize(300, 300);

		command = new JTextField(15);
		
		

		edit.setEditable(false);
		scrollPane=new JScrollPane(edit);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(250, 240));
		scrollPane.setMinimumSize(new Dimension(10, 10));
		
		
        
        

		//scrollPane.add(a);


		JButton button = new JButton("ENTER");
		button.addActionListener(this);
		command.addActionListener(this);  //Allows enter press on keyboard
		DefaultCaret caret = (DefaultCaret)edit.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		/*
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.NONE;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(command, c);
		add(button, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0.0;
		add(scrollPane, c);
		 */
		JPanel input = new JPanel();
		// BorderLayout layout = new BorderLayout();
		// command.setLocation(1000, 300);
		// a.setLocation(1000, 300);





		input.setLayout(new BorderLayout());
		input.add(scrollPane, BorderLayout.SOUTH);
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
		}

		if(i==1){
			player2 = command.getText();
		}

		i++;

		//Data.currentInput = command.getText();
		
		append(command.getText() + "\n", Color.BLACK);
		command.setText(" ");
		if(i==1){
			StyleConstants.setForeground(style, Color.blue);
			append("Enter Username for Player 2: \n", Color.BLUE);

		}

		if(i==2){
			RollDice(player1, player2);
		}

		
	}

	public static void run(){
		
		append("Enter Username for Player 1: \n", Color.RED);

	}

	public static String getPlayer1() {
		return player1;
	}


	public static String getPlayer2(){
		return player2;
	}

	public static void RollDice(String player1, String player2){

		DiceRoll players = new DiceRoll();

		if(players.getRoll() == "1" ){
			append((player1) + " to go first.\n", Color.RED);

		}
		else{
			append((player2) + " to go first.\n", Color.BLUE);
		}
	}
	public static void append(String s, Color c){
		//Add the ">" too each line in Dark Gray.
		StyleConstants.setForeground(style, Color.DARK_GRAY);
		try {
			doc.insertString(doc.getLength(), "> ", style);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Add the actual text the user inputs with the Color you want
		StyleConstants.setForeground(style, c);
		try {
			doc.insertString(doc.getLength(), s,style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

