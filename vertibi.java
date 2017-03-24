
import java.io.BufferedReader;
import java.io.FileReader;

class HMM {

	String [] state;
	char [] alphabet;
	double [] initialp;
	double [][] emissionp;
	double [][] transitionp;

	public HMM(){
		this.state = new String [2];
		this.alphabet = new char [2];
		this.initialp = new double [2];
		this.emissionp = new double[2][3];
		this.transitionp = new double[2][3];
	}

/*	public void setState(String [] s){
		this.state = s;
	}

	public void setAlphabet(String [] a){
		this.alphabet = a;
	}

	public void setInitialp(double [] ip){
		this.initialp = ip;
	}

	public void setEmissionp(double [] ep){
		this.emissionp = ep;
	}

	public void setTransitionp(double [] tp){
		this.transitionp = tp;
	}

*/





}


public class vertibi{

	public static void printM(double[][] grid) {
    for(int r=0; r<grid.length; r++) {
     for(int c=0; c<grid[r].length; c++)
       System.out.print(grid[r][c] + " ");
     System.out.println();
   }
 }

 public static double sumdouble(double[] input){
   double sum = 0.0;
   for (int i = 0;i<input.length;i++){
    sum = sum + input[i];
  }
  return sum;
}

public static void printD(double[] input){

 for (int i = 0;i<input.length;i++){
  System.out.print(input[i]+" ");
}
System.out.println();
}


    public static String[] readFASTA(String filepath){ //867, now s = 2nd last
    	String s,t;
    	s = "";
    	int i = 0;
    	//first count how many sequences are there
     try{
      BufferedReader br = new BufferedReader(new FileReader(filepath));
      try {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        line = br.readLine();
        
        while (line != null) {

          if((!line.startsWith(">"))){
            line = br.readLine();}
            else{
             i++;
             line = br.readLine();}
           }
           i++;

         } finally {
          br.close();
        }
      }catch(Exception e){
        e.printStackTrace();}
        System.out.println(i);
        String [] sequences = new String[i];
        String [] names = new String[i];

        i = 0;

        try{
          BufferedReader br = new BufferedReader(new FileReader(filepath));
          try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            names[0] = line;
            line = br.readLine();

            while (line != null) {

              if((!line.startsWith(">"))){
                sb.append(line);

                line = br.readLine();}
                else{
                  

                  sequences[i] = sb.toString();
                  i++;
                  names[i] = line;
                  sb = new StringBuilder();
                  line = br.readLine();}
                }
                sequences[i] = sb.toString();
              } finally {
                br.close();
              }
            }catch(Exception e){
              e.printStackTrace();}

      //System.out.println(names[103]);


      return sequences;


    }


    public static double PE(HMM h,char x,int state){
    	double [][] table = h.emissionp;
    	for(int i = 0; i<h.alphabet.length;i++){
    		if(x == h.alphabet[i]){
    			//System.out.println(i);
          return table[state][i];
        }
      }
      return 0.0;
    }

    public static double PT(HMM h,int dest, int src){
    	double [][] table = h.transitionp;
/*    	for(int i = 0; i< table[state].length;i++){
    		if(x == seq[i]){
    		return table[state][i];
    		}
    	}*/

    	return table[src][dest];
    }

    public static double PI(HMM h,int state){
    	
    	return h.initialp[state];
    }

    public static double maxd(double [] d){
    	double maxd = 0;
    	for(int i = 0; i<d.length; i++){
    		if(d[i]>=maxd){maxd = d[i];}
    	}
    	return maxd;
    }

    public static double maxindex(double [] d){
    	double maxd = 0.0;
    	double maxindex = 0.0;
    	for(int i = 0; i<d.length; i++){
    		if(d[i]>=maxd){maxd = d[i];maxindex = i;}
    	}
    	return maxindex;
    }

    public static double[] maxdc1(double [] d){
    	double[] maxd = new double[d.length];
    	double max = 0.0;
    	for(int i = 0; i<d.length; i++){
    		if(d[i]>=max){max = d[i];}
    	}
    	for(int i = 0; i<d.length;i++){
    		if(max == d[i]){maxd[i] = max;}
    	}
    	return maxd;

    }

    public static double power(double d){
    	double pw = 1.0; 
    	while(d !=0.0){
    		d--;
    		pw = pw*10.0;
    	}
    	return pw;
    }


    public static char[] vertibi(HMM h, char[] seq){
    	
    	double[][] bk = new double[h.state.length][seq.length];
    	int numstate = h.state.length;
    	char[] path = new char[seq.length];

    	double[][] v = new double[h.state.length][seq.length];

    	double[] mantissa = new double[seq.length];

    	for (int i = 0; i<h.state.length; i++){
    		v[i][0] = PI(h,i)*PE(h,seq[0],i);
    	}
//    	printM(v);

    	for (int col = 1; col< seq.length; col++){
    		
 //   	for(int col = 1; col<4;col++){	
    		mantissa[col] = col;
    		int cman = 0;
    		double small = 0.0;
    		for(int i = 0;i<h.state.length;i++){
    			if(v[i][col-1]>= small){small = v[i][col-1];}
    		}
    		while(small<0.0001){
    			small = small * 10;
    			cman++;
    		}
    		mantissa[col]=cman;
    		for (int row = 0; row < h.state.length;row++){


    			double [] vcol = new double[h.state.length];
    			for (int i = 0; i<h.state.length;i++){
    				vcol[i] = v[i][col-1] * PT(h,row,i) * power(mantissa[col]);
    				//System.out.println("i = "+i+", is "+vcol[i]);
    			}
    			double max = maxd(vcol);
    			double maxindex = maxindex(vcol);
    			//if(row == 0){System.out.println("maxindex"+maxindex);}
         bk[row][col] = maxindex;

    			//System.out.println("v is "+ PE(h,seq[col],row)+ "at char,i " +seq[col]+","+row);
         v[row][col] = max * PE(h,seq[col],row);
    			//System.out.println(v[row][col]);
       }
     }

/*    	for(int i = 0; i<bk.length;i++){
    		System.out.print(bk[i][0]+" ");
    	}
    	System.out.println();*/
/*     	printM(bk);
printM(v);*/

    	//final column
double [] vfinalcol = new double[h.state.length];
for(int i = 0; i<h.state.length;i++){
  vfinalcol[i] = v[i][seq.length-1];
}

double maxoverall = maxd(vfinalcol);

double maxidoverall = maxindex(vfinalcol);
    	//System.out.println(maxidoverall);


if (maxidoverall == 0.0){
 path[seq.length-1] = 'B';
}
else if (maxidoverall == 1.0){
 path[seq.length-1] = 'L';
}
else if (maxidoverall == 2.0){
 path[seq.length-1] = 'M';

}

maxidoverall = (bk[(int)maxidoverall][seq.length-1]);



for(int i = seq.length-2;i>=0;i--){
    	//B: hydrophobic,L: hydrophilic, M: mixed
  if (maxidoverall == 0.0){
   path[i] = 'B';
 }
 else if (maxidoverall == 1.0){
   path[i] = 'L';
 }
 else if (maxidoverall == 2.0){
   path[i] = 'M';

 }
 else{System.out.println("error in backprinting");}
 if(i !=0) {maxidoverall = bk[(int)maxidoverall][i];}

}

for(int i = 0; i<path.length;i++){
 System.out.print(path[i]);
}
System.out.println();


return path;

}


public static void main(String[] args) {
  String[] s ={"HPhobic","HPhilic","Mixed"};
  char[] a ={'A','V','I','L','M','F','Y','W','R','H','K','D','E','S','T','N','Q','G','P','C'}; 
  double[] ip = {1.0/3,1.0/3,1.0/3};
		double[][] ep = new double [s.length][a.length]; // 3x20
		//printM(ep);

		//hphobic
		for (int i = 0; i<8;i++){
			ep[0][i] = 0.6/8;
		}
		for (int i = 8; i<a.length;i++){
			ep[0][i] = 0.4/12;
		}

		//hphilic
		for (int i = 0; i<8;i++){
			ep[1][i] = 0.2/8;
		}
		for (int i = 8; i<a.length;i++){
			ep[1][i] = 0.8/12;
		}

		//mixed
		for (int i = 0; i<a.length;i++){
			ep[2][i] = 1.0/20;
		}
		
		System.out.println("ep=");

		printM(ep);
/*		double [] d2 = ep[0];
		double d =sumdouble(d2);
		System.out.println(d);*/


		double [][] tp = new double [s.length][s.length];
		// hphobic
		tp[0][0] = 4.0/5; //hphobic
		tp[0][1] = 0.2/5; //hphilic
		tp[0][2] = 0.8/5; //mixed

		// hphilic
		tp[1][0] = 0.3/8;
		tp[1][1] = 7.0/8;
		tp[1][2] = 0.7/8;

		//mixed 
		tp[2][0] = 0.5/7;
		tp[2][1] = 0.5/7;
		tp[2][2] = 6.0/7;

		System.out.println("tp=");

		printM(tp);







		HMM protein = new HMM();



		protein.state = s;
		protein.alphabet = a;
		protein.initialp = ip;
		protein.emissionp = ep;
		protein.transitionp= tp;

		String path = "hw3_proteins.fa";

		String[] sequence = readFASTA(path);
		String[] resultstrings = new String[sequence.length];

		for(int i = 0; i<sequence.length;i++){
//		for(int i = 0; i<10; i++){
			char [] seq = new char[sequence[i].length()];
			for (int j = 0; j<seq.length;j++){
       seq[j] = sequence[i].charAt(j);
     }
     char [] result = vertibi(protein,seq);
     resultstrings[i] = new String(result);
   }
/*
		String test = "MVLLRVLILLLSWAAGMGAPARWRIPKSSPPPRPAAQTRGKLCCLQLGSCIVLYPARRS";

    String test2 = "MRNGISPIIIDNTNLHAWEMKPYAVMALENNYEVIFREPDTRWKFNVQELARRNIHGVSREKIHRMKERYEHDVTFHSVLHAEKPSRMNRNQDRNNALSNNARYWNSYTEFPNRRAHGGFTNESSYHRRGGCHHGY";

System.out.println("test");
System.out.println("test");
System.out.println("test");
System.out.println("test");


		char [] temp = new char[test2.length()];

      for (int i = 0; i<test2.length();i++){
      	temp[i] = test2.charAt(i);

      }
      char[] temp1 = vertibi(protein,temp);
      for (char c : temp1) {
      	System.out.print(c+" ");
      	System.out.println();
      	
      }*/

		//MVLLRVLILLLSWAAGMGAPARWRIPKSSPPPRPAAQTRGKLCCLQLGSCIVLYPARRS

		//char [] result = vertibi(protein,seq);
		//System.out.println(seq.length);

      // question a: longest hydrophobic region ie most B
      System.out.println();
      System.out.println();
      System.out.println("Question a:");
      System.out.println();
      System.out.println();
      double [] seqCountB = new double[resultstrings.length];
      double [] kb = new double[resultstrings.length];
      for(int i = 0; i<resultstrings.length;i++){
//      for(int i = 0; i<10;i++){

      	double [] countB = new double[100];
      	int k = 0;
      	String current = resultstrings[i];
      	for(int j = 0; j<current.length();j++){
      		if(j != current.length()-1 && current.charAt(j) =='B' && current.charAt(j+1) =='B'){
      			countB[k]++;
      		}
      		else if(j != current.length()-1 && current.charAt(j) =='B' && current.charAt(j+1) !='B'){

      			countB[k]++;
      			k++;
      		}
      		else if(j == current.length()-1 && current.charAt(j) == 'B'){
      			countB[k]++;
      		}
      	}
      	seqCountB[i] = maxd(countB);
      	if(countB[0]!=0){k++;}
      	kb[i] = k;

      }


/*            	double [] countB = new double[100];
      	int k = 0;
      	String current1 = resultstrings[104];
      	for(int j = 0; j<current1.length();j++){
      		if(j != current1.length()-1 && current1.charAt(j) =='B' && current1.charAt(j+1) =='B'){
      			countB[k]++;
      		}
      		else if(j != current1.length()-1 && current1.charAt(j) =='B' && current1.charAt(j+1) !='B'){
      			
      			countB[k]++;
      			k++;
      		}
      		else if(j == current1.length()-1 && current1.charAt(j) == 'B'){
      			countB[k]++;
      		}
      	}*/
//      	printD(countB);
 //     	seqCountB[i] = maxd(countB);



        printD(seqCountB);
        System.out.println(maxd(seqCountB));
        double [] maxd1 = maxdc1(seqCountB);
        for(int i = 0; i<maxd1.length;i++){
          if(maxd1[i] !=0.0){System.out.println(i+" ");}
        }
        System.out.println();
        System.out.println(maxindex(seqCountB));
        System.out.println("for index 103, the 104th seq: >sp|Q86UK0|ABCAC_HUMAN ATP-binding cassette sub-family A member 12 OS=Homo sapiens GN=ABCA12 PE=1 SV=3");



System.out.println();
System.out.println();
System.out.println();
System.out.println("question b");
System.out.println();
System.out.println();
System.out.println();


      // question b: largest fraction of mixed amino acid
        double [] fracCountM = new double[resultstrings.length];
        for(int i = 0; i<resultstrings.length;i++){
//      for(int i = 0; i<10;i++){

         double countM = 0.0;
         String current = resultstrings[i];
         int length = current.length();
         for(int j = 0; j<current.length();j++){
          if(current.charAt(j) =='M'){
           countM++;
         }
       }
       fracCountM[i] = countM/length;

     }
      //printD(fracCountM);
     System.out.println(maxd(fracCountM));
      //System.out.println(maxindex(fracCountM));
     double [] maxd2 = maxdc1(fracCountM);
     for(int i = 0; i<maxd2.length;i++){
      if(maxd2[i] !=0.0){System.out.print(i+" ");}
    }
    System.out.println();


      //print out the longest sequence


/*      for(int j=0; j<resultstrings.length; j++){
      	String current = resultstrings[j];
      	if(current.length()>=280){
      		int count = 0;
      		for (int k = 0; k<current.length();k++){
      			if(current.charAt(k)=='B'){count++;}
      		}
      		if(count>=280){
      			System.out.println(current);
      		}
      	}
      }*/



/*      System.out.println(sequence[0]);

      char [] seq = new char[sequence[0].length()];
      for (int j = 0; j<seq.length;j++){
       seq[j] = sequence[0].charAt(j);
     }
      //vertibi(protein,seq);
     System.out.println("the next");
     System.out.println(resultstrings[0]);
*/


System.out.println();
System.out.println();
System.out.println();
System.out.println("question c");
System.out.println();
System.out.println();
System.out.println();
      //c3

// Hydrophobic



int [][] ct_B = new int [resultstrings.length][100];
int i = 0;
for (String s1 : resultstrings) { 
	int[] ctS = new int[20];
	int j = 0; int k = 0;
	for (int x = 0; x<s1.length();x++) {
		char c = s1.charAt(x);
		if(x!= s1.length()-1){
			if(c == 'B' ){
				k++;
				if(s1.charAt(x+1) != 'B'){
					ctS[j]=k;
					k=0;
					j++;
				}
			}
		}
		else{
			if(c == 'B'){
				k++;ctS[j]=k;
			}
			else{ctS[j]=k;}
		}
	}
	ct_B[i] = ctS;
	i++;
}
/*for (int[] d :ct_B ) {
	for (int b :d ) {
		System.out.print(b+" ");

		
	}
	System.out.println();
	
}
System.out.println();*/

int[] freq_B = new int[200];

for(int[] d: ct_B){
	for(int b:d){
		if(b!=0){
			freq_B[b]++;
		}

	}
}

/*for (int b : freq_B) {
	System.out.println(b);
}*/

System.out.println("next");
System.out.println();
System.out.println();

// hydrophilc
int [][] ct_L = new int [resultstrings.length][20];
i = 0;
for (String s1 : resultstrings) { 
	int[] ctS = new int[20];
	int j = 0; int k = 0;
	for (int x = 0; x<s1.length();x++) {
		char c = s1.charAt(x);
		if(x!= s1.length()-1){
			if(c == 'L' ){
				k++;
				if(s1.charAt(x+1) != 'L'){
					ctS[j]=k;
					k=0;
					j++;
				}
			}
		}
		else{
			if(c == 'L'){
				k++;ctS[j]=k;
			}
			else{ctS[j]=k;}
		}
	}
	ct_L[i] = ctS;
	i++;
}

int[] freq_L = new int[950];

for(int[] d: ct_L){
	for(int b:d){
		if(b!=0){
			freq_L[b]++;
		}

	}
}

/*for (int b : freq_L) {
	System.out.println(b);
}*/



System.out.println("next");
System.out.println();
System.out.println();

// mixed
int [][] ct_M = new int [resultstrings.length][20];
i = 0;
for (String s1 : resultstrings) { 
	int[] ctS = new int[20];
	int j = 0; int k = 0;
	for (int x = 0; x<s1.length();x++) {
		char c = s1.charAt(x);
		if(x!= s1.length()-1){
			if(c == 'M' ){
				k++;
				if(s1.charAt(x+1) != 'M'){
					ctS[j]=k;
					k=0;
					j++;
				}
			}
		}
		else{
			if(c == 'M'){
				k++;ctS[j]=k;
			}
			else{ctS[j]=k;}
		}
	}
	ct_M[i] = ctS;
	i++;
}

int[] freq_M = new int[3200];

for(int[] d: ct_M){
	for(int b:d){
		if(b!=0){
			freq_M[b]++;
		}

	}
}

/*for (int b : freq_M) {
	System.out.println(b);
}*/

System.out.println();
System.out.println();
System.out.println();
System.out.println("question d");
System.out.println();
System.out.println();
System.out.println();

int[] f_B = new int[20];
int[] f_L = new int[20];
int[] f_M = new int[20];

i = 0;

for (String ss : resultstrings) {
	char[]cs = ss.toCharArray();
	int j = 0;
	for (char c : cs) {
		char t = sequence[i].charAt(j);
		if(c == 'B'){
			if(t == 'A'){
				f_B[0]++;
			}
			else if(t == 'V'){
				f_B[1]++;
			}
			else if(t == 'I'){
				f_B[2]++;
			}
			else if(t == 'L'){
				f_B[3]++;
			}
			else if(t == 'M'){
				f_B[4]++;
			}
			else if(t == 'F'){
				f_B[5]++;
			}
			else if(t == 'Y'){
				f_B[6]++;
			}
			else if(t == 'W'){
				f_B[7]++;
			}
			else if(t == 'R'){
				f_B[8]++;
			}
			else if(t == 'H'){
				f_B[9]++;
			}
			else if(t == 'K'){
				f_B[10]++;
			}
			else if(t == 'D'){
				f_B[11]++;
			}
			else if(t == 'E'){
				f_B[12]++;
			}
			else if(t == 'S'){
				f_B[13]++;
			}
			else if(t == 'T'){
				f_B[14]++;
			}
			else if(t == 'N'){
				f_B[15]++;
			}
			else if(t == 'Q'){
				f_B[16]++;
			}
			else if(t == 'C'){
				f_B[17]++;
			}
			else if(t == 'G'){
				f_B[18]++;
			}
			else if(t == 'P'){
				f_B[19]++;
			}
			else{System.out.println("the fck?");}
		}
		else if(c == 'L'){
			if(t == 'A'){
				f_L[0]++;
			}
			else if(t == 'V'){
				f_L[1]++;
			}
			else if(t == 'I'){
				f_L[2]++;
			}
			else if(t == 'L'){
				f_L[3]++;
			}
			else if(t == 'M'){
				f_L[4]++;
			}
			else if(t == 'F'){
				f_L[5]++;
			}
			else if(t == 'Y'){
				f_L[6]++;
			}
			else if(t == 'W'){
				f_L[7]++;
			}
			else if(t == 'R'){
				f_L[8]++;
			}
			else if(t == 'H'){
				f_L[9]++;
			}
			else if(t == 'K'){
				f_L[10]++;
			}
			else if(t == 'D'){
				f_L[11]++;
			}
			else if(t == 'E'){
				f_L[12]++;
			}
			else if(t == 'S'){
				f_L[13]++;
			}
			else if(t == 'T'){
				f_L[14]++;
			}
			else if(t == 'N'){
				f_L[15]++;
			}
			else if(t == 'Q'){
				f_L[16]++;
			}
			else if(t == 'C'){
				f_L[17]++;
			}
			else if(t == 'G'){
				f_L[18]++;
			}
			else if(t == 'P'){
				f_L[19]++;
			}
			else{System.out.println("the fck?");}
		}
		else if(c == 'M'){
			if(t == 'A'){
				f_M[0]++;
			}
			else if(t == 'V'){
				f_M[1]++;
			}
			else if(t == 'I'){
				f_M[2]++;
			}
			else if(t == 'L'){
				f_M[3]++;
			}
			else if(t == 'M'){
				f_M[4]++;
			}
			else if(t == 'F'){
				f_M[5]++;
			}
			else if(t == 'Y'){
				f_M[6]++;
			}
			else if(t == 'W'){
				f_M[7]++;
			}
			else if(t == 'R'){
				f_M[8]++;
			}
			else if(t == 'H'){
				f_M[9]++;
			}
			else if(t == 'K'){
				f_M[10]++;
			}
			else if(t == 'D'){
				f_M[11]++;
			}
			else if(t == 'E'){
				f_M[12]++;
			}
			else if(t == 'S'){
				f_M[13]++;
			}
			else if(t == 'T'){
				f_M[14]++;
			}
			else if(t == 'N'){
				f_M[15]++;
			}
			else if(t == 'Q'){
				f_M[16]++;
			}
			else if(t == 'C'){
				f_M[17]++;
			}
			else if(t == 'G'){
				f_M[18]++;
			}
			else if(t == 'P'){
				f_M[19]++;
			}
			else{System.out.println("the fck?");}
		}
		else{System.out.println("the fck?");}
	j++;
	}
	



	i++;
}

for (int x : f_B) {
	System.out.println(x);
	
}
System.out.println("LLLLLLLLL");
for (int x : f_L) {
	System.out.println(x);
	
}

System.out.println("MMMMMMMMM");
for (int x : f_M) {
	System.out.println(x);
	
}








}
}