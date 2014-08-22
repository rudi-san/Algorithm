package de.rudi.algo2.pa4;

import java.util.ArrayList;
import java.util.HashMap;

public class JEdge {
	
	private HashMap<Integer, Integer[][]> fromMap;
	private HashMap<Integer, Integer[][]> toMap;
	
	public JEdge () {
		fromMap			= new HashMap<Integer, Integer[][]>();
		toMap			= new HashMap<Integer, Integer[][]>();
	}
	
	public void addEdge(int from, int to, int len) {
		Integer[][] fromEdge		= fromMap.get(from);
		int fromIndex				= 0;
		Integer[][] newFrom			= null;
		if (fromEdge!=null) {
			fromIndex					= fromEdge.length;
			newFrom					= new Integer[fromIndex+1][2];
			for (int i=0;i<fromIndex;i++) 
				for (int j=0;j<fromEdge[i].length;j++)
					newFrom[i][j]				= fromEdge[i][j];
		}
		else {
			newFrom					= new Integer[1][2];
		}
		newFrom[fromIndex][0]		= to;
		newFrom[fromIndex][1]		= len;
		fromMap.put				(from, newFrom);
		
		Integer[][] toEdge		= toMap.get(to);
		int toIndex				= 0;
		Integer[][] newTo			= null;
		if (toEdge!=null) {
			toIndex					= toEdge.length;
			newTo					= new Integer[toIndex+1][2];
			for (int i=0;i<toIndex;i++) 
				for (int j=0;j<toEdge[i].length;j++)
					newTo[i][j]				= toEdge[i][j];
		}
		else {
			newTo					= new Integer[1][2];
		}
		newTo[toIndex][0]		= from;
		newTo[toIndex][1]		= len;
		toMap.put				(to, newTo);
	}

	public Integer[][] getFromEdges(int from) {
		return 			fromMap.get(from);
	}
	public Integer[] getFromEdge (int from, int to) {
		Integer[][] edges		= getFromEdges(from);
		for (Integer[] edge : edges) {
			if (edge[0]==to)
				return edge;
		}
		return null;
	}
	public Integer getFromLength (int from, int to) {
		return getFromEdge(from,to)[1];
	}
	
	public Integer[][] getEdgesArray () {
		ArrayList<Integer[]> list	= new ArrayList<Integer[]>();
		for (Integer key : fromMap.keySet()) {
			Integer[][] value		= fromMap.get(key);
			for (Integer[] y : value) {
				Integer[] a			= new Integer[3];
				a[0]				= key;
				a[1]				= y[0];
				a[2]				= y[1];
				list.add			(a);
			}
		}
		Integer[][] array		= new Integer[list.size()][3];
		return 					list.toArray(array);
	}
	
	public Integer[][] getToEdges(int to) {
		return 			toMap.get(to);
	}
	
	public Integer[] getToEdge (int from, int to) {
		Integer[][] edges		= getToEdges(to);
		for (Integer[] edge : edges) {
			if (edge[0]==from)
				return edge;
		}
		return null;
	}
	
	public Integer getToLength (int from, int to) {
		return getToEdge(from,to)[1];
	}
}
