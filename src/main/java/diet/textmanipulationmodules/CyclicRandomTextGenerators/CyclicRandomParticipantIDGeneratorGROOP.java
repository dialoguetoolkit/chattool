/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.CyclicRandomTextGenerators;


import diet.utils.VectorToolkit;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Greg
 */
public class CyclicRandomParticipantIDGeneratorGROOP {

    Random r = new Random();

    public static String[] acceptableID = {
        "RRRR1",
        "RRRR2",
        "RRRR3",
        "RRRR4",
        "RRRR5",
        "RRRR6",
        "RRRR7",
        "RRRR8",
        "RRRR9",
        "RRRR0",
        "RRRRX",
        "RRRRY",
        "RRRRZ",
        "LLLL1",
        "LLLL2",
        "LLLL3",
        "LLLL4",
        "LLLL5",
        "LLLL6",
        "LLLL7",
        "LLLL8",
        "LLLL9",
        "LLLL0",
        "LLLLX",
        "LLLLY",
        "LLLLZ",
        
       
        
       
       
       
      
      };

    
    
    int counter=0;
    
    
    
    
    static Vector v2 =new Vector(Arrays.asList(acceptableID));

    public CyclicRandomParticipantIDGeneratorGROOP(){
        System.err.println("GENERATOR");
    }


    public synchronized String getNextOLD(){
        if(v2.size()==0)return "NOMOREIDSTOGENEREATE";
        Object o = v2.elementAt(0);
        System.err.println("---IDGENERATOBEGINNING THE 1st LIST HHERE ");
        VectorToolkit.list(v2);
        v2.removeElement(o);
        System.err.println("---IDGENERATORBEGINNING The 2nd list HERE RETURNING :"+(String)o);
         VectorToolkit.list(v2);
        counter++;
        return (String)o+counter;
    }
    public String getNext(){
        if(v2.size()==0)return "NOMOREIDSTOGENEREATE";
        Object o = v2.elementAt(r.nextInt(v2.size()));
        v2.removeElement(o);
        counter++;
        return (String)o+counter;
    }
    public String getNextNonDeleting(){
        if(v2.size()==0)return "NOMOREIDSTOGENEREATE";
        Object o = v2.elementAt(r.nextInt(v2.size()));
        //v2.removeElement(o);
        counter++;
        return (String)o+counter;
    }
    
    
    
    
    
     
    
    
    
    
    
    
}
