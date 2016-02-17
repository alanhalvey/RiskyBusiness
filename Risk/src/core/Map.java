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
	int x,y;

	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		drawBackgroundImage(g2d);

		for(int i = 0; i<=41;i++){
			x = getXCoordinate(i);
			y = getYCoordinate(i);
			drawLines(g2d, i, x, y);
			drawCountryNames(g2d, i, x, y);
			drawPlayers(g2d, i, x, y);
		}

		for(int i = 0; i<=41;i++){
			x = getXCoordinate(i);
			y = getYCoordinate(i);
			drawNodes(g2d, i, x, y);
		}

	}


	private void drawPlayers(Graphics2D g2d, int i, int x2, int y2) {
		if (!(CommandInput.getPlayer2().compareTo("") == 0)){

			boolean Player1 = arrayContains(Data.PLAYER_1_COUNTRIES, i);
			boolean Player2 = arrayContains(Data.PLAYER_2_COUNTRIES, i);
			boolean Neutral1 = arrayContains(Data.NEUTRAL_1_COUNTRIES, i);
			boolean Neutral2 = arrayContains(Data.NEUTRAL_2_COUNTRIES, i);
			boolean Neutral3 = arrayContains(Data.NEUTRAL_3_COUNTRIES, i);
			boolean Neutral4 = arrayContains(Data.NEUTRAL_4_COUNTRIES, i);





			String currentPlayer = "";
			int NumArmies = 0;
			Color PlayerColor = null;

			if (Player1 == true){
				currentPlayer = CommandInput.getPlayer1();
				PlayerColor = Color.RED;
				NumArmies = Data.PLAYER_1_ARMIES;
			}
			if (Player2){
				currentPlayer = CommandInput.getPlayer2();
				PlayerColor = Color.BLUE;
				NumArmies = Data.PLAYER_2_ARMIES;
			}
			if (Neutral1){
				currentPlayer = "Neutral 1";
				PlayerColor = new Color (125, 68, 3);
				NumArmies = Data.NEUTRAL_1_ARMIES;
			}
			if (Neutral2){
				currentPlayer = "Neutral 2";
				PlayerColor = Color.MAGENTA;
				NumArmies = Data.NEUTRAL_2_ARMIES;
			}
			if (Neutral3){
				currentPlayer = "Neutral 3";
				PlayerColor = Color.BLACK;
				NumArmies = Data.NEUTRAL_3_ARMIES;
			}
			if (Neutral4){
				currentPlayer = "Neutral 4";
				PlayerColor = Color.WHITE;
				NumArmies = Data.NEUTRAL_4_ARMIES;
			}

			FontMetrics fm = g2d.getFontMetrics();
			Rectangle r = fm.getStringBounds(" " +currentPlayer + " ", g2d).getBounds();

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(x+4, y+4, r.width, r.height);

			g2d.setColor(PlayerColor);

			g2d.drawRect(x+4, y+4 , r.width, r.height);
			g2d.drawString(currentPlayer, x+8, y+17);
			g2d.setFont(new Font("default", Font.BOLD, 11));
			FontMetrics fm1 = g2d.getFontMetrics();
			Rectangle r2 = fm.getStringBounds(("["+NumArmies+ " Armies] "), g2d).getBounds();

			g2d.setColor(Color.WHITE);
			if (NumArmies == 1)
				g2d.drawString("["+NumArmies+ " Army]", x+4, y+37);
			else{
				g2d.drawString("["+NumArmies+ " Armies]", x+4, y+37);
			}

		}
		
	}


	


	private void drawArmies(Graphics2D g2d, int NumArmies) {
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
			img = ImageIO.read(new File("background.jpg"));
		} catch (IOException e) {
		}
		ImageObserver observer = null;
		g2d.drawImage(img, 0, 0, observer);
	}


	private void drawLines(Graphics2D g2d, int i, int x, int y) {
		if (i == 8 || i == 22){
			if (i==8){
				int differenceInLatitude = Data.getCountryCoord()[22][0] - Data.getCountryCoord()[8][0] ;
				g2d.drawArc(x, y-70, differenceInLatitude, 130, -10, 190);			}
		}
		else{
			for(int j=0;j<=Data.ADJACENT[i].length-1;j++){
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

	
	public static boolean arrayContains(int[] array, Integer item) {
		return Arrays.stream(array).anyMatch(item::equals);
	}
	
	public void setContinentNodeColor (Graphics g, int i){
		Graphics g2d = g;
		switch (Data.CONTINENT_IDS[i]){
		case 0:g2d.setColor(new Color (196, 243, 8)) ;
		break;
		case 1:g2d.setColor(Color.BLUE) ;
		break;
		case 2:g2d.setColor(Color.GREEN) ;
		break;
		case 3:g2d.setColor(Color.magenta) ;
		break;
		case 4:g2d.setColor(Color.RED) ;
		break;
		case 5:g2d.setColor(new Color (153, 153, 102)) ;
		break;
		}
	}

	
	public void drawNodes(Graphics2D g2d, int i, int x, int y){
		g2d.setColor(Color.BLACK);
		Ellipse2D.Double outline = new Ellipse2D.Double(x-9,y-16,16,16);
		g2d.draw(outline);
		g2d.fill(outline);
		setContinentNodeColor(g2d, i);
		Ellipse2D.Double ellipse = new Ellipse2D.Double(x-7,y-14,12,12);
		g2d.draw(ellipse);
		g2d.fill(ellipse);
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
