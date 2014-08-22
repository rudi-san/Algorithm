package de.rudi.algo2.pa5;

//import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class Combinations {

	private int[]		 				c;	
//	private static final	BigInteger	NULL	= BigInteger.valueOf(0);
//	private static final	BigInteger	EINS	= BigInteger.valueOf(1);
	private TreeMap<Integer, Integer> 	bst		= new TreeMap<Integer, Integer>();
	private static Logger 				logger	= Logger.getLogger(Combinations.class);
	
	public Combinations (int n, int k) {
//		BigInteger count		= BigInteger.valueOf(1);
//		
//		for (int i=n;i>n-k;i--) {
//			BigInteger val			= BigInteger.valueOf(i);
//			count					= count.multiply(val);
//		}
//		
//		count					= count.divide(factorial(BigInteger.valueOf(k)));
//		c						= new int[count.intValue()];
		ArrayList<Integer> list	= new ArrayList<Integer>();
		int[] a					= new int[k];
		int zaehl				= 0;
		for (int z=0;z<n;z++)
			zaehl					= foo(0,z,n,k-1,list,a,zaehl);
		Integer[] x				= new Integer[list.size()];
		x						= list.toArray(x);
		c						= new int[x.length];
		for (int i=0;i<x.length;i++)
			c[i]					= x[i];
	}

	private int foo (int index, int wert, int n, int k, ArrayList<Integer> list, int[] a, int zaehl) {
		a[index]		= wert;
		if  (index==k)  { 
			int[] comb			= Arrays.copyOf(a,a.length);
			int x				= getBitComb(comb);
			if (cont(x,0)) {
				addBST				(comb, zaehl++);
				list.add			(x);
			}
		}
		if  (index<k) 
			for (int z=wert+1;z<n;z++)
				zaehl = foo		(index+1,z,n,k,list,a,zaehl);
		
		return zaehl;
	}
	
	public int[] getCombinations () {
		return 				c;
	}
	
	private static int getBitComb		(int[] a) {
		int bits				= 0;
		for (int i=0;i<a.length;i++) {
			int x	= 1<<a[i];
			bits		= bits|x;
		}
		return 				bits;
	}
	
	private void addBST			(int[] a, int value) {
		int key				= getBitComb(a);
		logger.debug(Arrays.toString(a)+" wird gespeichert als Key "+key);
		bst.put			(key, value);
	}
	
	public int getBST		(int x) throws NullPointerException {
		return 				bst.get(x);	
	}
	
	public int getBST		(int[] a) {
		return 				getBST(getBitComb(a));	
	}
	
//	private BigInteger factorial(BigInteger n)   {
//	    if 	(n.equals(NULL)) 	return EINS;
//	    else    				return n.multiply(factorial(n.subtract(EINS)));
//	}
	
	public boolean contains (int i, int v) {
		return Combinations.cont(getBST(i),v);
	}
	
	public static boolean cont (int bits, int v) {
		int verschieb	= 32-v-1;
		int test		= bits<<verschieb;
		test			= test>>>31;
		if (test==1)	return true;
		else			return false;
	}
	
	public static int[] getSubset (int bits) {
		ArrayList<Integer> list		= new ArrayList<Integer>();
		for (int i=0;i<32;i++) {
			if (cont(bits,i))
				list.add			(i);
		}
		int[] subset		= new int[list.size()];
		for (int i=0;i<subset.length;i++) {
			subset[i]		= list.get(i);
		}
		return 				subset;
	}
	
	public static int getSubsetOhneJ (int bits, int j) {
		int x		= 0xffffffff;
		int y		= 1<<j;
		int z		= x^y;
		return      bits & z;
	}
	
	public static void test01 () {
		for (int i=1;i<26;i++) {
			Combinations comb	= new Combinations(25,i);
			int[] x				= comb.getCombinations();
			logger.info ("i="+i+";len="+x.length);
			for (int j=0;j<x.length;j++) {
				int z		= comb.getBST(x[j]);
				if (j!=z)
					logger.fatal	("Hier stimmt was nicht: "+i+" "+j+" "+z);
			}
//			for (int[] y : x) 
//				System.out.println(Arrays.toString(y));
		}
	}
	
	public static void test02 () {
		int[] a			= { 0, 16 };
		int	test		= getBitComb(a);
		for (int x : a) {
			if (!cont(test,x))
				logger.fatal		("Fehler: "+x+" nicht in "+Arrays.toString(a));
		}
		if (cont(test,1))
			logger.fatal		("Fehler: 1 in "+Arrays.toString(a));
		int test2		= getSubsetOhneJ(test, 0);
		for (int x : a) {
			if (x!=0&&!cont(test2,x))
				logger.fatal		("Fehler: "+x+" nicht in "+Arrays.toString(a));
		if (cont(test2,0)||cont(test2,0))
			logger.fatal		("Fehler: 0 in "+Arrays.toString(a));
		}
	}
	
	public static void main(String[] args) {
		test01		();
		test02		();
	}

}
