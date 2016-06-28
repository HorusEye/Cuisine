package cuisine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.berkeley.compbio.jlibsvm.SVM;
import edu.berkeley.compbio.jlibsvm.binary.C_SVC;
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
	
	public static void generateSVMFIle(){
		try(FileWriter fw = new FileWriter("submit_svm_test.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)){
			
			String[] ingredientsSorted = Globals.ALL_INGREDIENTS.toArray(new String[Globals.ALL_INGREDIENTS.size()]);
			Arrays.sort(ingredientsSorted);
			Map<String,Integer> ingredients = new HashMap<String, Integer>();	
			for (int i = 1; i < ingredientsSorted.length +1; i++){
				ingredients.put(ingredientsSorted[i-1], i);
			}
			String[] cuisinesList = Globals.CUISINES.toArray(new String[Globals.CUISINES.size()]);
			Map<String,Integer> cuisines = new HashMap<String, Integer>();	
			
			for (int i = 1; i < cuisinesList.length + 1; i++) {
				cuisines.put(cuisinesList[i-1], i);
			}
			
			
			for (Cuisine cuisine : Globals.TEST_CUISINE) {
				if (cuisines.get(cuisine.getCuisine()) ==null) {
					System.out.println();
				}
//				out.print(cuisines.get(cuisine.getCuisine()) + " ");
				
				for (String ingr : cuisine.getIngredients()) {
					if (Globals.ALL_INGREDIENTS.contains(ingr)) {
						out.print(ingredients.get(ingr)+":1 ");
					}
				}
				out.println();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generateSubmissionsSVM(){
		SVMClassifier svm = new SVMClassifier();
		String[] arr = new String[]{"svm_train.txt", "svm_model"};
		try {
			svm.run(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}