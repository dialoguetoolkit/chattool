/*
 * JTurnsListTable.java
 *
 * Created on 06 October 2007, 16:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;

import diet.server.Participant;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import diet.server.conversationhistory.ConversationHistory;

public class JParticipantsTable extends JTable {///implements JDiETTableRowFilter{

  private JParticipantsTableModel jptm;
  boolean telegram = false;
  

  public JParticipantsTable(ConversationHistory cH, boolean telegram) {
    super();
    this.telegram=telegram;
    jptm = new JParticipantsTableModel(this,cH,telegram);
    this.setModel(jptm);
    this.setGridColor(Color.lightGray);
    setSize();
   
    
  }

 
  private void setSize(){
      if(telegram)this.setSizeTelegram();
      else{
          setSizeNonTelegram();
      }
      
  }

  private void setSizeTelegram(){
    TableColumn column = null;
    for (int i = 0; i <7; i++) {
      column = this.getColumnModel().getColumn(i);
      if(i==0 || i==1||i==2||i==3||i==4|i==5|i==6|i==7 |i==8||i==9||i==10||i==11||i==12){
         // column.setPreferredWidth(75);
         // column.setMinWidth(75);
         // column.setMaxWidth(75);
      }
       
  }

 }
  
  
 private void setSizeNonTelegram(){
    TableColumn column = null;
    for (int i = 0; i <7; i++) {
      column = this.getColumnModel().getColumn(i);
      if(i==0 || i==1||i==2||i==3||i==4|i==5|i==6|i==7 |i==8||i==9||i==10||i==11||i==12){
          column.setPreferredWidth(75);
          column.setMinWidth(75);
          column.setMaxWidth(75);
      }
       
  }

 }
 
 public String getColumnName(int column){
     return this.jptm.getColumnName(column);
 }




public void updateRowDataForSingleParticipant(Participant p){
    this.jptm.updateRowDataForSingleParticipant(p);
}


}
