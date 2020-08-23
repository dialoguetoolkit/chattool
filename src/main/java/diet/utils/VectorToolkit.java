/*
 * VectorToolkit.java
 *
 * Created on 28 October 2007, 13:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.utils;


import diet.message.MessageChatTextFromClient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
/**
 *
 * @author user
 */
public class VectorToolkit {
    
    /** Creates a new instance of VectorToolkit */
    public VectorToolkit() {
    }
    
    
    public static String displayVectorOfStrings(Vector vOfStrings, boolean showIndices){
        
        String retValue="";
        for( int i=0;i<vOfStrings.size();i++){
            String s = (String)vOfStrings.elementAt(i);
            if(!showIndices){
                retValue = retValue+s+"\n";
            }
            else{
                retValue = retValue+  i+") " +s+"\n";       
            }
        }
        return retValue;
    }
    
    public static Vector splitTabbedString(String s){
        Vector returnVector = new Vector();
        String s2 = "    ";
        
        return returnVector;
        
    }
    
    public static Vector appendVector2ToVector1(Vector v1, Vector v2){
        for(int i=0;i<v2.size();i++){
            v1.addElement(v2.elementAt(i));
        }
        return v1;
    }
    public static boolean vectorOfStringsContains(Vector v, String s){
        for(int i=0;i<v.size();i++){
            String thing = (String)v.elementAt(i);
            if(thing.equalsIgnoreCase(s))return true;
        }
        return false;
    }
    public static String createStringRepresentation(Vector v, String divisor){
        String s = "";
        for(int i=0;i<v.size();i++){
            String s2 = (String)v.elementAt(i);
            s=s+s2+divisor;
        }
        return s;
        
    }
   
    public static Vector sublist(Vector v,int lowest,int highest){
        Vector v2 = new Vector();
        for(int i=lowest;i<highest;i++){
            v2.addElement(v.elementAt(i));
        }
        return v2;
    }
    public static Vector getCopy(Vector input){
        Vector v = new Vector();
        for(int i=0;i<input.size();i++){
            v.addElement(input.elementAt(i));
        }
        return v;
    }
    public static void list(Vector v){
        try{
        for(int i=0;i<v.size();i++){
            String s =(String)v.elementAt(i);
            System.err.println(s);
        }
        }catch (Exception e){
            System.err.println("EXCEPTION PRINTING ");
            e.printStackTrace();
        }
    }

    static Random r = new Random();

    public static Vector randomSubset(Vector v,int size){
        Vector copyORIGINAL = getCopy(v);
        Vector subset = new Vector();
        while (subset.size()<size){
            int idx = r.nextInt(copyORIGINAL.size());
            Object o = copyORIGINAL.elementAt(idx);
            copyORIGINAL.remove(o);
            subset.add(o);
            if(copyORIGINAL.size()<=0)return subset;
        }
        return subset;
    }


    
    
    
    public static Vector combineANDSORTChatText(Vector v1, Vector v2){
          Vector v3 = (Vector)v1.clone();
          for(int i=0;i<v2.size();i++){
              v3.addElement(v2.elementAt(i));
          }
          return sortVectorOfChatText(v3);
    }
            
    
    
    
    
    public static Vector sortVectorOfChatText(Vector vChatText){
           Vector v2 = (Vector)vChatText.clone();
           Vector vSORTED = new Vector();
           while(v2.size()>0){
               
               MessageChatTextFromClient mct = (MessageChatTextFromClient)v2.firstElement();
               boolean foundChatTextThatIsLess = false;
               for(int j=0;j<vSORTED.size();j++){
                    MessageChatTextFromClient mct2 = (MessageChatTextFromClient)vSORTED.elementAt(j);
                    if(mct2.getTimeOfReceipt().getTime()>mct.getTimeOfReceipt().getTime()){
                         foundChatTextThatIsLess = true;
                         vSORTED.insertElementAt(mct, j);
                         v2.remove(mct);
                         break;
                    }
               }
               if(!foundChatTextThatIsLess){
                  vSORTED.insertElementAt(mct, vSORTED.size());
                  v2.remove(mct);
               }
               
           }
           return vSORTED;
        
    }
    
    
    static public Vector getCommonObjectsInBothVectors(Vector a, Vector b){
        Vector common = new Vector();
        Vector shortest;
        Vector longest;
        if(a.size()<b.size()){
            shortest=a;
            longest=b;
        }
        else{
            shortest=b;
            longest=a;
        }
        for(int i=0;i<shortest.size();i++){
            Object o = shortest.elementAt(i);
            if(longest.contains(o)){
                common.add(o);
            }
        }
        return common;  
    }
    
    
   
    
    
    public static Vector convertArrayToVector(Object[] o){
                Vector v = new Vector(Arrays.asList(o));
                return v;
    }
    
    public static String[] convertVectorToArrayOfStrings(Vector v){
          String[] output = new String[v.size()];
          for(int i=0;i<v.size();i++){
                 output[i] = (String)v.elementAt(i);
          }
          return output;
    }    
    
    
    static public Vector getCommonStrings(Vector v1, Vector v2){
         Vector vCommon = new Vector();
         for(int i=0;i<v1.size();i++ ){
             String s = (String)v1.elementAt(i);
             for(int j=0;j<v2.size();j++){
                 String s2 = (String)v2.elementAt(j);
                 if(s.equalsIgnoreCase(s2)){
                      vCommon.add(s);
                      break;
                 }
             }
         }
         return vCommon;
    }
    static public Vector getCommonStrings(Vector v1, Vector v2,Vector v3){
        return getCommonStrings(getCommonStrings(v1,v2),v3);
    }
    
    static public int getINDEXOFOBJECT(String sAsObject, Vector v){
       
        for(int i =0;i<v.size();i++){
            Object o = v.elementAt(i);
            if(o == sAsObject)return i;
        }
        return-1;
    }
    
    
    
    public static Vector getVectorTextFromJarTextFile(String s, Object callingObject){
        Vector v = new Vector();
         
         try{
                InputStream inp = callingObject.getClass().getResourceAsStream(s);    
                BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
                StringBuilder out = new StringBuilder();
                String line;
                 while ((line = reader.readLine()) != null) {
                   v.addElement(line);
                     
                }
                return v;
               
               
         }catch (Exception e){
             e.printStackTrace();
         }
         
        return v;
    }
    
    
    
    
        
        
   
    
}
