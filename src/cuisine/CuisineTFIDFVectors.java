package cuisine;

import java.util.HashMap;
import java.util.Map;


public class CuisineTFIDFVectors {

	private Map<String, Map<String, Double>> vector;
	
	public  Map<String, Map<String, Double>> calculateVector(){
		vector = new HashMap<String, Map<String, Double>>();
		for(String cuisine : Globals.CUISINES){
			Map<String, Double> tfidf = new HashMap<String, Double>();
			for(String ingredient : Globals.INGREDIENTS_PER_CUISINE.get(cuisine)){
				tfidf.put(ingredient, TfIdfCalculator.tfIdf(doc, docs, ingredient));
			}
		}
		
		return null;
		
	}
}
