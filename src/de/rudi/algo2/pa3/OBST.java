package de.rudi.algo2.pa3;

import java.util.Arrays;
import java.util.Scanner;

class OBST
{
   static String [] idnt;
   static int [][] c,r,w;
   static int [] p,q ;
    
   static int find(int i,int j)
   {
      int l=0,min=2000;
      for(int m=i+1;m<=j;m++)
         if(c[i][m-1]+c[m][j]<min)
           {
             min=c[i][m-1]+c[m][j];
             l=m;
           }
      return l;
   }
    
   static void print(int i,int j)
   {
       if(i<j)
           System.out.print(idnt[r[i][j]] + ' ');
       else
           return;
       print(i,r[i][j]-1);
       print(r[i][j],j);
   }
    
   public static void main(String[] args)
   {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the no, of identifiers");
        int n=sc.nextInt();
        int s=n+2;
        idnt=new String[s];
        c=new int[s][s];
        r=new int[s][s];
        p=new int[s];
        q=new int[s];
        w=new int[s][s]; 
        System.out.println("enter identifiers");
        for(int i=1;i<=n;i++)
            idnt[i]=sc.next();
        System.out.println("enter success propability for identifiers");
        for(int i=1;i<=n;i++)
            p[i] = sc.nextInt();
        System.out.println("enter failure propability for identifiers");
        for(int i=0;i<=n;i++)
            q[i] = sc.nextInt();
        for(int i=0;i<=n;i++)
         {
                w[i][i]=q[i];
                c[i][i]=r[i][i] = 0;
                w[i][i+1]=q[i]+q[i+1]+p[i+1];
                r[i][i+1]=i+1;
                c[i][i+1]=q[i]+q[i+1]+p[i+1];
         }
         w[n][n]=q[n];
         r[n][n]=c[n][n]=0;
         for(int m=2;m<=n;m++)
                for(int i=0,j,k;i<=n-m;i++)
                 {
                    j=i+m;
                    w[i][j]=w[i][j-1]+p[j]+q[j];
                    k=find(i,j);
                    r[i][j]=k;
                    c[i][j]=w[i][j]+c[i][k-1]+c[k][j];
                 }
           System.out.print( "\nTree in pre order : ");
           print(0,n);   
           for (int[] x : r) {
        	   System.out.println (Arrays.toString(x));
           }
  }
}