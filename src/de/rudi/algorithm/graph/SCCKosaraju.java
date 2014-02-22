package de.rudi.algorithm.graph;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.*;

public class SCCKosaraju {

  static void dfs(List<Integer>[] graph, boolean[] used, List<Integer> res, int u) {
    used[u] = true;
    for (int v : graph[u])
      if (!used[v])
        dfs(graph, used, res, v);
    res.add(u);
  }

  public static List<List<Integer>> scc(List<Integer>[] graph) {
    int n = graph.length;
    boolean[] used = new boolean[n];
    List<Integer> order = new ArrayList<Integer>();
    for (int i = 0; i < n; i++) {
    	if (i%1000==0) 
    		System.out.println ("Verarbeitet: "+1);
    	if (!used[i])
    		dfs(graph, used, order, i);
    }
    
    System.out.println ("FirstPass finished");

    @SuppressWarnings("unchecked")
	List<Integer>[] reverseGraph = new List[n];
    for (int i = 0; i < n; i++)
      reverseGraph[i] = new ArrayList<Integer>();
    for (int i = 0; i < n; i++)
      for (int j : graph[i])
        reverseGraph[j].add(i);

    List<List<Integer>> components = new ArrayList<List<Integer>>();
    Arrays.fill(used, false);
    Collections.reverse(order);

    for (int u : order)
      if (!used[u]) {
        List<Integer> component = new ArrayList<Integer>();
        dfs(reverseGraph, used, component, u);
        components.add(component);
      }

    return components;
  }

  // Usage example
  public static void test(String[] args) {
    @SuppressWarnings("unchecked")
	List<Integer>[] g = new List[3];
    for (int i = 0; i < g.length; i++)
      g[i] = new ArrayList<Integer>();
    
    g[2].add(0);
    g[2].add(1);
    g[0].add(1);
    g[1].add(0);

    List<List<Integer>> components = scc(g);
    System.out.println(components);
  }

  public static void main(String[] args) {
	    @SuppressWarnings("unchecked")
		List<Integer>[] g = new List[875714];
	    for (int i = 0; i < g.length; i++)
	        g[i] = new ArrayList<Integer>();
		try {
			FileReader fReader			= new FileReader("SCC.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				String[] str				= line.split("\\s");
				Integer id					= Integer.parseInt(str[0].trim());
				Integer head				= Integer.parseInt(str[1].trim());
				g[id-1].add					(head);
			}
			lReader.close				();
		    List<List<Integer>> components = scc(g);
		    for (List<Integer> l : components)
		    	System.out.println(l.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

  }
}