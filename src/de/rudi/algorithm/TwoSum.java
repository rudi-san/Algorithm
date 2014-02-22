package de.rudi.algorithm;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashSet;

import org.apache.log4j.Logger;


public class TwoSum {
	
	private static HashSet<Long> hash = new HashSet<Long>(1100000);
	private static Logger		logger	= Logger.getLogger(TwoSum.class);
	
	public static boolean isValid (int t) {
		for (Long x : hash) {
			if (hash.contains(t-x)&&!(t-x==x)) {
				return true;
			}
		}
		return false;
	}
	
	public static void fillHash () {
		logger.debug				("Start fillHash");
		try {
			FileReader fReader			= new FileReader("algo1_programming_prob_2sum.txt");
			LineNumberReader lReader	= new LineNumberReader(fReader);
			String line					= null;
			while ((line=lReader.readLine())!=null) {
				hash.add					(Long.parseLong(line.trim()));
			}
			lReader.close				();
			logger.debug				("End fillHash");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		fillHash		();
		int count		= 0;
		for (int t=-1000;t<1000;t++) {
			if (t%100==0)		logger.debug("t="+t+";count="+count);
			if (isValid(t))		count++;
		}
		System.out.println(count);
	}

}
