/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.SplitUtterance;

import java.util.Vector;

import diet.client.DocumentChange.DocChange;
import diet.client.DocumentChange.DocInsert;

/**
 *
 * @author Greg
 */
public class UtteranceSplitter {

    
    public static Vector[] splitUtterance(Vector v){
        boolean split = false;
        Vector firstHalf = new Vector();
        Vector secondHalf = new Vector();
        for(int i=0;i<v.size();i++){
            if(!split){
                firstHalf.addElement(v.elementAt(i));
            }else{
                secondHalf.addElement(v.elementAt(i));
            }
            
            DocChange dc = (DocChange)v.elementAt(i);
            if(dc instanceof DocInsert){
                DocInsert di = (DocInsert)dc;
                if(!split){
                    if(i>10&&di.str.equalsIgnoreCase(" ")){
                        split = true;
                    }
                }
            }
            
            
        }
        Vector[] vv = new Vector[2];
        vv[0]=firstHalf;
        vv[1]=secondHalf;
        System.err.println("FIRSTSIZE"+firstHalf.size());
         System.err.println("SECONDSIZE"+secondHalf.size());
        return vv;
    }
}
