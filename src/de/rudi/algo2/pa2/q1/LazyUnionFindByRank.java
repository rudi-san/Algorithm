package de.rudi.algo2.pa2.q1;

import java.util.logging.Logger;


public class LazyUnionFindByRank {
    private 	int[] 	id;
    private 	int[] 	rank;
    private 	int 	components;
    public static Logger	logger	= Logger.getLogger("Clustering");

    // instantiate N isolated components 0 through N-1
    public LazyUnionFindByRank(int anz) {
        id 				= new int[anz];
        rank 			= new int[anz];
        components 		= anz;
        for (int i = 0; i < anz; i++) {
            id[i] 		= i;
            rank[i] 	= 1;
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
// logger.debug (Arrays.toString(id)+";"+Arrays.toString(sz));
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        if (rank[i] < rank[j]) { 
        	id[i] = j; 
//        	rank[j] += rank[i]; 
        }
        else { 
        	id[j] = i; 
//        	rank[i] += rank[j];
        	if (rank[i]==rank[j])
        		rank[j]++;
        }
        components--;
    }
}