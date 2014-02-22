package de.rudi.algorithm;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

public class Median {

	private static PriorityQueue<MaxInt>	maxHeap	= new PriorityQueue<MaxInt>();
	private static PriorityQueue<Integer> 	minHeap	= new PriorityQueue<Integer>();
	private static Logger					logger	= Logger.getLogger(Median.class);
	
	public static Integer getMedian () {
		if (minHeap.size()>maxHeap.size()) 
			return (minHeap.peek());
		else
			return (maxHeap.peek().getValue());
	}
	
	public static void add (int newElem) {
		int max			= (maxHeap.isEmpty()) ? Integer.MIN_VALUE : maxHeap.peek().getValue();
		if (max>newElem) {
			maxHeap.add		(new MaxInt(newElem));
			while (maxHeap.size()>minHeap.size()+1)
				minHeap.add			(maxHeap.poll().getValue());
		}
		else {
			minHeap.add		(newElem);
			while (minHeap.size()>maxHeap.size()+1)
				maxHeap.add			(new MaxInt(minHeap.poll()));
		}
	}
	
	public static Integer[] fillStream () {
		logger.debug				("Start fillStream");
		ArrayList<Integer> list		= new ArrayList<Integer>();
		try {
			FileReader fReader			= new FileReader("Median.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				list.add					(Integer.parseInt(line.trim()));
			}
			lReader.close				();
			logger.debug				("End fillHash");
			Integer[] result			= new Integer[list.size()];
			return						list.toArray(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
//		Integer[] stream	= { 330, 66, -77, 2, -34, 123132, 88, 67, 9, -3, -44, 12312, -2323, 5 };
		long sum			= 0L;
		Integer[] stream	= fillStream();
//		maxHeap.add			((stream[0]>stream[1]) ? new MaxInt(stream[1]) :  new MaxInt(stream[0]));
//		sum					+= getMedian().longValue();
//		minHeap.add			((stream[0]>stream[1]) ? stream[0] : stream[1]);
//		sum					+= getMedian().longValue();
		for (int i=0;i<stream.length;i++) {
			add					(stream[i]);
			sum					+= getMedian().longValue();
//			Integer[] akt	= Arrays.copyOfRange(stream, 0, i+1);
//			Arrays.sort		(akt);
//			logger.debug	(Arrays.toString(akt));
//			int aktlen		= akt.length;
//			Integer median	= (aktlen%2==0) ? akt[aktlen/2-1] : akt[aktlen/2];
//			logger.debug	(median+" "+getMedian());
//			if (median.intValue()!=getMedian().intValue())
//				logger.debug	("==>"+median.toString()+"<== ==>"+getMedian()+"<==");			
		}
		logger.debug		 	(sum+" "+sum%10000);
	}
	
	public static final class MaxInt implements Comparable<MaxInt> {
		private Integer  value;
		public MaxInt (Integer value)	{ this.value 		= value; }
		public int compareTo(MaxInt o) 	{ return 			o.value-this.value;	}
		public Integer getValue ()		{ return 			value; }
	}

}
