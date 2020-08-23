/*
 * JTurnsListTableModel.java
 *
 * Created on 06 October 2007, 16:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import diet.server.conversationhistory.Conversant;
import diet.server.conversationhistory.ConversationHistory;
import diet.server.conversationhistory.turn.ArtificialTurnProducedByServer;
import diet.server.conversationhistory.turn.NormalTurnProducedByParticipant;
import diet.server.conversationhistory.turn.NormallTurnProducedByParticipantInterceptedByServer;
import diet.server.conversationhistory.turn.Turn;
import diet.utils.DateTimeUtils;

public class JTurnsListTableModel extends AbstractTableModel {

  private ConversationHistory c;
  private Vector data = new Vector();
  TableModel thisTableModel;
  JTable jt;
  boolean istelegram=false;

  public JTurnsListTableModel(JTable jt,ConversationHistory c,boolean istelegram) {
     super();
     this.istelegram=istelegram;
     this.c=c;
     this.jt = jt;
     this.thisTableModel = this;
  }

  public void addRow(Turn t){

        final Turn t2 = t;
        
        SwingUtilities.invokeLater(new Runnable(){public void run(){
         //fireTableRowsInserted(c.getContiguousTurns().size()-1,c.getContiguousTurns().size());
         //jt.getRowSorter().
            //((TableRowSorter)jt.getRowSorter()).setSortable(1,false);
            data.addElement(t2);
            //((TableRowSorter)jt.getRowSorter()).setModel(thisTableModel);
            
           /// jt.setRowSorter(new JDiETTableSorter(thisTableModel));
            fireTableDataChanged();
             //fireTableStructureChanged();
            //jt.setModel(thisTableModel);
          
            //((TableRowSorter)jt.getRowSorter()).setModel(thisTableModel);
              //((TableRowSorter)jt.getRowSorter()).setSortable(1,true);
           
            
            
         }  });
        
        
        //data.addElement(v);
        //refresh();
        
    }

  public boolean isCellEditable(int x, int y){
    return false;
  }

   public String getColumnName(int column){
     if(column==0){
         return "timestamp (server)";
     }
     else if(column==1){
         return "onset (client)";
     }
     else if (column==2){
         return "enter (client)";
     }
     else if(column ==3){
         return "Sender ID";
     }
     else if(column ==4){
         return "Username";
     }
     else if(column ==5){
         return "App. Orig.";
     }
     else if(column ==6){
         return "Text";
     }
     else if(column ==7){
         return "Recipients";
     }
     else if(column ==8){
         return "KDels"; 
     }
     else if(column ==9){
         return "DDels"; 
     }
     else if(column==10){
        return "Blocked";
    }
    
    else{
         return " ";
     }
   }

  public Object getValueAt(int x, int y){
    try{  
       //System.out.println("GET VALUE AT");
    
    if(x>=data.size())return " "; 
    Turn t = (Turn)data.elementAt(x);
    if(y==0){
       if(t instanceof NormalTurnProducedByParticipant){
           NormalTurnProducedByParticipant ntpbp =  (NormalTurnProducedByParticipant)t;
           return   ""+ntpbp.getTimeOnServerOfReceipt();
       }
       else if (t instanceof  ArtificialTurnProducedByServer){
          ArtificialTurnProducedByServer atpbs =  (ArtificialTurnProducedByServer)t;
          return  ""+atpbs.getTimeOnServerOfSending();
       }
       return t.getSender().getUsername();
    }
    else if(y==1){
       if(t instanceof NormalTurnProducedByParticipant){
           NormalTurnProducedByParticipant ntpbp =  (NormalTurnProducedByParticipant)t;
           return    ntpbp.getTimeOnClientOfStartTyping();
       }
       else{
           return "";
       }
    }
    else if(y==2){
       if(t instanceof NormalTurnProducedByParticipant){
           NormalTurnProducedByParticipant ntpbp =  (NormalTurnProducedByParticipant)t;
           return    ntpbp.getTimeOnClientOfSending();
       }
       else{
           return "";
       }
    }
    else if(y==3){
        return t.getSenderID();
    }
    else if(y==4){
        return t.getSender().getUsername();
    }
    else if(y==5){
        Conversant cnv = t.getApparentSender();
        if(cnv==null) return "" ;
        
        return cnv.getUsername();
    }
    else if(y==6){
        return t.getText();
    }
    else if(y==7){
        return t.getRecipientsAsString();
    }
    else if (y==8){
        return t.getKeypressDeletes(); 
    }
    else if(y==9){
       return t.getDocDeletes();
    }
    else if(y==10){
        if (t.getTypingWasBlockedDuringTyping())return "BLOCKED";
        return "OK";
        
    }
    
    else {
        return "---";
    }
    
   
    }catch (Exception e){
        e.printStackTrace();
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
    if(data==null)return 0;
     return this.data.size();
  }
  public int getColumnCount(){
     if(this.istelegram) return 8;
     return 10;
  }



}