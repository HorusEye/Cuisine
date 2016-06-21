package cuisine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Globals {
	private static List<String> stopwords = Arrays.asList("plain", "large",
			"small", "to", "or", "s", "lb", "n", "g");

	public static List<Cuisine> TEST_CUISINE;
	public static List<Cuisine> TRAIN_CUISINE;

	// contains all ingredients per given cuisine
	public static Map<String, List<String>> INGREDIENTS_PER_CUISINE;
	// distinct ingredients and cuisines in train set
	public static Set<String> ALL_INGREDIENTS;
	public static Set<String> CUISINES;

	// all duplicate ingredients for all cuisines
	public static List<List<String>> INGREDIENTS_FOR_ALL_CUISINES;

	public static Map<String, Map<String, Integer>> INGREDIENTS_PER_CUISINE_COUNT;

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
		filterRecipes(TEST_CUISINE);

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

	private static void filterRecipes(List<Cuisine> cuisines) {
		for (Cuisine cuisine : cuisines) {
			cuisine.setIngredients(filterIngredients(cuisine.getIngredients()));
		}
	}

	private static List<String> filterIngredients(List<String> toBeFiltered) {
		List<String> result = new ArrayList<String>();
		for (String ingredient : toBeFiltered) {
			ingredient = ingredient.toLowerCase();
			ingredient = removeSymbols(ingredient);
			List<String> separateWords = Arrays.asList(ingredient.split(" "));
			for (String word : separateWords) {
				word = word.replaceAll("\\s*", "");
				result.add(word);
			}

		}
		return result;
	}

	private static String removeSymbols(String ingredient) {
		ingredient = ingredient.replace("-", " ");
		ingredient = ingredient.replace("&", " ");
		ingredient = ingredient.replace("'", " ");
		ingredient = ingredient.replace("\"", " ");
		ingredient = ingredient.replace("%", " ");
		ingredient = ingredient.replace("!", " ");
		ingredient = ingredient.replace("(", " ");
		ingredient = ingredient.replace(")", " ");
		ingredient = ingredient.replace("/", " ");
		ingredient = ingredient.replace(",", " ");
		ingredient = ingredient.replace(".", " ");
		ingredient = ingredient.replace("\u2122", " ");
		ingredient = ingredient.replace("\u00AE", " ");
		ingredient = ingredient.replace("\u2019", " ");
		ingredient = ingredient.replaceAll("\\d", "");
		return ingredient;

	}

	private static void initTrainCuisines() throws Exception {

		System.out.println("Read Train Cuisine");
		String jsonContent = readFile(Constants.TRAIN_CUISINE_PATH);
		Type listOfA = new TypeToken<List<Cuisine>>() {
		}.getType();
		Gson gson = new Gson();

		TRAIN_CUISINE = gson.fromJson(jsonContent, listOfA);
		INGREDIENTS_PER_CUISINE = new HashMap<String, List<String>>();

		ALL_INGREDIENTS = new HashSet<String>();
		CUISINES = new HashSet<String>();
		INGREDIENTS_FOR_ALL_CUISINES = new ArrayList<List<String>>();
		INGREDIENTS_PER_CUISINE_COUNT = new HashMap<String, Map<String, Integer>>();

		filterRecipes(TRAIN_CUISINE);

		for (Cuisine cuisine : TRAIN_CUISINE) {

			List<String> ingredients = new ArrayList<String>();
			ingredients.addAll(cuisine.getIngredients());
			String cuisineName = cuisine.getCuisine();
			;

			if (!INGREDIENTS_PER_CUISINE.containsKey(cuisineName)) {
				INGREDIENTS_PER_CUISINE.put(cuisineName, ingredients);
			} else {
				INGREDIENTS_PER_CUISINE.get(cuisineName).addAll(ingredients);
			}

			if (!INGREDIENTS_PER_CUISINE_COUNT.containsKey(cuisine)) {
				INGREDIENTS_PER_CUISINE_COUNT.put(cuisineName,
						new HashMap<String, Integer>());
			}

			for (String ingredient : ingredients) {
				if (INGREDIENTS_PER_CUISINE_COUNT.get(cuisineName).containsKey(
						ingredient)) {
					INGREDIENTS_PER_CUISINE_COUNT.get(cuisineName).put(
							ingredient,
							INGREDIENTS_PER_CUISINE_COUNT.get(cuisineName).get(
									ingredient) + 1);
				} else {
					INGREDIENTS_PER_CUISINE_COUNT.get(cuisineName).put(
							ingredient, 1);
				}

			}

			CUISINES.add(cuisine.getCuisine().toLowerCase().trim());
			ALL_INGREDIENTS.addAll(ingredients);
			INGREDIENTS_FOR_ALL_CUISINES.add(ingredients);

		}

	}

	public void printLists() {
		System.out.println("Cuisines");
		// for(String cuisine : CUISINES){
		// System.out.print(cuisine + ", ");
		// }

		// System.out.println("Ingredients");
		// for(String ing : ALL_INGREDIENTS){
		// System.out.print(ing + ", ");
		// }
		//
		System.out.println("ING/CUISINE");
		for (Entry<String, List<String>> entry : INGREDIENTS_PER_CUISINE
				.entrySet()) {
			System.out.println(entry.getKey() + ": "
					+ entry.getValue().toString());
		}

	}

}