package de.rudi.algo2.pa6;

import java.util.List;

public final class SCC implements Comparable<SCC> {
	private int len;
	private List<Integer> list;

	public SCC(int len, List<Integer> list) {
		this.len = len;
		this.list = list;
	}

	public String toString() {
		return new String("Länge=" + len) + ";" + list.toString();
	}

	@Override
	public int compareTo(SCC o) {
		return this.len - o.len;
	}
}
