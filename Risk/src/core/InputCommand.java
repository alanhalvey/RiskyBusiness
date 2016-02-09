package core;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InputCommand extends JPanel {

		public static void main(String[] args){
	JPanel input = new JPanel();
	JFrame frame = new JFrame();
	frame.setSize(300, 300);
	JButton button = new JButton("ENTER");
	JTextField command = new JTextField(20);
	
	
	final int ROWS = 10; // Lines of text
	final int COLUMNS = 30; // Characters in each row
	JTextArea textArea = new JTextArea(ROWS, COLUMNS);
	textArea.setEditable(true); // display only
	

	//input.setLayout(new BorderLayout());
	input.add(textArea);
	input.add(command);
	input.add(button);
	
	frame.add(input);
	frame.setVisible(true);
		}
}
