/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.conversation.ui;

import diet.server.ConversationUIManager;
import diet.server.conversationhistory.ConversationHistory;
import diet.server.conversationhistory.turn.Turn;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author LX1C
 */
public class JGroupsConversationHistories extends JPanel{

    public ConversationUIManager cui;
    ConversationHistory cH;
    Hashtable<String,JGroupsConversationHistoriesPanel> htjp = new Hashtable();
    
    boolean telegram = false;
    
    public JGroupsConversationHistories( ConversationUIManager cui,ConversationHistory cH, boolean telegram) {
        this.cui = cui;
        this.cH = cH;
        this.telegram=telegram;
        GridLayout gl = new GridLayout(0,5); //Set this as a parameter
        this.setLayout(gl);
        this.setPreferredSize(new Dimension(500,400));
        this.setVisible(true);
       
    }
    
    public void addTurn(Turn t){
        
             System.err.println("jgch adding turn2");
        
             String subdialogueID = t.getSubdialogueID();
             final JGroupsConversationHistories jt = this;
            
             
             SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                  JGroupsConversationHistoriesPanel jchp = htjp.get(subdialogueID);
                  if(jchp==null){
                     jchp = new JGroupsConversationHistoriesPanel(jt, subdialogueID);
                     htjp.put(subdialogueID, jchp);
                     jt.add(jchp);
                     System.err.println("jgch adding turn3");
                     
                     int numberOfGroups = htjp.keySet().size();
                     
                     
                     if(numberOfGroups==1){
                         GridLayout gl = new GridLayout(0, 1); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if(numberOfGroups<4){
                         GridLayout gl = new GridLayout(0, 2); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if(numberOfGroups<9){
                         GridLayout gl = new GridLayout(0, 3); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if(numberOfGroups<16){
                         GridLayout gl = new GridLayout(0, 4); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if (numberOfGroups < 25){
                         GridLayout gl = new GridLayout(0, 5); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if (numberOfGroups < 36){
                         GridLayout gl = new GridLayout(0, 6); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if (numberOfGroups < 49){
                         GridLayout gl = new GridLayout(0, 7); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if (numberOfGroups < 64){
                         GridLayout gl = new GridLayout(0, 8); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else if (numberOfGroups < 81){
                         GridLayout gl = new GridLayout(0, 9); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     else{
                         GridLayout gl = new GridLayout(0, 10); //Set this as a parameter
                         jt.setLayout(gl);
                     }
                     
                     
                     
                     
                     
                     
                  }
                  jchp.processTurn( t);
                   System.err.println("jgch adding turn4");
                  
             }
             });
             
             
             
              
         
    }
    
    
    public void addSubgroup(String name){
        
        
        SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                  
                   
             }
        });
    }
    
    
    
    
    
    
    
}
