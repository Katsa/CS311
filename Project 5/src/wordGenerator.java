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
			//generateText(map, numGen, k);	
			
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
				System.out.println("gramX: "+Arrays.toString(gramX.getMyWords())+" count: "+ count);
				WordKgram gramS;
				if ((list.length-count)>k){
					gramS = new WordKgram(list, count+1, k);
					System.out.println("*+     gramS: "+Arrays.toString(gramS.getMyWords())+" count: "+ count);
				}else{
					gramS = new WordKgram(list, count+1, list.length-k);
					System.out.println("*-     gramS: "+Arrays.toString(gramS.getMyWords())+" count: "+ count+"l.length-k: "+(list.length-k));

				}
				if(map.containsKey(gramX)){
					map.get(gramX).add(gramS);
				}else{
					ArrayList<WordKgram> al = new ArrayList<WordKgram>();
					al.add(gramS);
					map.put(gramX, al);
				}
				count++;
				
				/*
				 * while((thisLine.length()-count)>=k){
					String gramX = thisLine.substring(count, k+count);
					String otherGramS;
					if ((thisLine.length()-count)>k){
						otherGramS = gramX.substring(1) + thisLine.substring(k+count, k+count+1);
					}else{
						otherGramS = gramX.substring(1);					
					}if(map.containsKey(gramX)){
						map.get(gramX).add(otherGramS);
					} else {
						ArrayList<String> al = new ArrayList<String>();
						al.add(otherGramS);
						map.put(gramX,al);
					}
					count++;
				}
				 */
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/*
	public static void generateText(HashMap<WordKgram,ArrayList<WordKgram>> map, int numGen, int k){
		int size = map.size();
		Random randomGen =  new Random();
		
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

				ArrayList<String> temp = map.get(gramX);
				System.out.println("size of map: " + map.size());
				//System.out.println("size of specific map: " + temp.size());
				int randomInt2 = randomGen.nextInt(temp.size());
				//System.out.println(randomInt2);

				String gramS = (String) temp.get(randomInt2);
				String gramC =gramS.substring(k-1);
				System.out.println("String C: " + gramC);

				String gramN = gramX.substring(k-(k-1)) + gramC;
				gramX = gramN;
				count ++;
			}
			else {
				done = true;
			}
		}	
	}
	*/
	
}