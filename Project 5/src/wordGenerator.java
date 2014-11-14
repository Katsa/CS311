import java.io.*;
import java.util.*;
import java.math.*;


class wordGenerator  {

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
			HashMap<WordKgram,ArrayList<WordKgram>> map = new HashMap<WordKgram,ArrayList<WordKgram>>();
			map = train(file,k);
			generateText(map, numGen, k);	
			
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
	    	while(list.length-count>k){
				WordKgram gramX = new WordKgram(list, count, k);
				//System.out.println("gramX: "+Arrays.toString(gramX.getMyWords())+" count: "+ count);
				WordKgram gramS;
				if ((list.length-count)>k){
					gramS = new WordKgram(list, count+1, k);
					//System.out.println("*+     gramS: "+Arrays.toString(gramS.getMyWords())+" count: "+ count);
				}else{
					gramS = new WordKgram(list, count+1, list.length-k);
					//System.out.println("*-     gramS: "+Arrays.toString(gramS.getMyWords())+" count: "+ count+"l.length-k: "+(list.length-k));

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
	
	public static void generateText(HashMap<WordKgram,ArrayList<WordKgram>> map, int numGen, int k){
		int size = map.size();
		Random randomGen =  new Random();
		
		Set<WordKgram> set = map.keySet();
		WordKgram[] keys = new WordKgram[size];
		set.toArray(keys);
		
		int randomInt = randomGen.nextInt(map.size());
		WordKgram gramX = keys[randomInt];
		
		//ArrayList<String> X = map.get(randomInt);
		//String gramX = X.get(0);

		
		boolean done = false;
		int count = 0;
		while(done == false) {
			if(numGen != count) {
				System.out.println("String X: " + gramX);

				ArrayList<WordKgram> temp = map.get(gramX);
				//System.out.println("size of map: " + map.size());
				//System.out.println("size of specific map: " + temp.size());
				int randomInt2 = randomGen.nextInt(temp.size());
				//System.out.println(randomInt2);

				WordKgram gramS = (WordKgram) temp.get(randomInt2);
				String gramC =gramS.getMyWords()[(k-1)];
				System.out.println("String C: " + gramC+" countXgramwords: "+gramX.getMyWords().length);
				
				String[] lalo= new String[k-1];
				//problem is here
				System.out.println(gramX.getMyWords().length-2);
				System.out.println("k: "+ k);
				System.out.println(Arrays.toString(gramX.getMyWords()));
				System.arraycopy(gramX.getMyWords(), 1, lalo, 0, k-1);
				ArrayList<String> lalo2 = new ArrayList<String>();
				for(String pepe:lalo){
					lalo2.add(pepe);
				}
				lalo2.add(gramC);
				
				String[] simpleArray = new String[lalo2.size()];
				lalo2.toArray(simpleArray);
				WordKgram gramN;
				gramN = new WordKgram(simpleArray,0,lalo2.size());
				gramX = gramN;
				count ++;
			}
			else {
				done = true;
			}
		}	
	}
	
	
}