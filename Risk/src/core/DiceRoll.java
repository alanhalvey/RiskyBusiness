package core;

import javax.swing.JOptionPane;

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
	
	public static void main(String[] args) {


		for (i = 0; i < 1; i++) { 
			Player1Roll = new Dice();
			Player1Roll.roll();
		}
		
		for (i = 0; i < 1; i++) {
			Player2Roll = new Dice();
			Player2Roll.roll();
		}
		//JOptionPane.showMessageDialog(null, "Player 1's Roll contained the number: \n" + Player1Roll.value() + "\n");
		//JOptionPane.showMessageDialog(null, "Player 2's Roll contained the number: \n" + Player2Roll.value() + "\n");

		System.exit(0);
	}
	
	public String getRoll(){
		if(Player1Roll.value() > Player2Roll.value()){
			//JOptionPane.showMessageDialog(null, "Player 1");
			 return "1";
		}
		else{
			 //JOptionPane.showMessageDialog(null, "Player 2");
			 return "2";
		}
	}
}
