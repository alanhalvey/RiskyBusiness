/*
 * Team Name: RiskyBusiness
 * 
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// put your code here

public class RiskyBusiness implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example

	private BoardAPI board;
	private PlayerAPI player;
	private String Choice = " ";
	private String Choice1 = " ";

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
		boolean switcher = false;

		getCountriesOwned();




		String command = "";
		Random random = new Random();
		int a = random.nextInt(1 - 0 + 1) + 1;
		//System.out.println(reinforcementChoice1() + " "+reinforcementChoice2());
		//System.out.println("a is " +a);
		
		if(reinforcementChoice1() == true ){
			//System.out.println("workdd");
			command = Choice;
		}
		else if(reinforcementChoice2() == true ){
			//System.out.println("did it?");
			command = Choice1;

		}

		/*else{
			command += countryNamesOwned.get((int) (Math.random() * countryNamesOwned.size()));
		}*/
		//command += reinforcementChoice1(); //countryNamesOwned.get((int) (Math.random() * 9));
		//command = command.replaceAll("\\s", "");
		command += " 1";
		return command;
	}

	public Boolean reinforcementChoice1(){
		boolean result = false;

		if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Central America")&& board.getOccupier(getCountryID("Central America"))==player.getId()&& result !=true){
			
			Choice1 =  "Central America";
			System.out.println(Choice1);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Brazil")&& board.getOccupier(getCountryID("Brazil"))==player.getId()&&result!=true){
			System.out.println(Choice1);
			Choice =  "Brazil";
			System.out.println(Choice);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Venezuela")&& board.getOccupier(getCountryID("Venezuela"))==player.getId()&&result!=true){
			System.out.println(Choice1);
			Choice =  "Venezuela";
			System.out.println(Choice);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Argentina")&& board.getOccupier(getCountryID("Argentina"))==player.getId()&&result!=true){
			System.out.println("Argentina");
			Choice =  "Argentina";
			System.out.println(Choice);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Peru")&& board.getOccupier(getCountryID("Peru"))==player.getId()&&result!=true){
			System.out.println("Peru");
			Choice =  "Peru ";
			System.out.println(Choice);
			result = true;
		}


		return result;
	}


	public Boolean reinforcementChoice2(){
		boolean result = false;

		int currentIndex = 0;
		//System.out.println("1"); 
		if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Siam")&& board.getOccupier(getCountryID("Siam"))==player.getId()&& result !=true){
			
			Choice1 =  "Siam";
			System.out.println(Choice1);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("E Australia")&& board.getOccupier(getCountryID("E Australia"))==player.getId()&&result!=true){
			
			Choice1 =  "E Australia";
			System.out.println(Choice1);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("New Guinea")&& board.getOccupier(getCountryID("New Guinea"))==player.getId()&&result!=true){
			
			Choice1 =  "New Guinea";
			System.out.println(Choice1);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("W Australia")&& board.getOccupier(getCountryID("W Australia"))==player.getId()&&result!=true){
			
			Choice1 =  "W Australia";
			System.out.println(Choice1);
			result = true;
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Indonesia")&& board.getOccupier(getCountryID("Indonesia"))==player.getId()&&result!=true){
			
			Choice1 =  "Indonesia";
			System.out.println(Choice1);
			result = true;
		}
		


		return result;
	}
	
	public Boolean reinforcementChoice3(){
		boolean result = false;
		
		
		
		return result;
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
		int k = 1;

		while(count==0){
			bestToAttackWith = owned[owned.length-k][0];
			for(int i=0;i<42;i++){
				if(board.isAdjacent(bestToAttackWith, i) && board.getOccupier(i)!=player.getId()){
					count++;
				}
			}
			k++;

		};

		int [][] adjacents = new int[count][2];
		z=0;
		for(int i=0;i<42;i++){
			if(board.isAdjacent(bestToAttackWith, i) && board.getOccupier(i)!=player.getId()){
				adjacents[z][1] = board.getNumUnits(i);
				adjacents[z][0] = i;
				z++;
			}
		}

		try {
			Thread.sleep(10);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		java.util.Arrays.sort(adjacents, new java.util.Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return Integer.compare(a[1], b[1]);
			}
		});



		int bestToAttack = adjacents[0][0];

		String toAttackWith = GameData.COUNTRY_NAMES[bestToAttackWith];
		String toAttack = GameData.COUNTRY_NAMES[bestToAttack];
		toAttackWith = toAttackWith.replaceAll("\\s", "");
		toAttack = toAttack.replaceAll("\\s", "");

		int armiesLeftInCountryToAttackWith = board.getNumUnits(bestToAttackWith);
		if(armiesLeftInCountryToAttackWith == 1){
			command = "Skip";
		}
		else if(armiesLeftInCountryToAttackWith > 1){
			command = toAttackWith + " "+toAttack + " " + getUnitsToAttackWith(bestToAttackWith);
		}
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
		String command = (board.getNumUnits(attackCountryId)-1)+"";
		// put your code here
		
		return(command);
	}

	public String getFortify () {
		String command = "";
		int tempUnits = 0;
		int tempAverage = 1000;
		int tempEnemyAverage = 1000;
		int currentBestFrom = -1;
		//int goodChoices[] = new int[];
		int count = 0;

		for(int i=0;i<42;i++){
			if(board.getNumUnits(i)>1 && board.getOccupier(i)==player.getId()){
				if(board.getNumUnits(i)>tempUnits /*|| neighbourAverage(i) < tempAverage || neighbourEnemyAverage(i)<tempEnemyAverage*/){
					count++;
				}
			}
		}
		int goodChoices[] = new int[count];
		count =0;
		for(int i=0;i<42;i++){
			if(board.getNumUnits(i)>1 && board.getOccupier(i)==player.getId()){
				if(board.getNumUnits(i)>tempUnits /*&& neighbourAverage(i) < tempAverage  neighbourEnemyAverage(i)<tempEnemyAverage*/){
					tempUnits = board.getNumUnits(i);
					currentBestFrom = i;
					tempAverage = neighbourAverage(i);
					goodChoices[count]=i;
					count++;
				}
			}
		}
		/*
		if(count!=0){
			currentBestFrom = goodChoices[(int)(Math.random() * goodChoices.length)];
			//System.out.println("current nest from "+GameData.COUNTRY_NAMES[currentBestFrom]);
		}
		 */

		int currentBestTo = -1;
		if(currentBestFrom!=-1){
			tempUnits = 1000;
			//System.out.println("current best from " +GameData.COUNTRY_NAMES[currentBestFrom]);
			int count2=0;
			for(int i=0;i<42;i++){
				if( board.getOccupier(i)==player.getId() && board.isAdjacent(i, currentBestFrom)){
					if(board.getNumUnits(i)<tempUnits){
						tempUnits = board.getNumUnits(i);
						count2++;
					}
				}
			}
			int choices[] = new int[count2];
			int k = 0;
			tempUnits = 1000;
			for(int i=0;i<42;i++){
				if(board.getOccupier(i)==player.getId() && board.isAdjacent(i, currentBestFrom)){
					if(board.getNumUnits(i)<tempUnits){
						tempUnits = board.getNumUnits(i);
						currentBestTo = i;
						choices[k] = i;
						k++;
					}

				}
			}
			//System.out.print("choices: ");
			for(int i =0;i<choices.length;i++){
				//System.out.print(GameData.COUNTRY_NAMES[choices[i]] +" ");
				for(int z=0;z<GameData.ADJACENT[choices[i]].length;z++){
					if(board.getOccupier(GameData.ADJACENT[choices[i]][z])!=player.getId()){
						if(choices[i]!=0){
							currentBestTo=choices[i];
						}
					}
				}
			}
			//System.out.println();

			// put code here
			if(currentBestTo!=-1 && board.getNumUnits(currentBestFrom)>3){
				String toFortifyFrom = GameData.COUNTRY_NAMES[currentBestFrom];
				String toFortifyTo= GameData.COUNTRY_NAMES[currentBestTo];
				toFortifyFrom = toFortifyFrom.replaceAll("\\s", "");
				toFortifyTo = toFortifyTo.replaceAll("\\s", "");
				command = toFortifyFrom + " "+ toFortifyTo + " "+((board.getNumUnits(currentBestFrom)/2)+1);
			}
			else{
				command = "skip";
			}
		}
		else{
			command = "skip";
		}
		return(command);
	}

	private int neighbourEnemyAverage(int i) {
		int temp[][] =  new int[10][2];
		int tempMin = 1000;
		int total = -1;
		int average = -1;
		//System.out.println(GameData.ADJACENT[i].length);
		for (int k=0;k<GameData.ADJACENT[i].length;k++){
			if(board.getOccupier(GameData.ADJACENT[i][k])==player.getId()){
				total = 0;
				for(int z =0;z<GameData.ADJACENT[GameData.ADJACENT[i][k]].length;z++){
					if(board.getOccupier(GameData.ADJACENT[GameData.ADJACENT[i][k]][z])!=player.getId()){
						temp[z][0] = GameData.ADJACENT[GameData.ADJACENT[i][k]][z];
						total += board.getNumUnits(GameData.ADJACENT[GameData.ADJACENT[i][k]][z]);
					}
				}
				if(total/GameData.ADJACENT[GameData.ADJACENT[i][k]].length < tempMin){
					tempMin = total/GameData.ADJACENT[GameData.ADJACENT[i][k]].length;
				}
			}

		}
		if(tempMin==0){
			return 1000;
		}
		else{
			return tempMin;
		}
	}

	private int neighbourAverage(int i) {
		int totalunitsneighbours=0;

		for(int k=0;k<GameData.ADJACENT[i].length;k++){
			totalunitsneighbours+=board.getNumUnits(k);
		}
		return totalunitsneighbours/GameData.ADJACENT[i].length;
	}

	public void getCountriesOwned() {

		String command = "";

		for(int i=0; i<42; i++){

			//System.out.println("" + board.getOccupier(i));
			if(player.getId() == (board.getOccupier(i))){
				countryIDsOwned.add(i);
				countryNamesOwned.add(GameData.COUNTRY_NAMES[i]);
			}
		}


		//System.out.println(countryNamesOwned + " - " + countryIDsOwned);

	}

	private int getCountryID(String countryName){

		int countryID = 0;
		for(int i=0; i<42; i++){
			if(countryName == GameData.COUNTRY_NAMES[i]){
				countryID = i;
			}
		}

		return countryID;
	}

	private int getUnitsToAttackWith(int i) {

		//int id = getCountryID(toAttackWith);
		int unitsToAttackWith = 0;

		if(board.getNumUnits(i) > 3){
			//System.out.println(board.getNumUnits(i));
			unitsToAttackWith = 3;
		}
		else if(board.getNumUnits(i)==3){
			unitsToAttackWith = 2;
		}
		else{
			unitsToAttackWith = 1;
		}


		return unitsToAttackWith;
	}
}