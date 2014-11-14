import java.io.*;
import java.util.*;
import java.math.*;


class tweetGenerator  {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		String file="";
		
		while(!file.equals("q")){
			
			//get info on what we're reading and how we'll process
			System.out.print("Please enter a training file, q to quit: ");
			file = scan.nextLine();
		
			
			System.out.print("Please enter the k value: ");
			int k = Integer.parseInt(scan.nextLine());
			
			int numGen = 0;
			while(numGen <=0 ){
				System.out.print("Enter number of characters to generate: ");
				numGen = Integer.parseInt(scan.nextLine());
			}
			System.out.print("Please enter the filename to where they should be outputed: ");
			String output = scan.nextLine();
			
			HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
			map = train(file,k);
			generateText(map, numGen, k, output);	
			
		}

	} // end main

	private static String[] readFile(String filename)
	throws Exception
	{
	String line = null;
	ArrayList<String> records = new ArrayList<String>();
	 
	// wrap a BufferedReader around FileReader
	BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	 
	// use the readLine method of the BufferedReader to read one line at a time.
	// the readLine method returns null when there is nothing else to read.
	while ((line = bufferedReader.readLine()) != null)
	{
	String[] temp = line.split(" ");
	for(String x: temp)
	records.add(x);
	}
	
	String[] wordString = new String[records.size()];
	for(int i =0; i < records.size(); i++) {
	wordString[i] = records.get(i);
	}
	
	// close the BufferedReader when we're done
	bufferedReader.close();
	return wordString;
	}

	public static HashMap<WordKgram, ArrayList<WordKgram>> train(String filename, int k) {
		HashMap<WordKgram,ArrayList<WordKgram>> map = new HashMap<WordKgram,ArrayList<WordKgram>>();
		try {
			String[] list = readFile(filename); 
		int count=0;
			while(list.length-count>=k){
				WordKgram gramX = new WordKgram(list, count, k);
				//System.out.println("gramX: "+gramX.getMyWords()[0]);
				WordKgram gramS;
				if ((list.length-count)>k){
					gramS = new WordKgram(list, count+1, k);
					//System.out.println("gramS: "+gramS.getMyWords()[0]);
				}else{
					gramS = new WordKgram(list, count+1, list.length-1);
				}
				if(map.containsKey(gramX)){
					map.get(gramX).add(gramS);
				}else{
					ArrayList<WordKgram> al = new ArrayList<WordKgram>();
					al.add(gramS);
					map.put(gramX, al);
				}
				count++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public static void generateText(HashMap<String,ArrayList<String>> map, int numGen, int k, String output){
		int size = map.size();
		Random randomGen =  new Random();
		FileWriter fw;
		try {
			fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);

			Set<String> set = map.keySet();
			String[] keys = new String[size];
			set.toArray(keys);
			
			int randomInt = randomGen.nextInt(map.size());
			String gramX = keys[randomInt];
			
			//ArrayList<String> X = map.get(randomInt);
			//String gramX = X.get(0);

			
			boolean done = false;
			int count = 0;
			while(done == false) {
				if(numGen != count) {
					System.out.println("String X: " + gramX);
					bw.write(gramX);

					ArrayList<String> temp = map.get(gramX);
					//System.out.println("size of map: " + map.size());
					//System.out.println("size of specific map: " + temp.size());
					System.out.println("temp " + temp);
					System.out.println(temp.size());
					int randomInt2 = randomGen.nextInt(temp.size());
					System.out.println("int " + randomInt2);
					//System.out.println(temp.size()-1);

					String gramS = (String) temp.get(randomInt2);
					System.out.println("String S: " + gramS);
					String gramC =gramS.substring(gramS.length()-1);
					System.out.println("String C: " + gramC);
					bw.write(gramC);

					String gramN = gramX.substring(k-(k-1)) + gramC;
					System.out.println("n: "+gramN);
					gramX = gramN;
					count ++;
				}
				else {
					done = true;
				}
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
			
	}
	
}
