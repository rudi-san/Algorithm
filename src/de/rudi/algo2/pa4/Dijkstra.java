package de.rudi.algo2.pa4;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {
	
	private static Vertex[] node		= null;
	
	public static void computePaths(Vertex source) {
		source.minDistance = 0;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// Visit each edge exiting u
			for (DEdge e : u.adjacencies) {
				Vertex v = e.target;
				int weight = e.weight;
				int distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}
	
	private static Vertex[] intializeVertex (int count, Integer[][] edges) {
		Vertex[] node 		= new Vertex[count];
		for (int i = 1; i < count; i++) {
			node[i] 		= new Vertex(new Integer(i).toString());
		}

		int zxEdge					= Integer.MIN_VALUE;
		ArrayList<DEdge> edgeList	= new ArrayList<DEdge>();
		DEdge[] edgeArray			= null;
		for (Integer[] edge : edges) {
			if (edge[0]!=zxEdge) {
				if (zxEdge!=Integer.MIN_VALUE) {
					edgeArray					= new DEdge[edgeList.size()];
					node[zxEdge].adjacencies	= edgeList.toArray(edgeArray);
				}
				edgeList 		= new ArrayList<DEdge>();
				zxEdge			= edge[0];
			}
			edgeList.add(new DEdge(node[edge[1]], edge[2]));
		}
		edgeArray					= new DEdge[edgeList.size()];
		node[zxEdge].adjacencies	= edgeList.toArray(edgeArray);
		return 						node;
	}
	
	private static Integer[][] getEdges (String fileName) {
		ArrayList<Integer[]> edgeArray	= new ArrayList<Integer[]>();
		Integer[][] edgeList			= null;
		try {
			FileReader fReader 			= new FileReader(fileName);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= null;
			while ((line = lReader.readLine()) != null) {
				String[] str 		= line.split("\\s");
				int id	 			= Integer.parseInt(str[0].trim());
				for (int i = 1; i < str.length; i++) {
					String[] entry 		= str[i].split(",");
					Integer[] edge		= new Integer[3];
					edge[0]				= id;
					edge[1] 			= Integer.parseInt(entry[0].trim());
					edge[2]				= Integer.parseInt(entry[1].trim());
//					System.out.println(Arrays.toString(edge));
					edgeArray.add		(edge);
				}
			}
			edgeList				= new Integer[edgeArray.size()][3];
			for (int i=0;i<edgeList.length;i++) 
				edgeList[i]          	= edgeArray.get(i);
			
			lReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 			edgeList;

	}

	private static void test01 () {
		int count				= 200+1;
		Integer[][] edges		= getEdges("data\\pa4\\dijkstraData.txt");
		node					= intializeVertex(count, edges);
		computePaths			(node[1]);
		try {
			PrintWriter writer			= new PrintWriter("dijkstraNewNew.out");
			for (int i = 1; i < node.length; i++) {
				writer.println("Distance to " + node[i] + ": "
						+ node[i].minDistance);
				List<Vertex> path = getShortestPathTo(node[i]);
				writer.println("Path: " + path);
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int[] getMinDist(int count, Integer[][] edges, int source) {
		node					= intializeVertex(count+1, edges);
		computePaths			(node[source]);
		int[] distances			= new int[node.length];
		for (int i=1;i<node.length;i++)
			distances[i]			= node[i].minDistance;
		return distances;
	}
	
	public static void main(String[] args) {
		test01();
		
	}
}
