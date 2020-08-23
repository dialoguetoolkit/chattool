/*
 * JTurnsHorizontalTableModel.java
 *
 * Created on 07 October 2007, 12:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import diet.server.conversationhistory.Conversant;
import diet.server.conversationhistory.ConversationHistory;
import diet.server.conversationhistory.turn.ArtificialTurnProducedByServer;
import diet.server.conversationhistory.turn.NormalTurnProducedByParticipant;
import diet.server.conversationhistory.turn.Turn;
import diet.utils.DateTimeUtils;

public class JTurnsHorizontalTableModel extends AbstractTableModel {

  private ConversationHistory cH;
  private JTable jt;
  private TableModel thisTableModel;

  public JTurnsHorizontalTableModel(JTable jt,ConversationHistory c) {
     super();
     this.cH=c;
     this.jt = jt;
     this.thisTableModel = this;
     
  }

  public void refresh(){
     //System.out.println("Refresh");
     SwingUtilities.invokeLater(new Runnable(){
         public void run(){
       ///  jt.setRowSorter(new JDiETTableSorter(thisTableModel));    
         //fireTableDataChanged();
         fireTableStructureChanged();
     }  });
    // jt.setRowSorter(new JDiETTableSorter(thisTableModel));
     //this.fireTableDataChanged();
     //this.fireTableStructureChanged();
  }

  public boolean isCellEditable(int x, int y){
    return false;
  }

   public String getColumnName(int column){

     if(column==0){
         return "Server Timestamp";
     }
     else if (column==1){
         return "Duration";
     }
     if (column<=cH.getConversants().size()+1){
         Conversant c2 = (Conversant)cH.getConversants().elementAt(column-2);
         return c2.getUsername();
     }
     else{
         return " ";
     }
   }

  public Object getValueAt(int x, int y){
    try{  
       //System.out.println("GET VALUE AT");
    Vector turns = cH.getTurns();
    Vector conversants = cH.getConversants();
    if(x>=turns.size())return " "; 
    if(y>=conversants.size()+2)return " ";//TOOLARGE "+x+ ","+y;
    Turn t = (Turn)turns.elementAt(x);
     if(y==0){
       if(t instanceof NormalTurnProducedByParticipant){
           NormalTurnProducedByParticipant ntpbp =  (NormalTurnProducedByParticipant)t;
           return    DateTimeUtils.getString(ntpbp.getTimeOnServerOfReceipt());
       }
       else if (t instanceof  ArtificialTurnProducedByServer){
          ArtificialTurnProducedByServer atpbs =  (ArtificialTurnProducedByServer)t;
          return  DateTimeUtils.getString(atpbs.getTimeOnServerOfSending());
       }
       else{
           return "----";
       }
    }
    else if(y==1){
       if(t instanceof NormalTurnProducedByParticipant){
           NormalTurnProducedByParticipant ntpbp =  (NormalTurnProducedByParticipant)t;
           return    ntpbp.getTimeOnClientOfSending()-ntpbp.getTimeOnClientOfStartTyping();
       }
       else if (t instanceof  ArtificialTurnProducedByServer){
          ArtificialTurnProducedByServer atpbs =  (ArtificialTurnProducedByServer)t;
          return "";
       }
       else{
           return "----";
       }
    }
    else {
      
        
       
        
        
        Conversant c2 = (Conversant)conversants.elementAt(y-2);
       
        
        
        
        
        JLabel jl = new JLabel();
        if(t.getSender().getUsername().equalsIgnoreCase("server")){
            jl.setForeground(Color.BLACK);
            jl.setBackground(Color.LIGHT_GRAY);
            
                   
            if(t.getSender().equals(c2)){
                jl.setText(t.getText());
                return jl;
            }
        }
        
        if(t.getSender().equals(c2)){
            return t.getText();
        }
        else if(t.getApparentSender().equals(c2)){     
            jl = new JLabel("APPARENT ORIGIN");
            jl.setForeground(Color.BLACK);
            jl.setBackground(Color.red);
            return  jl;
        }        
        else if(!t.getRecipients().contains(c2)){ 
            if(c2.getUsername().equalsIgnoreCase("server")){
                jl.setForeground(Color.BLACK);
                jl.setBackground(Color.LIGHT_GRAY);
                return jl;
            }  
            jl =  new JLabel("NOT RECEIVED");
            jl.setForeground(Color.WHITE);
            jl.setBackground(Color.black);
            return  jl;        
        }
        
        else{
           return " ";//"SENDERIS "+t.getApparentSender();
        }
        
    }    
    }catch (Exception e){
        return "UI ERROR";
    }
  }

  public Class getColumnClass(int column) {
               Class returnValue;
               if ((column >= 0) && (column < getColumnCount())) {
                 returnValue = getValueAt(0, column).getClass();
               } else {
                 returnValue = Object.class;
               }
               return returnValue;
  }
  
  public int getRowCount(){
  
     return this.cH.getTurns().size();
  }
  public int getColumnCount(){
     
      if(cH.getConversants().size()==0)return 3;
      return 2+cH.getConversants().size();
     
  }



}