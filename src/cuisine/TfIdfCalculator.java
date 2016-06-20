package cuisine;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class TfIdfCalculator {

	private static double tf(String cuisine, String term) {
//	    double result = 0;
//	    for (String word : doc) {
////	       if (term.equalsIgnoreCase(word))
////	              result++;
//	       }
	    return Globals.INGREDIENTS_PER_CUISINE_COUNT.get(cuisine).get(term) / Globals.INGREDIENTS_PER_CUISINE.size();
	}
	
	private static double idf(String term) {
	    int n = 0;
//	    for (List<String> doc : docs) {
//	        for (String word : doc) {
//	            if (term.equalsIgnoreCase(word)) {
//	                n++;
//	                break;
//	            }
//	        }
//	    }
	    
	    for (Entry<String, Map<String, Integer>> doc : Globals.INGREDIENTS_PER_CUISINE_COUNT.entrySet()) {
	        if(doc.getValue().containsKey(term)){
	        	n++;
	        }
	    }
	    return Math.log((double)Globals.CUISINES.size() / (double)n);
	}
	
	public static double tfIdf(String cuisine,List<String> terms) {
		double result = 0;
		for(String term :terms){
			result *= tf(cuisine, term)*idf( term);
		}
	    return result;
	}
}
