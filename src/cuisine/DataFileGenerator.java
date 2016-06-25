package cuisine;

import java.util.List;
import java.util.Set;

public class DataFileGenerator {

	public static String generateInstanceString(String[] ingredientsSorted, Cuisine cuisine ){
		String instance = "";
		List<String> listIngr = cuisine.getIngredients();
			for(String ingredient : ingredientsSorted){
				if(listIngr.contains(ingredient)){
					instance+="1,";
				}else{
					instance+="0,";
				}
			}
			
			instance+=cuisine.getCuisine();
		
		return instance;
	}
	
	public static String generateTestInstanceString(String[] ingredientsSorted, Cuisine cuisine ){
		String instance = "";
		List<String> listIngr = cuisine.getIngredients();
			for(String ingredient : ingredientsSorted){
				if(listIngr.contains(ingredient)){
					//skip ingrediets missing from train data in the instance
					if(Globals.ALL_INGREDIENTS.contains(ingredient)){
						instance+="1,";
					}
				}else{
					instance+="0,";
				}
			}
			
			instance = instance.substring(0, instance.length()-1);
		
		return instance;
	}
	
	public static String attributeAnnotation(String[] ingredientsSorted){
		String attributes = "";
	
		for(String ingredient : ingredientsSorted){
			attributes += "@attribute "+ingredient+" numeric\n";
		}
		
		return attributes;
	}
	
	public static String classAnotation(Set<String> cuisines){
		String classes = "@attribute cuisine {";
		
		for(String cuisine : cuisines.toArray(new String[cuisines.size()])){
			classes += cuisine + ", ";
		}
		
		classes = classes.substring(0, classes.length() - 2);
		classes += "}";
		
		return classes;
	}
	
//	public static void main(String[] args) {
//		
//	}
}
