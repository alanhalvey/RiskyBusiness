/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;
import java.awt.Color;

public class Country {
	private String name;
	private Player occupyingPlayer;
	private int playerArmies;
	private int X_Coordinate;
	private int Y_Coordinate;
	private int Adjacent[];
	private int continent;
	private String abbreviation;
	
	public Country(String name, Player occupyingPlayer, int playerArmies,int x, int y, int[] Adjacent, int continentIds, String abbreviation){
		this.setName(name);
		this.setOccupyingPlayer(occupyingPlayer);
		this.setPlayerArmies(playerArmies);
		this.setX_Coordinate(x);
		this.setY_Coordinate(y);
		this.Adjacent = Adjacent;
		this.setContinent(continentIds);
		this.abbreviation = abbreviation;
	}

	public Country(Country country) {
		this.name = country.name;
		this.occupyingPlayer = country.occupyingPlayer;
		this.playerArmies = country.playerArmies;
		this.setX_Coordinate(country.getX_Coordinate());
		this.setY_Coordinate(country.getY_Coordinate());
		this.Adjacent=country.Adjacent;
		this.continent = country.continent;
		this.abbreviation = abbreviation;
	}

	public Player getOccupyingPlayer() {
		return occupyingPlayer;
	}

	public void setOccupyingPlayer(Player occupyingPlayer2) {
		this.occupyingPlayer = occupyingPlayer2;
	}

	public int getPlayerArmies() {
		return playerArmies;
	}

	public void setPlayerArmies(int playerArmies) {
		this.playerArmies = playerArmies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX_Coordinate() {
		return X_Coordinate;
	}

	public void setX_Coordinate(int x_Coordinate) {
		X_Coordinate = x_Coordinate;
	}

	public int getY_Coordinate() {
		return Y_Coordinate;
	}

	public void setY_Coordinate(int y_Coordinate) {
		Y_Coordinate = y_Coordinate;
	}

	public int getContinent() {
		return continent;
	}

	public void setContinent(int continent) {
		this.continent = continent;
	}	
}