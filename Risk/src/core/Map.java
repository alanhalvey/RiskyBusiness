package core;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel {
	//Coordinates.
	int x,y;

	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Graphics2D variable we are using to draw all of the elements in this class onto the map.
		Graphics2D g2d = (Graphics2D) g;

		drawBackgroundImage(g2d);

		for(int i = 0; i<=41;i++){
			x = getXCoordinate(i);
			y = getYCoordinate(i);
			drawLinesBetweenNodes(g2d, i, x, y);
			drawCountryNames(g2d, i, x, y);
			drawPlayers(g2d, i, x, y);
		}

		//Separate for loop to ensure Nodes are always displayed on top of other elements drawn in this class.
		for(int i = 0; i<=41;i++){
			x = getXCoordinate(i);
			y = getYCoordinate(i);
			drawNodes(g2d, i, x, y);
		}

	}



	private void drawPlayers(Graphics2D g2d, int i, int x2, int y2) {
		if (!(CommandInput.getPlayer2().compareTo("") == 0)){

			//boolean tests to find which player is the current player.
			boolean Player1 = arrayContains(Data.PLAYER_1_COUNTRIES, i);
			boolean Player2 = arrayContains(Data.PLAYER_2_COUNTRIES, i);
			boolean Neutral1 = arrayContains(Data.NEUTRAL_1_COUNTRIES, i);
			boolean Neutral2 = arrayContains(Data.NEUTRAL_2_COUNTRIES, i);
			boolean Neutral3 = arrayContains(Data.NEUTRAL_3_COUNTRIES, i);
			boolean Neutral4 = arrayContains(Data.NEUTRAL_4_COUNTRIES, i);

			String playerName = "";
			int numArmies = 0;
			Color playerColor = null;

			//set details for current player based on above tests.
			if (Player1 == true){
				playerName = CommandInput.getPlayer1();
				playerColor = Color.RED;
				numArmies = Data.PLAYER_1_ARMIES;
			}
			if (Player2){
				playerName = CommandInput.getPlayer2();
				playerColor = Color.BLUE;
				numArmies = Data.PLAYER_2_ARMIES;
			}
			if (Neutral1){
				playerName = "Neutral 1";
				playerColor = new Color (125, 68, 3);
				numArmies = Data.NEUTRAL_1_ARMIES;
			}
			if (Neutral2){
				playerName = "Neutral 2";
				playerColor = Color.MAGENTA;
				numArmies = Data.NEUTRAL_2_ARMIES;
			}
			if (Neutral3){
				playerName = "Neutral 3";
				playerColor = Color.BLACK;
				numArmies = Data.NEUTRAL_3_ARMIES;
			}
			if (Neutral4){
				playerName = "Neutral 4";
				playerColor = Color.WHITE;
				numArmies = Data.NEUTRAL_4_ARMIES;
			}

			//Create a rectangle that will fit around the players name (with a little room to spare).
			FontMetrics fm = g2d.getFontMetrics();
			Rectangle r = fm.getStringBounds(" " +playerName + " ", g2d).getBounds();

			//Draw the backround rectangle behind the Player's name
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(x+4, y+4, r.width, r.height);

			g2d.setColor(playerColor);

			//Draw an outline around the rectangle which contains the players name
			g2d.drawRect(x+4, y+4 , r.width, r.height);

			//Display the player's name on the map.
			g2d.drawString(playerName, x+8, y+17);

			g2d.setFont(new Font("default", Font.BOLD, 11));

			//Draw info on each players armies on the map.
			drawNumArmies(g2d, numArmies);
		}

	}


	private void drawNumArmies(Graphics2D g2d, int NumArmies) {
		g2d.setColor(Color.WHITE);
		
		if (NumArmies == 1)
			g2d.drawString("["+NumArmies+ " Army]", x+4, y+37);
		else{
			g2d.drawString("["+NumArmies+ " Armies]", x+4, y+37);
		}

	}


	private void drawBackgroundImage(Graphics2D g2d) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(Screen.class.getResource("/image/background.jpg"));
		} catch (IOException e) {
		}
		ImageObserver observer = null;
		g2d.drawImage(img, 0, 0, observer);
	}


	private void drawLinesBetweenNodes(Graphics2D g2d, int i, int x, int y) {
		/* This is for the case of Alaska and Kamatchka nodes.
		 * I drew an arc between them because it made more sense 
		 * than drawing a line extending through the sides of the 
		 * program window, given the GUI design we have opted to use.
		 */
		if (i == 8 || i == 22){
			if (i==8){
				int differenceInLatitude = Data.getCountryCoord()[22][0] - Data.getCountryCoord()[8][0] ;
				g2d.drawArc(x, y-70, differenceInLatitude, 130, -10, 190);			}
		}
		//draw the lines between the other countries Nodes.
		else{
			//cycle through adjacent countries to each country
			for(int j=0;j<=Data.ADJACENT[i].length-1;j++){
				//if countries are adjacent
				if (contains(Data.ADJACENT[i][j], i)){
					g2d.setColor(Color.WHITE);
					g2d.drawLine(Data.getCountryCoord()[i][0] -1, Data.getCountryCoord()[i][1]-8, Data.getCountryCoord()[Data.ADJACENT[i][j]][0]-1, Data.getCountryCoord()[Data.ADJACENT[i][j]][1] -8);
				}
			}
		}
	}


	private int getXCoordinate(int i) {
		int x = Data.getCountryCoord()[i][0];
		return x;
	}


	private int getYCoordinate(int i) {
		int y = Data.getCountryCoord()[i][1];
		return y;
	}

	
	//simple method to test if an integer array contains a specific integer.
	public static boolean arrayContains(int[] array, Integer item) {
		return Arrays.stream(array).anyMatch(item::equals);
	}

	
	public void setContinentNodeColor (Graphics g2d, int i){
		//Changes colour based on which continent current country is.
		switch (Data.CONTINENT_IDS[i]){
		//North America
		case 0:g2d.setColor(new Color (196, 243, 8)) ;
		break;

		//Europe
		case 1:g2d.setColor(Color.BLUE) ;
		break;

		//Asia
		case 2:g2d.setColor(Color.GREEN) ;
		break;

		//Australia
		case 3:g2d.setColor(Color.magenta) ;
		break;

		//Africa
		case 4:g2d.setColor(Color.RED) ;
		break;

		//South America
		case 5:g2d.setColor(new Color (153, 153, 102)) ;
		break;
		}
	}


	public void drawNodes(Graphics2D g2d, int i, int x, int y){
		g2d.setColor(Color.BLACK);
		
		//I decided to draw an outline and inner circle for each node, to make them more clear on the map.
		Ellipse2D.Double nodeOutlineCircle = new Ellipse2D.Double(x-9,y-16,16,16);
		g2d.draw(nodeOutlineCircle);
		g2d.fill(nodeOutlineCircle);
		setContinentNodeColor(g2d, i);
		Ellipse2D.Double nodeInnerCircle = new Ellipse2D.Double(x-7,y-14,12,12);
		g2d.draw(nodeInnerCircle);
		g2d.fill(nodeInnerCircle);
	}


	public void drawCountryNames(Graphics2D g2d, int i, int x, int y){
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("default", Font.BOLD, 12));

		//For East and West Australia, we need to shift x to the left so they display correctly on the map
		if (Data.COUNTRY_NAMES[i].compareTo("E Australia") == 0 || Data.COUNTRY_NAMES[i].compareTo("W Australia") == 0){
			x = x-85;
		}
		g2d.drawString(Data.COUNTRY_NAMES[i], x+8, y+2);
		g2d.setFont(new Font("default", Font.ITALIC, 12));
	}


}
