/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel {
	//Coordinates.
	int x,y;
	Country countries[] = new Country[42];
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Graphics2D variable we are using to draw all of the elements in this class onto the map.
		Graphics2D g2d = (Graphics2D) g;
		drawBackgroundImage(g2d);


		for(int i = 0;i<41;i++)	drawLinesBetweenNodes(g2d, i, x, y);

		for(int i = 0; i<=41;i++){
			x = getXCoordinate(i);
			y = getYCoordinate(i);
			drawNodes(g2d, i, x, y);
		}

		for(int i = 0; i<=41;i++){
			x = getXCoordinate(i);
			y = getYCoordinate(i);
			drawCountryNames(g2d, i, x, y);
		}
		drawKey(g2d);
		drawStats(g2d);
		Screen.mainFrame.repaint();
	}

	private void drawKey(Graphics2D g2d){
		g2d.setColor(Color.lightGray);
		g2d.fillRect(1000, 10, 300, 80);
		if(CommandInput.currentPlayer!=""){
			g2d.setFont(new Font("default", Font.BOLD, 14));
			g2d.setColor(CommandInput.player1Colour);
			Ellipse2D.Double key = new Ellipse2D.Double(1020,20,10,10);
			g2d.fill(key);
			g2d.drawString(CommandInput.getPlayer1(), 1040, 30);

			g2d.setColor(CommandInput.player2Colour);
			key = new Ellipse2D.Double(1020,40,10,10);
			g2d.fill(key);
			g2d.drawString(CommandInput.getPlayer2(), (int)key.x+20, (int)key.y+10);

			g2d.setColor(Color.BLACK);
			key = new Ellipse2D.Double(1020,60,10,10);
			g2d.fill(key);
			g2d.drawString("Neutral 1", (int)key.x+20, (int)key.y+10);

			g2d.setColor(Color.GREEN);
			key = new Ellipse2D.Double(1160,20,10,10);
			g2d.fill(key);
			g2d.drawString("Neutral 2", (int)key.x+20, (int)key.y+10);

			g2d.setColor(Color.RED);
			key = new Ellipse2D.Double(1160,40,10,10);
			g2d.fill(key);
			g2d.drawString("Neutral 3", (int)key.x+20, (int)key.y+10);

			g2d.setColor(Color.YELLOW);
			key = new Ellipse2D.Double(1160,60,10,10);
			g2d.fill(key);
			g2d.drawString("Neutral 4", (int)key.x+20, (int)key.y+10);
		}
	}
	
	private void drawStats(Graphics2D g2d){
		g2d.setColor(Color.lightGray);
		g2d.fillRect(5, 610, 750 , 50 );
		g2d.fillRect(5, 575, 110, 35);
		
		if(CommandInput.currentPlayer!=""){
			
			g2d.setFont(new Font("default", Font.BOLD, 14));
			g2d.setColor(CommandInput.player1Colour);
			Ellipse2D.Double key = new Ellipse2D.Double(20,620,10,10);
			g2d.fill(key);
			g2d.drawString("Player Stats:",  20, 600);
			g2d.drawString(CommandInput.getPlayer1() +": Num of Territories: (" + Deck.calculateTerritories(CommandInput.getPlayer1()) + ") Number of Reinforcements: (" + Deck.player1.numReinforcements +") Number of Territory Cards: (" + Deck.calculateTerritoryCards(CommandInput.player1)+")" ,  (int)key.x+20, (int)key.y+10);
			g2d.setColor(CommandInput.player2Colour);
			key = new Ellipse2D.Double(20,640,10,10);
			g2d.fill(key);
			g2d.drawString(CommandInput.getPlayer2() +": Num of Territories: (" + Deck.calculateTerritories(CommandInput.getPlayer2()) + ") Number of Reinforcements: (" + Deck.player2.numReinforcements +") Number of Territory Cards: (" + Deck.calculateTerritoryCards(CommandInput.player2)+ ")" , (int)key.x+20, (int)key.y+10);
			
		}

	}
	private void drawNumArmies(Graphics2D g2d, int i) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("default", Font.BOLD, 12));
		int NumArmies = Deck.countriesAfterShuffle[i].getPlayerArmies();
		if (NumArmies == 1)
			g2d.drawString("("+NumArmies+ " Army)", x-20, y+13);
		else{
			g2d.drawString("("+NumArmies+ " Armies)", x-20, y+13);
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
				g2d.drawArc(Data.getCountryCoord()[8][0] - 2, Data.getCountryCoord()[8][1]-70, differenceInLatitude, 130, -10, 190);			}
		}
		//draw the lines between the other countries Nodes.
		else{
			//cycle through adjacent countries to each country
			for(int j=0;j<=Data.ADJACENT[i].length-1;j++){
				//if countries are adjacent
				if (contains(Data.ADJACENT[i][j], i)){
					g2d.setColor(Color.ORANGE);
					g2d.drawLine(Data.getCountryCoord()[i][0] -1, Data.getCountryCoord()[i][1]-8, Data.getCountryCoord()[Data.ADJACENT[i][j]][0]-1, Data.getCountryCoord()[Data.ADJACENT[i][j]][1] -8);
				}
			}
		}
	}

	private int getXCoordinate(int i) {
		return Deck.countriesAfterShuffle[i].getX_Coordinate();
	}

	private int getYCoordinate(int i) {
		return Deck.countriesAfterShuffle[i].getY_Coordinate();
	}

	//simple method to test if an integer array contains a specific integer.
	public static boolean arrayContains(int[] array, Integer item) {
		return Arrays.stream(array).anyMatch(item::equals);
	}

	public void setContinentNodeColor (Graphics g2d, int i){
		//Changes colour based on which continent current country is.
		switch (Deck.countriesAfterShuffle[i].getContinent()){
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
		setContinentNodeColor(g2d, i);
		//I decided to draw an outline and inner circle for each node, to make them more clear on the map.
		Ellipse2D.Double nodeOutlineCircle = new Ellipse2D.Double(x-11.5,y-17.25,20,20);
		g2d.draw(nodeOutlineCircle);
		g2d.fill(nodeOutlineCircle);
		if(CommandInput.currentPlayer!=""){
			Ellipse2D.Double countryNodeOutlineCircle = new Ellipse2D.Double(x-8,y-13.5,13,13);
			Ellipse2D.Double nodeInnerCircle = new Ellipse2D.Double(x-7.5,y-13,12,12);
			g2d.setColor(Color.WHITE);
			g2d.draw(countryNodeOutlineCircle);
			g2d.setColor(Deck.countriesAfterShuffle[i].getOccupyingPlayer().playerColor);
			g2d.fill(nodeInnerCircle);
			drawNumArmies(g2d, i);
		}
	}

	public void drawCountryNames(Graphics2D g2d, int i, int x, int y){
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("default", Font.BOLD, 12));

		g2d.drawString(Deck.countriesAfterShuffle[i].getName(), x-20, y-30);
		g2d.setFont(new Font("default", Font.ITALIC, 12));
		g2d.drawString("( "+Deck.countriesAfterShuffle[i].getAbbreviation() + " )", x-20, y-18);
		
	}
}