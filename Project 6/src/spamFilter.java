
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class spamFilter {

	final static File allfolder = new File("path to spamdata/");
	final static File spamfolder = new File("path to spam/");
	final static File hamfolder = new File("path to ham/");
	
	public static void main(String[] args) throws IOException
	{		
		
		Scanner scan = new Scanner(System.in);
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt")); 
		
		System.out.println("How many messages to classify?");
		
		int nummsgs = Integer.parseInt(scan.nextLine());
		System.out.println("Classifying 1000 random messages...");
		
		
		
		
        System.out.println("all messages:");
		readFiles(allfolder);		
		System.out.println();
		
		System.out.println("spam messages:");
		readFiles(spamfolder);
		System.out.println();
		
		System.out.println("ham messages:");
		readFiles(hamfolder);
		
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
	            System.out.println(filename);
	        }
	    }
	}

	
	/*
	 * TO DO
	 * Preprocessor: Reads email messages to fill a table of features.
	 * You may modify the method header (return type, parameters) as you see fit.
	 */
	public static void preprocess()
	{
		
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