package de.rudi.algorithm;

public class GgT {

	public static int ggT (int a, int b) {
		return (b==0) ? a : ggT(b,a%b);
	}
	
	public static void main(String[] args) {
		int[][] z = {	{14,122},
						{222211,4513},
						{3,45741},
						{332,4},
						{34,234234},
						{1,4},
						{34,557},
						{123,1230},
						{220,44545512}
		};
		for (int i=0;i<z.length;i++) {
			System.out.println(z[i][0]+"\t"+z[i][1]+"\tggT="+ggT(z[i][0],z[i][1]));
		}
	}

}
