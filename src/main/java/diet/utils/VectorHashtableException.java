/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils;

/**
 *
 * @author sre
 */
public class VectorHashtableException extends Exception{
    
       public String message ="";   
    
       public VectorHashtableException(){
           
       }
       public VectorHashtableException(String s){
           this.message=s;           
       }
}
