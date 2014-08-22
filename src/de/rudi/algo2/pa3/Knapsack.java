package de.rudi.algo2.pa3;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
//import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class Knapsack {

//	private static ArrayList<Integer> choosen	= new ArrayList<Integer>();
	private static int[][] items;
	private static Logger logger 				= Logger.getLogger(Knapsack.class);
	
	private static int prod() throws IOException {
		String[] fileName			= { "knapsack1.txt", "knapsack_big.txt"
									  , "test01.txt", "test02.txt", "test99.txt" };
		FileReader fReader 			= new FileReader("data\\pa3\\"+fileName[4]);
		LineNumberReader lReader 	= new LineNumberReader(fReader);
		String line 				= lReader.readLine();
		String[] wLen 				= line.split(" ");
		int size 					= Integer.parseInt(wLen[0]);
		int itemAnz 				= Integer.parseInt(wLen[1]);
		items 						= new int[itemAnz][2];
		int itemIndex 				= 0;
		while ((line = lReader.readLine()) != null) {
			String[] words 				= line.split(" ");
			items[itemIndex][0] 		= Integer.parseInt(words[0]);
			items[itemIndex][1] 		= Integer.parseInt(words[1]);
			itemIndex++;
		}
		lReader.close();
		// for (int[] item : items)
		// System.out.println(Arrays.toString(item));

//		int[][] zxTab 		= new int[itemAnz + 1][size + 1];
		int[] zxTab 		= new int[size + 1];
		// 0-er Element der Zwischentabelle auf 0 setzen (alle Gewichte)
//		Arrays.fill		(zxTab[0], 0);
		Arrays.fill		(zxTab, 0);
		// Schleife über die Items
		for (int i = 1; i < itemAnz + 1; i++) {
			if (i%1000==0)
				logger.info("Verarbeitete Items: "+i);
			// untersuchtes Item  (Index ist um eins vermindert)
//			zxTab[i]		= process(zxTab[i-1], value, weight);
			zxTab			= process(zxTab, i-1);
		}
//		System.out.println("Ergebnis: " + zxTab[itemAnz][size]);
		return 					zxTab[size];
	}
	
	private static int[] process (int[] left, int index) {
		int[] right			= new int[left.length];
		int value 			= items[index][0];
		int weight 			= items[index][1];
		for (int x = 0; x < left.length; x++) {
			// der Wert des aktuellen Gewichts des Vor-Items wird nach i1 gestellt
			int i1 			= left[x];
			int i2 			= i1;
			// wenn das Gewicht des Items kleiner oder gleich dem aktuellen Gewicht ist . . . 
			if (weight <= x)
				// . . . wird der Wert des Gewichts des Vor-Items minus Item-Gewicht 
				//       addiert zum Item-Gewicht nach i2 gestellt
				i2 				= left[x - weight] + value;
			
			// der höhere Wert von i1 und i2 wird als Wert des aktuellen Gewichts abgelegt
			right[x] 	= (i1 > i2) ? i1 : i2;
//			if (i2>i1)
//				choosen.add		(x-weight);
		}
		return 				right;
	}

	public static void main(String[] args) {
		try {
			System.out.println("Ergebnis: " +prod());
//			for (int x : choosen) 
//				System.out.println("Item "+x+" [Value="+items[x][0]+"] [Weight="+items[x][1]+"]");
		} catch (IOException e) {
		}
	}

}