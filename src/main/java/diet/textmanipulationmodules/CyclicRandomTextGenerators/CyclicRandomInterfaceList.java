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
public class CyclicRandomInterfaceList extends CyclicRandomTextGenerator{

    public CyclicRandomInterfaceList(Vector v){
        super(v);
    }
    
    public  CyclicRandomInterfaceList(String[] interfaces){
        Vector allPossibleInterfaces = new Vector();
        for(int i=0;i<interfaces.length;i++){
             String iface = interfaces[i];
             allPossibleInterfaces.addElement(iface);
        }
        super.setPossibleWords(allPossibleInterfaces);
    }
            
}
