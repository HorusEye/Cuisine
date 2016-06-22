package cuisine;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Globals global = new Globals();
		global.init();
//		global.printLists();
		
		SubmissionGenerator.generateDataFile();
		
//		SubmissionGenerator.generateSubmissions();
		
		System.out.println("fin");
	}

}
