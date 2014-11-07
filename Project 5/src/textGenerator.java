
import java.util.*;
import java.math.*;
import java.io.*;

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
	
	//Generator: Generates numGen characters based on the transitional probabilities estimated by the trainer 
	public static void generateText(HashMap<Integer,ArrayList<String>> map, int size, int numGen, int k){
		
	}
	
	
	
}