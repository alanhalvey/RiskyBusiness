/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
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

public class DiceRoll {
	
	static int i;
	static String string1 = "-1";
	static String string2 = "-1";
	
	private static Dice Player1Roll = new Dice();
	private static Dice Player2Roll = new Dice();

	private static Dice AttackerRoll = new Dice();
	private static Dice DefenderRoll = new Dice();

	private static int[] AttackerRolls = new int[3];
	private static int[] DefenderRolls = new int[2];
	
	private static int attackerHighestRoll;
	private static int defenderHighestRoll;
	
	private static int attackerSecondHighestRoll = 0;
	private static int defenderSecondHighestRoll = 0;
	
	public void rollDice(){
			Player1Roll.roll();
			Player2Roll.roll();
	}
	
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
	
	public static int getPlayer1RollValue(){
		return Player1Roll.value();
	}
	public static int getPlayer2RollValue(){		
		return Player2Roll.value();
	}
	
	public static int getAttackerRollValue(){
		return AttackerRoll.value();
	}
	public static int getDefenderRollValue(){
		return DefenderRoll.value();
	}
	
	public static void combatDiceRoll(String currentPlayer, String attackingPlayer, String defendingPlayer, int numberOfUnitsToAttackWith, int numberOfUnitsToDefendWith){
		
		Color attackingPlayerColour = Gameplay.attackingPlayerColour;
		Color defendingPlayerColour = Gameplay.defendingPlayerColour;

		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
		}
		if(currentPlayer == CommandInput.player2){
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;
		}
		if(currentPlayer == "Neutral 1"){
			attackingPlayer = "Neutral 1";
			attackingPlayerColour = Color.BLACK;
		}
		if(currentPlayer == "Neutral 2"){
			attackingPlayer = "Neutral 2";
			attackingPlayerColour = Color.GREEN;
		}
		if(currentPlayer == "Neutral 3"){
			attackingPlayer = "Neutral 3";
			attackingPlayerColour = Color.RED;
		}
		if(currentPlayer == "Neutral 4"){
			attackingPlayer = "Neutral 4";
			attackingPlayerColour = Color.YELLOW;
		}
				
		for(i=0; i<numberOfUnitsToAttackWith; i++){
			AttackerRoll.roll();		
			AttackerRolls[i] = getAttackerRollValue();
			CommandInput.appendStringTo(attackingPlayer + " roll " + (i) + " = " + AttackerRolls[i] + "\n", attackingPlayerColour);
			
			if(i==0){
				attackerHighestRoll = AttackerRolls[i];
			}
			else if(AttackerRolls[i] > attackerHighestRoll){
				attackerSecondHighestRoll = attackerHighestRoll;
				attackerHighestRoll = AttackerRolls[i];
			}
			else if((AttackerRolls[i] <= attackerHighestRoll) && (attackerSecondHighestRoll == 0)){
				attackerSecondHighestRoll = AttackerRolls[i];
			}
			else if((AttackerRolls[i] > attackerSecondHighestRoll) && (AttackerRolls[i] <= attackerHighestRoll)){
				attackerSecondHighestRoll = AttackerRolls[i];
			}
		}
		System.out.println("");
		for(i=0; i<numberOfUnitsToDefendWith; i++){
			DefenderRoll.roll();
			DefenderRolls[i] = getDefenderRollValue();
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " roll " + (i) + " = " + DefenderRolls[i] + "\n", defendingPlayerColour);			
			
			if(i==0){
				defenderHighestRoll = DefenderRolls[i];
			}
			else if(DefenderRolls[i] > defenderHighestRoll){
				defenderSecondHighestRoll = defenderHighestRoll;
				defenderHighestRoll = DefenderRolls[i];
			}
			else if(DefenderRolls[i] <= defenderHighestRoll){
				defenderSecondHighestRoll = DefenderRolls[i];
			}
		}
		System.out.println("ah = " + attackerHighestRoll);
		System.out.println("ash = " + attackerSecondHighestRoll);		
		System.out.println("dh = " + defenderHighestRoll);
		System.out.println("dsh = " + defenderSecondHighestRoll);

		CommandInput.appendStringTo(attackingPlayer + " highest roll = " + attackerHighestRoll  + "\n", attackingPlayerColour);
		if(attackerSecondHighestRoll != 0){
			CommandInput.appendStringTo(attackingPlayer + " second highest roll = " + attackerSecondHighestRoll  + "\n", attackingPlayerColour);
		}
		
		CommandInput.appendStringTo(Gameplay.defendingPlayerString + " highest roll = " + defenderHighestRoll + "\n", defendingPlayerColour);
		if(defenderSecondHighestRoll != 0){
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " second highest roll = " + defenderSecondHighestRoll  + "\n", defendingPlayerColour);
		}
		
		highestDiceRollWinner();
		if((attackerSecondHighestRoll > 0) || (defenderSecondHighestRoll > 0)){
			secondDiceRollWinner();
		}
		
		if(string1 == "0"){
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
		}
	}
	
	public static String highestDiceRollWinner(){
		
		if(attackerHighestRoll > defenderHighestRoll){
			string1 = "0";
		}
		if(attackerHighestRoll <= defenderHighestRoll){
			string1 = "1";
		}
		return string1;
	}
	
	public static String secondDiceRollWinner(){
			
		if(attackerSecondHighestRoll > defenderSecondHighestRoll){
			string2 = "0";
		}
		if(attackerSecondHighestRoll <= defenderSecondHighestRoll){
			string2 = "1";
		}	
		
		return string2;	
	}
	
	public static int getAttackerHighestRoll(){
		return attackerHighestRoll;
	}
	public static int getDefenderHighestRoll(){
		return defenderHighestRoll;
	}
}