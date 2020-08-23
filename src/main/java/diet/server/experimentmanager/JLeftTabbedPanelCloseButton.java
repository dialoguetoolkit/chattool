/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.experimentmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class JLeftTabbedPanelCloseButton extends JButton implements ActionListener{

   
    private EMUI em;
    private JComponent jc;
    //private 
    
    
            
    public JLeftTabbedPanelCloseButton(EMUI em, JComponent jc){
        
        this.em=em;
        this.jc=jc;
        super.setForeground(Color.red);
        super.setText("x");
        super.setPreferredSize(new Dimension(15,15));
        super.addActionListener(this);
        
    }        
             
     public void actionPerformed(ActionEvent e) {
         Object[] possibilities = {"keep experiment running", 
                                   "stop experiment and keep window", 
                                   "stop experiment and discard window",
                                   "stop experiment and keep window and stop clients",
                                   "stop experiment and discard window and stop clients",
         };
         String s = (String)JOptionPane.showInputDialog(
                    null,
                    "What do you want to do with current experiment?",
                    "Experiment management",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "keep experiment running");
         if(s==null)return;
         if(s.equalsIgnoreCase("stop experiment and keep window")){
             em.stopAllExperimentThreads(jc,false,false);
         }
         else if(s.equalsIgnoreCase("stop experiment and discard window")){
              em.stopAllExperimentThreads(jc,true,false);
         }
         else if(s.equalsIgnoreCase("stop experiment and keep window and stop clients")){
             em.stopAllExperimentThreads(jc,false,true);
         }
         else if(s.equalsIgnoreCase("stop experiment and discard window and stop clients")){
             em.stopAllExperimentThreads(jc,true,true);
         }
         
                 
    }
     
     
}
