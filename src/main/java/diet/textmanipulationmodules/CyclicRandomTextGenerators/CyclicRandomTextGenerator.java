/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.CyclicRandomTextGenerators;

import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Greg
 */
public class CyclicRandomTextGenerator {

    public Vector allPossibleWords = new Vector();
    public Hashtable wordsUsed = new Hashtable();
    Random r = new Random();
    
    public CyclicRandomTextGenerator(){
        
    }
    public CyclicRandomTextGenerator(Vector v){
        allPossibleWords =v;
    }
    public void setPossibleWords(Vector v){
        allPossibleWords = v;
    }
    
    public String getNext(Object participant){
        try{
        Vector v = (Vector)wordsUsed.get(participant);
        if(v==null){
            v=new Vector();
            wordsUsed.put(participant, v);
        }
        Vector candidates = new Vector();
        for(int i=0;i<allPossibleWords.size();i++){
            String s = (String)allPossibleWords.elementAt(i);
            if(!v.contains(s)){
                candidates.addElement(s);
            }
        }
        
        if(candidates.size()==0){
            int i = r.nextInt(allPossibleWords.size());
            String s = (String)allPossibleWords.elementAt(i);
            v = new Vector();
            v.addElement(s);
            this.wordsUsed.put(participant, v);
            return s;
        }
        int i = r.nextInt(candidates.size());
        String s = (String)candidates.elementAt(i);
        v.addElement(s);
        return s;
        }catch (Exception e){
            
            e.printStackTrace();
            System.exit(-1312312);
            return "ERROR";
        }
        
    }
    
    
    
}
