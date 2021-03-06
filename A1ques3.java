//A1ques3.java
// Qin Liu 260578790
// Comp 462 Assignment 1 Question 3b

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.*; 
import java.util.*; 

public class A1ques3 {

	public static int subst(char sc, char tc,int match,int tsn, int tvn){
		int sub=0;

		if(sc == tc && sc == ' '){ sub = 0;}
		else if(sc == tc && sc != ' '){ sub = match;
		}
		else if(sc == 'A'){
			if(tc == 'C' || tc == 'T')
			{
				sub = tvn;
			}
			else if(tc == 'G'){
				sub = tsn;
			}
		}

		else if(sc == 'C'){
			if(tc == 'A' || tc == 'G')
			{
				sub = tvn;
			}
			else if(tc == 'T'){
				sub = tsn;
			}
		}

		else if(sc == 'G'){
			if(tc == 'C' || tc == 'T')
			{
				sub = tvn;
			}
			else if(tc == 'A'){
				sub = tsn;
			}
		}

		else if(sc == 'T'){
			if(tc == 'A' || tc == 'G')
			{
				sub = tvn;
			}
			else if(tc == 'C'){
				sub = tsn;
			}
		}

		else{sub = -10;// not in ACGT
		}



		return sub;
	}

	public static int[][]  modifyNW(String s,String t,int match,int tsn,int tvn,int a, int b){
		int sl = s.length(); 
		int tl = t.length(); 
		int i=0;
		int j = 0;
		int k = 0;

		int ptscheck = Math.abs(b)/Math.abs(a*3);
    // System.out.println(ptscheck);

    int [][] x = new int [sl+1][tl+1]; //i+1 = 5 j+1 = 4

    x[0][0]=0;
    
    for(i=1;i<=sl;i++){
    	if(i%3 == 0){	
    		x[i][0] = i*a;}
    		else{x[i][0] = i*b;}
    	}

    	for(j=1;j<=tl;j++){
    		if(j%3 == 0){
    			x[0][j] = j*a;}
    			else{x[0][j] = j*b;}
    		}

    for(i = 1;i<=sl;i++){//col
      for(j = 1;j<=tl;j++){//row

      	// System.out.println("i= "+i + ", j ="+j);
      	char sc = s.charAt(i-1);
      	char tc = t.charAt(j-1);
      	int sub = subst(sc,tc,match,tsn,tvn);

      	// System.out.println("sc= "+sc + ", tc ="+tc);
      	int [] res;

      	if(i>=3 && j <3) {
      		res = new int [3+ptscheck];
      		res[0] = x[i-1][j-1] + sub;
      		// System.out.println("check");
      		res[1] = x[i-1][j]+ b;
      		// System.out.println("check");
      		res[2] = x[i][j-1] + b;
      		// System.out.println("check");

      		for(k=1;k<=ptscheck;k++){
      			if(i-3*k >=0){res[2+k]=x[i-3*k][j]+3*k*a;}

      		}
      	}

      	else if(i<3 && j>=3){
      		res = new int [3+ptscheck];
      		res[0] = x[i-1][j-1] + sub;
      		// System.out.println("check");
      		res[1] = x[i-1][j]+ b;
      		// System.out.println("check");
      		res[2] = x[i][j-1] + b;
      		// System.out.println("check");

      		for(k=1;k<=ptscheck;k++){
      			if(j-3*k >=0){res[2+k]=x[i][j-3*k]+3*k*a;}
      		}
      	}
      	else if(i<3 && j<3){
      		res = new int[3];
      		res[0] = x[i-1][j-1] + sub;
      		// System.out.println("check");
      		res[1] = x[i-1][j]+ b;
      		// System.out.println("check");
      		res[2] = x[i][j-1] + b;
      		// System.out.println("check");
      	}
      	else{res = new int [3+2*ptscheck];


        // Arrays.fill(res, );
      		res[0] = x[i-1][j-1] + sub;
    		// System.out.println("check");
      		res[1] = x[i-1][j]+ b;
      		// System.out.println("check");
      		res[2] = x[i][j-1] + b;
      		// System.out.println("check");

      		for(k=1;k<=ptscheck;k++){
      			if(j-3*k >=0){res[2+k]=x[i][j-3*k]+3*k*a;}
      		}

      		for(k=1;k<=ptscheck;k++){
      			if(i-3*k >=0){res[2+ptscheck+k]=x[i-3*k][j]+3*k*a;}
      		}
      	}


      	int max = res[0];

      	for (int n = 1; n < res.length; n++) {
      		if (res[n] > max) {
      			max = res[n];
      		}
      	}
      	x[i][j] = max;


      }
      
  }


  return x;


}



public static void trace(int [][] x,String s, String t, int match, int tsn, int tvn, int a, int b){
    s = new StringBuilder(s).reverse().toString();//ACGGTCGTG GTGCTGGCA hence sindex from 0 to 8
    t = new StringBuilder(t).reverse().toString();//TCGACA  ACAGCT tindex from 0 to 5

    int sindex = 0;int tindex=0; int k = 0;

    int ptscheck = Math.abs(b)/Math.abs(a*3);

    int c = x.length-1; // rows, r=5
    int r = x[1].length-1;  // columns c =6
    int i;

    char [] sc = new char [r+c]; 
    char [] tc = new char [r+c];
    Arrays.fill(sc, ' ');
    Arrays.fill(tc, ' ');

    while(r != 0 && c !=  0){
    	double [] option = new double [3+2*ptscheck]; 
    	for(int xx = 0;xx<option.length;xx++){
    		option[xx] = Double.NEGATIVE_INFINITY;
    	}
    	int current = x[c][r];
    	char ss = s.charAt(sindex);
    	char tt = t.charAt(tindex);
    	int sub = subst(ss,tt,match,tsn,tvn);

    	option[0] = x[c-1][r-1]; //diag
    	option[1] = x[c][r-1]; //up
    	option[2] = x[c-1][r]; //left


    	if(c>=3){

    		for(int y = 1; y<= ptscheck; y++){
    			option[2+y] = x[c-3*y][r]; //left 3
    		}	
    	}

    	if(r>=3){

    		for(int y = 1;y<= ptscheck; y++){
    			option[2+ptscheck+y] = x[c][r-3*y];
    		}
    	}

    	//int index = choose(option);
    	int index = 0;

    	boolean rb = false; boolean cb = false;
    	if(r>=3){rb = true;}
    	if(c>=3){cb = true;}


    	if(current == option[0]+sub ){
    		index = 0;

    	}
    	else if(current == option[1]+b){
    		index = 1;
    	}
    	else if(current == option[2]+b){
    		index = 2;
    	}
    	else if(cb && current == option[3]+ 3*a){
    		index = 3;
    	}
    	else if(rb && current == option[4] + 3*a){
    		index =4;
    	}


    	if(index == 0){
    		sc[k] = s.charAt(sindex);
    		tc[k] = t.charAt(tindex);
    		sindex++;
    		tindex++;
    		k++;
    		r--;
    		c--;
    	}
    	else if(index == 2){ //left
    		sc[k] = s.charAt(sindex);
    		tc[k] = '-';
    		sindex++;
    		k++;
    		c--;

    	}
    	else if(index == 1){ //up
    		sc[k] = '-';
    		tc[k] = t.charAt(tindex);
    		tindex++;
    		k++;
    		r--;

    	}
    	else if(index > 2 && index <= 2+ptscheck){// left 3
    		for(int y = 0;y<3;y++){
    			sc[k] = s.charAt(sindex);
    			tc[k] = '-';
    			sindex++;
    			k++;
    			c--;

    		}

    	}
    	else if(index > 2+ptscheck && index <= 2+2*ptscheck){
    		for(int y = 0;y<3;y++){
    			sc[k] = '-';
    			tc[k] = t.charAt(tindex);
    			tindex++;
    			k++;
    			r--;    		}

    	}

    	while(r!=0 && c==0){
    		sc[k] = '-';
    		tc[k] = t.charAt(tindex);
    		tindex++;
    		k++;
    		r--;
    	}

    	while(c!=0 && r==0){
    		sc[k] = s.charAt(sindex);
    		tc[k] = '-';
    		sindex++;
    		k++;
    		c--;




    	}



    	String scc = new String(sc);
    	scc = new StringBuilder(scc).reverse().toString();
    	String tcc = new String(tc);   
    	tcc = new StringBuilder(tcc).reverse().toString();

    	System.out.println(scc);
    	System.out.println(tcc);
    	i=0;
    	int score=0;
    	int sub=0;

    }


    public static int [][] q3a(String filepath,int a,int b, int match, int tsn, int tvn){

    //read in fa file

    	String s="s";
    	String t="t";

    	try{
    		BufferedReader br = new BufferedReader(new FileReader(filepath));
    		try {
    			StringBuilder sb = new StringBuilder();
    			String line = br.readLine();
    			line = br.readLine();

    			while (line != null) {

    				if((!line.startsWith(">"))){
    					sb.append(line);

    					line = br.readLine();}
    					else{
    						s = sb.toString();
    						sb = new StringBuilder();
    						line = br.readLine();}
    					}
    					t = sb.toString();
    				} finally {
    					br.close();
    				}
    			}catch(Exception e){
    				e.printStackTrace();}  

    //call nw
    				int [][] x;
    				x = modifyNW(s,t,match,tsn,tvn,a,b);
    				int c = x.length-1;
    				int r = x[1].length-1;
    				System.out.println("Score = "+ x[c][r]);


    //call trace
    				trace(x,s,t,match,tsn,tvn,a,b);    
    				return x;
    			}


    			public static void main(String[] args) {

				//STORE RESULTS IN RES.TXT
    				try{
    					System.setOut(new PrintStream(new FileOutputStream("res.txt")));
    				}catch(Exception e){
    					e.printStackTrace();}


    					int a = -2;
    					int b = -10;
    					int match = 1;
    					int tsn = -1;
    					int tvn = -2;

	//			String filepath = "BRCA1_reduced.fa";
    					String filepath = "BRCA1.fa";

    					int [][] res = q3a(filepath,a,b,match,tsn,tvn);



    				}
    			}




