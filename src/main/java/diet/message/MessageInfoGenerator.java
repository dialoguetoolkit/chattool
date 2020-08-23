/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 *
 * @author user
 */
public class MessageInfoGenerator {

    
    static public Vector[] getAllFields(Message m){
        Vector[] v = new Vector[2];
        Vector vFieldValues = new Vector();
        Vector vFieldNames = new Vector();
        
        Class c = m.getClass();
        Field[] fields = c.getDeclaredFields();
        for(int i=0;i<fields.length;i++){
            Field f = fields[i];
            try{
              Object o = f.get(m);
              String s =f.getName();
              vFieldNames.addElement(s);
              vFieldValues.addElement(o);
            }catch (Exception e){
                System.err.println("ERROR ATTEMPTING TO REFLECT MESSAGE FIELD");
                e.printStackTrace();
            }
        }     
        v[0]=vFieldNames;
        v[1]=vFieldValues;
        return v;
    }
    
    static public Vector getAllNamesAndFieldsForUI(Message m){
        Vector v = new Vector();
        Vector[] both = getAllFields(m);
        Vector vFieldNames = both[0];
        Vector vFieldValues = both[1];
        for(int i=0;i<vFieldNames.size();i++){
            String sName = (String)vFieldNames.elementAt(i);
            String sName2 = sName.toUpperCase()+": ";
            sName2 = sName2+ (String)vFieldValues.elementAt(i);
            v.addElement(sName2);
        }
        return v;
      
    }
    
    static public Vector[] getAllMethods(Message m){
        Vector[] v = new Vector[2];
        Vector vFieldValues = new Vector();
        Vector vFieldNames = new Vector();
        
        Class c = m.getClass();
        Method[] methods = c.getDeclaredMethods();
        for(int i=0;i<methods.length;i++){
            Method f = methods[i];
            
            try{
              if(f.getGenericParameterTypes().length==0&&!f.getName().endsWith("getAllParameters")&&(f.getName().startsWith("get")||f.getName().startsWith("is"))){  
              //System.out.println("TRYING WITH "+m.getClass().toString()+": "+f.getName());
              Object o = f.invoke(m, (Object)null) ;
              //Object o = ":";
              
              String s =f.getName();
              vFieldNames.addElement(s);
              vFieldValues.addElement(o);
              }
            }catch (Exception e){
                System.err.println("ERROR ATTEMPTING TO REFLECT MESSAGE WITH : "+m.getClass().toString()+": "+f.getName());
                
                if(e instanceof InvocationTargetException){
                    //System.err.println("(1)"+((InvocationTargetException)e).getTargetException().getCause().toString());
                    System.err.println("(2)"+((InvocationTargetException)e).getTargetException().getMessage());
                    System.err.println("(3):");
                    ((InvocationTargetException)e).getTargetException().printStackTrace();
                    
                }
                e.printStackTrace();
            }
            
        }     
        v[0]=vFieldNames;
        v[1]=vFieldValues;
        return v;
    }
    
    static public Vector getAllNamesAndMethodsForUI(Message m){
        Vector v = new Vector();
        Vector[] both = getAllMethods(m);
        Vector vFieldNames = both[0];
        Vector vFieldValues = both[1];
        for(int i=0;i<vFieldNames.size();i++){
            String sName = (String)vFieldNames.elementAt(i);
            String sName2 = sName.toUpperCase()+": ";
            sName2 = sName2+ vFieldValues.elementAt(i).toString();
            v.addElement(sName2);
        }
        return v;
      
    }
    
    
}

 

