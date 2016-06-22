package cuisine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

public class KNNClasifier {

	public static BufferedReader readDataFile(String fileName){
		BufferedReader inputReader = null;
		
		try{
			inputReader = new BufferedReader(new FileReader(fileName));
			
		}catch(FileNotFoundException fnfe){
			System.out.println("File not found " + fileName);
		}
		
		return inputReader;
	}
	
	public static Classifier getClassifier(String fileName) throws Exception{
		BufferedReader dataFile = readDataFile(fileName);
		
		Instances data = new Instances(dataFile);
		data.setClassIndex(data.numAttributes()-1);
		
		Classifier ibk = new IBk();
		ibk.buildClassifier(data);
		
		return ibk;
		
	}
	
	public static double classifyInstance(Classifier ibk, Instance instance) throws Exception{
		return ibk.classifyInstance(instance);
	}
}
