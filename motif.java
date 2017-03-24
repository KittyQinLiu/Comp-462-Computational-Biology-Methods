//motif.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

public class motif{

	public static String[] readFASTA(String filepath){ 
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
        //System.out.println(i);
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

    public static int[] readintarray(String path){
      int [] res; int i = 0;
      try {
       BufferedReader br = new BufferedReader(new FileReader(path));
       String line;
       while ((line = br.readLine()) != null) {
        i++;

      } 
    } catch(Exception e){
        e.printStackTrace();}
    res = new int[i];
    i=0;
    try {
     BufferedReader br = new BufferedReader(new FileReader(path));
     String line;
     while ((line = br.readLine()) != null) {
       Integer parsed = Integer.parseInt(line);
       res[i] = parsed;i++;

     } 
   } catch(Exception e){
        e.printStackTrace();}
        return res;
}

    public static void pr_str_ele(String[] a){
    	for(int i = 0; i<a.length; i++){
    		System.out.println(a[i]);
    	}
    }

    public static String[] enu_pattern(String[] alphabet, int length){
      String [] patterns = new String[7*7*7*7*7];
      int k = 0;
      for(int i = 0; i<7; i++){
        for(int j = 0; j<7; j++){
          for (int x = 0; x<7; x++ ) {
            for (int y = 0; y<7; y++) {
              for (int z = 0; z<7; z++) {
                patterns[k] = "";
                patterns[k] = alphabet[z]+" "+alphabet[y]+" "+alphabet[x]+" "+alphabet[j]+" "+alphabet[i];
                k++;
              }
              
            }
            
          }
        }
      }
      return patterns;
    }

    public static int[] count(String[] pat, String[] seq){

      int[] count = new int[pat.length];

      int i = 0;

      for(String p: pat){
        count[i]=0;
        p = makepat(p);
        Pattern cp = Pattern.compile(p);
        for (String s : seq) {
          Matcher m = cp.matcher(s);
          int k = 0;
          while(m.find(k)){
            count[i]++;
            k = m.start();
            k++;
            //System.out.println(k);
          }
                 
        }
        i++;


      }

      return count;
        
      

    }


    public static String makepat(String p){
        String cp ="";
        String temp = "";
        for (int j = 0;j<p.length() ;j++ ) {
          if(j != p.length()-1){
            if(p.charAt(j+1) != ' '){
              temp = temp + p.charAt(j);
            }
            else{
              temp = temp + p.charAt(j);
              cp = cp+"("+temp+")";
              temp = "";
              j++;
            }

          }
          else{temp = temp + p.charAt(j);
          }  
    }
    cp = cp+"("+temp+")";
    return cp;
  }

  public static double[] p(String [] pat){
    double [] res = new double [pat.length];
    int index = 0;
    for (String p : pat) {
      int[] tempres = new int[5];
      String temp = "";
      int i = 0; 
      for (int j = 0;j<p.length() ;j++ ) {
          if(j != p.length()-1){
            if(p.charAt(j+1) != ' '){
              temp = temp + p.charAt(j);
            }
            else{
              temp = temp + p.charAt(j);
              tempres[i] = (temp.length()+1)/2;
              temp = "";
              j++;i++;
            }

          }
          else{temp = temp + p.charAt(j);
          }  
          tempres[i] = (temp.length()+1)/2;
      }

/*      for (int ii : tempres) {
        System.out.print(ii + " ");
      }
      System.out.println();*/

      double pr = 1.0;
      for (int ii : tempres) {
        pr = pr * ii/4.0;
      }
      res[index] = pr;
      index++;
    }
/*    for (double i :res ) {
      System.out.println(i);
      
    }*/
    return res;
  }

    public static double[] calcPFNeg(String [] pat,String[] seq){
      double [] res = new double[7];
      int totallength = 0;

      for (String s : seq) {
        totallength = totallength+ s.length();
      }

      int ct_A = 0;
      int ct_C = 0;
      int ct_G = 0;
      int ct_T = 0;

      for (String s : seq) {
        char[] cs = s.toCharArray();
        for (char c: cs) {

          if(c == 'A'){
            ct_A++;
          }
          else if(c == 'C'){
            ct_C ++;
          }
          else if(c == 'G'){
            ct_G++;
          }
          else if(c == 'T'){
            ct_T++;
          }
          else{System.out.println("the fuck?");}
          
        }
        
      }

      res[0] = ct_A*1.0/totallength; //A
      res[1] = ct_C*1.0/totallength; //C
      res[2] = ct_G*1.0/totallength; //G
      res[3] = ct_T*1.0/totallength; //T
      res[4] = (ct_A+ct_G)*1.0/totallength; //AG
      res[5] = (ct_C+ct_T)*1.0/totallength; //CT
      res[6] = (ct_A+ct_G+ct_C+ct_T)*1.0/totallength;
      for (double d :res ) {
        System.out.println(d);
        
      }

      return res;

  }

    public static double[] pwneg(String [] pat, double[] pneg){
    double [] res = new double [pat.length];
      int k = 0;
      for(int i = 0; i<7; i++){
        for(int j = 0; j<7; j++){
          for (int x = 0; x<7; x++ ) {
            for (int y = 0; y<7; y++) {
              for (int z = 0; z<7; z++) {
                res[k] = pneg[z]*pneg[y]*pneg[x]*pneg[j]*pneg[i]*1.0;
                k++;
              }
              
            }
            
          }
        }
      }

      //System.out.println(res[0]+" "+res[7*7*7*7*7-1]);
    return res;
  }






  public static double[] z(double[] p, String[] seq, int[] count){
    int length = p.length;
    //length of p should equal to length of count
    int e = 0;
    for (String s : seq) {
      int cur = s.length();
      e = e+(cur-5+1);
    }
    double[] res = new double[length];
    for (int i = 0;i<length ;i++){
      double prob = p[i];
      double ew = e*prob/1.0;
      res[i] = (count[i]-ew)/Math.sqrt(ew);
    }
    return res;
  }

/*  public static double[][] twodsort(double [] z){
    double[][] 2dz= new double[z.length][2];
    for(int i = 0; i<z.length;i++){
      double[i][0] = i;
      double[i][1] = z[i];
    }
    java.util.Arrays.sort(2dz, new java.util.Comparator<double[]>() {
    public int compare(double[] a, double[] b) {return Double.compare(a[0], b[0]);}
    });
    return 2dz;

  }*/

  public static double [][] rankmax(double [] z){
    double [][] az = new double[z.length][2];
    for(int i = 0; i<z.length;i++){
      az [i][0] = z[i];
      az [i][1] = i;
    }
    java.util.Arrays.sort(az, new java.util.Comparator<double[]>() {
    public int compare(double[] a, double[] b) {return Double.compare(a[0], b[0]);}});
    return az;

  }






























	public static void main(String[] args) {
		String [] pos = readFASTA("GATA2_chr1.fa");
		String [] neg = readFASTA("not_GATA2_chr1.fa");

    String [] alphabet = {"A","C","G","T","A|G","C|T","A|C|G|T"};
    String [] patterns = enu_pattern(alphabet,5);

    //System.out.println(test(patterns[0]));
    //System.out.println(test(patterns[7*7*7*7*7-1]));
    //System.out.println(makepat("A A|G A|C|G|T C G"));
    String [] reducedpos = new String [100];
    for(int i = 0; i<100;i++){
      reducedpos[i] = pos[i];
    }

    String [] reducedneg = new String [100];
    for(int i = 0; i<100;i++){
      reducedneg[i] = neg[i];
    }

    String [] reducedpat = new String [100];
    for(int i = 0; i<100;i++){
      reducedpat[i] = patterns[i];
    }

    


/*    int [] ctpos = count(patterns, pos);
    for (int i = 0;i<ctpos.length;i++) {
      System.out.println(ctpos[i]);      
    }*/
    int [] ctpos = readintarray("ctpos.txt");
/*    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    */


/*    int [] ctneg = count(patterns, neg);
    for (int i = 0;i<ctneg.length;i++) {
      System.out.println(ctneg[i]);      
    }*/
    int [] ctneg = readintarray("ctneg.txt");

/*    //*****************important reduced example

    int [] ctneg = readintarray("reducedctneg.txt");
    int [] ctpos = readintarray("reducedctpos.txt");

    double [] reducedpr = p(reducedpat);
    double [] reducedposzw = z(reducedpr,reducedpos,ctpos);
    double [][] reducedposres = rankmax(reducedposzw);

    double [] reducednegzw = z(reducedpr,reducedneg,ctneg);
    double [][] reducednegres = rankmax(reducednegzw);

    for(int i = 0; i<ctpos.length;i++){
      int index = (int)reducedposres[i][1];
      System.out.println(reducedposres[i][0]+"  "+reducedposres[i][1]+"   "+reducednegres[index][0]);
    }
    int index = (int)reducedposres[reducedposres.length-1][1];
    System.out.println("At "+index+", pattern = "+patterns[index]);*/

        //*****************important reduced example


    //double [] pr = p(patterns);
    double[] pneg =calcPFNeg(patterns,neg);

    double [] pr=pwneg(patterns,pneg);


    double [] poszw = z(pr,pos,ctpos);
    double [][] posres = rankmax(poszw);

    double [] negzw = z(pr,neg,ctneg);
    double [][] negres = rankmax(negzw);

    for(int i = 0; i<ctpos.length;i++){
      int index = (int)posres[i][1];
      System.out.println(posres[i][0]+"  "+posres[i][1]+"   "+negres[index][0]);
    }
    int index = (int)posres[posres.length-1][1];
    System.out.println("At "+index+", pattern = "+patterns[index]);




    System.out.println();








    




/*  //matcher testing  
    Pattern p = Pattern.compile(pt);
    Matcher m = p.matcher("AAAAAAAA");
    int count = 0;
    int i = 0;
    while(m.find(i)){
      count ++;i++;
    }
    System.out.println(count);*/

		
	}
}
