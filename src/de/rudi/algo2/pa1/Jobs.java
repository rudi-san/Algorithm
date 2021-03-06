package de.rudi.algo2.pa1;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Arrays;

public class Jobs implements Comparable<Jobs> {

	private static boolean question2 = false;
	private int weight;
	private double len;
	private double sort;

	public Jobs(int weight, int len) {
		this.weight = weight;
		this.len = len;
		if (question2)
			sort = weight / this.len;
		else
			sort = weight - len;
	}

	@Override
	public int compareTo(Jobs o) {
		double d = o.sort - this.sort;
		if (d == 0)
			return o.weight - this.weight;
		else
			return (d > 0) ? 1 : -1;
	}

	@Override
	public String toString() {
		return "[len=" + len + "];[weight=" + weight + "];[sort=" + sort + "]";
	}

	public static void prod() {
		String[] fileName	= { "jobs.txt", "test01.txt", "test02.txt" };
		try {
			File file			= new File("data\\pa1\\"+fileName[0]);
			FileReader fReader = new FileReader(file);
			LineNumberReader lReader = new LineNumberReader(fReader);
			String line = lReader.readLine();
			int len = Integer.parseInt(line.trim());
			Jobs[] jobs = new Jobs[len];
			int i = 0;
			while ((line = lReader.readLine()) != null) {
				String[] words = line.split(" ");
				jobs[i++] = new Jobs(Integer.parseInt(words[0]),
						Integer.parseInt(words[1]));
			}
			lReader.close();
			Arrays.sort(jobs);
			long sum = 0;
			long compl = 0;
			for (Jobs j : jobs) {
				compl += j.len;
				sum += compl * j.weight;
				// if (compl<10000)
				// System.out.println (j+";[compl="+compl+"]:[sum="+sum+"]");
			}
			System.out.println("Completion Time: " + compl + ";Sum :" + sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		prod();
		question2 = true;
		prod();
	}
}