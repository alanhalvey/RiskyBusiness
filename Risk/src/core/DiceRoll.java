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
	static String string;
	
	private static Dice Player1Roll = new Dice();
	private static Dice Player2Roll = new Dice();

	private static Dice AttackerRoll = new Dice();
	private static Dice DefenderRoll = new Dice();

	private static int[] AttackerRolls = new int[3];
	private static int[] DefenderRolls = new int[2];
	
	private static int attackerHighestRoll;
	private static int defenderHighestRoll;
	
	private static int attackerSecondHighestRoll;
	private static int defenderSecondHighestRoll;
	
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
		
		Color attackingPlayerColour = Color.ORANGE;
		Color defendingPlayerColour = Color.ORANGE;

		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
			
			defendingPlayer = CommandInput.player2;
			defendingPlayerColour = CommandInput.player2Colour;
		}
		else{
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;

			defendingPlayer = CommandInput.player1;
			defendingPlayerColour = CommandInput.player1Colour;
		}
				
		for(i=0; i<numberOfUnitsToAttackWith; i++){
			AttackerRoll.roll();		
			AttackerRolls[i] = getAttackerRollValue();
			//CommandInput.appendStringTo(attackingPlayer + " roll " + i + " = " + AttackerRolls[i] + "\n", attackingPlayerColour);
			
			if(i==0){
				attackerHighestRoll = AttackerRolls[i];
			}
			if(i>0 &&(AttackerRolls[i] > attackerHighestRoll)){
				attackerSecondHighestRoll = attackerHighestRoll;
				attackerHighestRoll = AttackerRolls[i];
			}
		}
		System.out.println("");
		for(i=0; i<numberOfUnitsToDefendWith; i++){
			DefenderRoll.roll();
			DefenderRolls[i] = getDefenderRollValue();
			//CommandInput.appendStringTo(defendingPlayer + " roll " + i + " = " + DefenderRolls[i] + "\n", defendingPlayerColour);			
			if(i==0){
				defenderHighestRoll = DefenderRolls[i];
			}
			if((i>0) && (DefenderRolls[i] > defenderHighestRoll)){
				defenderSecondHighestRoll = defenderHighestRoll;
				defenderHighestRoll = DefenderRolls[i];
			}
		}

		CommandInput.appendStringTo(attackingPlayer + " highest roll = " + attackerHighestRoll  + "\n", attackingPlayerColour);
		if(attackerSecondHighestRoll != 0){
			CommandInput.appendStringTo(attackingPlayer + " second highest roll = " + attackerSecondHighestRoll  + "\n", attackingPlayerColour);
		}
		
		CommandInput.appendStringTo(defendingPlayer + " highest roll = " + defenderHighestRoll + "\n", defendingPlayerColour);
		if(defenderSecondHighestRoll != 0){
			CommandInput.appendStringTo(defendingPlayer + " second highest roll = " + defenderSecondHighestRoll  + "\n", defendingPlayerColour);
		}
		
		combatDiceRollWinner();
		
		if(string == "0"){
			CommandInput.appendStringTo(attackingPlayer + " wins combat" + "\n", attackingPlayerColour);		
			}
		if(string == "1"){
			CommandInput.appendStringTo(defendingPlayer + " wins combat" + "\n", defendingPlayerColour);
		}
	}
	
	public static String combatDiceRollWinner(){
		if(attackerHighestRoll > defenderHighestRoll){
			string = "0";
		}
		if(attackerHighestRoll <= defenderHighestRoll){
			string = "1";
		}
		return string;
	}
	
	public static int getAttackerHighestRoll(){
		return attackerHighestRoll;
	}
	public static int getDefenderHighestRoll(){
		return defenderHighestRoll;
	}
}