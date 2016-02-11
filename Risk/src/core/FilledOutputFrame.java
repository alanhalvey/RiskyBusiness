package core;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FilledOutputFrame extends JPanel  {
 	
	
	 public FilledOutputFrame() {
	
	JPanel outPanel = new JPanel();
	JTextArea textArea = new JTextArea();
	
	
	textArea.setSize(100, 100);
	textArea.setEditable(false);
	textArea.append(Data.currentInput);
	//textArea.setCaretPosition(textArea.getDocument().getLength());
	
	
	
	outPanel.setSize(300, 300);
	outPanel.add(textArea);
	
	add(outPanel);
	 
	 }
	}