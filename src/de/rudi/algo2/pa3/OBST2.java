package de.rudi.algo2.pa3;
import java.util.Arrays;
 
public class OBST2 {
    static int[] elements = { 20, 5, 17, 10, 20, 3, 25 };;
    static int[][] dp;
    static int[] sum;
 
    public static int optimal(int s, int t) {
        if (s == t)
            return 0;
        else { 
        	if (dp[s][t] != -1)
        		return dp[s][t];
        	else {
        		int min = Integer.MAX_VALUE;
        		for (int i = s; i <= t; i++) {
        			int leftSubtree		= (i - 1 >= s) ? optimal(s, i - 1) + sum[i - 1] - sum[s]	 + elements[s]  	: 0;
        			int rightSubtree	= (i + 1 <= t) ? optimal(i + 1, t) + sum[t] 	- sum[i + 1] + elements[i + 1] 	: 0;
        			min	= Math.min(min, leftSubtree+rightSubtree);
//        			min = Math.min	(min, 
//        				(	(i - 1 >= s) ? sum[i - 1] - sum[s]	+ elements[s] + optimal(s, i - 1) : 0)
//        				 + ((i + 1 <= t) ? optimal(i + 1, t) + sum[t] - sum[i + 1] + elements[i + 1] : 0));
        		}
        		return dp[s][t] = min;
        	}
        }
    }
 
    public static void main(String[] args) {
            int n 			= 7;
            sum 			= new int[n];
            sum[0] 			= elements[0];
            for (int i = 1; i < n; i++)
                sum[i] 			= sum[i - 1] + elements[i];
           	System.out.println(Arrays.toString(sum));
            dp 				= new int[n][n];
            for (int i = 0; i < dp.length; i++)
                Arrays.fill		(dp[i], -1);
            System.out.println(optimal(0, n - 1));
            for (int[] x : dp)
            	System.out.println(Arrays.toString(x));
    }
}