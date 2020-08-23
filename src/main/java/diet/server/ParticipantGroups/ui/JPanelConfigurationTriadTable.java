/*
 * JTurnsListTable.java
 *
 * Created on 06 October 2007, 16:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.ParticipantGroups.ui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import javax.swing.ListSelectionModel;

public class JPanelConfigurationTriadTable extends JTable {///implements JDiETTableRowFilter{

  private JPanelConfigurationTriadTableModel jpcttm;
 
  

  public JPanelConfigurationTriadTable() {
    super();
    jpcttm = new JPanelConfigurationTriadTableModel(this);
    this.setModel(jpcttm);
    this.setGridColor(Color.lightGray);
    setSize();
    this.setVisible(true);
    
    
    
    this.setRowSelectionAllowed(false);    // model.
    this.setColumnSelectionAllowed(true);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    
     this.getColumnModel().getSelectionModel().setSelectionInterval(0, 0);
  }

 
  

 private void setSize(){
    if(2<5)return;
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
     return this.jpcttm.getColumnName(column);
 }



public Vector<String> getSelection(){
        
        Vector<String> vs = new Vector();
        int col = this.getSelectedColumn();
        for(int i=0;i<jpcttm.getRowCount();i++){
            vs.add((String)jpcttm.getValueAt(i, col));
        }
        return vs;
        
    }


}
