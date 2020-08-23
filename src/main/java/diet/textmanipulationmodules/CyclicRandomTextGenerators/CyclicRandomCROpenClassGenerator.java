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
public class CyclicRandomCROpenClassGenerator extends CyclicRandomTextGenerator{

    public CyclicRandomCROpenClassGenerator(Vector v) {
        super(v);
    }

    public CyclicRandomCROpenClassGenerator() {
        Vector v = new Vector();
       
        v.addElement("Uhh?");
        v.addElement("sorry what?");
        v.addElement("what?");
        v.addElement("What?");
        v.addElement("srry wht?");
        v.addElement("huh?");
        v.addElement("srry wot?");
        super.setPossibleWords(v);
    }

    
}
