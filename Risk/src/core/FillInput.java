package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FillInput extends JPanel implements ActionListener {

	
	protected JTextField command;
	
	public FillInput() {
		
		setSize(1400, 100);
		
		command = new JTextField(40);
        command.addActionListener(this);
	
		JButton button = new JButton("ENTER");
		button.addActionListener(this);
		JPanel input = new JPanel();

		input.add(command);
		input.add(button);
		
		
		add(input);
		
		
		
		
	}

	
	public void actionPerformed(ActionEvent e) {
		Data.currentInput = command.getText();
		command.selectAll();
		//System.out.println(Data.currentInput);
	}
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


