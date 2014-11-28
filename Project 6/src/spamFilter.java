import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class spamFilter {

	final static File allfolder = new File("/Users/nudlz/Desktop/CS/CS311/HW6/src/spamdata/");
	final static File spamfolder = new File("/Users/nudlz/Desktop/CS/CS311/HW6/src/spamdata/spam/");
	final static File hamfolder = new File("/Users/nudlz/Desktop/CS/CS311/HW6/src/spamdata/ham/");
	//the most common words in English according to wikpedia
	final static String[] commonWords = {"the","be","to","of","and","a","in","that",
			"have","I","it","for","not","on","with","he","as","you","do",
			"at","this","but","his","by","from","they","we","say","her",
			"she","or","an","are","will","my","one","all","would","there","their",
			"what","so","up","out","if","about","who","get","which","go","me",
			"when","make","can","like","time","no","just","him","know","take",
			"people","into","year","your","good","some","could","them","see",
			"other","than","then","now","look","only","come","its","over","think",
			"also","back","after","use","subject","two","how","i","our","work","first","well",
			"way","even","want","because","any","these","give","day","most","us","is"};
	
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
		HashMap<ArrayList<Pair>,String >mapp = train(preprocess(spamfolder,50),preprocess(hamfolder,50));
		System.out.println("got here at least...");
		classify(preprocess(spamfolder,50),preprocess(hamfolder,50), mapp);
		System.out.println("So far so good");
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
		
		//Common words into arrayList so we can use contains method
		ArrayList<String> commons =new ArrayList<String>(Arrays.asList(commonWords));
		HashMap<String, Integer> freqz = new HashMap<String, Integer>();
		ArrayList<String> filez = readFiles(folder);
		ArrayList<String> Sfilez = readFiles(spamfolder);
		//Randomly select files from spamdata to get our features.
		Random randomGen = new Random();
		for (int i =0; i<100; i++){
			//needs to be fixed for hamfolder
			String name = filez.get(randomGen.nextInt(filez.size()));
			BufferedReader bufferedReader;
			if(Sfilez.contains(name)){
				bufferedReader = new BufferedReader(new FileReader("src/spamdata/spam/"+name));
			}else{
				bufferedReader = new BufferedReader(new FileReader("src/spamdata/ham/"+name));
			}		    String line = null;
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
	public static HashMap<ArrayList<Pair>,String> train(String[] Sfeatures, String[] Hfeatures) throws IOException
	{
		HashMap<ArrayList<Pair>,String>map=new HashMap<ArrayList<Pair>,String>();
		//read random 1000 files
		ArrayList<String> filez = readFiles(allfolder);
		ArrayList<String> Sfilez = readFiles(spamfolder);
		ArrayList<String>contents = new ArrayList<String>();
		ArrayList<Pair>ffeatures = new ArrayList<Pair>();
		
		String type=null;
		Random randomGen = new Random();
		for(int i=0;i<1000;i++){
			String name = filez.get(randomGen.nextInt(filez.size()));
			BufferedReader bufferedReader;
			if(Sfilez.contains(name)){
				bufferedReader = new BufferedReader(new FileReader("src/spamdata/spam/"+name));
				type = "spam";
			}else{
				bufferedReader = new BufferedReader(new FileReader("src/spamdata/ham/"+name));
				type = "ham";
			}
			String line = null;
			while ((line = bufferedReader.readLine()) != null){
				String[] temp = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
				for(String xt: temp){
					contents.add(xt);
				}
			}
			//store which features the message contains.
			for(String feat:Sfeatures){
				if(contents.contains(feat)){
					Pair st = new Pair(feat,true);
					ffeatures.add(st);
				}else{
					Pair sf = new Pair(feat,false);
					ffeatures.add(sf);
				}
			}
			for(String feat:Hfeatures){
				if(contents.contains(feat)){
					Pair ht = new Pair(feat,true);
					ffeatures.add(ht);
				}else{
					Pair hf = new Pair(feat,false);
					ffeatures.add(hf);
				}
			}
			map.put(ffeatures,type);
		}
		return map;
	}
	
	public static double probability(ArrayList<Pair> features, HashMap<ArrayList<Pair>,String> train_map){
		ArrayList<String> Afilez = readFiles(allfolder);
		ArrayList<String> Sfilez = readFiles(spamfolder);
		double pr_S = Sfilez.size() / Afilez.size();
		double pr_H = 1.0 - pr_S;
		double pr_S_W;
		double pr_T = 1.0;
		double n_S = 0.0;
		double n_H = 0.0;
		double n_fi_S = 0.0;
		double n_fi_H = 0.0;
		for (Pair feat:features){
			if(feat.getContains()){
				for(ArrayList<Pair> key:train_map.keySet()){
					if (train_map.get(key).equals("spam")){
						n_S++;
						for(Pair kip:features){
							if(kip.getFeature().equals(feat.getFeature())){
								if(kip.getContains())
									n_fi_S++;
							}
						}
					}else{
						n_H++;
						for(Pair kip:features){
							if(kip.getFeature().equals(feat.getFeature())){
								if(kip.getContains())
									n_fi_H++;
							}
						}
					}
				}
				if(((n_H/n_fi_H)*(pr_H))==0||((n_S/n_fi_S)*(pr_S))==0)
					System.out.println("error:zero");
				//This computes the probability of: pr_S_W = pr_W_S * pr_S / pr_W_H * pr_H
				pr_S_W = ((n_S/n_fi_S)*(pr_S))/((n_H/n_fi_H)*(pr_H));
				pr_T = pr_T*pr_S_W;
			}		
		}
		return pr_T;
	}
	/*
	 * TO DO
	 * Classifier: Reads and classifies an email message as either spam or ham.
	 * You may modify the method header (return type, parameters) as you see fit.
	 */
	public static void classify(String[] Sfeatures, String[] Hfeatures, HashMap<ArrayList<Pair>,String>map) throws IOException
	{
		int success=0;
		int false_pos=0;
		int false_neg=0;
		double d = 0.9;
		for(int j=0;j<10;j++){
			
			//randomly choose a message
			ArrayList<String> filez = readFiles(allfolder);
			ArrayList<String> Sfilez = readFiles(spamfolder);
			ArrayList<String>contents = new ArrayList<String>();
			ArrayList<Pair>ffeatures = new ArrayList<Pair>();
			Random randomGen = new Random();
			String name = filez.get(randomGen.nextInt(filez.size()));
			BufferedReader bufferedReader;
			String type;
			if(Sfilez.contains(name)){
				bufferedReader = new BufferedReader(new FileReader("src/spamdata/spam/"+name));
				type="spam";
			}else{
				bufferedReader = new BufferedReader(new FileReader("src/spamdata/ham/"+name));
				type="ham";
			}
			String line = null;
			while ((line = bufferedReader.readLine()) != null){
				String[] temp = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
				for(String xt: temp){
					contents.add(xt);
				}
			}
			//store which features the message contains.
			for(String feat:Sfeatures){
				if(contents.contains(feat)){
					Pair st = new Pair(feat,true);
					ffeatures.add(st);
				}else{
					Pair sf = new Pair(feat,false);
					ffeatures.add(sf);
				}
			}
			for(String feat:Hfeatures){
				if(contents.contains(feat)){
					Pair ht = new Pair(feat,true);
					ffeatures.add(ht);
				}else{
					Pair hf = new Pair(feat,false);
					ffeatures.add(hf);
				}
			}//ffeatures = arraylist of pairs: <feature,boolean>
			//note whether it is ham or spam
			//determine the set of features, D, contained in this message
			if(probability(ffeatures,map)>=d){
				//label message as Spam
				if(type.equals("spam"))
					success++;
				else
					false_pos++;
			}else{
				//label message as ham
				if(type.equals("ham"))
					success++;
				else
					false_neg++;
			}
		}
		System.out.println("goods"+success);
		System.out.println("false poss"+false_pos);
		System.out.println("false neg"+false_neg);


	}
	
	public static class Pair {

		  private final String feature;
		  private static boolean contains=false;

		  public Pair(String left, boolean right) {
		    this.feature = left;
		    this.contains = right;
		  }

		  public String getFeature() { return feature; }
		  public boolean getContains() { return contains; }

		  @Override
		  public int hashCode() { return feature.hashCode() *31; }

		  @Override
		  public boolean equals(Object o) {
		    if (o == null) return false;
		    if (!(o instanceof Pair)) return false;
		    Pair pairo = (Pair) o;
		    return this.feature.equals(pairo.getFeature()) &&
		           (this.contains&&pairo.getContains());
		  }

		}
	
}
