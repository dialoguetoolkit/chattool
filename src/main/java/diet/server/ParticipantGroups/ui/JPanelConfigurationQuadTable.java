/*
 * JTurnsListTable.java
 *
 * Created on 06 October 2007, 16:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.ParticipantGroups.ui;

import diet.server.Conversation;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class JPanelConfigurationQuadTable extends JTable   {///implements JDiETTableRowFilter{

  Conversation c;  
    
  private JPanelConfigurationQuadTableModel jpctm;
 
  

  public JPanelConfigurationQuadTable(Conversation c) {
    super();
    this.c=c;
    jpctm = new JPanelConfigurationQuadTableModel(this,c);
    this.setModel(jpctm);
    this.setGridColor(Color.lightGray);
    setSize();
    this.setVisible(true);
    
    
    
    this.setRowSelectionAllowed(false);    // model.
    this.setColumnSelectionAllowed(true);
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    
    this.getColumnModel().getSelectionModel().setSelectionInterval(0, 0);
    
    /*     this.getColumnModel().getSelectionModel().setSelectionInterval(1, 1);
         final ListSelectionModel sel = this.getColumnModel().getSelectionModel();
        sel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //If the column is disabled, reselect previous column
                if (sel.isSelectedIndex(disabled_col))
                    sel.setSelectionInterval(cur_col,cur_col);
                //Set current selection
                else cur_col = sel.getMaxSelectionIndex();
            }
        });
    */
    
    
  }

 private int disabled_col = 0, cur_col = 1;
  

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
     return this.jpctm.getColumnName(column);
 }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {
        super.columnSelectionChanged(e);
        //4ated methods, choose Tools | Templates.
       // System.err.println("HERE1:SELECTIONFIRST:"+e.getFirstIndex()+ " SELECTIONLAST: "+ e.getLastIndex());
        //super.columnSelectionChanged(e); //To change body of gener
        // this.getSelectionModel().clearSelection();
       
        if(e.getFirstIndex()!=0){
           // super.columnSelectionChanged(e);
            
      
        }
       
         System.err.println("HERE2:SELECTIONFIRST:"+e.getFirstIndex()+ " SELECTIONLAST: "+ e.getLastIndex());
    }

    public void updateData(){
        //jpctm.updateData();
    }

    
    public Vector<String> getSelection(){
        System.err.println("Selected Column: "+this.getSelectedColumn());
        System.err.println("Rowcount: "+jpctm.getRowCount());
        
        Vector<String> vs = new Vector();
        int col = this.getSelectedColumn();
        for(int i=0;i<jpctm.getRowCount();i++){
            vs.add((String)jpctm.getValueAt(i, col));
            System.err.println("TABLE: "+jpctm.getValueAt(i, col));
        }
        return vs;
        
    }
    
}



    
    
    
  


    
    
    
    



