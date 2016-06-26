package cuisine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class SubmissionGenerator {
	public static void generateSubmissionsTFIDF() throws Exception {
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
//			break;
		}
		
		bw.close();
	}
	
	
	public static void generateSubmissionsKNN() throws Exception {
		System.out.println("Generate submission");
		
		File outputFile = new File(Constants.SUBMISSION_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("id,cuisine");
		
		Classifier classifier = KNNClasifier.getClassifier("data_train.txt");
		Instances testData = KNNClasifier.getInstances("data_test.txt");
		
		for(int i =0; i<testData.numInstances(); i++){
			double classNum = KNNClasifier.classifyInstance(classifier, testData.instance(i));
			bw.newLine();
			bw.append(Globals.TEST_CUISINE.get(i).getId() + "," + KNNClasifier.getData().classAttribute().value((int) classNum));
//			break;
		}
		
		bw.close();
	}
	
	
	public static void generateDataFile() throws Exception {
		System.out.println("Generate dataFile");
		
		String[] ingredientsSorted = Globals.ALL_INGREDIENTS.toArray(new String[Globals.ALL_INGREDIENTS.size()]);
		Arrays.sort(ingredientsSorted);
		
		
		File outputFile = new File(Constants.DATA_FILE_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("@relation " + outputFile.getName());
		bw.newLine();
		bw.newLine();
		bw.append(DataFileGenerator.attributeAnnotation(ingredientsSorted));
		bw.newLine();
		bw.append(DataFileGenerator.classAnotation(Globals.CUISINES));
		bw.newLine();
		bw.append("@data");
		for(Cuisine cuisine : Globals.TEST_CUISINE){
			bw.newLine();
//			bw.append(DataFileGenerator.generateInstanceString(ingredientsSorted, cuisine));
			bw.append(DataFileGenerator.generateTestInstanceString(ingredientsSorted, cuisine));
		}

		
		bw.close();
	}
}