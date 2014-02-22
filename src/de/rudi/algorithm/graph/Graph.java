package de.rudi.algorithm.graph;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Graph {

	private HashMap<Integer, Vertex> 	v;
	private ArrayList<Long>				e;
	private Random						random;
	
	public Graph () {
		v		= new HashMap<Integer, Vertex>();
		e		= new ArrayList<Long>();
		random	= new Random();
	}
	
	public void addVertex(Integer id, Integer[] nodes) {
		if (v.containsKey(id)) {
			System.out.println("Vertex mit id="+id+" bereits vorhanden");
			System.exit(0);
		}
		v.put(id, new Vertex(nodes));
		for (Integer node : nodes) 	{
			if (!v.containsKey(node)||!v.get(node).isNode(id))
				e.add(together(id, node));
		}
	}
	
	public int mergeRandomEdge() {

//		System.out.print	(e.size());
		if (v.size()==2) {
			return e.size();
		}
		int r			= random.nextInt(e.size());
		int iV1			= parted(e.get(r))[0];
		int iV2			= parted(e.get(r))[1];
//		System.out.println("Collapse "+iV2+" in "+iV1);
		Vertex v1		= v.get(iV1);
		Vertex v2		= v.get(iV2);

		for (Integer i : v2.getNodes()) {
			Vertex zx		= v.get(i);
			if (zx!=v1) {
				zx.changeNode	(iV2, iV1);
				v1.addNode		(i);
				edgeChange		(iV2, iV1, i);
			}
		}
		edgeRemove		(iV1, iV2);
		v1.removeNode	(iV2);
		v.remove		(iV2);
		return   -1;
	}

	public boolean inspect () {
		boolean 	ok	= true;
		for (Integer iVer : v.keySet()) {
			Vertex ver		= v.get(iVer);
			for (Integer i : ver.getNodes()) {
				if (!v.containsKey(i)) {
					System.out.println("Edge "+i+" in Vertex "+iVer+" nicht vorhanden: ");
					ok	= false;
				}
				else {
					if (!v.get(i).isNode(iVer)) {
						System.out.println("Edge "+i+" in Vertex nicht vorhanden: "+iVer);
						ok	= false;						
					}
				}
			}
		}
		for (Long l : e) {
			int i1			= parted(l)[0];
			int i2			= parted(l)[1];
			if (!v.containsKey(i1) || !v.containsKey(i2))  {
				System.out.println("Edge nicht vorhanden: "+i1+" "+i2);
				ok	= false;				
			}
		}
		return ok;
	} 
	
	private int edgeRemove (int i1, int i2) {
		int z			= 0;
		while (e.remove(together(i1,i2)))
			z++;
		while (e.remove(together(i2,i1)))
			z++;
		return 			z;
	}

	private void edgeChange (int alt, int neu, int i) {
		while (e.remove(together(alt,i)))
			e.add			(together(neu, i));
		while (e.remove(together(i,alt)))
			e.add			(together(i, neu));
	}

	public static long together (int i1, int i2) {
		long l		= i1;
		return		(l<<32)+i2;
	}
	
	public static int[] parted (long l) {
		int[] i		= new int[2];
		i[0]		= (int)(l>>>32);
		long x		= l<<32;
		i[1]		= (int)(x>>>32);
		return		i;
	}
	
	public static int prod () {
		Graph graph					= new Graph();
		try {
			FileReader fReader			= new FileReader("kargerMinCut.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				String[] str				= line.split("\\s");
				Integer id					= Integer.parseInt(str[0].trim());
				Integer[] array				= new Integer[str.length-1];
				for (int i=0;i<array.length;i++)
					array[i]					= Integer.parseInt(str[i+1].trim());
				graph.addVertex				(id, array);
			}
			lReader.close				();
			int anzahl 					= 0;
			while						((anzahl=graph.mergeRandomEdge())<0) 
											;
//			System.out.println			("MinCut="+anzahl);
			return 						anzahl;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void test() {
		Graph graph		= new Graph();
		Integer[][]	ti	= { { 1, 2, 3 } ,
							{ 2, 3 } ,
							{ 3, 1, 4 } ,
							{ 4 }
						  };
		for (Integer[] i : ti ) {
			Integer[] rest	= Arrays.copyOfRange(i, 1, i.length);
			graph.addVertex(i[0], rest);
		}
		while (graph.mergeRandomEdge()<0);
	}
	
		public static void main(String[] args) {
//			test();
			int min 	= 1000;
			for (int i=0;i<10;i++) {
				int anzahl = prod();
				System.out.println			((i+1)+". MinCut="+anzahl);
				min = (anzahl<min) ? anzahl : min;
			}
			System.out.println ("All: MinCut = "+min);
	}
}
