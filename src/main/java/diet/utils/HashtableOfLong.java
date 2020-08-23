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
public class HashtableOfLong {
    
    Hashtable ht = new Hashtable();
    long initialValue =0;
    
    public HashtableOfLong(long initialVal){
        this.initialValue=initialVal;
    }
    
    public void put(Object key, long value){
        ht.put(key,value);
    }
    
    public long get(Object key){
        Object o = ht.get(key);
        if(o == null){
            ht.put(key, new Long(0));
            return 0;
        }
        return (long)o;
    }
    
}
