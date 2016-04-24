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
	private String Choice = "";
	private String Choice1 = "";
	private String Choice2 = "";
	private String Choice3= "";
	private String prevFortify = "";
	private int changeReinforce = 0;
	private int changeStuff = 0;

	ArrayList<Integer> countryIDsOwned = new ArrayList<Integer>();
	ArrayList<String> countryNamesOwned = new ArrayList<String>();

	ArrayList<Integer> continentIDsOwned = new ArrayList<Integer>();
	ArrayList<String> continentNamesOwned = new ArrayList<String>();


	RiskyBusiness (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
		return;
	}

	public String getName () {
		String command = "";
		// put your code here

		command = "ALBOT HITLER";
		return(command);
	}

	public String getReinforcement () {
		boolean switcher = false;

		getCountriesOwned();
		System.out.println(countryNamesOwned);

		String command = "";
		Random random = new Random();
		int a = random.nextInt(1 - 0 + 1) + 1;

		if(reinforcementChoice7()==true){
			command = Choice;
		}
		else if(reinforcementChoice1() == true ){
			//System.out.println("workdd");
			command = Choice;
		}
		else if(reinforcementChoice2() == true ){
			//System.out.println("did it?");
			command = Choice1;

		}
		else if (reinforcementChoice3() == true){
			command = Choice2;
		}
		else if (reinforcementChoice4() == true){
			command = Choice3;
		}

		command = command.replaceAll("\\s", "");
		if(player.getNumUnits()>3){
			command += " " + 3;			
		}
		else{
			command += " " + 1;
		}
		return command;
	}

	private boolean reinforcementChoice7(){
		boolean result = false;

		getCountriesOwned();
		System.out.println(countryNamesOwned);

		getContinentsOwned();
		System.out.println(continentNamesOwned);

		if(continentIDsOwned.contains(0) && continentIDsOwned.contains(4) && continentIDsOwned.contains(1) && continentIDsOwned.contains(5) && continentIDsOwned.contains(2)){
			Choice = "Siam";
			result = true;
		}
		
		//if you own north america, south america, europe, and africa
		else if(continentIDsOwned.contains(0) && continentIDsOwned.contains(4) && continentIDsOwned.contains(1) && continentIDsOwned.contains(5)){

			if(changeReinforce == 0){
				if(countryNamesOwned.contains("India")){
					if(countryNamesOwned.contains("Siam")){
						Choice = "Siam";
					}
					else{
						Choice = "India";
					}
				}
				else{
					Choice = "Middle East";
				}
				changeReinforce++;
			}

			else if(changeReinforce == 1){
				String var1 = "Ukraine";
				String var2 = "E Africa";
				if(changeStuff==0){
					Choice = var1;
					changeStuff++;
				}
				else{
					Choice = var2;
					changeStuff=0;
				}
				changeReinforce++;
			}
			else if(changeReinforce == 2){
				if(countryNamesOwned.contains("Kamchatka")){
					if(countryNamesOwned.contains("Mongolia")){
						Choice = "Mongolia";
					}
					else{
						Choice = "Kamchatka";
					}
				}
				else{
					Choice = "Alaska";
				}
				changeReinforce=0;
			}
			result=true;
		}
		
		//if you own north america, south america, and europe
		else if(continentIDsOwned.contains(0) && continentIDsOwned.contains(4) && continentIDsOwned.contains(1)){

			if(changeReinforce == 0){
				if(countryNamesOwned.contains("Middle East")){
					Choice = "Middle East";
				}
				else{
					Choice = "S Europe";
				}
				changeReinforce++;
			}
			else if(changeReinforce == 1){
				Choice = "Ukraine";
				changeReinforce++;
			}
			else if(changeReinforce == 2){
				if(countryNamesOwned.contains("N Africa")){
					Choice = "N Africa";
				}
				else{
					Choice = "W Europe";	
				}
				changeReinforce=0;
			}
			result=true;
		}
		
		//if you own north and south america
		else if(continentIDsOwned.contains(0) && continentIDsOwned.contains(4)){
			if(changeReinforce == 0){
				if(countryNamesOwned.contains("N Africa")){
					Choice = "N Africa";
				}
				else{
					Choice = "Brazil";
				}
				changeReinforce++;
			}
			else if(changeReinforce == 1){
				if(countryNamesOwned.contains("Iceland")){
					Choice = "Iceland";
				}
				else{
					Choice = "Greenland";
				}
				changeReinforce++;
			}
			else if(changeReinforce == 2){
				if(countryNamesOwned.contains("Kamchatka")){
					Choice = "Kamchatka";
				}
				else{
					Choice = "Alaska";	
				}
				changeReinforce=0;
			}
			result=true;
		}
		
		//if you own south america and central america (the country)
		else if(continentIDsOwned.contains(4) && countryNamesOwned.contains("CentralAmerica")){

			if(changeReinforce==0){
				if(countryNamesOwned.contains("N Africa")){
					Choice = "N Africa";
				}
				else{
					Choice = "Brazil";
				}
				changeReinforce++;
			}
			else if(changeReinforce==1){
				if(countryNamesOwned.contains("W United States") && countryNamesOwned.contains("E United States")){
					String var1 = "W United States";
					String var2 = "E United States";
					if(changeStuff==0){
						Choice = var1;
						changeStuff++;
					}
					else{
						Choice = var2;
						changeStuff=0;
					}
				}
				else{
					Choice = "CentralAmerica";
				}

				changeReinforce=0;
			}
			result=true;
		}
		//if you own north america
		else if(continentIDsOwned.contains(0)){
			if(changeReinforce==0){
				if(countryNamesOwned.contains("Venezuela")){
					Choice = "Venezuela";
				}
				else{
					Choice = "Central America";
				}
				changeReinforce++;
			}
			else if(changeReinforce==1){
				if(countryNamesOwned.contains("Iceland")){
					Choice = "Iceland";
				}
				else{
					Choice = "Greenland";
				}
				changeReinforce++;
			}
			else if(changeReinforce==2){
				if(countryNamesOwned.contains("Kamchatka")){
					Choice = "Kamchatka";
				}
				else{
					Choice = "Alaska";
				}
				changeReinforce=0;
			}
			result=true;
		}

		return result;
	}

	private void getContinentsOwned(){

		continentNamesOwned.clear();
		continentIDsOwned.clear();

		if(countryIDsOwned.contains(0)&&countryIDsOwned.contains(1)&&countryIDsOwned.contains(2)&&countryIDsOwned.contains(3)&&countryIDsOwned.contains(4)&&countryIDsOwned.contains(5)&&countryIDsOwned.contains(6)&&countryIDsOwned.contains(7)&&countryIDsOwned.contains(8)){
			continentNamesOwned.add("North America");
			continentIDsOwned.add(0);
		}
		if(countryIDsOwned.contains(9)&&countryIDsOwned.contains(10)&&countryIDsOwned.contains(11)&&countryIDsOwned.contains(12)&&countryIDsOwned.contains(13)&&countryIDsOwned.contains(14)&&countryIDsOwned.contains(15)){
			continentNamesOwned.add("Europe");
			continentIDsOwned.add(1);
		}
		if(countryIDsOwned.contains(16)&&countryIDsOwned.contains(17)&&countryIDsOwned.contains(18)&&countryIDsOwned.contains(19)&&countryIDsOwned.contains(20)&&countryIDsOwned.contains(21)&&countryIDsOwned.contains(22)&&countryIDsOwned.contains(23)&&countryIDsOwned.contains(24)&&countryIDsOwned.contains(25)&&countryIDsOwned.contains(26)&&countryIDsOwned.contains(27)){
			continentNamesOwned.add("Asia");
			continentIDsOwned.add(2);
		}
		if(countryIDsOwned.contains(28)&&countryIDsOwned.contains(29)&&countryIDsOwned.contains(30)&&countryIDsOwned.contains(31)){
			continentNamesOwned.add("Australia");
			continentIDsOwned.add(3);
		}
		if(countryIDsOwned.contains(32)&&countryIDsOwned.contains(33)&&countryIDsOwned.contains(34)&&countryIDsOwned.contains(35)){
			continentNamesOwned.add("South America");
			continentIDsOwned.add(4);
		}
		if(countryIDsOwned.contains(36)&&countryIDsOwned.contains(37)&&countryIDsOwned.contains(38)&&countryIDsOwned.contains(39)&&countryIDsOwned.contains(40)&&countryIDsOwned.contains(41)){
			continentNamesOwned.add("Africa");
			continentIDsOwned.add(5);
		}
	}

	private boolean reinforcementChoice1(){
		boolean result = false;

		if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Central America")&& board.getOccupier(getCountryID("Central America"))==player.getId()&& result !=true ){
			if(board.getNumUnits(7)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(7, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice =  "Central America";
				System.out.println(Choice);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Brazil")&& board.getOccupier(getCountryID("Brazil"))==player.getId() && result!=true){
			if(board.getNumUnits(34)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(34, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice =  "Brazil";
				System.out.println(Choice);
				result = true;
			}
		}

		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Venezuela")&& board.getOccupier(getCountryID("Venezuela"))==player.getId() &&result!=true){
			if(board.getNumUnits(32)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(32, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice =  "Venezuela";
				System.out.println(Choice);
				result = true;
			}
		}

		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Argentina")&& board.getOccupier(getCountryID("Argentina"))==player.getId()&&result!=true){
			if(board.getNumUnits(35)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(35, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice =  "Argentina";
				System.out.println(Choice);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Peru")&& board.getOccupier(getCountryID("Peru"))==player.getId() &&result!=true){
			if(board.getNumUnits(33)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(33, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice =  "Peru";
				System.out.println(Choice);
				result = true;
			}
		}


		return result;
	}

	private boolean reinforcementChoice2(){
		boolean result = false;

		int currentIndex = 0;
		//System.out.println("1"); 
		if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Siam")&& board.getOccupier(getCountryID("Siam"))==player.getId()&&  result !=true){
			if(board.getNumUnits(22)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(22, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice1 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice1 =  "Siam";
				System.out.println(Choice1);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("E Australia")&& board.getOccupier(getCountryID("E Australia"))==player.getId() &&result!=true){
			if(board.getNumUnits(28)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(28, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice1 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice1 =  "E Australia";
				System.out.println(Choice1);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("New Guinea")&& board.getOccupier(getCountryID("New Guinea"))==player.getId() && board.getNumUnits(33)<15 &&result!=true){
			if(board.getNumUnits(29)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(29, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice1 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice1 =  "New Guinea";
				System.out.println(Choice1);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("W Australia")&& board.getOccupier(getCountryID("W Australia"))==player.getId()&&result!=true){
			if(board.getNumUnits(30)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(30, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice1 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice1 =  "W Australia";
				System.out.println(Choice1);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Indonesia")&& board.getOccupier(getCountryID("Indonesia"))==player.getId() &&result!=true){
			if(board.getNumUnits(31)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(31, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice1 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice1 =  "Indonesia";
				System.out.println(Choice1);
				result = true;
			}
		}



		return result;
	}

	private boolean reinforcementChoice3(){
		boolean result = false;

		if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Kamchatka")&& board.getOccupier(getCountryID("Kamchatka"))==player.getId() && result !=true){
			if(board.getNumUnits(21)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(21, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice2 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice2 =  "Kamchatka";
				System.out.println(Choice2);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Greenland")&& board.getOccupier(getCountryID("Greenland"))==player.getId() &&result!=true){
			if(board.getNumUnits(4)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(4, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice2 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice2 =  "Greenland";
				System.out.println(Choice2);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Alaska")&& board.getOccupier(getCountryID("Alaska"))==player.getId() &&result!=true){
			if(board.getNumUnits(8)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(8, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice2 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice2 =  "Alaska";
				System.out.println(Choice2);
				result = true;
			}
		}
		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("Middle East")&& board.getOccupier(getCountryID("Middle East"))==player.getId() &&result!=true){
			if(board.getNumUnits(18)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(18, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice2 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice2 =  "Middle East";
				System.out.println(Choice2);
				result = true;
			}
		}


		else if(Arrays.asList(GameData.COUNTRY_NAMES).contains("China")&& board.getOccupier(getCountryID("China"))==player.getId()  &&result!=true){
			if(board.getNumUnits(27)>15){
				for (int i=0; i<GameData.NUM_COUNTRIES;i++) {
					if(board.isAdjacent(27, i) && board.getOccupier(i)== player.getId()){
						System.out.println("got to here");
						Choice2 = GameData.COUNTRY_NAMES[i];
						result = true;
					}
				}
			}
			else{
				Choice2 =  "China";
				System.out.println(Choice2);
				result = true;
			}
		}


		return result;
	}
	private boolean reinforcementChoice4(){
		boolean result = false;

		for(int i=0;i<GameData.NUM_COUNTRIES;i++){
			if(board.getOccupier(i) == player.getId()){
				Choice3 = GameData.COUNTRY_NAMES[i];
				result = true;
			}

		}

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
		if(player.getCards().size()>4){
			ArrayList<Card> a = player.getCards();
			int in=0,cav=0,art = 0,wild=0;
			for(int i=0;i<a.size();i++){
				System.out.println("Cards: "+a.get(i).getInsigniaId() + " " + a.get(i).getInsigniaName());
				if(a.get(i).getInsigniaId()==0){
					in++;
				}
				if(a.get(i).getInsigniaId()==1){
					cav++;
				}
				if(a.get(i).getInsigniaId()==2){
					art++;
				}
				if(a.get(i).getInsigniaId()==3){
					wild++;
				}
			}
			if(in>=3){
				command = "iii";
			}
			if(cav>=3){
				command = "ccc";
			}
			if(art>=3){
				command = "aaa";
			}
			if(wild>=3){
				command = "www";
			}
			if(art>0&&cav>0&&in>0){
				command = "aic";
			}
			if(art>0&&cav>0&&wild>0){
				command = "acw";
			}
			if(art>0&&in>0&&wild>0){
				command = "aiw";
			}
			if(cav>0&&in>0&&wild>0){
				command = "ciw";
			}
			System.out.println("command is "+ command);
		}
		else if (player.getCards().size()<5){
			command = "skip";
		}
		System.out.println(player.getCards().size());
		return(command);
	}

	public String getBattle () {
		String command = "";
		try {
			Thread.sleep(10);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
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
		if((armiesLeftInCountryToAttackWith < 3) || ((armiesLeftInCountryToAttackWith/1.4)<board.getNumUnits(bestToAttack))){
			System.out.println(" = " + armiesLeftInCountryToAttackWith/1.4);
			command = "Skip";
		}
		else if(armiesLeftInCountryToAttackWith >= 3){
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

		String command = ((int)(board.getNumUnits(attackCountryId)-1))+"";

		if(getNumberOfOpposingNeighbours(attackCountryId)==0){	
			int var = 0;
			if (player.getNumUnits()>10){
				var = 3;
			}
			else{
				var = 1;
			}
			command = ((int)(board.getNumUnits(attackCountryId)-var))+"";
		}
		else if(getNumberOfOpposingNeighbours(attackCountryId)==1){	
			command = ((int)(board.getNumUnits(attackCountryId))/2)+"";
		}
		else if(getNumberOfOpposingNeighbours(attackCountryId)==2){	
			command = ((int)(board.getNumUnits(attackCountryId))/3)+"";
		}
		else if(getNumberOfOpposingNeighbours(attackCountryId)==3){	
			command = ((int)(board.getNumUnits(attackCountryId))/4)+"";
		}
		else if(getNumberOfOpposingNeighbours(attackCountryId)==2){	
			command = "0";
		}

		return(command);
	}

	private int getNumberOfOpposingNeighbours(int attackingCountryId){
		int numberOfOpposingNeighbours=0;

		for(int k=0;k<GameData.ADJACENT[attackingCountryId].length;k++){
			if(board.getOccupier(GameData.ADJACENT[attackingCountryId][k])!=player.getId()){
				numberOfOpposingNeighbours++;
			}
		}
		return numberOfOpposingNeighbours;
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

			if(currentBestTo!=-1 && getNumberOfOpposingNeighbours(currentBestFrom)==0){
				String toFortifyFrom = GameData.COUNTRY_NAMES[currentBestFrom];
				String toFortifyTo= GameData.COUNTRY_NAMES[currentBestTo];
				toFortifyFrom = toFortifyFrom.replaceAll("\\s", "");
				toFortifyTo = toFortifyTo.replaceAll("\\s", "");

				if(prevFortify==toFortifyTo){
					command = toFortifyFrom + " "+ toFortifyTo + " "+((board.getNumUnits(currentBestFrom))/4);
				}
				else{
					command = toFortifyFrom + " "+ toFortifyTo + " "+((board.getNumUnits(currentBestFrom))-1);
				}
				prevFortify=toFortifyFrom;
			}
			else if(currentBestTo!=-1 && board.getNumUnits(currentBestFrom)>3){
				String toFortifyFrom = GameData.COUNTRY_NAMES[currentBestFrom];
				String toFortifyTo= GameData.COUNTRY_NAMES[currentBestTo];
				toFortifyFrom = toFortifyFrom.replaceAll("\\s", "");
				toFortifyTo = toFortifyTo.replaceAll("\\s", "");
				command = toFortifyFrom + " "+ toFortifyTo + " "+((board.getNumUnits(currentBestFrom)/2)+1);
				prevFortify=toFortifyFrom;
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

	private void getCountriesOwned() {

		countryNamesOwned.clear();
		countryIDsOwned.clear();

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