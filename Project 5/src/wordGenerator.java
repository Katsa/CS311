import java.io.*;
import java.util.*;
import java.math.*;


class wordGenerator  {

	public static void main(String[] args) throws IOException {
		
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
			
			System.out.println("Please input output filename: ");
			String output = scan.nextLine();
			
			HashMap<WordKgram,ArrayList<WordKgram>> map = new HashMap<WordKgram,ArrayList<WordKgram>>();
			map = train(file,k);
			for (WordKgram key:map.keySet()){
				System.out.println("["+(key.toString())+"]"+"  "+print(map.get(key)));
			}
			System.out.println("------------------------");
			System.out.println(" ");
			generateText(map, numGen, k, output);	
			
		}

	} // end main
	private static String print(ArrayList<WordKgram>list){
		String result="";
		result=result+"{";
		for(WordKgram gr:list){
			result=result+gr.toString()+", ";
		}
		result=result+"}";
		return result;
	}
	private static String[] readFile(String filename)
	throws Exception
	{
	    String line = null;
	    ArrayList<String> records = new ArrayList<String>();
	 
	    // wrap a BufferedReader around FileReader
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	 
	    // use the readLine method of the BufferedReader to read one line at a time.

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
	   
	    // close the BufferedReader
	    bufferedReader.close();
	    return wordString;
	}
	//Trainer: sets up the transitional probabilities for the given k. You may modify this method header.
	public static HashMap<WordKgram, ArrayList<WordKgram>> train(String filename, int k) {
		HashMap<WordKgram,ArrayList<WordKgram>> map = new HashMap<WordKgram,ArrayList<WordKgram>>();
	    try {
	    	String[] list = readFile(filename); 
	    	int count=0;
	    	while(list.length-count>k){
				WordKgram gramX = new WordKgram(list, count, k);
				WordKgram gramS;
				//Checks how far should the next k-gram collect its words
				if ((list.length-count)>k){
					gramS = new WordKgram(list, count+1, k);
				}else{
					gramS = new WordKgram(list, count+1, list.length-k);
					
				}
				//Adds to map.
				//the method containsKey uses the methods equals() and hashCode(), which are overriden
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
			System.out.println("ouch");
			e.printStackTrace();
		}
		return map;
	}
	//Generator: Generates numGen characters based on the transitional probabilities estimated by the trainer 
	public static void generateText(HashMap<WordKgram,ArrayList<WordKgram>> map, int numGen, int k, String output) throws IOException{
		int size = map.size();
		Random randomGen =  new Random();	
		Set<WordKgram> set = map.keySet();
		WordKgram[] keys = new WordKgram[size];
		set.toArray(keys);
		FileWriter fw = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fw);

		//Randomly select first gramX
		int randomInt = randomGen.nextInt(map.size());
		WordKgram gramX = keys[randomInt];	
		int count = 0;
		while(numGen != count) {
				ArrayList<WordKgram> temp = map.get(gramX);
				System.out.println("Array in gramX "+ Arrays.toString(gramX.getMyWords()));
				//This takes care of very random cases
				if(temp == null) {
					System.out.println("...");
					int newRandInt = randomGen.nextInt(map.size());
					gramX = keys[newRandInt];
				}	
				else {
					//Randomly selects the gramS from gramX's domain
					int randomInt2 = randomGen.nextInt(temp.size());
					WordKgram gramS = (WordKgram) temp.get(randomInt2);
					String gramC =gramS.getMyWords()[(k-1)];
					System.out.print("String S: " + Arrays.toString(gramS.getMyWords())+"  ");
					System.out.println("C: " + gramC);
					//Builds gramN; which becomes the next gramX
					String[] lalo= new String[k-1];
					String aX = Arrays.toString(gramX.getMyWords());
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
					String aN =  Arrays.toString(gramN.getMyWords());
					String aC = aX + " / " +aN;
					System.out.println(aC);
					
					bw.write(aX);
					gramX=gramN;
					count ++;
				}
			}
		bw.close();	
		}	
	}
	
	
