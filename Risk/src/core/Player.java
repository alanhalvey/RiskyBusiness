/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;

public class Player {
	String playerName;
	Color playerColor;
	int numTerritories;
	int numReinforcements;
	boolean fortified = true;
	
	public Player(String playerName, Color playerColor, int numTerritories, int numReinforcements, boolean fortified ){
		this.playerName = playerName;
		this.playerColor = playerColor;
		this.numTerritories = numTerritories;
		this.numReinforcements = numReinforcements;
		this.fortified = fortified;
	}	
	
	public void setnumTerritories(){
		this.numTerritories = numTerritories;
	}
	
	public int getnumTerritories(){
		return numTerritories;
	}
}