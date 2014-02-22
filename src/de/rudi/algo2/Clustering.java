package de.rudi.algo2;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.logging.Logger;


public class Clustering {
	
	private static HashSet<Edge> 	edge	= new HashSet<Edge>();
//	private static Cluster[] 		c;		
	private static UnionFind 		uf;
	public 	static Logger			logger	= Logger.getLogger("Clustering");
	
	public static void prod() {
		try {
			FileReader fReader			= new FileReader("clustering1.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= lReader.readLine();
			int len						= Integer.parseInt(line.trim());
//			c							= new Cluster[len];
			uf							= new UnionFind(len);
//			for (int i=0;i<c.length;i++)
//				c[i]						= new Cluster(i);
			while ((line=lReader.readLine())!=null) {
				String[] words				= line.split(" ");
				edge.add					(new Edge	( Integer.parseInt(words[0])
														, Integer.parseInt(words[1])
														, Integer.parseInt(words[2])));
			}
			lReader.close				();
			while (uf.components()>4) {
				Edge min					= minEdge();
				int n1						= uf.find(min.getV1());
				int n2						= uf.find(min.getV2());
//				logger.info					("min="+min);
				uf.unite					(n1, n2);
				edge.remove					(min);
				updateEdge					();
			}
			System.out.println			(minEdge());
		} catch (IOException e) {
			
		}
	}
			
	private static Edge minEdge () {
		Edge min			= new Edge(0, 0, Integer.MAX_VALUE);
		for (Edge e : edge) {
			min				= (e.isValid()&&e.getCost()<min.getCost()) ? e : min;
		}
		return min;
	}
	
	private static void updateEdge		() {
		for (Edge e : edge) {
			if (uf.find(e.getV1(), e.getV2()))
				e.setValidFalse();
		}
	}
	
	public static void main (String[] args) {
		prod		();
	}
}
