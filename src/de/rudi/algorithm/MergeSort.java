package de.rudi.algorithm;

public class MergeSort {

	public static int[] sort (int[] array) {
		int len			= array.length;
		if  (len<2)
			return array;
		else {
			int[] left		= new int[len/2];
			int[] right		= new int[len-(len/2)];
			for (int i=0;i<left.length;i++) {
				left[i]	= array[i];
			}
			for (int i=0;i<right.length;i++) {
				right[i]	= array[i+len/2];
			}
			left	= sort(left);
			right	= sort(right);
			return merge(left,right);
		}
	}
	
	public static int[] merge(int[] left, int[] right) {
		int len			= left.length+right.length;
		int[] array		= new int[len];
		int iLeft		= 0;
		int iRight		= 0;
		for (int i=0;i<len;i++) {
			if (iRight==right.length || iLeft < left.length && left[iLeft] < right[iRight]) {
				array[i]	= left[iLeft];
				iLeft++;
			}
			else {
				array[i]	= right[iRight];
				iRight++;
			}
		}
		return array;	
	}
	
	public static void print (int[] array) {
		boolean start = true;
		for (int i : array) { 
			if (start) {
				System.out.print 	(i);
				start				= false;
			}
			else
				System.out.print	(", "+i);
		}
		System.out.println		();
	}
	
	public static void main(String[] args) {
		int[] i1 	= {3,7,44,0,-55555,777777,2,32323,6,3465,6,6,77,5245252};
		print		(i1);
		print		(sort(i1));
		int[] i2 	= {3};
		print		(i2);
		print		(sort(i2));
		int[] i3 	= {};
		print		(i3);
		print		(sort(i3));
		int[] i4 	= {-55555, 0, 2, 3, 6, 6, 6, 7, 44, 77, 3465, 32323, 777777, 5245252};
		print		(i4);
		print		(sort(i4));
	}

}
