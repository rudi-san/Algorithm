package de.rudi.algorithm.graph;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

public class Dijkstra {

	private static int[][][]	node;
	private static boolean[]	processed;
//	private static Heap			heap;
	private static PriorityQueue<DijkstraElem> queue;
	private static int			count;
	private static int[]		pathLen;
	private static Integer[][]	pathList;
	private static Logger		logger;
	
	public static void process (int index, int len, ArrayList<Integer> path) {

		pathLen[index]			= len;
		pathList[index]			= new Integer[path.size()];
		pathList[index]			= path.toArray(pathList[index]);
		processed[index]		= true;
		if (--count==0)			return;

		for (int[] i : node[index]) {
			if (!processed[i[0]]) {
				path.add				(index);
				ArrayList<Integer> newPath	= new ArrayList<Integer>();
				int vg					= -1;
				for (Integer x : path) {
					if (x!=vg) {
						newPath.add				(x);
						vg						= x;
					}
				}
				queue.add(new DijkstraElem(pathLen[index]+i[1], i[0], newPath));
//				heap.insert				(pathLen[index]+i[1], i[0], newPath);
			}
		}

		DijkstraElem min					= null;
		do {
//			min					= heap.extractMin();
			min					= queue.poll();
		} while (min!=null&&processed[min.getValue()]);
		
		if (min!=null) 
			process					(min.getValue(), min.getKey(), min.getPath());
		else
			logger.debug			("kein Heap bei "+index+"; count="+count);
	}
	
	public static void test(String[] args) {
		int[][][] data		= { { }
							  ,	{ { 2, 1 }, { 3, 4 }  }
							  ,	{ { 3, 2 }, { 4, 3 }  } 
							  , { { 4, 2 }  }
							  , { }
							  , { }
							  , { }
							  , { }
							  , { }
							  
		};

		node				= data;
		count				= node.length;
		processed			= new boolean[count];
		Arrays.fill			(processed, false);
		pathLen				= new int[count];
		Arrays.fill			(pathLen, 1000000);
//		heap				= new Heap();
		queue				= new PriorityQueue<DijkstraElem>();
		int start			= 1;
		count				-= start;
		process				(start, 0, new ArrayList<Integer>());
   		System.out.println	(Arrays.toString(pathLen));
	}

  	public static void prod(String[] args) {
  		node						= new int[300][][];
  		try {
			FileReader fReader			= new FileReader(args[0]);
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				String[] str				= line.split("\\s");
				Integer id					= Integer.parseInt(str[0].trim());
				node[id]					= new int[str.length-1][2];

				for (int i=1;i<str.length;i++) {
					String[] entry				= str[i].split(",");
					if (entry.length<2)			logger.info("Entry.length<2 id="+id+" i="+i);
					node[id][i-1][0]			= Integer.parseInt(entry[0].trim());
					node[id][i-1][1]			= Integer.parseInt(entry[1].trim());
				}
			}
			lReader.close				();
 		} catch (Exception e) {
			  e.printStackTrace();
		}
			
		count				= 300;
		processed			= new boolean[count];
		Arrays.fill			(processed, false);
		pathLen				= new int[count];
		Arrays.fill			(pathLen, 1000000);
		pathList			= new Integer[count][];
//		heap				= new Heap();
		queue				= new PriorityQueue<DijkstraElem>();
		int start			= 1;
		count				-= start;
		process				(start, 0, new ArrayList<Integer>());
		logger.debug		("Path: "+Arrays.toString(pathLen));
		int[] problem		= {7,37,59,82,99,115,133,165,188,197,196,95,26,157,72};
		for (int p : problem) {
			System.out.print(p+"="+pathLen[p]+", ");
			System.out.println (Arrays.toString(pathList[p]));
		}
		System.out.println	();
   	}

  	public static void main(String[] args) {
		logger			= Logger.getLogger(Dijkstra.class);
//		test			(args);
		String[] file	= { "dijkstraData.txt" };
//		String[] file	= { "dijkstraTest.txt" };
		prod			(file);
	}

}
