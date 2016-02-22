package core;

public class Country {
	private String name;
	private String occupyingPlayer;
	private int playerArmies;
	private int X_Coordinate;
	private int Y_Coordinate;
	private int Adjacent[];
	private int continent;
	
	public Country(String name, String occupyingPlayer, int playerArmies,int x, int y, int[] Adjacent, int continentIds){
		this.setName(name);
		this.setOccupyingPlayer(occupyingPlayer);
		this.setPlayerArmies(playerArmies);
		this.setX_Coordinate(x);
		this.setY_Coordinate(y);
		this.Adjacent = Adjacent;
		this.setContinent(continentIds);
	}

	public Country(Country country) {
		this.name = country.name;
		this.occupyingPlayer = country.occupyingPlayer;
		this.playerArmies = country.playerArmies;
		this.setX_Coordinate(country.getX_Coordinate());
		this.setY_Coordinate(country.getY_Coordinate());
		this.Adjacent=country.Adjacent;
		this.continent = country.continent;
	}

	public String getOccupyingPlayer() {
		return occupyingPlayer;
	}

	public void setOccupyingPlayer(String occupyingPlayer) {
		this.occupyingPlayer = occupyingPlayer;
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
