package core;

public class Fortify {




	public Fortify(Country takeArmies, Country putArmies, int amountMoved){

for(int k=0; k<= Data.COUNTRY_NAMES.length-1;k++){
		if(takeArmies.getName() != Data.COUNTRY_NAMES[k] || putArmies.getName() != Data.COUNTRY_NAMES[k] ){
			System.out.println("Sorry this isnt a country");
		}
		else{
		for(int j =0; j<takeArmies.Adjacent.length-1; j++){
			for(int i=0; i<putArmies.Adjacent.length-1;i++){
				if(takeArmies.Adjacent[j]==putArmies.Adjacent[i]){
					takeArmies.setPlayerArmies(takeArmies.getPlayerArmies()-amountMoved);
					putArmies.setPlayerArmies(putArmies.getPlayerArmies()+amountMoved);
				}
			}

		}
		}
	}
	
	}
}



