package de.rudi.algorithm.graph;

import java.util.Arrays;
import java.util.ArrayList;

public class DirectedVertex {

	private ArrayList<Integer>	nodes;
	private boolean				explored;
	
	public DirectedVertex			(Integer[] nodes) {
		this.nodes		= new ArrayList<Integer>(Arrays.asList(nodes));
		this.explored	= false;
	}
	
	public void addNode		(Integer i) {
		nodes.add		(i);
	}
		
	public boolean isNode	(Integer v)  {
		return 			nodes.contains(v);
	}
	
	public boolean isExplored () {
		return explored;
	}
	
	public void setExplored (boolean value)  {
		explored	= value;
	}

	public Integer[] getNodes () {
		Integer[] edges		= new Integer[nodes.size()];
		edges				= nodes.toArray(edges);
		return				edges;
	}
	
	public void print () {
		Integer[] edges		= getNodes();
		boolean start		= true;
		for (Integer i : edges) {
			if (start)			{ System.out.print(i); start = false; }
			else				System.out.print(", "+i);
		}
		System.out.println	();
	}

	public static void main(String[] args) {
//		Integer[] i		= { 2, 4, 6, 12555, 88, 234234, 65454, 1, 7 };
//		DirectedVertex v		= new DirectedVertex(i);
		
	}
}
