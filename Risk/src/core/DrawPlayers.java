package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class DrawPlayers extends JPanel {

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);

		for (int i = 0; i<42;i++){
			boolean Player1 = Map.arrayContains(Data.PLAYER_1_COUNTRIES, i);
			boolean Player2 = Map.arrayContains(Data.PLAYER_2_COUNTRIES, i);
			boolean Neutral1 = Map.arrayContains(Data.NEUTRAL_1_COUNTRIES, i);
			boolean Neutral2 = Map.arrayContains(Data.NEUTRAL_2_COUNTRIES, i);
			boolean Neutral3 = Map.arrayContains(Data.NEUTRAL_3_COUNTRIES, i);
			boolean Neutral4 = Map.arrayContains(Data.NEUTRAL_4_COUNTRIES, i);





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
			int x = Data.getCountryCoord()[i][0];
			int y = Data.getCountryCoord()[i][1];
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

	

}

