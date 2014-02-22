package de.rudi.algo2;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.logging.Logger;

public class Clustering3 {
	private static HashSet<CEdge> 	edge	= new HashSet<CEdge>();
	private static UnionFind 		uf;
	public 	static Logger			logger	= Logger.getLogger("Clustering");
	
	public static void prod() {
		try {
			FileReader fReader			= new FileReader("edgeFile_small.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);

			String line					= lReader.readLine();
			while ((line=lReader.readLine())!=null) {
				String [] word				= line.split(" ");
				int n1						= Integer.parseInt(word[0]);
				int n2						= Integer.parseInt(word[1]);
				int cost					= Integer.parseInt(word[2]);
				edge.add					(new CEdge(n1, n2, cost));
			}
			lReader.close				();
			uf							= new UnionFind(10);

			while (CEdge.isAnyValid()) {
				logger.info					(edge.toString());
				CEdge min					= minEdge();
				int n1						= uf.find(min.getV1());
				int n2						= uf.find(min.getV2());
				uf.unite					(n1, n2);
				edge.remove					(min);
				updateEdge					();
			}
			
			logger.info						("Anzahl Cluster "+uf.components());
		} catch (IOException e) {
			
		}
	}
			
	private static CEdge minEdge () {
		CEdge min			= new CEdge(0, 0, Integer.MAX_VALUE);
		for (CEdge e : edge) {
			min				= (e.isValid()&&e.getCost()<min.getCost()) ? e : min;
		}
		return min;
	}
	
	private static void updateEdge		() {
		for (CEdge e : edge) {
			if (uf.find(e.getV1(), e.getV2()))
				e.setValidFalse();
		}
	}
	
	public static void main (String[] args) {
		prod		();
	}
}
