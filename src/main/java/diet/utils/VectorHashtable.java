/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils;

import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class VectorHashtable {
    
    
    public Hashtable ht = new Hashtable();
    
    
    public void put(Object key, Object value) {
         Vector v =(Vector) ht.get(key);
         if(v==null){
             v = new Vector();
             v.addElement(value);
             ht.put(key, v);
             System.err.println("Adding new key: "+ key);
         }
         else{
             if(v.contains(value)){
                 System.err.println(value.getClass().toString());
                // throw new VectorHashtableException(value.toString());
                // System.exit(-435435);
             }
             v.addElement(value);
             ht.put(key, v);
         }
    }
     public Vector get(Object key){
         Vector v =(Vector) ht.get(key);
         if(v==null){
             v = new Vector();
             ht.put(key, v);
         }
         return v;
     }
    
     public Set getAllKeys(){
         return ht.keySet();
     }
    
}
