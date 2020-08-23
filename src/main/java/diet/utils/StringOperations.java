/*
 * StringOperations.java
 *
 * Created on 12 December 2007, 17:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.utils;

import diet.server.ConversationController.ClassLoader.ClassLoad;
import java.util.Date;


/**
 *
 * @author user
 */
public class StringOperations {
    
    /** Creates a new instance of StringOperations */
    public StringOperations() {
    }
    
    public static String returnOtherString(String searchString,String s1,String s2){
        if(s1.equalsIgnoreCase(searchString)) {
            return s2;
        }
        else if(s2.equalsIgnoreCase(searchString)){
            return s1;
        }
        System.err.println("NONE OF THE 2 STRINGS MATCH "+searchString+"//"+s1+"//"+s2);
        System.exit(-123456);
        return null;
        
    }
    
    static String posts = "[\\[\\]\\ .}{|@#$%^&*()/,!?:;]+\\s*";

    public static String[] splitOnlyText(String s){
         return s.split(posts);
    }

    public static boolean isASubWordOfB(String a, String b){
       String bString[] = b.split(posts);
       for(int i=0;i<bString.length;i++){
           String s = bString[i];
           if(s.equalsIgnoreCase(a)) return true;
       }
       return false;
    }




    public static void mainOLD(String[] args){
        //System.err.println("a...b,c/d//ef[[gh]]]ijklmn");

        String[] a = splitOnlyText("a ...b,c/d//e f[[gh]]]ij&&&&   k lm}n");
        for(int i=0;i<a.length;i++){
            System.err.println(a[i]);
        }

        boolean tt = isASubWordOfB("CAR","WE LOVE /car/THE CART");
        if(tt)System.err.println("IT IS)");

    }

 public static void main(String[] args){
        //System.err.println("a...b,c/d//ef[[gh]]]ijklmn");

        //String a = "THIS IS A SINGLE STRING \nAND IT HAS ALL THE TEXT IN IT";
        //System.out.println(a.replaceAll("\n", "(NEWLINE)"));
        
     
        System.out.println(System.nanoTime());
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());
        
        ClassLoad cl = new ClassLoad();        
        
        
    }

    
   public static String appChar_ToRight(String s, String appChar, int minLength){

        int spacestoadd = minLength - s.length();
       // System.err.println("STRINGOP:"+s+"-----"+s.length()+"---------"+minLength+"---------"+spacestoadd);//System.exit(-555);
        if(spacestoadd<1)return ""+s;
        for(int i=0;i<spacestoadd;i++){
            s=s+appChar;
        }
        return s;
    }
 
 
 
 
 
    public static String appWS_ToRight(String s, int minLength){

        int spacestoadd = minLength - s.length();
       // System.err.println("STRINGOP:"+s+"-----"+s.length()+"---------"+minLength+"---------"+spacestoadd);//System.exit(-555);
        if(spacestoadd<1)return ""+s;
        for(int i=0;i<spacestoadd;i++){
            s=s+" ";
        }
        return s;
    }
    
    public static boolean doesStringContainWhitespace(String s){
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) return true;
        }
        return false;
    }
    
    
        
        
    
    
    
    
    public static int getIndexOfPrecedingWhiteSpace(String s,int startIndex){
        for(int i=startIndex;i>=0;i--){
             Character c = s.charAt(i);
             if(Character.isWhitespace(c))return i;
        }
        return -1;
    }
    
    
    
    /**
637     * <p>Strips any of a set of characters from the end of a String.</p>
    *
     * <p>A {@code null} input String returns {@code null}.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is {@code null}, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the set of characters to remove, null treated as whitespace
     * @return the stripped String, {@code null} if null String input
     */
    public static String stripEnd(final String str, final String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
       } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return str.substring(0, end);
    }
  
    
    
}
