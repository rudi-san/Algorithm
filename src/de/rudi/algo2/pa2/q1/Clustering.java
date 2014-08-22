package de.rudi.algo2.pa2.q1;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.logging.Logger;

public class Clustering {

	private static HashSet<Edge> edge = new HashSet<Edge>();
	// private static Cluster[] c;
	private static LazyUnionFindByRank uf;
	public static Logger logger = Logger.getLogger("Clustering");

	public static void prod() {
		String[] fileName			= { "clustering1.txt", "test01.txt" };
		try {
			FileReader fReader 			= new FileReader("data\\pa2q1\\"+fileName[0]);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= lReader.readLine();
			int nodesAnz 				= Integer.parseInt(line.trim());
			// c = new Cluster[len];
			uf 							= new LazyUnionFindByRank(nodesAnz);
			// for (int i=0;i<c.length;i++)
			// c[i] = new Cluster(i);
			while ((line = lReader.readLine()) != null) {
				String[] words 		= line.split(" ");
				edge.add			( new Edge	( Integer.parseInt(words[0])
												, Integer.parseInt(words[1])
												, Integer.parseInt(words[2])
												)
									);
			}
			lReader.close();
			while (uf.components() > 4) {
				Edge min 			= minEdge();
				int n1 				= uf.find(min.getV1());
				int n2 				= uf.find(min.getV2());
				// logger.info ("min="+min);
				uf.unite			(n1, n2);
				edge.remove			(min);
				updateEdge			();
			}
			int[][]	auswertung		= new int[4][2];
			for (int[] aus : auswertung) {
				aus[0]		= -1;
				aus[1]		= 0;
			}
			int maxAusw		= 0;
			for (int i=0;i<nodesAnz;i++) {
				int x 			= uf.find(i);
				boolean found	= false;
				for (int j=0;j<4;j++) {
					if (x==auswertung[j][0]) {
						auswertung[j][1]++;
						found		= true;
					}
				}
				if (!found) {
					auswertung[maxAusw][0] 	= x;
					auswertung[maxAusw][1]	= 1;
					maxAusw++;
				}
			}
			for (int[] ausw : auswertung) 
				System.out.println("Cluster: "+ausw[0]+" Anzahl: "+ausw[1]);
			System.out.println(minEdge());
		} catch (IOException e) {

		}
	}

	private static Edge minEdge() {
		Edge min = new Edge(0, 0, Integer.MAX_VALUE);
		for (Edge e : edge) {
			min = (e.isValid() && e.getCost() < min.getCost()) ? e : min;
		}
		return min;
	}

	private static void updateEdge() {
		for (Edge e : edge) {
			if (uf.find(e.getV1(), e.getV2()))
				e.setValidFalse();
		}
	}

	public static void main(String[] args) {
		prod();
	}
}