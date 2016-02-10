package core;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FillInput extends JPanel {
	
		
		 public FillInput() {
		 createComponents();
		 setSize(1400, 100);
		 }
		 private void createComponents() {
		JButton button = new JButton("ENTER");
		JTextField command = new JTextField(85);
		JPanel input = new JPanel();
		input.add(command);
		input.add(button);
		
		add(input);
		 }
		
	/*public static void main(String[] args){
		
	
	JFrame frame = new JFrame();
	frame.setSize(1400, 100);
	
	
	

	//input.setLayout(new BorderLayout());
	
	
	input.add(button);
	input.add(command);
	
	
	frame.add(input);
	frame.setVisible(true);
		*/
}

