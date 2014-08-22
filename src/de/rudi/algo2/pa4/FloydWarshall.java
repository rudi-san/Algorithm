package de.rudi.algo2.pa4;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class FloydWarshall {

    private static short[][][] 				a;
    private static HashMap<Long, Short> 	edgeMap;
	private static Logger 					logger = Logger.getLogger(FloydWarshall.class);

	private static void test01() {
		String[] fileName = { "g1.txt", "g2.txt", "g3.txt" };
		try {
			FileReader fReader 			= new FileReader("data\\pa4\\" + fileName[0]);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= lReader.readLine();
			String[] wLen 				= line.split(" ");
			int vLen 					= Integer.parseInt(wLen[0])+1;
			logger.info					("Anzahl Knoten="+vLen);
			a							= new short[vLen][vLen][vLen];
			edgeMap						= new HashMap<Long, Short>();
			int z						= 0;
			while ((line = lReader.readLine()) != null) {
				String[] words 			= line.split(" ");
				addEdge					( Short.parseShort(words[0])
										, Short.parseShort(words[1])
										, Short.parseShort(words[2])
										);
				if (++z%10==0)  System.out.println("eingelesen: "+z+" Fälle");
			}
			lReader.close				();
			logger.info					("Einlesen abgeschlossen");
			floydWarshall				(vLen);
			for (int i=1;i<vLen;i++)
				for (int j=1;j<vLen;j++) 
				System.out.println ("Kürzester Weg von "+i+" nach "+j+" = "+a[i][j][vLen]);

		} catch (Exception e) {
			System.out.println("Leider daneben\n" + e.getMessage());
		}
	}

	private static void floydWarshall(int vLen) {
		for (int i=0;i<vLen;i++)
			for (int j=0;j<vLen;j++)
			     intializeA			(i,j,0);
		
		for (int k=1;k<vLen;k++)
			for (int j=1;j<vLen;j++)
				for (int i=1;i<vLen;i++)
					computeA			(i,j,k);		
	}

	private static void intializeA(int i, int j, int k) {
		if (i==j) {
			a[i][j][k]	= 0;
		}
		else {
			Short len		= getEdge(i, j);
			if (len != null)
				a[i][j][k] 	= len;
			else
				a[i][j][k]	= Short.MAX_VALUE;
		}
	}
	private static void computeA (int i, int j, int k) {
		short case1		= a[i][j][k-1];
		short case2		= (short)(a[i][k][k-1] + a[k][j][k-1]);
		a[i][j][k] 		= (case1 < case2) ? case1 : case2;
	}

	private static void addEdge(int from, int to, short len) {
		long l			= from;
		l 				= l << 32;
		l				+= to;
		Short edge		= len;
		edge			= edgeMap.get(l);
		edge			= (edge<len) ? edge : len;
		edgeMap.put		(l, edge);
	}

	private static Short getEdge(int from, int to) {
		long l			= from;
		l 				= l << 32;
		l				+= to;
		return 			edgeMap.get(l);
	}

	public static void main(String[] args) {
		test01			();

	}

}
