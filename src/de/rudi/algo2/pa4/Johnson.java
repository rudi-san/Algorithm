package de.rudi.algo2.pa4;

import java.io.FileReader;
import java.io.LineNumberReader;
import org.apache.log4j.Logger;

public class Johnson {
	
	private static Logger logger	= Logger.getLogger(Johnson.class);
	private int vLen;

	public Johnson () {
		JEdge edges		= getEdges();

		for (int i=1;i<=vLen;i++) {
			edges.addEdge(0, i, 0);
		}
		int[] dist				= BellmanFord.getDistances(vLen, 0, edges);
		
		Integer[][] rewEdges	= getEdges().getEdgesArray();
		Integer[][] newEdges 		= new Integer[rewEdges.length][3];
		for (int i=0;i<rewEdges.length;i++) {
			newEdges[i][0]			= rewEdges[i][0];
			newEdges[i][1]			= rewEdges[i][1];
			newEdges[i][2]			= rewEdges[i][2] + dist[rewEdges[i][0]] - dist[rewEdges[i][1]];
		}
		int minMinDist			= Integer.MAX_VALUE;
		for (int i=1;i<vLen;i++) {
			int min					= Integer.MAX_VALUE;
			int[] minDist			= Dijkstra.getMinDist(vLen,	 newEdges, i);
			for (int m=0;m<minDist.length;m++) {
				int aktDist		= minDist[m] - dist[i] + dist[m];
				min			= (aktDist<min) ? aktDist : min;
			}
			minMinDist	=	(min<minMinDist) ? min : minMinDist;
		}
		System.out.println(minMinDist);
	}
	
	public JEdge getEdges () {
			
		JEdge edgeMap		= new JEdge();	
		String[] fileName = { "g1.txt", "g2.txt", "g3.txt", "large.txt" };
		try {
			FileReader fReader 			= new FileReader("data\\pa4\\" + fileName[3]);
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
	
	public static void main(String[] args) {
		new Johnson			();

	}

}
