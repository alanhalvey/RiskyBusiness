/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 */
package core;

import java.awt.Color;

class Dice {
	private int faceValue;

	public void roll() {
		faceValue = 1 + (int) (Math.random() * 6.0);
	}

	public int value() {
		return faceValue;
	}

	public Dice() {
		roll();
	}
} 

//DiceRoll class allows the random player generator at the beginning of the game
//It is also a crucial factor in deciding battles within the combat function, the risk/luck feature in the game.
public class DiceRoll {

	static int i;
	static String string1 = "-1";
	static String string2 = "-1";

	private static Dice Player1Roll = new Dice();
	private static Dice Player2Roll = new Dice();

	private static Dice AttackerRoll = new Dice();
	private static Dice DefenderRoll = new Dice();

	private static int[] AttackerRolls = new int[3]; //Attacker has 3 rolls max
	private static int[] DefenderRolls = new int[2]; //Defender has 2 rolls max
	private static String tempRolls = new String();

	//Rolls a dice for each player (used at the start of the game deciding which player to go first)
	public void rollDice(){
		Player1Roll.roll();
		Player2Roll.roll();
	}

	//Return a string regarding winner/loser of dice roll.
	public String rollWinnerOutcome(){
		if(Player1Roll.value() > Player2Roll.value()){
			return "1";
		}
		if(Player1Roll.value() < Player2Roll.value()){
			return "2";
		}
		else{
			return "3";
		}	
	}

	//getter for each player roll
	public static int getPlayer1RollValue(){
		return Player1Roll.value();
	}
	public static int getPlayer2RollValue(){		
		return Player2Roll.value();
	}

	//getter for attacker and defender player rolls
	public static int getAttackerRollValue(){
		return AttackerRoll.value();
	}
	public static int getDefenderRollValue(){
		return DefenderRoll.value();
	}

	//main dice roll function used in the combat function.
	public static void combatDiceRoll(String currentPlayer, String attackingPlayer, String defendingPlayer, int numberOfUnitsToAttackWith, int numberOfUnitsToDefendWith){

		string1 = "-1";
		string2 = "-1";		
		int attackerHighestRoll = 0; //holds the attackers highest roll
		int defenderHighestRoll = 0; //holds the defenders highest roll

		int attackerSecondHighestRoll = 0; // holds the attackers second highest roll
		int defenderSecondHighestRoll = 0; // holds the defenders second highest roll
		
		@SuppressWarnings("unused")
		Color attackingPlayerColour = Combat.attackingPlayerColour;
		@SuppressWarnings("unused")
		Color defendingPlayerColour = Combat.defendingPlayerColour;

		//Set the attacking player and their respective colour. 
		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
		}
		if(currentPlayer == CommandInput.player2){
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;
		}

		//while 'i' is less than the user input for number of units to attack with
		for(i=0; i<numberOfUnitsToAttackWith; i++){ 
			AttackerRoll.roll(); 		
			AttackerRolls[i] = getAttackerRollValue();

			if(i==0){ //if it is the first roll, then that roll is the highest roll.
				attackerHighestRoll = AttackerRolls[i];
				tempRolls = "[" + AttackerRolls[i];
			}
			else if(AttackerRolls[i] > attackerHighestRoll){ //if the latest roll is greater than the highest roll, the previous roll becomes the 2nd highest roll, and the latest roll becomes the highest roll
				attackerSecondHighestRoll = attackerHighestRoll;
				attackerHighestRoll = AttackerRolls[i];
			}
			else if((AttackerRolls[i] <= attackerHighestRoll) && (attackerSecondHighestRoll == 0)){ //if the latest roll is <= to the highest roll and the 2nd highest roll has not been given a value yet, then give it the latest value
				attackerSecondHighestRoll = AttackerRolls[i];
			}
			else if((AttackerRolls[i] > attackerSecondHighestRoll) && (AttackerRolls[i] <= attackerHighestRoll)){ //If the latest roll is > 2nd highest roll & latest roll <= highest roll, then the latest roll becomes the 2nd highest roll
				attackerSecondHighestRoll = AttackerRolls[i];
			}
		}

		for(i=1; i<numberOfUnitsToAttackWith; i++){
			tempRolls += ", " + AttackerRolls[i];
		}
		CommandInput.appendStringTo(attackingPlayer + " rolled " + tempRolls + "]\n", Color.BLACK);		

		System.out.println("");
		for(i=0; i<numberOfUnitsToDefendWith; i++){
			DefenderRoll.roll();
			DefenderRolls[i] = getDefenderRollValue();

			//if else statements are slightly simpler in defenders case as there is only two rolls
			if(i==0){ //if it is the first roll, then that roll is the highest roll.
				defenderHighestRoll = DefenderRolls[i];
				tempRolls = "[" + DefenderRolls[i];
			}
			else if(DefenderRolls[i] > defenderHighestRoll){ //if the latest roll is greater than the highest roll, the previous roll becomes the 2nd highest roll, and the latest roll becomes the highest roll
				defenderSecondHighestRoll = defenderHighestRoll;
				defenderHighestRoll = DefenderRolls[i];
			}
			else if(DefenderRolls[i] <= defenderHighestRoll){ //If the latest roll is <= to the highest roll, latest roll becomes the 2nd highest roll.
				defenderSecondHighestRoll = DefenderRolls[i];
			}
		}

		for(i=1; i<numberOfUnitsToDefendWith; i++){
			tempRolls += ", " + DefenderRolls[i];
		}
		CommandInput.appendStringTo(Combat.defendingPlayerString + " rolled " + tempRolls + "]\n", Color.BLACK);		

		System.out.println("ah = " + attackerHighestRoll);
		System.out.println("ash = " + attackerSecondHighestRoll);
		System.out.println("dh = " + defenderHighestRoll);
		System.out.println("dsh = " + defenderSecondHighestRoll);

		/*CommandInput.appendStringTo(attackingPlayer + " highest roll = " + attackerHighestRoll  + "\n", attackingPlayerColour);
		if(attackerSecondHighestRoll != 0){ //if there was a second roll for the attacker
			CommandInput.appendStringTo(attackingPlayer + " second highest roll = " + attackerSecondHighestRoll  + "\n", attackingPlayerColour);
		}

		CommandInput.appendStringTo(Gameplay.defendingPlayerString + " highest roll = " + defenderHighestRoll + "\n", defendingPlayerColour);
		if(defenderSecondHighestRoll != 0){ //if there was a second roll for the defender
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " second highest roll = " + defenderSecondHighestRoll  + "\n", defendingPlayerColour);
		}*/
		 		
		highestDiceRollWinner(attackerHighestRoll, defenderHighestRoll);
		if((attackerSecondHighestRoll > 0) && (defenderSecondHighestRoll > 0)){ //If both players had a second roll, then the second highest rolls are compared and a winner given
			secondDiceRollWinner(attackerSecondHighestRoll, defenderSecondHighestRoll);
		}

		/*if(string1 == "0"){
			CommandInput.appendStringTo(attackingPlayer + " wins highest dice roll combat" + "\n", Color.BLACK);		
			}
		if(string1 == "1"){
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " wins highest dice roll combat" + "\n", Color.BLACK);
		}
		if(string2 == "0"){
			CommandInput.appendStringTo(attackingPlayer + " wins second highest dice roll combat" + "\n", Color.BLACK);		
			}
		if(string2 == "1"){
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " wins second highest dice roll combat" + "\n", Color.BLACK);
		}
		if(string2 == "-1"){
			CommandInput.appendStringTo("There was no second roll for each player" + "\n", Color.BLACK);
		}*/
	}

	public static String highestDiceRollWinner(int attackerHighestRoll, int defenderHighestRoll){ //attacker and defender highest rolls compared

		//attackerHighestRoll = 6;

		if(attackerHighestRoll > defenderHighestRoll){
			string1 = "0";
		}
		if(attackerHighestRoll <= defenderHighestRoll){
			string1 = "1";
		}
		return string1;
	}

	public static String secondDiceRollWinner(int attackerSecondHighestRoll, int defenderSecondHighestRoll){ //attacker and defender 2nd highest rolls compared

		if(attackerSecondHighestRoll > defenderSecondHighestRoll){
			string2 = "0";
		}
		if(attackerSecondHighestRoll <= defenderSecondHighestRoll){
			string2 = "1";
		}	

		return string2;	
	}
}