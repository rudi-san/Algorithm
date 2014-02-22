package de.rudi.algorithm.graph;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.*;

import org.apache.log4j.Logger;


public class KosarajuNew {

	private static Logger				logger;
	private static int[][] 				graph;	
	private static int[][] 				wGraph;	
	private static boolean[] 			used;
	private static int[]		 		leader;
	private static int[]				order;
	private static int					t 			= 0;
	private static int					maxInd		= 0;
	private static int					s			= 0;
	
  static void dfs01(int u) {

	  used[u] 		= true;
	  if (wGraph[u]!=null) {
		  for (Integer v : wGraph[u]) {
			  if (v!=null&&!used[v])
				  dfs01				(v);
		  }
	  }
	  t++;
	  order[t]		 = u;
  }

  static void dfs02(int[][] graph, int u) {
	    used[u] 		= true;
	    leader[u]		= s;
	    if (graph[u]!=null) {
		    for (Integer v : graph[u]) {
		      if (v!=null&&!used[v])
		        dfs02				(graph, v);
		    }
	    }
	  }

  public static void scc() {
	  int n					= maxInd+1;
	  used 					= new boolean[n];
	  Arrays.fill			(used, false);
	  order 				= new int[n];
	  leader				= new int[n];
    
    @SuppressWarnings("unchecked")
    ArrayList<Integer>[] rGraph = new ArrayList[n];
    for (int i = 0; i < n; i++) 
        rGraph[i] = new ArrayList<Integer>();
 
    for (int i = 0; i < n; i++) {
    	if (graph[i]!=null) {
	      for (Integer j : graph[i]) {
	        if (j!=null)
	    	  rGraph[j].add(i);
	      }
        }
    }
    logger.debug			("rGraph="+Arrays.toString(rGraph));
    wGraph					= new int[n][];
    for (int i = 0; i < n; i++) {
    	wGraph[i]				= new int[rGraph[i].size()];
		for (int j=0;j<wGraph[i].length;j++)
			wGraph[i][j]				= rGraph[i].get(j);
    }
    
	logger.debug				("wGraph="+compress(wGraph, maxInd));

    for (int i = maxInd; i > 0; i--) 
    	if (!used[i])		dfs01(i);
    
    logger.debug				("order="+Arrays.toString(order));
    logger.info					("FirstPass finished");

    Arrays.fill					(used, false);

    for (int i = maxInd; i > 0; i--)  {
    	int u						= order[i];
    	logger.debug				("outerLoop order["+i+"]="+u+" used[4]="+used[4]);
    	if (!used[u]/*&&graph[u].length>0*/) {
    		logger.debug			("innerLoop u="+u);
    		s = u;
    		dfs02(graph, u);
      }
    }
    logger.info		("SecondPass finished");

    logger.debug 	("leader="+Arrays.toString(leader));
    
    int[] countScc	= new int[n];
    for (int i : leader) {
    	if (used[i])
    		countScc[i]++;
    	}
    	logger.info	(Arrays.toString(Arrays.copyOfRange(countScc, 0, 10)));
    	Arrays.sort(countScc);
    	logger.debug			("countScc="+Arrays.toString(countScc));
    	for (int i=countScc.length-1;i>-1;i--) 
    		if (countScc[i]>2) System.out.print (countScc[i]+", ");
    	System.out.println();
  	}

  	private static String compress (int[][] array, int len) {
  		StringBuffer buf		= new StringBuffer();
  		buf.append				("[ ");
  		boolean start			= true;
  		for (int i=0;i<len;i++) {
  			if (start)			{ buf.append(Arrays.toString(array[i])); start = false; }
  			else				  buf.append(", "+Arrays.toString(array[i]));
  		}
  		return				buf.toString();
  	}

  	public static void test(String[] args) {
  	    @SuppressWarnings("unchecked")
		ArrayList<Integer>[] xGraph = new ArrayList[875716];
  	    for (int i = 0; i < xGraph.length; i++) 
  	        xGraph[i] = new ArrayList<Integer>();
  		try {
			FileReader fReader			= new FileReader(args[0]);
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				String[] str				= line.split("\\s");
				if (str.length<2) logger.info (line+" < 2 ");
				Integer id					= Integer.parseInt(str[0].trim());
				for (int i=1;i<str.length;i++)
					xGraph[id].add				(Integer.parseInt(str[i].trim()));
			}
			lReader.close				();
 		} catch (Exception e) {
			  e.printStackTrace();
		}
			
		graph						= new int[xGraph.length][];
		for (int i = 0; i < xGraph.length; i++) {
			graph[i]				= new int[xGraph[i].size()];
			for (int j=0;j<graph[i].length;j++)
				graph[i][j]				= xGraph[i].get(j);
		}

		for (int i=0;i<graph.length;i++) {
			if (graph[i].length>0)
				maxInd					= i;
		}
		logger.info					("MaxInd="+maxInd);
		logger.debug				("graph="+compress(graph, maxInd));
		
		scc							();
   	}
  	
  	public static void main (String[] args) {
  		logger			= Logger.getLogger(KosarajuNew.class);
//  		String[] file	= {"SCC.test.txt"};
  		String[] file	= {"SCC.txt"};
  		test			(file);
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