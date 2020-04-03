//----------------------------------------------------------------------------------------
// Word.java
// Constructor for a Word object that has a String containing the word itself and
// and Int containing its frequency. Constructor, and two comparators.
//---------------------------------------------------------------------------------------
import java.util.*;

public class Word implements Comparator<Word>, Comparable<Word>{
	public String word;
	public int freq;
	
	public Word(){
	}
	
	//constructor
	public Word(String w, int f){
		word = w;
		freq = f;
	}
	
	public String toString(){
		return word;
	}
	
	//Overriding compareTo method
	public int compareTo(Word w){
		return (this.word).compareTo(w.word);
	}
	
	//Overriding the compare method to sort the frequency
	//returns positive int if w is "greater than" w1
	//in this case "greater than" means smaller frequency
	public int compare(Word w, Word w1){
		if(w.freq < w1.freq){
			return 1;
		}
		if(w.freq == w1.freq){
			return 0;
		}
		return -1;
	}
}

