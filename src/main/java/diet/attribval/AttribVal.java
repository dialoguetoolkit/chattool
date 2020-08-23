/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.attribval;

import java.io.Serializable;

/**
 *
 * @author gj
 */
public class AttribVal implements Serializable {
    
    public String id;
    public Object value;
    
    public AttribVal (String id, Object value){
        this.id=id;
        this.value=value;
        if(id==null){
            this.id = "NULLVALUE";
        }
        if(value==null){
            this.value = "NULLVALUE";
        }
        
    }
    
    public String getID(){
        return id;
    }
    
    public Object getVal(){
        return value;
    }
    
    public String getValAsString(){
        if(value instanceof String) return   (String)value;
        if(value instanceof Long) return  ""+ (Long)value;
        if(value instanceof Integer) return  ""+ (Integer)value;
        if(value instanceof Double) return  ""+ (Double)value;
        if(value instanceof Float) return  ""+ (Float)value;
        return value.getClass().getName()+":"+value.toString();
    }
    
    
    public String toString(){
        String retval = "["+id;
        if (value instanceof String) retval = retval + ", "+value+"]";
        else if (value instanceof Long) retval = retval + ", "+value+"]";
        else if (value instanceof Integer) retval = retval + ", "+value+"]";
        else retval = retval +value.getClass().getName()+  "]";
        return retval;
        
    }
    
}
