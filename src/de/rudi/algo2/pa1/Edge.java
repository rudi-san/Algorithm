package de.rudi.algo2.pa1;

public class Edge {

	private int v1;
	private int v2;
	private int cost;
	private boolean valid;
	private static int validCount = 0;

	public Edge(int v1, int v2, int cost) {
		this.v1 = v1 - 1;
		this.v2 = v2 - 1;
		this.cost = cost;
		valid = true;
		validCount++;
	}

	public int getV1() {
		return v1;
	}

	public int getV2() {
		return v2;
	}

	public int getCost() {
		return cost;
	}

	public boolean isValid() {
		return valid;
	}

	public void setV1(int v1) {
		this.v1 = v1;
	}

	public void setV2(int v2) {
		this.v2 = v2;
	}

	public void setValidFalse() {
		valid = false;
		validCount--;
	}

	public static boolean isAnyValid() {
		return validCount > 0;
	}

	@Override
	public String toString() {
		return "[v1=" + (v1 + 1) + "];[v2=" + (v2 + 1) + "];[cost=" + cost
				+ "]";
	}
}