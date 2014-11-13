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

	private static ArrayList<String> readFile(String filename)
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
	        records.add(line);
	    }
	   
	    // close the BufferedReader when we're done
	    bufferedReader.close();
	    return records;
	}

	public static HashMap<String, ArrayList<String>> train(String filename, int k) {
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
	    try {
			for(String thisLine:readFile(filename)){
				
				int count=0;
				
				while((thisLine.length()-count)>=k){
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
					int randomInt2 = randomGen.nextInt(temp.size());
					//System.out.println(randomInt2);

					String gramS = (String) temp.get(randomInt2);
					String gramC =gramS.substring(k-1);
					System.out.println("String C: " + gramC);
					bw.write(gramC);

					String gramN = gramX.substring(k-(k-1)) + gramC;
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




















