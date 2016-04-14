/*
 * Team Name: RiskyBusiness
 * 
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */

import java.util.ArrayList;
import java.util.Arrays;

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

	public String getReinforcement () {


		getCountriesOwned();



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
		//get adjacents;
		int numCountriesOwned = 0;
		for(int i=0;i<42;i++){
			if(board.getOccupier(i)==player.getId()){
				 numCountriesOwned++;
			}
		}
		
		int [][] owned = new int[numCountriesOwned][2];
		int z = 0;
		for(int i=0;i<42;i++){
			if(board.getOccupier(i)==player.getId()){
				owned[z][1] = board.getNumUnits(i);
				owned[z][0] = i;
				z++;
			}
		}
		java.util.Arrays.sort(owned, new java.util.Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return Integer.compare(a[1], b[1]);
			}
		});
		
		int bestToAttackWith = owned[owned.length-1][0];
		
		
		
		
		
		int count = 0;
		for(int i=0;i<42;i++){
			if(board.isAdjacent(bestToAttackWith, i) && board.getOccupier(i)!=player.getId()){
				count++;
			}
		}
		int [][] adjacents = new int[count][2];
		z=0;
		for(int i=0;i<42;i++){
			if(board.isAdjacent(bestToAttackWith, i) && board.getOccupier(i)!=player.getId()){
				adjacents[z][1] = board.getNumUnits(i);
				adjacents[z][0] = i;
				z++;
			}
		}


		java.util.Arrays.sort(adjacents, new java.util.Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return Integer.compare(a[1], b[1]);
			}
		});


		for(int i = 0;i<adjacents.length;i++){
			System.out.println(adjacents[i][0]);
		}
		int bestToAttack = adjacents[0][0];


		String toAttackWith = GameData.COUNTRY_NAMES[bestToAttackWith];
		String toAttack = GameData.COUNTRY_NAMES[bestToAttack];
		System.out.println(toAttackWith  + " "+ toAttack);
		toAttackWith = toAttackWith.replaceAll("\\s", "");
		toAttack = toAttack.replaceAll("\\s", "");


		command = toAttackWith + " "+toAttack + " " + 3;
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here

		if(board.getNumUnits(countryId)>2){
			command = "2";
		}
		else{
			command = "1";
		}

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
