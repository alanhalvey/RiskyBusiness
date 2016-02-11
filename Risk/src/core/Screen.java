package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;





public class Screen extends JFrame{

	

	public static void main(String[] args) throws IOException {
		
		//JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("RiskMap2.jpg"))));
		
		JLabel OutputWindow = new JLabel("OutPut");
	    OutputWindow.setFont(new Font("Verdana",1,20));
	    
	    JLabel InputText = new JLabel("Please enter your command:");
	    InputText.setFont(new Font("Verdana",1,12));
	    
	    
	    
	    
	    
	    
	    
	    JFrame frame = new JFrame("RISK"); // creates the JFrame(a window with decorations)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stops the program when window is closed
        frame.setSize(1350, 700);
        
        
        
 
        
        JPanel content = new JPanel();
        JPanel content1 = new JPanel(); // the main panel of the JFrame, remembet you cant add content directly to JFrame
        JPanel content2 = new JPanel();
        JPanel content3 = new JPanel();// panel for the text field
        
        Map points = new Map();
        JPanel input = new FillInput();
        JPanel Outputwriting = new FilledOutputFrame();
        
        content.setSize(1380,700);
        content.setLocation(0, 0);
        content.setBackground(Color.WHITE);
        
        
        content1.setSize(1380,700);
        content1.setLocation(0, 0);
        content1.setLayout((LayoutManager) new BoxLayout(content1, BoxLayout.PAGE_AXIS));
        content1.add(points);
               
        
        content2.setSize(300, 400);
        content2.setLocation(1010, 50);
        //content2.setBackground(Color.RED);
        //content2.add(OutputWindow);
        content2.add(Outputwriting);
        
       
 
        content3.setSize(750, 50);
        content3.setLocation(150, 605);
        content3.add(InputText);
        content3.add(input);
        
        
 
        
        frame.getContentPane().add(content3); // adds the buttonarea panel to the main panel
        frame.getContentPane().add(content2); // adds the buttonarea panel to the main panel
        frame.getContentPane().add(content1);
        frame.getContentPane().add(content);
        frame.setBackground(Color.WHITE);
        
        
        frame.setVisible(true); // makes the window visible, put at end of program        
		
	}
	
		
	}


