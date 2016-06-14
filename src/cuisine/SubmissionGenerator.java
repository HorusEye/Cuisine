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
		
		for(Cuisine cuisine : Globals.TEST_CUISINE) {
			bw.newLine();
			bw.append(cuisine.getId() + "," + cuisine.getCuisine());
		}
		
		bw.close();
	}
}