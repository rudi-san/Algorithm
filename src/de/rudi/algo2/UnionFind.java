package de.rudi.algo2;

import java.util.logging.Logger;


public class UnionFind {
    private int[] id;
    private int[] sz;
    private int components;
	public 	static Logger			logger	= Logger.getLogger("Clustering");

    // instantiate N isolated components 0 through N-1
    public UnionFind(int N) {
        id = new int[N];
        sz = new int[N];
        components = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    // return id of component corresponding to element x
    public int find(int x) {
        while (x != id[x])
            x = id[x];
        return x;
    }

    // return number of connected components
    public int components() {
        return components;
    }

    // are elements p and q in the same component?
    public boolean find(int p, int q) {
        return find(p) == find(q);
    }

    // merge components containing p and q
    public void unite(int p, int q) {
//    	logger.debug		(Arrays.toString(id)+";"+Arrays.toString(sz));
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        if   (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else                 { id[j] = i; sz[i] += sz[j]; }
        components--;
    }
}
