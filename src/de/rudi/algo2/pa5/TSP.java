package de.rudi.algo2.pa5;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class TSP {

	private static int				vLen;
	private static Edges			e;
	private static Logger			logger = Logger.getLogger(TSP.class); 
	
	
	private static float[][] computeDist(int m, float[][] left) {
		Combinations combLeft		= new Combinations(vLen,m-1);
//		int[] cLeft					= combLeft.getCombinations();
		Combinations combRight		= new Combinations(vLen,m);
		int[] cRight				= combRight.getCombinations();
		for (int x : cRight)
			logger.info					("Combinations "+Arrays.toString(Combinations.getSubset(x)));
		logger.debug				("Anzahl S: "+cRight.length);
		float[][] right				= new float[cRight.length][vLen];
		for (float[] f : right) 
			Arrays.fill					(f, Float.POSITIVE_INFINITY);
		for (int s=0;s<cRight.length;s++) {
				int[] subset				= Combinations.getSubset(cRight[s]);
				for (int j : subset) {
//					if (Combinations.cont(cRight[s], j)&&j!=0) {
					if (j!=0) {
						int sOhneJ						= Combinations.getSubsetOhneJ(cRight[s], j);
						int[] ohneSubset				= Combinations.getSubset(sOhneJ);
						int leftIndex					= 0;
						try {
							leftIndex						= combLeft.getBST(sOhneJ);
						}
						catch (NullPointerException e) {
							logger.fatal					(e.getMessage());
							logger.fatal					("Subset="+Arrays.toString(Combinations.getSubset(cRight[s]))
															+";j="+j+";ohneSubset="+Arrays.toString(ohneSubset));
							System.exit						(0);
						}
						float min						= Float.POSITIVE_INFINITY;
						for (int k=0;k<vLen;k++) {
							float dist						= e.getDist(j, k) + left[leftIndex][k];
							min								= (dist<min) ? dist : min;
						}
						right[s][j]   					= min;
					}
				}
//			}
		}
		return 					right;
	}
	
	public static void main(String[] args) {
		
		e					= new Edges("data\\pa5\\tsp.txt");
		vLen				= e.getVLen();
		Combinations comb	= new Combinations(vLen,1);
		int[] c				= comb.getCombinations();

		float[][] a			= new float[c.length][vLen];
		Arrays.fill			(a[0], Float.POSITIVE_INFINITY);
		a[0][0]				= 0.f;
		logger.info			("[i=0] "+Arrays.toString(a[0]));
		
		for (int i=0;i<a.length;i++)
			logger.debug	(Arrays.toString(a[i]));
		
		for (int m=2;m<=vLen;m++) {
			logger.info		("Verarbeitet Subsets der Länge: "+m);
			a				= computeDist(m,a);
			for (int i=0;i<a.length;i++)
				logger.info	("[i="+i+"] "+Arrays.toString(a[i]));
		}
		
		float min		= Float.MAX_VALUE;
		for (int i=1;i<vLen;i++) {
			float dist			= a[0][i] + e.getDist(0, i);
			min					= (dist<min) ? dist : min;
		}
		System.out.println ("Ermittelter Mindestweg: "+min);
	}
}
