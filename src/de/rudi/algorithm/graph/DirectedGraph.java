package de.rudi.algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DirectedGraph {

	private HashMap<Integer, DirectedVertex> 	v;
	private ArrayList<Long>				e;
	
	public DirectedGraph () {
		v		= new HashMap<Integer, DirectedVertex>();
		e		= new ArrayList<Long>();
	}
	
	public void addVertex(Integer id, Integer[] nodes) {
		if (v.containsKey(id)) {
			System.out.println("Vertex mit id="+id+" bereits vorhanden");
			System.exit(0);
		}
		v.put(id, new DirectedVertex(nodes));
		for (Integer node : nodes) 
			e.add(together(id, node));
	}

	public boolean inspect () {
		boolean 	ok	= true;
		for (Integer iVer : v.keySet()) {
			DirectedVertex ver		= v.get(iVer);
			for (Integer i : ver.getNodes()) {
				if (!v.containsKey(i)) {
					System.out.println("Edge "+i+" in Vertex "+iVer+" nicht vorhanden: ");
					ok	= false;
				}
				else {
					if (!v.get(i).isNode(iVer)) {
						System.out.println("Edge "+i+" in Vertex nicht vorhanden: "+iVer);
						ok	= false;						
					}
				}
			}
		}
		for (Long l : e) {
			int i1			= parted(l)[0];
			int i2			= parted(l)[1];
			if (!v.containsKey(i1) || !v.containsKey(i2))  {
				System.out.println("Edge nicht vorhanden: "+i1+" "+i2);
				ok	= false;				
			}
		}
		return ok;
	} 
	
	public static long together (int i1, int i2) {
		long l		= i1;
		return		(l<<32)+i2;
	}
	
	public static int[] parted (long l) {
		int[] i		= new int[2];
		i[0]		= (int)(l>>>32);
		long x		= l<<32;
		i[1]		= (int)(x>>>32);
		return		i;
	}
	
	public void dfs (Integer start) {
		System.out.print		(start+" ");
		DirectedVertex node		= v.get(start);
		node.setExplored		(true);
		for (Integer i : node.getNodes()) {
			if (!v.get(i).isExplored())
				dfs					(i);
		}
	}
	
	public void startDFS (Integer start) {
		for (DirectedVertex node : v.values()) 
			node.setExplored(false);
		dfs					(start);
		System.out.println	();
	}

	public static void test() {
		DirectedGraph graph		= new DirectedGraph();
		Integer[][]	ti	= { { 1, 2 } ,
							{ 2, 3 } ,
							{ 3, 4 } ,
							{ 4, 1 } ,
							{ 5, 3 }
						  };
		for (Integer[] i : ti ) {
			Integer[] rest	= Arrays.copyOfRange(i, 1, i.length);
			graph.addVertex(i[0], rest);
		}
		for (int i=0;i<ti.length;i++) 
			graph.startDFS		(ti[i][0]); 
	}
	
		public static void main(String[] args) {
			test();
	}
}
