package de.rudi.algo2.knapsack;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;

public class Knapsack1 {
	
	public static void prod() throws IOException {

		FileReader fReader			= new FileReader("data\\knapsack1.txt");
		LineNumberReader lReader	= new LineNumberReader(fReader);
		String line					= lReader.readLine();
		String[] wLen				= line.split(" ");
		int size					= Integer.parseInt(wLen[0]);
		int len						= Integer.parseInt(wLen[1]);
		int[][] items				= new int[len][2];
		int itemIndex				= 0;
		while ((line=lReader.readLine())!=null) {
			String[] words				= line.split(" ");
			items[itemIndex][0]			= Integer.parseInt(words[0]);
			items[itemIndex][1]			= Integer.parseInt(words[1]);
			itemIndex++;
		}
		lReader.close				();
//		for (int[] item : items)
//			System.out.println(Arrays.toString(item));
		
		int[][] a					= new int[len+1][size+1];
		Arrays.fill					(a[0], 0);
		for (int i=1;i<len+1;i++) {
			int value			= items[i-1][0];
			int weight			= items[i-1][1];
			for (int x=0;x<a[i].length;x++) {
				int i1				= a[i-1][x];
				int	i2				= i1;
				if (weight<=x)
					i2					= a[i-1][x-weight]+value;
				a[i][x]				= (i1>i2) ? i1 : i2;
			}
		}
		System.out.println			("Ergebnis: "+a[len][size]);
	}
	
	public static void main(String[] args) {
		try 					{ prod();	} 
		catch (IOException e) 	{			}
	}

}
