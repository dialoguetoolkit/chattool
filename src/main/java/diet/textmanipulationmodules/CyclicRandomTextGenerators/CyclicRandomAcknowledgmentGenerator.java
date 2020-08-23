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
public class CyclicRandomAcknowledgmentGenerator extends CyclicRandomTextGenerator{

    public CyclicRandomAcknowledgmentGenerator(Vector v){
        super(v);
    }
    
    public  CyclicRandomAcknowledgmentGenerator(){
        Vector allPossibleAcknowledgments = new Vector();
        allPossibleAcknowledgments.addElement("OK");
        allPossibleAcknowledgments.addElement("k");
        allPossibleAcknowledgments.addElement("Ok");
        allPossibleAcknowledgments.addElement("ok");
        allPossibleAcknowledgments.addElement("ok..");
        allPossibleAcknowledgments.addElement("k right");
        allPossibleAcknowledgments.addElement("ok right");
        allPossibleAcknowledgments.addElement("k rite");
        allPossibleAcknowledgments.addElement("okey");
        allPossibleAcknowledgments.addElement("ok thnks");
        allPossibleAcknowledgments.addElement("ok thanks");

        super.setPossibleWords(allPossibleAcknowledgments);
    }
            
}
