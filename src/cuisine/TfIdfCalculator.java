package cuisine;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TfIdfCalculator {

	private static double tf(String cuisine, String term) {
		if (Globals.INGREDIENTS_PER_CUISINE_COUNT.get(cuisine).containsKey(term)) {
			return Globals.INGREDIENTS_PER_CUISINE_COUNT.get(cuisine).get(term)/(double)Globals.INGREDIENTS_PER_CUISINE.get(cuisine).size();
		}
		return 0;
	}

	public static double idf(String term) {
		double n = 0.0;
		for (String cuisine : Globals.CUISINES) {
			if (Globals.INGREDIENTS_PER_CUISINE_COUNT.get(cuisine).containsKey(term)) {
				n = n +1;
			}
		} 
		if (n>0) {
			return 1 + Math.log((double)Globals.CUISINES.size()/n);
		}
		return 1;
//		if(Globals.INGREDIENTS_PER_CUISINE_COUNT.get(cuisine).containsKey(term)){
//			return Math.log(Globals.RECIPIES_PER_CUISINE_COUNT.get(cuisine)/(double)Globals.INGREDIENTS_PER_CUISINE_COUNT.get(cuisine).get(term));
//		}else{
//			return 0.0;
//		}
	}

	public static double tfIdf(String cuisine, List<String> terms) {
		double queryTF = (1.0/(double)terms.size());
		double nominator = 0.0;
		double denominatorPart1 = 0.0;
		double denominatorPart2 = 0.0;
		for (String term : terms) {
			double idf = 0;
			if (Globals.IDFS.containsKey(term)) {
				idf = Globals.IDFS.get(term);
			} else {
				idf = 1;
			}
			double q = queryTF*idf;
			double perCat = tf(cuisine, term)*idf;
			nominator += (q)*(perCat);
			denominatorPart1 += q*q;
			denominatorPart2 += perCat*perCat;
		}
		return nominator/(Math.sqrt(denominatorPart1)*Math.sqrt(denominatorPart2));
	}
}
