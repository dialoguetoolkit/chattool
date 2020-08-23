/*
 * JTurnsListTableModel.java
 *
 * Created on 06 October 2007, 16:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.ParticipantGroups.ui;

import diet.server.conversation.ui.*;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.ParticipantConnection;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import diet.server.conversationhistory.ConversationHistory;
import java.util.Date;
import java.util.Vector;
import javax.swing.SwingUtilities;

public class JPanelGroupsTableModel extends AbstractTableModel {

  private ConversationHistory cH;
  private Conversation c;
  
  TableModel thisTableModel;
  JTable jt;
//  UIUpdatingThread uit;
  
  
  public JPanelGroupsTableModel(JTable jt,ConversationHistory cH) {
     super();
     this.cH=cH;
     this.c=cH.getConversation();
     
     this.jt = jt;
     this.thisTableModel = this;
     //uit = new UIUpdatingThread();
     //uit.start();
  }

  
        
        
        //data.addElement(v);
        //refresh();
        
    

  public boolean isCellEditable(int x, int y){
    return false;
  }

   public String getColumnName(int column){
     if(column==0){
         return "Participant ID";
     }
     else if(column==1){
         return "Username";
     }
     else if (column==2){
         return "Group ID";
     }
    
    
    else{
         return " ";
     }
   }

  public Object getValueAt(int x, int y){
      
    
    try{  
        Participant p = (Participant)c.getParticipants().getAllParticipants().elementAt(x);
        ParticipantConnection pc = p.getConnection();
        
        if(y==0){
            return p.getParticipantID();
        }
        if(y==1){
            return p.getUsername();
        }
        if (y==2){
            return c.getController().pp.getSubdialogueID(p);
        }
        
        else{
            return "" ;
                  
        }
    
    
   
    }catch (Exception e){
        e.printStackTrace();
        return "UI ERROR";
    }
  }

  public Class getColumnClass(int column) {
                 return Object.class;         
      
              
  }
  
  public int getRowCount(){
    try{
     return c.getParticipants().getAllParticipants().size();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return 0;
  }
  public int getColumnCount(){
     return 3;
  }

  public void updateData(){
       fireTableDataChanged();
       System.err.println("PARTICIPANTPARTNERINGUPDATING THE DATA: "+c.getParticipants().getAllParticipants().size());
       Vector v = c.getParticipants().getAllParticipants();
       for(int i=0;i<v.size();i++){
           Participant p = (Participant)v.elementAt(i);
           System.err.println("PPSETTING THE DATA2: "+p.getUsername()+" "+p.getNumberOfChatMessagesProduced());
       }
       
       
       
      
  }

  public void updateRowDataForSingleParticipant(Participant p){
      final int index = c.getParticipants().getAllParticipants().indexOf(p); 
      SwingUtilities.invokeLater(new Runnable(){
      public void run(){
            fireTableRowsUpdated(index, index);  
            
         }
      });
      
      
  }
  
  
    
    
    
  
}