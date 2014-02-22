package de.rudi.algo2;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Arrays;

public class Prim {

	public static void prod() {
		try {
			FileReader fReader			= new FileReader("edges.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= lReader.readLine();
			String[] wLen				= line.split(" ");
			int nLen					= Integer.parseInt(wLen[0]);
			boolean[] nodes				= new boolean[nLen];
			Arrays.fill					(nodes, false);
			int eLen					= Integer.parseInt(wLen[1]);
			Edge[] edges				= new Edge[eLen];
			int x						= 0;
			while ((line=lReader.readLine())!=null) {
				String[] words				= line.split(" ");
				edges[x++]					= new Edge	( Integer.parseInt(words[0])
														, Integer.parseInt(words[1])
														, Integer.parseInt(words[2]));
			}
			lReader.close				();
			nodes[0]					= true;
			int cost					= 0;
			while (!allNodesCaught(nodes))  {
				Edge min		= edges[0];
				for (int i=1;i<edges.length;i++) {
					int v1		= edges[i].getV1();
					int v2		= edges[i].getV2();
					if (nodes[v1]&&!nodes[v2] || !nodes[v1]&&nodes[v2])
						min				= (min.getCost()<edges[i].getCost()) ? min : edges[i];
				}
				if (nodes[min.getV1()])
					nodes[min.getV2()]		= true;
				else
					nodes[min.getV1()]		= true;
				System.out.println		(min);
				cost					+= min.getCost();
			}
			System.out.println			("Cost for all: "+cost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean allNodesCaught (boolean[] nodes) {
		for (boolean node : nodes) {
			if (!node)	return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		prod		();

	}

}
