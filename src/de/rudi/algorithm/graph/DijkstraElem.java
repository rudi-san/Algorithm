package de.rudi.algorithm.graph;

import java.util.ArrayList;

public class DijkstraElem implements Comparable<DijkstraElem> {

	private int key;
	private int value;
	private ArrayList<Integer> path;
	
	public DijkstraElem (int key, int value, ArrayList<Integer> path) {
		this.key		= key;
		this.value		= value;
		this.path		= path;
	}
	public int getKey() {
		return key;
	}
	public int getValue() {
		return value;
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
	
	@Override
	public int compareTo(DijkstraElem o) {
		return this.key-o.key;
	}
}
