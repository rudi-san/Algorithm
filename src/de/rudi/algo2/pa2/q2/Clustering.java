package de.rudi.algo2.pa2.q2;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Clustering {

	public static Logger logger = Logger.getLogger("Clustering1");

	public static void prod() {
		String[] fileName				= { "clustering_big.txt", "test01.txt", "test02.txt" };
		try {
			FileReader fReader 			= new FileReader("data\\pa2q2\\"+fileName[0]);
			LineNumberReader lReader 	= new LineNumberReader(fReader);

			String line 				= lReader.readLine();
			String[] zWords 			= line.split(" ");
			int len 					= Integer.parseInt(zWords[0].trim());
			int bitLen 					= Integer.parseInt(zWords[1].trim());
			String[] node 				= new String[len];
			
			for (int i= 0;(line = lReader.readLine()) != null;i++) {
				node[i]			= compress(line, bitLen);
			}
			
			lReader.close				();
			
			HashMap<String, ArrayList<Integer>> map	= new HashMap<String, ArrayList<Integer>>();
			
			for (int i=0;i<len;i++) {
				ArrayList<Integer> zxList	= map.get(node[i]);
				if (zxList==null) 
					zxList						= new ArrayList<Integer>();
				zxList.add					(i);
				map.put						(node[i], zxList);
			}
			
			UnionFind uf				= new UnionFind(len);

			for (int i = 0; i < len; i++) {
//				if (i % 1000 == 0)
//					logger.info("Verarbeitete Nodes=" + i);
				
				String[] varia			= getVaria(node[i]);
				
				for (int j =0; j < varia.length; j++) {
					String key					= varia[j];
					ArrayList<Integer>  list	= map.get(key);
					if (list!=null) {
						ArrayList<Integer> newList		= new ArrayList<Integer>();
						for (int x : list) {
							if (i!=x) {
								uf.unite			(i, x);
								newList.add			(x);
							}
						}
						if (newList.size()>0)
							map.put		(key, newList);
						else
							map.remove	(key);
					}
				}
			}

			System.out.println		("Anzahl Cluster: "+uf.components());

		} catch (IOException e) {}
	}
	
	private static String compress (String source, int len) {
		StringBuffer buf	= new StringBuffer(len);
		for (int i=0;i<len*2;i+=2)
			buf.append			(source.substring(i, i+1));
		return 				buf.toString();
	}
	
	private static String[] getVaria (String source) {
		String[] ret		= new String[301];
		int ind				= 0;
		ret[ind++]			= source;
		
		char[]  c			= source.toCharArray();
 		
		for (int i=0;i<c.length;i++) {
			c[i] 				= (c[i]=='0') ? '1' : '0';
			ret[ind++]			= String.valueOf(c);
			for (int j=i+1;j<c.length;j++) {
				c[j] 				= (c[j]=='0') ? '1' : '0';
				ret[ind++]			= String.valueOf(c);
				c[j] 				= (c[j]=='0') ? '1' : '0';
			}
			c[i] 				= (c[i]=='0') ? '1' : '0';
		}
		
		return ret;
	}

	public static void main(String[] args) {
		prod();
	}
}