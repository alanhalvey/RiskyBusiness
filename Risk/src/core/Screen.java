package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Screen extends JFrame{
	volatile static boolean flag = true;

	public static void main(String[] args)throws IOException {

		
		JLabel inputScreenTitle = new JLabel("Enter your command:");
		inputScreenTitle.setFont(new Font("Consolas",1,12));

		JFrame mainFrame = new JFrame("RISK"); 
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stops the program when window is closed
		mainFrame.setSize(1350, 700);

		JPanel bankgroundPanel = new JPanel();
		JPanel mapPanel = new JPanel(); 
		JPanel inputOutputPanel = new JPanel();// panel for the text field

		Map fullMap = new Map();
		JPanel commandInputPanel = new CommandInput();

		bankgroundPanel.setSize(1380,700);
		bankgroundPanel.setLocation(0, 0);
		bankgroundPanel.setBackground(Color.WHITE);


		mapPanel.setSize(1380,700);
		mapPanel.setLocation(0, 0);
		mapPanel.setLayout((LayoutManager) new BoxLayout(mapPanel, BoxLayout.PAGE_AXIS));
		mapPanel.add(fullMap);


		inputOutputPanel.setSize(300, 300);
		inputOutputPanel.setLocation(1000, 100);
		inputOutputPanel.add(inputScreenTitle);
		inputOutputPanel.add(commandInputPanel); 

		mainFrame.getContentPane().add(inputOutputPanel); 
		mainFrame.getContentPane().add(mapPanel);
		mainFrame.getContentPane().add(bankgroundPanel);
		mainFrame.setBackground(Color.WHITE);

		mainFrame.setVisible(true);     	

		CommandInput.run();
		while(flag){
			//if the both users have put in their name
			if ((CommandInput.getPlayer2().length() >= 3) && (CommandInput.getPlayer2().length() <= 10)){
				mainFrame.repaint();
				flag = false;
			}	
		}

	}	

}