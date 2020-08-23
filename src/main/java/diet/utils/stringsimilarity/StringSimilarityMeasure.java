/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.stringsimilarity;
import java.util.Vector;



/**
 *
 * @author user
 */


public class StringSimilarityMeasure  {

    
    
    
static public Vector getAllSharedWords(Vector a, Vector b){
        Vector shared = new Vector();
        for(int i=0;i<a.size();i++){
            String s = (String)a.elementAt(i);
            for(int j=0;j<b.size();j++){
                String s2=(String)b.elementAt(j);
                if(s.equalsIgnoreCase(s2)){
                    shared.addElement(s);
                    break;
                }
            }
        }
        return removeDuplicates(shared);
    }
    
    static public Vector getAllWordsDeletedFromAThatAreNotInBorC(Vector a,Vector b,Vector c){
        Vector deleted = new Vector();
        Vector bUniq=removeDuplicates(b);
        Vector cUniq=removeDuplicates(c);
        for(int i=0;i<bUniq.size();i++){
            cUniq.addElement(bUniq.elementAt(i));
        }
        return getAllWordsDeletedFromA(a,cUniq);
    }
    
    static public Vector getAllWordsDeletedFromA(Vector a,Vector b){
        Vector deleted =new Vector();
        Vector aUniq = removeDuplicates(a);
        Vector bUniq = removeDuplicates(b);
        for(int i=0;i<aUniq.size();i++){
            String s = (String)aUniq.elementAt(i);
            boolean found = false;
            for(int j=0;j<bUniq.size();j++){
                String s2 =(String)bUniq.elementAt(j);
                if(s.equalsIgnoreCase(s2)){
                    found =true;
                    break;
                }
            }
            if(!found){
                deleted.addElement(s);
            }
        }
        return removeDuplicates(deleted);
    }
          
    
    
    static int privateconvertdescTypeToInt(String desctype){
        
        if(desctype.equalsIgnoreCase("N"))return 0;
        if(desctype.equalsIgnoreCase("F"))return 1;
        if(desctype.equalsIgnoreCase("P"))return 2;
        if(desctype.equalsIgnoreCase("L"))return 3;
        if(desctype.equalsIgnoreCase("M"))return 4;
        System.err.println("...Desctype is:  ("+desctype+")");
        System.exit(-45213);
        return -5;
        
    }
    
    
    static public boolean isDescTypeAMoreAbstractThanB(String a, String b){
        if(a.equalsIgnoreCase(""))System.exit(-1234562);
        if(b.equalsIgnoreCase(""))System.exit(-1234563);
        int aInt = privateconvertdescTypeToInt(a);
        int bInt = privateconvertdescTypeToInt(b);
        return (aInt>bInt);
    }
    
    static public int howMuchMoreAbstractIsAThanB(String descTypeA, String descTypeB){
        if(descTypeA.equalsIgnoreCase(""))System.exit(-1234569);
         if(descTypeB.equalsIgnoreCase(""))System.exit(-1234568);
        
        int a = privateconvertdescTypeToInt(descTypeA);
        int b = privateconvertdescTypeToInt(descTypeB);
        return (a-b);
    }
    
    
    static public float[] getProportionOfWordsOfAInBandBinA(String a,String b){
        System.exit(-4);
        //First number is divided by total number of unique words in A
        //Second number is divided by total number of unique words in B
        if(a.length()==0||b.length()==0){
            float[] result = {0,0};
            return result;
        }
        Vector aVect = removeDuplicates(splitIntoWords(a));
        Vector bVect = removeDuplicates(splitIntoWords(b));
        float matchingStrings =0;
        for(int i=0;i<aVect.size();i++){
            String asunique = (String)aVect.elementAt(i);
            for(int j=0;j<bVect.size();j++){
                String bsunique = (String)bVect.elementAt(j);
                if(bsunique.equalsIgnoreCase(asunique))matchingStrings++;
            }
        }
        float[] result = new float[2];
        result[0]=1;
        result[0]=matchingStrings/(float)aVect.size();
        result[1]=matchingStrings/(float)bVect.size();
        if(result[0]>1)System.exit(-2);
        if(result[1]>1)System.exit(-2);
        if(Float.isNaN(result[0]))result[0]=0;
        if(Float.isNaN(result[1]))result[1]=0;                
        return result;    
    }
    

    static public int[] getNoOfUniqueWordsInA_NoOfUniqueWordsInB_NoOfMatchingWords(String a,String b){
        //First number is divided by total number of unique words in A
        //Second number is divided by total number of unique words in B
        if(a.length()==0||b.length()==0){
            int[] result = {0,0};
            return result;
        }
        Vector aVect = removeDuplicates(splitIntoWords(a));
        Vector bVect = removeDuplicates(splitIntoWords(b));
        int matchingStrings =0;
        for(int i=0;i<aVect.size();i++){
            String asunique = (String)aVect.elementAt(i);
            for(int j=0;j<bVect.size();j++){
                String bsunique = (String)bVect.elementAt(j);
                if(bsunique.equalsIgnoreCase(asunique))matchingStrings++;
            }
        }
        int[] result = new int[3];
        result[0]=aVect.size();
        result[1]=bVect.size();
        result[2]= matchingStrings;
        
        if(Float.isNaN(result[0]))result[0]=0;
        if(Float.isNaN(result[1]))result[1]=0;                
        return result;    
    }
    
    
    
    
    
    static public Vector splitIntoWords(String s){
        Vector words = new Vector();
       
        
        String sCleaned ="";
        for(int i=0;i<s.length();i++){
            char schar = s.charAt(i);
            if(Character.isLetterOrDigit(schar)){
                sCleaned = sCleaned+schar;
            }
            else{
                sCleaned = sCleaned+":";
            }
        }
//        System.out.println("sCleaned: "+sCleaned);
        String[] sCleanedArray = sCleaned.split("[\\:]");
        for(int i=0;i<sCleanedArray.length;i++){
            sCleanedArray[i].trim();
//            System.out.println(i+" "+sCleanedArray[i]);
        }
        
        
        Vector removeBlankSpace = new Vector();
        for(int i=0;i<sCleanedArray.length;i++){
            String sNew = sCleanedArray[i].trim();
            if(sNew.length()!=0)removeBlankSpace.addElement(sNew);
        }
        
        return removeBlankSpace;
    }
    
    
   
    
   static public Vector removeDuplicates (Vector v){
       Vector unique = new Vector();
       for(int i=0;i<v.size();i++){
          String s = (String)v.elementAt(i);
          boolean uniqueString = true;
          for(int j=0;j<unique.size();j++){
              String uniq = (String)unique.elementAt(j);
              if(uniq.equalsIgnoreCase(s)){
                  uniqueString = false;
                  break;
              }
          }
          if(uniqueString)unique.addElement(s);
          
       }
       //printVector(unique);
       return unique;  
   } 
    

   static public void printVector(Vector v){
       for(int i=0;i<v.size();i++){
           String s = (String)v.elementAt(i);
           System.out.println(i+": "+s);
       }
   
       
   }
   public static int longestSubstr(String str_, String toCompare_) 
{
  if (str_.length()==0 || toCompare_.length()==0)
    return 0;
 
  int[][] compareTable = new int[str_.length()][toCompare_.length()];
  int maxLen = 0;
 
  for (int m = 0; m < str_.length(); m++) 
  {
    for (int n = 0; n < toCompare_.length(); n++) 
    {
      compareTable[m][n] = (str_.charAt(m) != toCompare_.charAt(n)) ? 0
          : (((m == 0) || (n == 0)) ? 1
              : compareTable[m - 1][n - 1] + 1);
      maxLen = (compareTable[m][n] > maxLen) ? compareTable[m][n]
          : maxLen;
    }
  }
  return maxLen;
}

   
   public static String calculatesLongestCommonSubSequence(String x, String y){
       int M = x.length();
        int N = y.length();
        String longestSequence="";

        // opt[i][j] = length of LCS of x[i..M] and y[j..N]
        int[][] opt = new int[M+1][N+1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = M-1; i >= 0; i--) {
            for (int j = N-1; j >= 0; j--) {
                if (x.charAt(i) == y.charAt(j))
                    opt[i][j] = opt[i+1][j+1] + 1;
                else 
                    opt[i][j] = Math.max(opt[i+1][j], opt[i][j+1]);
            }
        }

        // recover LCS itself and print it to standard output
        int i = 0, j = 0;
        while(i < M && j < N) {
            if (x.charAt(i) == y.charAt(j)) {
                longestSequence=longestSequence+(x.charAt(i));
                i++;
                j++;
            }
            else if (opt[i+1][j] >= opt[i][j+1]) i++;
            else                                 j++;
        }
        System.out.println();
        return longestSequence;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see diet.utils.stringsimilarity.SimilarityMeasure#similarity(java.lang.Object, java.lang.Object)
	 */
	
        //Had to remove the override...wouldn't compile
	public double similarity(String a, String b) {
		float[] pair = getProportionOfWordsOfAInBandBinA(a, b);
		return ((double) pair[0] + (double) pair[1]) / 2.0;
	}

}



