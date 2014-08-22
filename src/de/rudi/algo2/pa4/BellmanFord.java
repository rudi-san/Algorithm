package de.rudi.algo2.pa4;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class BellmanFord {
	
	private static int    vLen;
	private static Logger logger		= Logger.getLogger(BellmanFord.class);

	public static int[] getDistances (int vLen, int s, JEdge edges) {
		vLen++;
		int[] a     	= new int[vLen];
		Arrays.fill		(a, Integer.MAX_VALUE);
		a[s]  			= 0;

		for (int i=0;i<vLen-1;i++) {
			logger.debug		("Entfernung="+i);
			a					= getDist(vLen, i, a, edges);
		}
		int[] aLast				= getDist(vLen, vLen, a, edges);
		for (int i=0;i<a.length;i++)
			if (a[i]!=aLast[i])
				return null;
		
		return a;
	}
	
	private static int[] getDist (int vLen, int i, int[] a, JEdge edges) {
		int[] ret				= new int[a.length];
		for (int v=0;v<vLen;v++) {
			int case1 				= a[v];
			int case2 				= Integer.MAX_VALUE;
			Integer[][] toEdges 	= edges.getToEdges(v);
			if (toEdges!=null) {
				for (Integer[] e : toEdges) {
					int vorPath		= a[e[0]];
					if (vorPath!=Integer.MAX_VALUE) {
						int neuPath		= vorPath+e[1];
						case2			= (case2<neuPath) ? case2 : neuPath;
					}
				}
			}
			ret[v]					= (case1<case2) ? case1 : case2;
		}

		return					ret;
	}
	
	public static void test01 () {
	try {
		FileReader fReader = new FileReader("data\\pa4\\dijkstraData.txt");
		LineNumberReader lReader = new LineNumberReader(fReader);
		String line = null;
		JEdge edgeMap		= new JEdge();	
		Integer id 			= 0;
		while ((line = lReader.readLine()) != null) {
			String[] str 		= line.split("\\s");
			id 					= Integer.parseInt(str[0].trim());
			for (int i = 1; i < str.length; i++) {
				String[] entry = str[i].split(",");
				int target = Integer.parseInt(entry[0].trim());
				int weight = Integer.parseInt(entry[1].trim());
				edgeMap.addEdge(id, target, weight);
			}
		}
		lReader.close		();
		int[] dist			= getDistances		(id, 1, edgeMap);
		System.out.println(Arrays.toString(dist));
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public JEdge getEdges (String fileName) {
		
		JEdge edgeMap		= new JEdge();	
		try {
			FileReader fReader 			= new FileReader("data\\pa4\\" + fileName);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= lReader.readLine();
			String[] wLen 				= line.split(" ");
			vLen 						= Integer.parseInt(wLen[0]);
			logger.debug				("Anzahl Knoten="+vLen);
//			int z						= 0;
			while ((line = lReader.readLine()) != null) {
				String[] words 			= line.split(" ");
				edgeMap.addEdge			( Integer.parseInt(words[0])
										, Integer.parseInt(words[1])
										, Integer.parseInt(words[2])
										);
//				if (++z%1000==0)  System.out.println("eingelesen: "+z+" Fälle");
			}
			lReader.close				();
			logger.debug					("Einlesen abgeschlossen");
		}
		catch (Exception e) {
		}
		return 			edgeMap;	
	}
	
	public int test02 ()   {
		String[] fileName = { "g1.txt", "g2.txt", "g3.txt" };
		JEdge edges			= getEdges(fileName[2]);
		int minMinDist			= Integer.MAX_VALUE;
		for (int i=1;i<vLen;i++) {
			if (i%10==0) System.out.println(i);
			int min					= Integer.MAX_VALUE;
			int[] minDist			= getDistances(vLen, i, edges);
			for (int m=0;m<minDist.length;m++) {
				min			= (minDist[m]<min) ? minDist[m] : min;
			}
			minMinDist	=	(min<minMinDist) ? min : minMinDist;
		}
		return minMinDist;
	}
 	public static void main(String[] args) {
//		test01();
 		System.out.println	(new BellmanFord().test02());

	}

}
