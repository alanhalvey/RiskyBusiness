/*
 * Alan Halvey - 14465722
 * Alan Holmes - 14719591
 * Greg Sloggett - 14522247
 * 
 */
package core;

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
	
	public static void combatDiceRoll(){
		for(i=0; i<3; i++){
			AttackerRoll.roll();		
			AttackerRolls[i] = getAttackerRollValue();
			CommandInput.appendStringTo("Player 1 rolls = " + AttackerRolls[i] + "\n", CommandInput.player1Colour);
			
			if(i==0){
				attackerHighestRoll = AttackerRolls[i];
			}
			if(i>0 &&(AttackerRolls[i] > attackerHighestRoll)){
				attackerHighestRoll = AttackerRolls[i];
			}
		}
		System.out.println("");
		for(i=0; i<2; i++){
			DefenderRoll.roll();
			DefenderRolls[i] = getDefenderRollValue();
			CommandInput.appendStringTo("Player 2 rolls = " + DefenderRolls[i] + "\n", CommandInput.player2Colour);			
			if(i==0){
				defenderHighestRoll = DefenderRolls[i];
			}
			if((i>0) && (DefenderRolls[i] > defenderHighestRoll)){
				defenderHighestRoll = DefenderRolls[i];
			}
		}

		CommandInput.appendStringTo("Player 1 highest roll = " + attackerHighestRoll  + "\n", CommandInput.player1Colour);
		CommandInput.appendStringTo("Player 2 highest roll = " + defenderHighestRoll + "\n", CommandInput.player2Colour);
		
		combatDiceRollWinner();
		
		if(string == "0"){
			CommandInput.appendStringTo("Player 1 wins combat" + "\n", CommandInput.player1Colour);		}
		if(string == "1"){
			CommandInput.appendStringTo("Player 2 wins combat" + "\n", CommandInput.player2Colour);
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