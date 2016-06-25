package cuisine;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Globals global = new Globals();
		global.init();
//		global.printLists();
		
		Classifier classifier = KNNClasifier.getClassifier("data_train.txt");
		Instances testData = KNNClasifier.getInstances("data_test.txt");
		
		for(int i =0; i<testData.numInstances(); i++){
			double classNum = KNNClasifier.classifyInstance(classifier, testData.instance(i));
			System.out.println(KNNClasifier.getData().classAttribute().value((int) classNum));
			break;
		}
		
//		SubmissionGenerator.generateDataFile();
		
//		SubmissionGenerator.generateSubmissions();
		
		System.out.println("fin");
	}

}
