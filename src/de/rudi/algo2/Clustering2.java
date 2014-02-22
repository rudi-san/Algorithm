package de.rudi.algo2;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.logging.Logger;


public class Clustering2 {
	
	public 	static Logger			logger	= Logger.getLogger("Clustering");
	
	public static void prod() {
		try {
			FileReader fReader			= new FileReader("clustering_small.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);

			String line					= lReader.readLine();
			String[] zWords				= line.split(" ");
			int len						= Integer.parseInt(zWords[0].trim());
			int bitLen					= Integer.parseInt(zWords[1].trim());
			int[][] node				= new int[len][bitLen];
			int i						= 0;
			while ((line=lReader.readLine())!=null) {
				String[] words				= line.split(" ");
				for (int j=0;j<bitLen;j++) 
					node[i][j]				= Integer.parseInt(words[j].trim());
				i++;
			}
			lReader.close				();

			PrintWriter writer			= new PrintWriter("edgeFile_small.txt");
			for (i=0;i<len-1;i++) {
				if (i%1000==0)  		logger.info ("Verarbeitete Nodes="+i);
				for (int j=i+1;j<len;j++) {
//					if (j%1000==0)  		logger.info ("Verarbeitete Vergleichnodes: "+j);
					int distance			= 0;
					for (int k=0;k<bitLen;k++) {
						if (node[i][k]!=node[j][k])
							distance++;
					}
					if (distance<3) {
						writer.println	(i+" "+j+" "+distance);
					}
				}
			}
			writer.close			();
		} catch (IOException e) {
			
		}
	}

	public static void main (String[] args) {
		prod		();
	}
}
