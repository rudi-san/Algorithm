package de.rudi.algorithm.graph;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.*;

import org.apache.log4j.Logger;


public class Kosaraju {

	private static Logger				logger;
	private static Integer[][]			graph;
	private static boolean[] 			used;
	private static List<Integer> 		order; 
	private static List<Integer> 		component; 
	private static List<List<Integer>>	components;
	
  static void dfs(List<Integer> list, int u) {
    used[u] 		= true;
    if (graph[u]!=null) {
	    for (Integer v : graph[u]) {
	      if (v!=null&&!used[v])
	        dfs				(list, v);
	    }
	    list.add		(u);
    }
  }

  public static List<List<Integer>> scc() {
    int n 				= graph.length;
    used 				= new boolean[n];
    Arrays.fill			(used, false);
    order 				= new ArrayList<Integer>();
    for (int i = 0; i < n; i++) 
    	if (!used[i])		dfs(order, i);
    
    logger.debug		("FirstPass finished");

    @SuppressWarnings("unchecked")
    ArrayList<Integer>[] reverseGraph = new ArrayList[n];
    for (int i = 0; i < n; i++)
      reverseGraph[i] = new ArrayList<Integer>();
    for (int i = 0; i < n; i++) {
    	if (graph[i]!=null) {
	      for (Integer j : graph[i]) {
	        if (j!=null)
	    	  reverseGraph[j].add(i);
	      }
        }
    }
    graph		= new Integer[n][];
    for (int i=0;i<n;i++) {
    	graph[i]	= new Integer[reverseGraph[i].size()];
    	graph[i]	= reverseGraph[i].toArray(graph[i]);
    }
 
    components 			= new ArrayList<List<Integer>>();
    Arrays.fill			(used, false);
    Collections.reverse	(order);

    for (int u : order)
      if (!used[u]) {
        component = new ArrayList<Integer>();
        dfs(component, u);
        components.add(component);
      }

    return components;
  }
  // Usage example
  public static void test(String[] args) {

	  graph 							= new Integer[11][3];
	  graph[0][0]						= 3;
	  graph[3][0]						= 2;
	  graph[2][0]						= 0;
	  graph[3][1]						= 5;
	  graph[5][0]						= 6;
	  graph[5][1]						= 9;
	  graph[9][0]						= 10;
	  graph[10][0]						= 9;
	  graph[6][0]						= 5;
	  graph[1][0]						= 4;
	  graph[4][0]						= 1;
	  graph[4][1]						= 6;
	  graph[4][2]						= 7;
	  graph[7][0]						= 8;
	  graph[6][1]						= 10;

	  List<List<Integer>> components 	= scc();
	  System.out.println				(components);
  }

  	public static void prod(String[] args) {
  		graph		 = new Integer[875715][];
  		try {
			FileReader fReader			= new FileReader("SCC.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				String[] str				= line.split("\\s");
				if (str.length<2) logger.debug (line+" < 2 ");
				Integer id					= Integer.parseInt(str[0].trim());
				graph[id]	 				= new Integer[str.length-1];
				for (int i=1;i<str.length;i++)
					graph[id][i-1]				= (Integer.parseInt(str[i].trim()));
			}
			lReader.close					();
			List<List<Integer>> components 	= scc();
			ArrayList<SCC> sccList			= new ArrayList<SCC>();
			for (List<Integer> l : components) {
				sccList.add						(new SCC(l.size(), l));
			}
			Collections.sort				(sccList);
			for (SCC s	: sccList)
				if (s.len>1)		System.out.println(s);
  		} catch (Exception e) {
			  e.printStackTrace();
  		}
  	}
  	
  	public static void main (String[] args) {
  		logger		= Logger.getLogger(Kosaraju.class);
  		prod(args);
//  		test(args);
  	}
  	
  	public static final class SCC implements Comparable<SCC>{
  		private int len ;
  		private List<Integer> list;
  		public SCC (int len, List<Integer> list) {
  			this.len		= len;
  			this.list		= list;
  		}
  		public String toString () {
  			return new String("Länge="+len)+";"+list.toString();
  		}
		@Override
		public int compareTo(SCC o) {
			return this.len-o.len;
		}
  	}
}