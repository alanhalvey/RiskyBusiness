/*
 * Alan Halvey -
 * Alan Holmes -
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
	String string;
	private static Dice Player1Roll = new Dice();
	private static Dice Player2Roll = new Dice();
	
	public void rollDice(){
			
			Player1Roll = new Dice();
			Player1Roll.roll();
		
			Player2Roll = new Dice();
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
	
	public int getPlayer1RollValue(){
		return Player1Roll.value();
	}
	public int getPlayer2RollValue(){		
		return Player2Roll.value();
	}
}