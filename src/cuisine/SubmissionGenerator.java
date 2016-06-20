package cuisine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SubmissionGenerator {
	public static void generateSubmissions() throws Exception {
		System.out.println("Generate submission");
		
		File outputFile = new File(Constants.SUBMISSION_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("id,cuisine");
		
		CuisineTFIDFVectors result = new CuisineTFIDFVectors();
		
		for(Cuisine cuisine : Globals.TEST_CUISINE) {
			bw.newLine();
			result.calculateVectors(cuisine.getIngredients());
			String cuisineName =  result.getBestCuisine();
			bw.append(cuisine.getId() + "," + cuisineName);
		}
		
		bw.close();
	}
}