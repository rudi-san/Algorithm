package de.rudi.algo2.pa6;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;

import org.apache.log4j.Logger;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class);;
	private static int[][] 		graph;
	private static int[][] 		wGraph;
	private static boolean[] 	used;
	private static int[] 		leader;
	private static int[] 		order;
	private static int 			t 					= 0;
	private static int 			s 					= 0;
	private static int 			vLen				= 0;

	static void dfs01(int u) {
		used[u] = true;
		if (wGraph[u] != null) {
			for (Integer v : wGraph[u]) {
				if (v != null && !used[v])
					dfs01(v);
			}
		}
		t++;
		order[t] = u;
	}

	static void dfs02(int[][] graph, int u) {
		used[u] = true;
		leader[u] = s;
		if (graph[u] != null) {
			for (Integer v : graph[u]) {
				if (v != null && !used[v])
					dfs02(graph, v);
			}
		}
	}

	public static int[] scc(int maxInd) {
		logger.info					("maxInd="+maxInd);
		int n 						= vLen*2 + 1;
//		@SuppressWarnings("unchecked")
//		ArrayList<Integer>[] rGraph = new ArrayList[n];
//		for (int i = 0; i < rGraph.length; i++)
//			rGraph[i] 					= new ArrayList<Integer>();
		
//		for (int i = 0; i < n; i++) {
//			if (graph[i] != null) {
//				for (Integer j : graph[i]) {
//					if (j != null)
//						rGraph[j].add				(i);
//				}
//			}
//		}
		int[][] rGraph				= new int[n][];
		for (int i=0;i<n;i++) {
			if (graph[i] != null) {
				for (int j : graph[i]) {
					int rIndex			= 0;
					if (rGraph[j]==null) {
						rGraph[j]			= new int[1];
					}
					else {
						rIndex				= rGraph[j].length;
						rGraph[j]			= Arrays.copyOf(rGraph[j], rIndex+1);
					}
					rGraph[j][rIndex]	= i;
				}
			}
		}
//		logger.debug				("rGraph=" + Arrays.toString(rGraph));
		wGraph 						= new int[n][];
//		for (int i = 0; i < n; i++) {
//			wGraph[i] 					= new int[rGraph[i].size()];
//			for (int j = 0; j < wGraph[i].length; j++)
//				wGraph[i][j] 				= rGraph[i].get(j);
//		}
		for (int i = 0; i < n; i++) {
			int len						= (rGraph[i]==null) ? 0 : rGraph[i].length;
			wGraph[i] 					= new int[len];
			for (int j = 0; j < wGraph[i].length; j++)
				wGraph[i][j] 				= rGraph[i][j];
		}
//		logger.debug				("wGraph=" + compress(wGraph, maxInd));
		used 						= new boolean[n];
		Arrays.fill					(used, false);
		order 						= new int[n];
		leader 						= new int[n];
		for (int i = maxInd; i > 0; i--)
			if (!used[i])
				dfs01				(i);
		logger.debug				("order=" + Arrays.toString(order));
		logger.info					("FirstPass finished");
		Arrays.fill					(used, false);
		for (int i = maxInd; i > 0; i--) {
			int u 						= order[i];
			logger.debug				("outerLoop order["+i+"]="+u+" used[4]="+used[4]);
			if (!used[u]/* &&graph[u].length>0 */) {
				logger.debug				("innerLoop u=" + u);
				s 							= u;
				dfs02						(graph, u);
			}
		}
		logger.info					("SecondPass finished");
		logger.debug				("leader=" + Arrays.toString(leader));
		return						leader;
//		int[] countScc 				= new int[n];
//		for (int i : leader) {
//			if (used[i])
//				countScc[i]++;
//		}
//		logger.debug		("countScc=" +Arrays.toString(countScc));
//		Arrays.sort(countScc);
//		logger.debug		("sortiert=" + Arrays.toString(countScc));
//		for (int i = countScc.length - 1; i > -1; i--)
//			if (countScc[i] > 2)
//				System.out.print(countScc[i] + ", ");
//		System.out.println();
	}

//	private static String compress(int[][] array, int len) {
//		StringBuffer buf = new StringBuffer();
//		buf.append("[ ");
//		boolean start = true;
//		for (int i = 0; i < len; i++) {
//			if (start) {
//				buf.append(Arrays.toString(array[i]));
//				start = false;
//			} else
//				buf.append(", " + Arrays.toString(array[i]));
//		}
//		return buf.toString();
//	}

	public static void test(String fileName) {
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] xGraph = new ArrayList[17];
		for (int i = 0; i < xGraph.length; i++)
			xGraph[i] 					= new ArrayList<Integer>();
		try {
			FileReader fReader 			= new FileReader(fileName);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= null;
			while ((line = lReader.readLine()) != null) {
				String[] str 				= line.split("\\s");
				if (str.length < 2)
					logger.info					(line + " < 2 ");
				Integer id 					= Integer.parseInt(str[0].trim());
				for (int i = 1; i < str.length; i++)
					xGraph[id].add				(Integer.parseInt(str[i].trim()));
			}
			lReader.close				();
		} catch (Exception e) {
			e.printStackTrace			();
		}

		scc						(makeIntArray(xGraph));	
	}
	
	private static int makeIntArray (ArrayList<Integer>[] xGraph) {
		graph 						= new int[xGraph.length][];
		for (int i = 0; i < xGraph.length; i++) {
			graph[i] 					= new int[xGraph[i].size()];
			for (int j = 0; j < graph[i].length; j++)
				graph[i][j] 				= xGraph[i].get(j);
		}
		int maxInd				= 0;
		for (int i = 0; i < graph.length; i++) {
			if (graph[i].length > 0)
				maxInd 				= i;
		}
		return 					maxInd;
//		logger.info				("MaxInd=" + maxInd);
//		logger.debug			("graph=" + compress(graph, maxInd));

	}
	
	@SuppressWarnings("unchecked")
	public static int get2SATIntArray (String fileName)  {
		ArrayList<Integer>[] xGraph = null;

		try {
			FileReader fReader 			= new FileReader(fileName);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= lReader.readLine();
			String[] wLen 				= line.split(" ");
			vLen 						= Integer.parseInt(wLen[0]);
			logger.info					("Anzahl Variablen="+vLen);
			xGraph 						= new ArrayList[vLen*2+1];
			for (int i = 0; i < xGraph.length; i++)
				xGraph[i] 					= new ArrayList<Integer>();
			while ((line = lReader.readLine()) != null) {
				String[] words 				= line.split(" ");
				int in01					= Integer.parseInt(words[0]);
				int in02					= Integer.parseInt(words[1]);
				boolean ww01				= (in01>=0);
				boolean ww02				= (in02>=0);
				int var01					= (ww01) ? in01 : in01*-1;
				int var02					= (ww02) ? in02 : in02*-1;
				int true01					= (ww01) ? var01 : var01+vLen;
				int true02					= (ww02) ? var02 : var02+vLen;
				int false01					= (ww01) ? var01+vLen : var01;
				int false02					= (ww02) ? var02+vLen : var02;
				xGraph[false01].add			(true02);	
				xGraph[false02].add			(true01);	
			}
			lReader.close				();
			logger.debug				("Einlesen abgeschlossen");
		}
		catch (FileNotFoundException e) {
			System.out.println		(e.getMessage());
			System.exit				(0);
		} catch (IOException e) {
			System.out.println		(e.getMessage());
			System.exit				(0);
		}
		return				makeIntArray(xGraph);
	}
	
	public static boolean process2SAT (String fileName)  {
		int maxInd				= get2SATIntArray (fileName);
		int[] a					= scc(maxInd);
		boolean isSatisfiable	= true;
		for (int i=0;i<vLen;i++) {
			if (a[i]==a[i+vLen]) 
				isSatisfiable		= false;
		}
		return isSatisfiable;
	}

	public static void main(String[] args) {

//		test("data\\pa6\\scc.txt");
		String[] fileName		= { "2sat1.txt", "2sat2.txt", "2sat3.txt"
								  , "2sat4.txt", "2sat5.txt", "2sat6.txt" };
//		String name = fileName[5];
		for (String name : fileName) {
			graph=null; wGraph=null; used=null;	leader=null; order=null;
			t = s = 0;
			boolean isSatisfiable	= process2SAT ("data\\pa6\\"+name);
			String out				= (isSatisfiable) ? "is satisfiable" : "is *not* satisfiable";
			System.out.println		(name+" "+out);
		}
	}
}