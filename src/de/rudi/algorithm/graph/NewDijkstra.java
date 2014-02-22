package de.rudi.algorithm.graph;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class NewDijkstra
{
    public static void computePaths(NewVertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<NewVertex> vertexQueue = new PriorityQueue<NewVertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    NewVertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                NewVertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<NewVertex> getShortestPathTo(NewVertex target)
    {
        List<NewVertex> path = new ArrayList<NewVertex>();
        for (NewVertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
    	NewVertex[] node		= new NewVertex[201];
    	for (int i=1;i<201;i++) {
    		node[i]       				= new NewVertex(new Integer(i).toString());
    	}
    	
    	try {
			FileReader fReader			= new FileReader("dijkstraData.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				String[] str				= line.split("\\s");
				Integer id					= Integer.parseInt(str[0].trim());	
				ArrayList<Edge> edgeList	= new ArrayList<Edge>();
				for (int i=1;i<str.length;i++) {
					String[] entry				= str[i].split(",");
					int target					= Integer.parseInt(entry[0].trim());
					int weight					= Integer.parseInt(entry[1].trim());
					edgeList.add				(new Edge(node[target], weight));
				}
				Edge[] edgeArray			= new Edge[edgeList.size()];
				node[id].adjacencies 		= edgeList.toArray(edgeArray);
		}
		lReader.close				();
		} catch (Exception e) {
		  e.printStackTrace();
	}

      computePaths(node[1]);
        
      for (int i=1;i<node.length;i++) {
		    System.out.println("Distance to " + node[i] + ": " + node[i].minDistance);
		    List<NewVertex> path = getShortestPathTo(node[i]);
		    System.out.println("Path: " + path);
        }
    }
}
