package core;

public class Country {
	private String name;
	private String occupyingPlayer;
	private int playerArmies;
	
	public Country(String name, String occupyingPlayer, int playerArmies){
		this.name = name;
		this.setOccupyingPlayer(occupyingPlayer);
		this.setPlayerArmies(playerArmies);
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
}
