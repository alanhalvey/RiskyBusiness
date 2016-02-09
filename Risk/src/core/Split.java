import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;





public class Split {

	

	public static void main(String[] args) throws IOException {
		
		//JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("RiskMap2.jpg"))));
		
		JLabel OutputWindow = new JLabel("OutPut");
	    OutputWindow.setFont(new Font("Verdana",1,20));
	    
	    JLabel InputWindow = new JLabel("Input");
	    InputWindow.setFont(new Font("Verdana",1,20));
	    
	    BufferedImage img = null;
	    try {
	        img = ImageIO.read(new File("background.jpg"));
	    } catch (IOException e) {
	    }
	    ImageObserver observer = null;
		
	    
	    
	    
	    
	    
	    JFrame frame = new JFrame("RISK"); // creates the JFrame(a window with decorations)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stops the program when window is closed
        frame.setSize(1350, 700);
        
        
        
 
        
        JPanel content = new JPanel();
        JPanel content1 = new JPanel(); // the main panel of the JFrame, remembet you cant add content directly to JFrame
        JPanel content2 = new JPanel();
        JPanel content3 = new JPanel();// panel for the text field
        
        content.setSize(1350,700);
        content.setLocation(0, 0);
        content.setBackground(Color.WHITE);
        
        
        content1.setSize(1000,600);
        content1.setLocation(5, 5);
               
        
        content2.setSize(300, 300);
        content2.setLocation(1010, 5);
        content2.setBackground(Color.RED);
        content2.add(OutputWindow);
        
       
 
        content3.setSize(300, 300);
        content3.setLocation(1010, 310);
        content3.add(InputWindow);
        content3.setBackground(Color.GREEN);
        
 

        frame.getContentPane().add(content3); // adds the buttonarea panel to the main panel
        frame.getContentPane().add(content2); // adds the buttonarea panel to the main panel
        frame.getContentPane().add(content1);
        frame.getContentPane().add(content);
        frame.setBackground(Color.WHITE);
        
        frame.setVisible(true); // makes the window visible, put at end of program        
		
	}
	
		
	}


