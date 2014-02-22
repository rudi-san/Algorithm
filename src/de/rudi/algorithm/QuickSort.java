package de.rudi.algorithm;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class QuickSort {
	
	private static int laufart	= 0;

	public static int sort (Integer[] array, int offset, int len) {
		if  (len>1) {
			int z			= 0;
			if (laufart==1)
				swap (array, offset, offset);
			if (laufart==2)
				swap (array, offset, offset+len-1);
			if (laufart==3)
				swap (array, offset, middle(array, offset,offset+len-1));
			
			int i 			= offset+1; 
			for (int j=i;j<offset+len;j++) { 
				z++;
				if (array[offset]>array[j]) 
					swap			(array,j,i++);
			}
			swap			(array,	i-1,	offset);
			z += sort			(array,	offset,	i-offset-1);
			z += sort			(array,	i,		offset+len-i);
			return z;
		}
		return 0;
	}
	
	private static int middle(Integer[] array, int offset, int i) {
		int first = array[offset];
		int last  = array[i];
		int middle	= array[(offset+i)/2];
		if (first>middle&&first<last || first>last&&first<middle) return offset;
		if (last>middle&&last<first || last>first&&last<middle) return i;
		return (offset+i)/2;
	}

	public static void swap (Integer[] array, int iAlt, int iNeu) {
		int x			= array[iAlt];
		array[iAlt] 	= array[iNeu];
		array[iNeu]		= x;
	}
	
	public static void print (Integer[] array, int offset, int len) {
		boolean start = true;
		for (int i=offset;i<len+offset;i++) { 
			if (start) {
				System.out.print 	(array[i]);
				start				= false;
			}
			else
				System.out.print	(", "+array[i]);
		}
		System.out.println		();
	}
	
	public static void test() {
		Integer[][] i 	= {	{3,7,44,0,-55555,777777,2,32323,61,3465,6,63,77,5245252}
					  ,	{3}
					  , {}
					  , {8,7,6,5,4,3,2,1}
					  , {3,4,5,88,88,-2,0,88,234242,-66,4}
					  , {-55555, 0, 2, 3, 6, 7, 44, 61, 63, 77, 3465, 32323, 777777, 5245252}
					};
		for (Integer[] x : i) {
			print		(x,0,x.length);
//			print		(sort(x,0,x.length),0,x.length);
			sort		(x,0,x.length);
			print		(x,0,x.length);
		}
	}
	
	public static void prod () {
		for (laufart=1;laufart<4;laufart++) {
			ArrayList<Integer> list		= new ArrayList<Integer>();
			try {
				FileReader fReader			= new FileReader("QuickSort.txt");
				LineNumberReader lReader	= new LineNumberReader(fReader);
				String line					= null;
				while ((line=lReader.readLine())!=null) {
					list.add					(Integer.parseInt(line.trim()));
				}
				lReader.close				();
				Integer[] array				= new Integer[list.size()];
				array						= list.toArray(array);
//				print		(array,0,array.length);
				int z 		= sort (array,0,array.length);
//				print		(array,0,array.length);
				System.out.println("------>"+z+"<--------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
//		test();	
		prod();
	}

}
