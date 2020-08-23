/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils;

/**
 *
 * @author sre
 */
public class ArrayToolkit {
    
      public static String convertArrayOfObjectsToString(Object[] arr,String divchar){
          String retvalue ="";
          for(int i=0;i<arr.length;i++){
              Object o = arr[i];
              if(o instanceof String){
                   retvalue = retvalue+ (String)o;
                   
                   
              }
              else if (o instanceof Integer){
                  retvalue = retvalue+ ((Integer)o); 
              }
              else if (o instanceof Long){
                  retvalue = retvalue+ ((Long)o); 
              }
              if(i%2==1)retvalue=retvalue+divchar;
          }
          return retvalue;
          
      }
    
}
