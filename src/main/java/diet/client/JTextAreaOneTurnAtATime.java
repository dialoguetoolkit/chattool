/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Greg
 */
public class JTextAreaOneTurnAtATime extends JTextArea {
    public Vector allTurns = new Vector();
    public int numberOfTurnsToDisplay = 2; 
     
    
    public JTextAreaOneTurnAtATime(int x,int y){
        super(x,y);
    }
    
    
    
    
    public void append(String s2){
        
        if(!s2.endsWith("\n"))s2 = s2+ "\n";
        final String s = s2;
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
               allTurns.addElement(s);
               int start = allTurns.size()-numberOfTurnsToDisplay;
                if(start>=allTurns.size())start=allTurns.size()-1;
                if(start<0)start=0;
                setText("");
                String txt = "";
                for(int i=start;i<allTurns.size();i++){
                   txt=txt+(String)allTurns.elementAt(i);
                }
                 setText(txt);
             }
             });
             
    }
}
