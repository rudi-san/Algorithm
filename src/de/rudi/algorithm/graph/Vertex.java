package de.rudi.algorithm.graph;

import java.util.Arrays;
import java.util.ArrayList;

public class Vertex {

	private ArrayList<Integer>	nodes;
	
	public Vertex			(Integer[] nodes) {
		this.nodes		= new ArrayList<Integer>(Arrays.asList(nodes));
	}
	
	public void addNode		(Integer i) {
		nodes.add		(i);
	}
	
	public void changeNode	(Integer alt, Integer neu) {
		while (nodes.remove	(alt))
			nodes.add		(neu);
	}
	
	public void removeNode 	(Integer i) {
		while (nodes.remove	(i));
	}
	
	public boolean isNode	(Integer v)  {
		return 			nodes.contains(v);
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
		Integer[] i		= { 2, 4, 6, 12555, 88, 234234, 65454, 1, 7 };
		Vertex v		= new Vertex(i);
		v.print			();
		v.changeNode	(88, 0);									v.print			();
		v.addNode		(10);	v.addNode(10);	v.addNode(10);		v.print			();
		v.removeNode	(10);										v.print			();
		v.addNode		(4);										v.print			();
		v.changeNode	(4, 3);										v.print			();
		
	}
}
