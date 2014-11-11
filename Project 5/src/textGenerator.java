
import java.util.*;
import java.math.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
			
			int numGen = 0;
			while(numGen <=0 ){
				System.out.print("Enter number of characters to generate: ");
				numGen = Integer.parseInt(scan.nextLine());
			}
			
		}
	}
	
	//Trainer: sets up the transitional probabilities for the given k. You may modify this method header.
	public void train(String file, int k){
		
		
	}

	public void getText(String filename, int k) {
		Hashtable grams = new Hashtable();
		BufferedReader read = null;
		 try {
		 	String thisLine;
		 	String gramX;
		 	read = new BufferedReader(new FileReader(filename));
		 	thisLine = read.readLine();
		 	while(thisLine != null) {
				//This is where we read what we want and put it in the righ tplace

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
		
	}
	
	
	
}