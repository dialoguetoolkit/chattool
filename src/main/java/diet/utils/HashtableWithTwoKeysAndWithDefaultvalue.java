/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils;

import java.util.Hashtable;

/**
 *
 * @author gj
 */
public class HashtableWithTwoKeysAndWithDefaultvalue {
    
    // Hashtable ht = new Hashtable();
    Object defaultValue;
    public Hashtable htht = new Hashtable();
    
    
    
    
    public HashtableWithTwoKeysAndWithDefaultvalue(Object defaultValue){
        this.defaultValue=defaultValue;
    }
    
    public Object getObject(Object key1, Object key2 ){
         Hashtable ht = (Hashtable) htht.get(key1);
         if(ht==null){
             ht = new Hashtable();
             htht.put(key1,ht);
         }
        
        Object retValue = ht.get(key2);
        if(retValue==null){
            ht.put(key2, defaultValue);
            return defaultValue;
        }
        return retValue;
    }
    public void putObject(Object key1, Object key2, Object value){
        Hashtable ht = (Hashtable) htht.get(key1);
        if(ht==null){
            ht = new Hashtable();
            htht.put(key1,ht);
        }
        ht.put(key2, value);
    }
    
}
