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

//DiceRoll class allows the random player generator at the beginning of the game
//It is also a crucial factor in decinding battles within the combat function, the risk/luck feature in the game.
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
	
	private static int attackerHighestRoll; //holds the attackers highest roll
	private static int defenderHighestRoll; //holds the defenders highest roll
	
	private static int attackerSecondHighestRoll = 0; // holds the attackers second highest roll
	private static int defenderSecondHighestRoll = 0; // holds the defenders second highest roll
	
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
		
		Color attackingPlayerColour = Gameplay.attackingPlayerColour;
		Color defendingPlayerColour = Gameplay.defendingPlayerColour;

		//Set the attacking player and their respective colour. 
		if(currentPlayer == CommandInput.player1){
			attackingPlayer = CommandInput.player1;
			attackingPlayerColour = CommandInput.player1Colour;
		}
		if(currentPlayer == CommandInput.player2){
			attackingPlayer = CommandInput.player2;
			attackingPlayerColour = CommandInput.player2Colour;
		}
		//Neutrals don't attack in this sprint, but this is here incase they do in a future sprint.
		/*if(currentPlayer == "Neutral 1"){
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
		}*/
				
		//while 'i' is less than the user input for number of units to attack with
		for(i=0; i<numberOfUnitsToAttackWith; i++){ 
			AttackerRoll.roll(); 		
			AttackerRolls[i] = getAttackerRollValue();
			CommandInput.appendStringTo(attackingPlayer + " roll " + (i + 1) + " = " + AttackerRolls[i] + "\n", attackingPlayerColour);
			
			if(i==0){ //if it is the first roll, then that roll is the highest roll.
				attackerHighestRoll = AttackerRolls[i];
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
		System.out.println("");
		for(i=0; i<numberOfUnitsToDefendWith; i++){
			DefenderRoll.roll();
			DefenderRolls[i] = getDefenderRollValue();
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " roll " + (i + 1) + " = " + DefenderRolls[i] + "\n", defendingPlayerColour);			
			
			//if else statements are slightly simpler in defenders case as there is only two rolls
			if(i==0){ //if it is the first roll, then that roll is the highest roll.
				defenderHighestRoll = DefenderRolls[i];
			}
			else if(DefenderRolls[i] > defenderHighestRoll){ //if the latest roll is greater than the highest roll, the previous roll becomes the 2nd highest roll, and the latest roll becomes the highest roll
				defenderSecondHighestRoll = defenderHighestRoll;
				defenderHighestRoll = DefenderRolls[i];
			}
			else if(DefenderRolls[i] <= defenderHighestRoll){ //If the latest roll is <= to the highest roll, latest roll becomes the 2nd highest roll.
				defenderSecondHighestRoll = DefenderRolls[i];
			}
		}

		CommandInput.appendStringTo(attackingPlayer + " highest roll = " + attackerHighestRoll  + "\n", attackingPlayerColour);
		if(attackerSecondHighestRoll != 0){ //if there was a second roll for the attacker
			CommandInput.appendStringTo(attackingPlayer + " second highest roll = " + attackerSecondHighestRoll  + "\n", attackingPlayerColour);
		}
		
		CommandInput.appendStringTo(Gameplay.defendingPlayerString + " highest roll = " + defenderHighestRoll + "\n", defendingPlayerColour);
		if(defenderSecondHighestRoll != 0){ //if there was a second roll for the defender
			CommandInput.appendStringTo(Gameplay.defendingPlayerString + " second highest roll = " + defenderSecondHighestRoll  + "\n", defendingPlayerColour);
		}
		
		highestDiceRollWinner();
		if((attackerSecondHighestRoll > 0) && (defenderSecondHighestRoll > 0)){ //If both players had a second roll, then the second highest rolls are compared and a winner given
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
	
	public static String highestDiceRollWinner(){ //attacker and defender highest rolls compared
		
		if(attackerHighestRoll > defenderHighestRoll){
			string1 = "0";
		}
		if(attackerHighestRoll <= defenderHighestRoll){
			string1 = "1";
		}
		return string1;
	}
	
	public static String secondDiceRollWinner(){ //attacker and defender 2nd highest rolls compared
			
		if(attackerSecondHighestRoll > defenderSecondHighestRoll){
			string2 = "0";
		}
		if(attackerSecondHighestRoll <= defenderSecondHighestRoll){
			string2 = "1";
		}	
		
		return string2;	
	}
}