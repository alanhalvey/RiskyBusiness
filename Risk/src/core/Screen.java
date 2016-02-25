/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Screen extends JFrame{

	static JFrame mainFrame = new JFrame("RISK"); 

	public Screen() throws IOException {
		
		JLabel inputScreenTitle = new JLabel("Enter your command:");
		inputScreenTitle.setFont(new Font("Consolas",1,12));

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stops the program when window is closed
		mainFrame.setSize(1350, 700);

		JPanel bankgroundPanel = new JPanel();
		JPanel mapPanel = new JPanel(); 
		JPanel inputOutputPanel = new JPanel();// panel for the text field
		
		JPanel commandInputPanel = new CommandInput();
		Deck.shuffledDeck();
		Map fullMap = new Map();

		bankgroundPanel.setSize(1380,700);
		bankgroundPanel.setLocation(0, 0);
		bankgroundPanel.setBackground(Color.WHITE);

		mapPanel.setSize(1380,700);
		mapPanel.setLocation(0, 0);
		mapPanel.setLayout((LayoutManager) new BoxLayout(mapPanel, BoxLayout.PAGE_AXIS));
		mapPanel.add(fullMap);

		inputOutputPanel.setSize(300, 550);
		inputOutputPanel.setLocation(1000, 100);
		inputOutputPanel.add(inputScreenTitle);
		inputOutputPanel.add(commandInputPanel); 

		mainFrame.getContentPane().add(inputOutputPanel); 
		mainFrame.getContentPane().add(mapPanel);
		mainFrame.getContentPane().add(bankgroundPanel);
		mainFrame.setBackground(Color.WHITE);

		mainFrame.setVisible(true);     	
	}	
}