
/*
 * This class encapsulates K words/strings so that the
 * group of K words can be treated as a key in a map or an
 * element in a set, or an item to be searched for in an array.
 */

public class WordKgram implements Comparable<WordKgram>{
    
    private String[] myWords;
    
    /*
     * Store the k words that begin at index start of array list as
     * the K words of this K-gram.
     * @param list contains at least k words beginning at index start
     * @start is the first of the K words to be stored in this K-gram
     * @n is the number of words to be stored (the k in this K-gram)
     */
    public WordKgram(String[] list, int start, int n) {
        myWords = new String[n];
        System.arraycopy(list, start, myWords, 0, n);
    }
    
    /**
     * Return value that meets criteria of compareTo conventions.
     * @param wg is the WordKgram to which this is compared
     * @return appropriate value less than zero, zero, or greater than zero
     */
    public int compareTo(WordKgram wg) {
        // TODO  implement this method
        return 0;
    }
    
    /**
     * Return true if this K-gram is the same as the parameter: all words the same.
     * @param o is the WordKgram to which this one is compared
     * @return true if o is equal to this K-gram
     */
    public boolean equals(Object o){
        WordKgram wg = (WordKgram) o;
        // TODO return correct value
      
        return true;
    }
    

}