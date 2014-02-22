package de.rudi.algo2.knapsack;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Hashtable;

//import org.apache.log4j.Logger;

public class Knapsack2 {
	
	private static Hashtable<Integer, Integer>[] 	results;
	private static int								len;
	private static int[][] 							items;
//	private static Logger							logger	= Logger.getLogger(Knapsack2.class);
	
	@SuppressWarnings("unchecked")
	public static void prod() throws IOException {

		FileReader fReader			= new FileReader("data\\knapsack_big.txt");
		LineNumberReader lReader	= new LineNumberReader(fReader);
		String line					= lReader.readLine();
		String[] wLen				= line.split(" ");
		int size					= Integer.parseInt(wLen[0]);
		len							= Integer.parseInt(wLen[1]);
		items						= new int[len][2];
		int itemIndex				= 0;
		while ((line=lReader.readLine())!=null) {
			String[] words				= line.split(" ");
			items[itemIndex][0]			= Integer.parseInt(words[0]);
			items[itemIndex++][1]		= Integer.parseInt(words[1]);
		}
		lReader.close				();
//		for (int[] item : items)
//			System.out.println(Arrays.toString(item));
		
		results						= new Hashtable[len];
		for (int i=0;i<results.length;i++) 
			results[i]					= new Hashtable<Integer, Integer>();
		System.out.println			("Ergebnis: "+knapsack(size, 0));
	}
	
	private static int knapsack(int currentWeight, int i)
	{
		if	(i < len) {
			if( results[i].containsKey(currentWeight) ) 
				return 				results[i].get(currentWeight); 

			int value			= items[i][0];
			int actWeight		= items[i][1];
			int restWeight		= currentWeight-actWeight;
//			wenn Element i nicht in der Lösungsmenge vorhanden ist, dann
//			ermittelt man mit dem aktuellen Gewicht und dem nächsten Element i+1
		    int a				= knapsack(currentWeight, i+1);
		    int b				= 0;
//		    wenn das Element überhaupt noch in den Rucksack hineinpasst, ....
		    if( restWeight >= 0 )
//		    	... dann könnte man schauen, was der Wert des Elements mit dem
//		    	    optimierten Wert der übrigen Elemente ergibt
		        b 					= value + knapsack(restWeight, i+1);
//		    Die beiden Werte (Element ist Teil der Lösungsmenge oder nicht)
//		    werden verglichen und der größere Wert wird zurückgegeben
		    int max				= (a>b) ? a : b;
//		    if (b>a)   		    logger.debug(i+" "+value+" "+actWeight);
		    results[i].put		(currentWeight, max);
//		    if (x>0&&x!=max)			 logger.debug("curWeight="+currentWeight+";Element="+i+";max="+max+";x="+x);
		    return				max;
	  }
	  else
		  return 0;
	}
	
	public static void main(String[] args) {
		try 					{ prod();	} 
		catch (IOException e) 	{			}
	}

}
