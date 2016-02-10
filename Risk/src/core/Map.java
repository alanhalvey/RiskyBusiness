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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);


		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("background.jpg"));
		} catch (IOException e) {
		}
		ImageObserver observer = null;
		g2d.drawImage(img, 0, 0, observer);


		for(int i = 0; i<=41;i++){
			Data.X_Coordinates[i]=(Data.getCountryCoord()[i][0]);
			Data.Y_Coordinates[i]=(Data.getCountryCoord()[i][1]);

		}
		for (int i = 0; i <= 41; i++) {
			Dimension size = getSize();
			int w = size.width ;
			int h = size.height;

			g2d.setColor(Color.BLUE);
			int x = Data.X_Coordinates[i] % w;
			int y = Data.Y_Coordinates[i] % h;


			//g2d.draw3DRect(x, y-10, 5, 5, true);

			g.setColor(Color.BLACK);
			Ellipse2D.Double outline = new Ellipse2D.Double(x-9,y-16,16,16);
			g2d.draw(outline);
			g2d.fill(outline);
			
			switch (Data.CONTINENT_IDS[i]){
			case 0:g2d.setColor(Color.YELLOW) ;
			break;
			case 1:g2d.setColor(Color.BLUE) ;
			break;
			case 2:g2d.setColor(Color.GREEN) ;
			break;
			case 3:g2d.setColor(Color.magenta) ;
			break;
			case 4:g2d.setColor(Color.ORANGE) ;
			break;
			case 5:g2d.setColor(Color.orange) ;
			break;
			}

			Ellipse2D.Double ellipse = new Ellipse2D.Double(x-7,y-14,12,12);
			g2d.draw(ellipse);
			g2d.fill(ellipse);
			
			g2d.setColor(Color.WHITE);
			if (i>0){
				if (Data.CONTINENT_IDS[i] == Data.CONTINENT_IDS[i-1]){
					g2d.drawLine(Data.X_Coordinates[i-1], Data.Y_Coordinates[i-1]-10, Data.X_Coordinates[i], Data.Y_Coordinates[i]-10);
				}
			}	




			g.setColor(Color.BLACK);
			g.setFont(new Font("default", Font.BOLD, 11));

			if (Data.COUNTRY_NAMES[i].compareTo("E Australia") == 0 || Data.COUNTRY_NAMES[i].compareTo("W Australia") == 0){
				x = x-70;
				y = y+2;
			}

			




			g2d.drawString(Data.COUNTRY_NAMES[i], x+8, y+2);
			g.setFont(new Font("default", Font.ITALIC, 12));

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
				currentPlayer = "Player 1";
				PlayerColor = Color.RED;
				NumArmies = Data.PLAYER_1_ARMIES;
			}
			if (Player2){
				currentPlayer = "Player 2";
				PlayerColor = Color.BLUE;
				NumArmies = Data.PLAYER_2_ARMIES;
			}
			if (Neutral1){
				currentPlayer = "Neutral 1";
				PlayerColor = Color.ORANGE;
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
				PlayerColor = Color.CYAN;
				NumArmies = Data.NEUTRAL_4_ARMIES;
			}
			
			FontMetrics fm = g.getFontMetrics();
			Rectangle r = fm.getStringBounds(currentPlayer, g).getBounds();
			
			g.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(x+8, y+4, r.width, r.height);
			
			g.setColor(PlayerColor);
			
			g2d.drawRect(x+8, y+4 , r.width, r.height);
			g2d.drawString(currentPlayer, x+8, y+17);
			g.setFont(new Font("default", Font.BOLD, 11));
			FontMetrics fm1 = g.getFontMetrics();
			Rectangle r2 = fm.getStringBounds(("["+NumArmies+ " Armies] "), g).getBounds();
			g.setColor(Color.WHITE);
			g2d.fillRect(x+4, y+25, r2.width, r2.height);
			
			g.setColor(PlayerColor);
			
			g2d.drawString("["+NumArmies+ " Armies]", x+4, y+37);
			
			
			





		}

	}
	public static boolean arrayContains(int[] array, Integer item) {
		return Arrays.stream(array).anyMatch(item::equals);
	}

}
