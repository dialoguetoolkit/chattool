/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.CyclicRandomTextGenerators;

import java.util.Vector;

/**
 *
 * @author Greg
 */
public class CyclicRandomEnglishDemoSelfRepairInitiation extends CyclicRandomTextGenerator{

    public CyclicRandomEnglishDemoSelfRepairInitiation(Vector v){
        super(v);
    }
    
    public  CyclicRandomEnglishDemoSelfRepairInitiation(){
        Vector allPossibleRepairs = new Vector();
        allPossibleRepairs.addElement("uh");
        allPossibleRepairs.addElement("um");
        allPossibleRepairs.addElement("uhh I mean");
        super.setPossibleWords(allPossibleRepairs);
    }
            
}
