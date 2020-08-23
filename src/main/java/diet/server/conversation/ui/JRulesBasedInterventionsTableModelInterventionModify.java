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
import diet.server.ConversationController.autointervention.interventionmodify;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;


public class JRulesBasedInterventionsTableModelInterventionModify extends AbstractTableModel {

  Conversation c;
  private JTable jt;
  

  public JRulesBasedInterventionsTableModelInterventionModify(JTable jt,Conversation c) {
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
         return "Phrase to be removed";
     }
     else if (column==5){
         return "Phrase to be inserted";
     }
     else{
         return " ";
     }
   }

  public Object getValueAt(int x, int y){
    try{  
        interventionmodify im =   c.ai.getModifyRules().elementAt(x);
        if(y==0) return im.interventionid;
        if(y==1) return im.participantID;
        if(y==2) return im.poscrit;
        if(y==3) return im.negcrit;
        if(y==4)return im.stringToBeReplaced;
        if(y==5)return im.stringReplacement;
        return "";
    }catch(Exception e){
        e.printStackTrace();
    }       
     return "UIERROR";
        
        
  }

  public Class getColumnClass(int column) {
               return String.class;
  }
  
  public int getRowCount(){
     return c.ai.getModifyRules().size();
    
  }
  public int getColumnCount(){
    return 6;
     
     
  }



}