
import java.util.*;
import java.math.*;
import java.io.*;
import java.util.Random;


public class textGenerator {
	
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		String file="";
		
		while(!file.equals("q")){
			
			//get info on what we're reading and how we'll process
			System.out.print("Please enter a training file, q to quit: ");
			file = scan.nextLine();
		
			
			System.out.print("Please enter the k value: ");
			int k = Integer.parseInt(scan.nextLine());
			
			
			//int numGen = 0;
			//while(numGen <=0 ){
			//	System.out.print("Enter number of characters to generate: ");
			//	numGen = Integer.parseInt(scan.nextLine());
			//}
			getText(file,k);
			System.out.println();
		}
	}
	
	//Trainer: sets up the transitional probabilities for the given k. You may modify this method header.
	public void train(String file, int k){
		HashMap<Integer,ArrayList<String>> map = new HashMap();
		BufferedReader read = null;
		try {
			String thisLine;
			String gramX;
			String gramS;
			read =  new BufferedReader(new FileReader(file));
			thisLine = read.readLine();
			while(thisLine != null) {
				int count = 0;
				int location = 0;

				for(int i = 0; i < thisLine.length()/k; i++) {
					gramX = thisLine.substring(count, k + 1);
					//gramS = gramX.substring(1) + thisLine.substring(k+1, k+2);
					gramS = thisLine.substring(count+1, k+2);

					if(map.containsValue(gramX)) {
						map.put(,gramS)
					}
					else {
						ArrayList<String> al = new ArrayList();
						al.add(gramX);
						al.add(gramS);
						map.put(,al)
						location ++;
					}
					count ++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(read != null)read.close();
		 	} catch (IOException er) {
	 			er.printStackTrace();
		 	}
		}

		
	}

	public void getText(String filename, int k) {
		HashMap grams = new HashMap();
		BufferedReader read = null;
		 try {
		 	String thisLine;
		 	String gramX;
		 	read = new BufferedReader(new FileReader(filename));
		 	thisLine = read.readLine();
		 	while(thisLine != null) {

				int count = 0;

				for(int i = 0; i < thisLine.length()/k; i++) {
					gramX = thisLine.substring(count, k + 1);
					String otherGramS = gramX.substring(1) + thisLine.substring(k+1, k+2);
					String nextGramS = thisLine.substring(count+1, k+2);

					if(grams.containsValue(gramX)){
						grams.put(gramX, nextGramS);
					} else {
						ArrayList al = new ArrayList();
						al.add(nextGramS);
						grams.put(gramX,al);
					}
					count++;
				}
			}
		 } catch (IOException e) {
		 	e.printStackTrace();
		 } finally {
		 	try {
		 		if(read != null)read.close();
		 	} catch (IOException er) {
		 		er.printStackTrace();
		 	}
		 }


	}
	
	//Generator: Generates numGen characters based on the transitional probabilities estimated by the trainer 
	public static void generateText(HashMap<Integer,ArrayList<String>> map, int size, int numGen, int k){
		Random randomGen =  new Random();
		int randomInt = randomGen.nextInt(map.size());

		ArrayList<String> X = map.get(randomInt);
		String gramX = X.get(0);

		for(int i = 0; i < size; i++) {
			System.out.println(gramX);
			ArrayList temp = map.get(randomInt);
			int randomInt2 = randomGen.nextInt((map.get(randomInt)).size());

			String gramS = (String) temp.get(randomInt2);
			String gramC =gramS.substring(k-1);
			System.out.println(gramC);

			String gramN = gramX.substring(k-1) + gramC;
			gramX = gramN;
		}
	}
	
	
	
}