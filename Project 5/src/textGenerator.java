import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class test  {

  public static void main (String args[]) {
  
    HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
    try {
		for(String thisLine:readFile("baba.txt")){
			int count=0;
			int k=3;
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
}
