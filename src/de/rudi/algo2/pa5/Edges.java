package de.rudi.algo2.pa5;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class Edges {

	private HashMap<Long, Float> 	edgeMap	= new HashMap<Long, Float>();
	private float[][] 				vertex;
	private int						vLen;
	private static Logger			logger	= Logger.getLogger(Edges.class);
	
	public Edges (String fileName) {
		try {
			FileReader fReader 			= new FileReader(fileName);
			LineNumberReader lReader 	= new LineNumberReader(fReader);
			String line 				= lReader.readLine();
			String[] wLen 				= line.split(" ");
			vLen 						= Integer.parseInt(wLen[0]);
			logger.debug				("Anzahl Knoten="+vLen);
			vertex						= new float[vLen][2];
			int z						= 0;
			while ((line = lReader.readLine()) != null) {
				String[] words 			= line.split(" ");
				vertex[z][0]			= Float.parseFloat(words[0]);
				vertex[z][1]			= Float.parseFloat(words[1]);
				z++;
			}
			for (int i=0;i<vertex.length;i++) {
				for (int j=0;j<vertex.length;j++) {
					addMap			(i,j);
				}
			}
			lReader.close				();
			logger.debug					("Einlesen abgeschlossen");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void addMap		(int i, int j) {
		long l			= i;
		l				= l<<32;
		l				+= j;
		float xAxis		= vertex[i][0] - vertex[j][0];
		xAxis			*= xAxis;
		float yAxis		= vertex[i][1] - vertex[j][1];
		yAxis			*= yAxis;
		float dist		= (float) Math.sqrt(xAxis+yAxis);
		logger.debug	("[i="+i+"];[j="+j+"];[dist="+dist+"]");
		edgeMap.put		(l, dist);
	}
	
	public float getDist	(int i, int j) {
		long l			= i;
		l				= l<<32;
		l				+= j;
		return			edgeMap.get(l);
	}
	
	public int getVLen	() {
		return vLen;
	}
	
	public static void main(String[] args) {
		Edges e			= new Edges("data\\pa5\\tsp.txt");
		for (int i=0;i<25;i++) {
			for (int j=0;j<25;j++) {
				System.out.println ("i["+i+"];j["+j+"];"+e.getDist(i, j));
			}
		}
		
		for (int i=0;i<25;i++) {
			for (int j=0;j<25;j++) {
				if (e.getDist(i, j)!=e.getDist(j, i))
					System.out.println ("i["+i+"];j["+j+"];"+e.getDist(i, j)+"!="+e.getDist(j, i));
			}
		}
	}

}
