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

	
	protected JTextField command;
	protected JTextArea a;
	protected JScrollPane scrollPane;
	public static boolean inputUpdated = false;
	
	public FillInput2() {
		super(new GridBagLayout());
		setSize(300, 300);
		
		command = new JTextField(15);
		a = new JTextArea(10,10);
		scrollPane = new JScrollPane(a);
		a.setEditable(false);
		JButton button = new JButton("ENTER");
		button.addActionListener(this);
		
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
		//BorderLayout layout = new BorderLayout();
		//command.setLocation(1000, 300);
		//a.setLocation(1000, 300);
		
		input.setLayout(new BorderLayout());
		input.add(a,BorderLayout.SOUTH);
		input.add(button, BorderLayout.EAST);
		input.add(command);
		
		
		
		
		
		
		
		
		
		add(input);
		
		
	}

	
	public void actionPerformed(ActionEvent e) {
		Data.currentInput = command.getText();
		command.selectAll();
		inputUpdated = true;
		a.setText(command.getText());
		System.out.println(Data.currentInput);
		
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


