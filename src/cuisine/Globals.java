package cuisine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Globals {
	public static List<Cuisine> TEST_CUISINE;
	public static List<Cuisine> TRAIN_CUISINE;
	
	//contains all ingredients per given cuisine
	public static Map<String, List<String>> INGREDIENTS_PER_CUISINE;
	public List<String> ALL_INGREDIENTS;
	public static Set<String> CUISINES;
	

	public static void init() throws Exception {
		initTestCuisines();
		initTrainCuisines();
	
	}
	
	private static void initTestCuisines() throws Exception {
		
		System.out.println("Read Test Cuisine");
		String jsonContent = readFile(Constants.TEST_CUISINE_PATH);
		Type listOfA = new TypeToken<List<Cuisine>>() {
		}.getType();
		Gson gson = new Gson();
		TEST_CUISINE = gson.fromJson(jsonContent, listOfA);
		
	}
	
	private static String readFile(String path) throws FileNotFoundException {
		File file = new File(path);
		StringBuilder sb = new StringBuilder((int) file.length());
		Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
		String lineSeparator = System.getProperty("line.separator");

		try {
			while (sc.hasNextLine()) {
				sb.append(sc.nextLine()).append(lineSeparator);
			}
			return sb.toString();
		} finally {
			sc.close();
		}
	}
	
	private static void initTrainCuisines() throws Exception {
		
		System.out.println("Read Train Cuisine");
		String jsonContent = readFile(Constants.TRAIN_CUISINE_PATH);
		Type listOfA = new TypeToken<List<Cuisine>>() {
		}.getType();
		Gson gson = new Gson();
		TRAIN_CUISINE = gson.fromJson(jsonContent, listOfA);
		INGREDIENTS_PER_CUISINE = new HashMap<String, List<String>>();
		CUISINES = new HashSet<String>();
		
		
		for (Cuisine cuisine : TRAIN_CUISINE) {
			
			List<String> ingredients = new ArrayList<String>();
			ingredients.addAll(cuisine.getIngredients());

			if(!INGREDIENTS_PER_CUISINE.containsKey(cuisine.getCuisine().toLowerCase().trim())){
				INGREDIENTS_PER_CUISINE.put(cuisine.getCuisine().toLowerCase().trim(), ingredients);
			}else{
				INGREDIENTS_PER_CUISINE.get(cuisine.getCuisine().toLowerCase().trim()).addAll(ingredients);
			}
			
			CUISINES.add(cuisine.getCuisine().toLowerCase().trim());
			
		}
	}
	
	
	

	
}