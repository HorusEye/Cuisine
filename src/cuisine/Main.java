package cuisine;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Globals global = new Globals();
		global.init();
//		global.printLists();
		
		
//		SubmissionGenerator.generateSVMFIle();
		SubmissionGenerator.generateSubmissionsSVM();
//		SubmissionGenerator.generateDataFile();
		
//		SubmissionGenerator.generateSubmissionsTFIDF();
		
		System.out.println("fin");
	}

}
