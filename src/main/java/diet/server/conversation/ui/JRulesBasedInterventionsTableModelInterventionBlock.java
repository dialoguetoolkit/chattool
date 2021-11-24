/*
 * JTurnsHorizontalTableModel.java
 *
 * Created on 07 October 2007, 12:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;
import diet.server.Conversation;
import diet.server.ConversationController.autointervention.interventionblock;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import diet.server.conversationhistory.Conversant;
import diet.server.conversationhistory.turn.ArtificialTurnProducedByServer;
import diet.server.conversationhistory.turn.NormalTurnProducedByParticipant;
import diet.server.conversationhistory.turn.Turn;
import diet.utils.DateTimeUtils;

public class JRulesBasedInterventionsTableModelInterventionBlock extends AbstractTableModel {

  Conversation c;
  private JTable jt;
  

  public JRulesBasedInterventionsTableModelInterventionBlock(JTable jt,Conversation c) {
     super();
     this.c=c;
     this.jt = jt;
     jt.setModel(this);
     
  }

  public void refresh(){
     //System.out.println("Refresh");
     SwingUtilities.invokeLater(new Runnable(){
         public void run(){
      
         fireTableStructureChanged();
     }  });
    
  }

  public boolean isCellEditable(int x, int y){
    return false;
  }

   public String getColumnName(int column){

     if(column==0){
         return "Rule No.";
     }
     else if (column==1){
         return "ParticipantID";
     }
     else if (column==2){
         return "Must have this phrase";
     }
     else if (column==3){
         return "Must NOT have this phrase";
     }
     else if (column==4){
         return "Notification to sender";
     }
     else{
         return "";
     }
   }

  public Object getValueAt(int x, int y){
    try{  
        interventionblock ib =   c.getAI().getBlockRules().elementAt(x);
        if(y==0) return ib.interventionid;
        if(y==1) return ib.participantID;
        if(y==2) return ib.poscrit;
        if(y==3) return ib.negcrit;
        if(y==4)return ib.response;
        return "";
    }catch(Exception e){
        e.printStackTrace();
    }       
     return "UIERROR";
        
        
  }

  
  
  public int getRowCount(){
     return c.getAI().getBlockRules().size();
    
  }
  public int getColumnCount(){
    return 5;
     
     
  }



}