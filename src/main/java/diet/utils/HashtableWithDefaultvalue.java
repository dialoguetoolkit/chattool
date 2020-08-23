/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class HashtableWithDefaultvalue {
    
    public Hashtable ht = new Hashtable();
    Object defaultValue;
    
    public HashtableWithDefaultvalue(Vector defaultValue){
        this.defaultValue=defaultValue.clone();
    }
     public HashtableWithDefaultvalue(String defaultValue){
        this.defaultValue=defaultValue+"";
    }
    public HashtableWithDefaultvalue(int defaultValue){
        this.defaultValue=defaultValue;
    }
     public HashtableWithDefaultvalue(long defaultValue){
        this.defaultValue=defaultValue;
    }
      public HashtableWithDefaultvalue(double defaultValue){
        this.defaultValue=defaultValue;
    }
        public HashtableWithDefaultvalue(Color c){
        this.defaultValue= new Color(c.getRed(), c.getGreen(), c.getBlue());
    }
    
    public Object getObject(Object key){
        Object retValue = ht.get(key);
        if(retValue==null){
            if(defaultValue instanceof Vector){
                Object o = ((Vector)defaultValue).clone();
                ht.put(key,o);
                return o;
            }
            else if(defaultValue instanceof String){
                Object o = ((String)defaultValue)+"";
                ht.put(key,o);
                return o;
            }
            else if(defaultValue instanceof Color){
                int r = ((Color)defaultValue).getRed();
                int g = ((Color)defaultValue).getGreen();
                int b = ((Color)defaultValue).getBlue();
                Object o = new Color(r,g,b);
                ht.put(key,o);
                return o;
            }
            else{
                Object o = defaultValue;
                ht.put(key,o);
                return o;
            }
        }
        return retValue;
    }
    public void putObject(Object key, Object value){
        ht.put(key, value);
    }
    
}
