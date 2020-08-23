/*
 * JTurnsHorizontalTable.java
 *
 * Created on 07 October 2007, 12:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;

import diet.server.Conversation;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;



public class JRulesBasedInterventionsTable extends JTable {///implements JDiETTableRowFilter{

   
///  private TableRowSorter sorter;
///  private RowFilter rf;
    
    TableModel tm;

  public JRulesBasedInterventionsTable(Conversation  c) {
    super();
    
    
    
    this.setGridColor(Color.lightGray);
    setSize();
    this.setShowGrid(true);
    
  }
 
  
  
  public void setModel(TableModel tm){
      super.setModel(tm);
      this.tm=tm;
       
       
  }
  

 private void setSize(){
    
    // TableColumnModel tcm = this.getColumnModel();
     //tcm.getColumn(0).setPreferredWidth(150);
     //tcm.getColumn(0).setMaxWidth(160);
     //tcm.getColumn(1).setPreferredWidth(40);
     //tcm.getColumn(1).setMaxWidth(60);

 }
 
 public String getColumnName(int column){
     return tm.getColumnName(column);
 }

    @Override
    public Object getValueAt(int row, int column) {
        //if(2<5) return "HERE";
        
        return tm.getValueAt(row, column);
    }

    @Override
    public int getColumnCount() {
        //if(2<5)return 3;
        return tm.getColumnCount();
    }

    @Override
    public int getRowCount() {
        //if(2<5)return 3;
        return tm.getRowCount();
    }
 


 

 
 
 
}
