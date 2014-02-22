package de.rudi.algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Logger;

public class Heap {

	private 		int[][] 				array;
	private         ArrayList<Integer>[] 	pathList;
	private 		int     				len;
	private static 	Logger					logger;

	public Heap () {
		this(100);
	}
	
	@SuppressWarnings("unchecked")
	public Heap (int capacity) {
		logger		= Logger.getLogger(Heap.class);
		array 		= new int[capacity][2];
		pathList	= new ArrayList[capacity];
		len			= 0;
	}
	
	private Heap (int[][] array, ArrayList<Integer>[] pathList, int len) {
		this.pathList	= Arrays.copyOf(pathList, pathList.length);
		this.array		= Arrays.copyOf(array, array.length);
		this.len		= len;
	}
	
	public Heap clone () {
		return new Heap(array, pathList, len);
	}
	
	public void insert (int key, int value, ArrayList<Integer> path) {

		if (len==array.length) {
			logger.debug			("array.length="+array.length+";len="+len);
			int[][] newArray		= new int[array.length*2][2];
			for (int i=0;i<array.length;i++)
				newArray[i]					= array[i];
			array								= newArray;
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] newPathList	= new ArrayList[pathList.length*2];
			for (int i=0;i<pathList.length;i++)
				newPathList[i]				= pathList[i];
			pathList					= newPathList;
		}
		array[len][0]			= key;
		array[len][1]			= value;
		pathList[len]			= path;
		bubbleUp				(len);
		len++;
		if (value==95) {
			logger.info		(value+";Distance="+key+";Path="+path.toString());
		}
	}
	
	public void delete (int value) {
		for (int i=0;i<len;i++) {
			if (array[i][1]==value) {
				array[i][0]		= 1000000;
//				swap			(i,--len);
				bubbleDown		(i);
//				bubbleUp		(i);
//				bubbleDown		(i);
//				logger.debug	("nach bubbleUp:   "+this);
			}
		}
	}
	
	private void bubbleUp 	(int last) {
		if  (last==0)	return;
		int parent		= last/2;
		if  (array[last][0]<array[parent][0]) {
			swap			(last,parent);
			bubbleUp		(parent);
		}
	}
	
	public DijkstraElem extractMin () {
		if (array[0][1]==95)
			logger.debug		("extractMin len="+len);
		if  (len==0)		return null;
		int[] x				= array[0];
		DijkstraElem d	= new DijkstraElem(x[0], x[1], pathList[0]);
		swap				(0,--len);
		bubbleDown			(0);
		delete				(x[1]);
		return 				d;
	}

	private void bubbleDown 	(int parent) {
		int left			= parent*2+1;
		int right			= parent*2+2;
		if  (left>len-1)	return;
		if  (right>len-1||array[left][0]<array[right][0]) {
			if (array[left][0]<array[parent][0]) {
				swap			(left, parent);
				bubbleDown		(left);
			}
		}
		else {
			if (right<=len&&array[right][0]<array[parent][0]) {
				swap			(right, parent);
				bubbleDown		(right);
			}
		}		
	}

	public void swap (int iAlt, int iNeu) {
		int[] x					= array[iAlt];
		array[iAlt] 			= array[iNeu];
		array[iNeu]				= x;
		ArrayList<Integer> y	= pathList[iAlt];
		pathList[iAlt]			= pathList[iNeu];
		pathList[iNeu]			= y;
	}
	
	public String toString () {
		Heap newHeap			= clone();
		StringBuffer buf		= new StringBuffer();
		buf.append				("[");
		DijkstraElem x		= null;
		while	((x=newHeap.extractMin())!=null) {
			buf.append(" ["+x.getKey()+","+x.getValue()+", Path=[");
			for (Integer i : x.getPath()) {
				buf.append(" "+i+",");
			}
			buf.append (" ]");
		}
		buf.append				(" ]");
		return 					buf.toString();
	}

	public static void main(String[] args) {
//		Heap h		= new Heap(2);
//		h.insert	(88, 1);
//		System.out.println (h);
//		h.insert	(7, 2);
//		h.insert	(766, 5);
//		h.insert	(0, 7);
//		h.insert	(66, 9);
//		h.insert	(7777, 0);
//		System.out.println (h);
//		h.delete	(2);
//		System.out.println (h);
	}
}
