package cuisine;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Globals global = new Globals();
		global.init();
//		global.printLists();
		
		
		
//		SubmissionGenerator.generateDataFile();
		
		SubmissionGenerator.generateSubmissionsKNN();
		
		System.out.println("fin");
	}

}
