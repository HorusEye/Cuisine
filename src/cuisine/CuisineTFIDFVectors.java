package cuisine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CuisineTFIDFVectors {

	private String bestCuisine;
	private double bestResult;
	
	
	public  void calculateVectors(List<String> terms){
		bestResult = 0;
		double temp = 0;
		int k = 0;
		for(String cuisine : Globals.CUISINES){
			k++;
			if(k==10) break;
			
			temp = TfIdfCalculator.tfIdf(cuisine, terms);
			//System.out.println(cuisine  + " - " + temp);
			if( temp > bestResult) {
				bestResult = temp;
				bestCuisine = cuisine;
			}
		
		}
		
	}


	public String getBestCuisine() {
		return bestCuisine;
	}



	public double getBestResult() {
		return bestResult;
	}

}
