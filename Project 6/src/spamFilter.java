import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class spamFilter {

	final static File allfolder = new File("/Users/nudlz/Desktop/CS/CS311/HW6/src/spamdata/");
	final static File spamfolder = new File("/Users/nudlz/Desktop/CS/CS311/HW6/src/spamdata/spam/");
	final static File hamfolder = new File("/Users/nudlz/Desktop/CS/CS311/HW6/src/spamdata/ham/");
	
	public static void main(String[] args) throws IOException
	{		
		
		Scanner scan = new Scanner(System.in);
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt")); 
		
		System.out.println("How many messages to classify?");
		
		int nummsgs = Integer.parseInt(scan.nextLine());
		System.out.println("Classifying 1000 random messages...");
		
		
		
		/*
        System.out.println("all messages:");
		readFiles(allfolder);		
		System.out.println();
		
		System.out.println("spam messages:");
		readFiles(spamfolder);
		System.out.println();
		
		System.out.println("ham messages:");
		readFiles(hamfolder);
		*/
		System.out.println(Arrays.toString(preprocess(spamfolder,50)));
	}
	

	/*
	 * Takes as parameter the name of a folder and returns a list of filenames (Strings) 
	 * in the folder.
	 */
	public static ArrayList<String> readFiles(File folder){
		
		//List to store filenames in folder
		ArrayList<String> filelist = new ArrayList<String>();
		
		//call to recursive method that reads all filenames in folder
		listFilesForFolder(folder, filelist );

		return filelist;
	}
	
	
	/*
	 * Recursive method that takes as parameter a folder and an empty ArrayList and
	 * fills the list with the names of all the files in the given foler.
	 */
	public static void listFilesForFolder(File folder, ArrayList<String> files) {
		
		for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) 
	            listFilesForFolder(fileEntry, files);
	        else {
	        	String filename = fileEntry.getName();
	        	files.add(filename);
	            //System.out.println(filename);
	        }
	    }
	}
	//Helper function that returns K most frequent words in a hashmap.
	public static ArrayList<String> kmostfreq(HashMap<String, Integer> map, int k) { 
		List<Map.Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(map.entrySet());
		//Sorting by value
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
	            	}});
	      	
		ArrayList<String> result = new ArrayList<String>();
		for (int i = list.size()-1;i>=list.size()-k;i--) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) list.get(i);
			result.add(entry.getKey());
		} 
		return result;
	}
	
	
	
	/*
	 * TO DO
	 * Preprocessor: Reads email messages to fill a table of features.
	 * You may modify the method header (return type, parameters) as you see fit.
	 */
	public static String[] preprocess(File folder, int k) throws IOException
	{
		//the fifty most common words in English according to wikpedia
		String[] commonWords = {"the","be","to","of","and","a","in","that",
				"have","I","it","for","not","on","with","he","as","you","do",
				"at","this","but","his","by","from","they","we","say","her",
				"she","or","an","are","will","my","one","all","would","there","their",
				"what","so","up","out","if","about","who","get","which","go","me",
				"when","make","can","like","time","no","just","him","know","take",
				"people","into","year","your","good","some","could","them","see",
				"other","than","then","now","look","only","come","its","over","think",
				"also","back","after","use","subject","two","how","i","our","work","first","well",
				"way","even","new","want","because","any","these","give","day","most","us","is"};
		//Common words into arrayList so we can use contains method
		ArrayList<String> commons =new ArrayList<String>(Arrays.asList(commonWords));
		HashMap<String, Integer> freqz = new HashMap<String, Integer>();
		ArrayList<String> filez = readFiles(folder);
		//Randomly select files from spamdata to get our features.
		Random randomGen = new Random();
		for (int i =0; i<100; i++){
			int randInt = randomGen.nextInt(filez.size());
		    BufferedReader bufferedReader = new BufferedReader(new FileReader("src/spamdata/spam/"+filez.get(randInt)));
		    String line = null;
		    while ((line = bufferedReader.readLine()) != null)
		    {
		    	String[] temp = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		    	for(String xt: temp){
		    		if(!commons.contains(xt)){
		    			if(freqz.containsKey(xt)){
		    				freqz.put(xt, freqz.get(xt)+1);
		    			}else{
		    				freqz.put(xt, 1);
		    			}
		    		}		
		    	}
		    }
		    bufferedReader.close();
		}
		return kmostfreq(freqz,k).toArray(new String[k]);
		
       
	}
	
	
	/*
	 * TO DO
	 * Trainer: Reads email messages and computes probabilities for the Bayesian formula.
	 * You may modify the method header (return type, parameters) as you see fit.
	 */
	public static void train()
	{
		
	}


	/*
	 * TO DO
	 * Classifier: Reads and classifies an email message as either spam or ham.
	 * You may modify the method header (return type, parameters) as you see fit.
	 */
	public static void classify()
	{
		
	}
	
}
