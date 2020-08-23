/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils;

import diet.server.Participant;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class VectorHashtableEnforcingUniqueness {
    
    
    public Hashtable ht = new Hashtable();
    
    
    public void removeAllValuesOfKey(Object key){
         ht.put(key, new Vector());
    }
    
    public void removeAllKeyValuePairs(Object value){       
         Set ks  = ht.keySet();
         for(Object k: ks){
             Vector values = (Vector)ht.get(k);
             if(values!=null){
                 if(values.contains(value)){
                     values.remove(value);
                     System.err.println("Removing value");
                 }
             }
         }
    }
    
    
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
                 System.err.println("VECTOR HASHTABLE ALREADY CONTAINS OBJECT...");
                // throw new VectorHashtableException(value.toString());
                // System.exit(-435435);
             }
             else{
                v.addElement(value);    
             }
             
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
    
    
    
}
