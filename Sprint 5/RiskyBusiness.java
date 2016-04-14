import java.util.ArrayList;

// put your code here



public class RiskyBusiness implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example
	
	private BoardAPI board;
	private PlayerAPI player;
	
	ArrayList<Integer> countryIDsOwned = new ArrayList<Integer>();
	ArrayList<String> countryNamesOwned = new ArrayList<String>();

	
	RiskyBusiness (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
		return;
	}
	
	public String getName () {
		String command = "";
		// put your code here
		command = "BOT";
		return(command);
	}

	static int i=0;
	public String getReinforcement () {
		
		if(i==0){
			getCountriesOwned();
		}
		i++;
		
		
		String command = "";
		// put your code here

		command += countryNamesOwned.get((int) (Math.random() * 9));
		command = command.replaceAll("\\s", "");
		command += " 1";
		return(command);
	}
	
	public String getPlacement (int forPlayer) {
		String command = "";
		// put your code here
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		return(command);
	}
	
	public String getCardExchange () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}

	public String getBattle () {
		String command = "";
		// put your code here
		
		command += countryNamesOwned.get((int) (Math.random() * 9));
		command += " " + GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)] + " ";
		command += "2";
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		command = "1";
		return(command);
	}

	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
		command = "0";
		return(command);
	}

	public String getFortify () {
		String command = "";
		// put code here
		command = "skip";
		return(command);
	}

	public void getCountriesOwned() {
		
		String command = "";

		for(int i=0; i<42; i++){
			
			System.out.println("" + board.getOccupier(i));
			if(player.getId() == (board.getOccupier(i))){
				countryIDsOwned.add(i);
				countryNamesOwned.add(GameData.COUNTRY_NAMES[i]);
			}
		}
		
		
		System.out.println(countryNamesOwned + " - " + countryIDsOwned);
		
	}
}
