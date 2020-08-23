/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.CyclicRandomTextGenerators;

import diet.utils.VectorToolkit;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Greg
 */
public class CyclicRandomIntGenerator {

    public Vector allPossibleIntegers = new Vector();
    public Hashtable wordsUnUsed = new Hashtable();
    
    Random r = new Random();
    
    public CyclicRandomIntGenerator(int low, int numberOfItems){
        for(int i=low;i<low+numberOfItems;i++){
            Integer ii = new Integer(i);
            allPossibleIntegers.addElement(ii);
        }
        System.err.println("THESIZEOFCYCLIICIS"+allPossibleIntegers.size());
    }
    public CyclicRandomIntGenerator(Vector v){
        allPossibleIntegers =v;
    }
    public void setPossibleInts(Vector v){
        allPossibleIntegers = v;
    }
    
    
    public synchronized int getCurrentValue(Object key){
        Vector v = (Vector)wordsUnUsed.get(key);
        if(v==null){
            v=  VectorToolkit.randomSubset(allPossibleIntegers, allPossibleIntegers.size());
             System.err.println("THESIZEOFVIS"+v.size());
            this.wordsUnUsed.put(key, v);
            
            System.err.println("GETTING");
            return (int)v.elementAt(0);
        }
        return (int)v.elementAt(0);
    }
    
    
    public synchronized int generateNext(Object key){
       
        Vector v = (Vector)wordsUnUsed.get(key);
      
        
        if(v==null || v.size() <=1){
            v=  VectorToolkit.randomSubset(allPossibleIntegers, allPossibleIntegers.size());
            this.wordsUnUsed.put(key, v);
            System.err.println("RESETTING "+v.size());
            return (int)v.elementAt(0);
           
        }
       
        v.removeElementAt(0);
        this.wordsUnUsed.put(key, v);
        return (int)v.elementAt(0);
    }
    
    
    
    
    public static void main(String[] args){
         CyclicRandomIntGenerator cri = new CyclicRandomIntGenerator(0,4);
         Object o = new Object();
         Object o2 = new Object();
         //System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
          cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         cri.generateNext(o);
         System.err.println(cri.getCurrentValue(o));
         
         
         //System.err.println("VALUE" +cri.getCurrentValue(o));
         //cri.generateNext(o);
         //System.err.println("VALUE" +cri.getCurrentValue(o));
    }
    
}
