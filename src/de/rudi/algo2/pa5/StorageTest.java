package de.rudi.algo2.pa5;

public class StorageTest {

	public static void main (String[] args) {
		for (int i=2;i<24;i++) {
			int[]	c			= new Combinations(24,i).getCombinations();
			System.out.println	("Länge "+i);
			double[][] d		= new double[c.length][24];
			System.out.println	("Anzahl Kombinationen: "+d.length);
		}
	}
}
