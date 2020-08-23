/*
 * JTurnsHorizontalTable.java
 *
 * Created on 07 October 2007, 12:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.conversation.ui;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import diet.server.conversationhistory.ConversationHistory;


public class JTurnsHorizontalTable extends JTable {///implements JDiETTableRowFilter{

  private JTurnsHorizontalTableModel jthtm;
  private JTurnsHorizontalTableCellRenderer jthtcr;
///  private TableRowSorter sorter;
///  private RowFilter rf;

  public JTurnsHorizontalTable(ConversationHistory c) {
    super();
    jthtm = new JTurnsHorizontalTableModel(this,c);
    this.setModel(jthtm);
    this.setGridColor(Color.lightGray);
    //this.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
    //this.setAutoCreateRowSorter(true);
    ///sorter = new JDiETTableSorter(jthtm);
    ///this.setRowSorter(sorter);
    
    //this.setAutoCreateColumnsFromModel(false);
    
   /// sorter.setSortsOnUpdates(true);
    jthtcr = new JTurnsHorizontalTableCellRenderer(this); 
    this.setDefaultRenderer(Object.class,jthtcr);
    setSize();
    this.setShowGrid(true);
    
  }


 private void setSize(){
     TableColumnModel tcm = this.getColumnModel();
     tcm.getColumn(0).setPreferredWidth(150);
     tcm.getColumn(0).setMaxWidth(160);
     tcm.getColumn(1).setPreferredWidth(40);
     tcm.getColumn(1).setMaxWidth(60);

 }
 
 public String getColumnName(int column){
     return jthtm.getColumnName(column);
 }
 


 public void refresh(){
     this.jthtm.refresh();
 }

 protected TableColumnModel createDefaultColumnModel()
    {
        if (5<1) // OOB Exception if this is true
        {
            return super.createDefaultColumnModel();
        }
        else // dirty work-round, requires other fixes too
        {
            return new SafeTableColumnModel();
        }
    }

 
 ///public void setRowSorter(RowSorter trs){
///     super.setRowSorter(trs);
///     if(trs instanceof TableRowSorter){
///         this.sorter = (TableRowSorter)trs;
///     } 
///     if(rf!=null){   
///       this.sorter.setRowFilter(rf);
///     }
     
     
     
 ///}

 ///public boolean setTableRowFilter(String filterText){
///     RowFilter<JLexiconTableModel, Object> rf2 = null;
 ///    rf =null;
 ///    sorter.setRowFilter(null);
    /// try {
    ///         System.err.println("TABLE.SETROWFILTER "+filterText);
    ///         rf2 = RowFilter.regexFilter(filterText);                     
    ///    } catch (java.util.regex.PatternSyntaxException e) {
    ///         return false;
    ///    }
    ///    sorter.setRowFilter(rf2);
    ///    rf = rf2;
    ///    return true;
        
 ///}

 
 
 
class SafeTableColumnModel extends DefaultTableColumnModel
{
    SafeTableColumnModel()
    {
        super();
    }

    /**
     * Hook to allow us to try and trap index out-of-bounds exception.
     */
    public TableColumn getColumn( int column )
    {
        if ((column == TableModelEvent.ALL_COLUMNS) ||
            (column == TableModelEvent.HEADER_ROW))
        {
            //NOTE System.out.println( "ERROR! Request trapped for header row or all columns (" + column + ")" );

            column = 0; // try this instead, which moves the problem along a bit!
        }

        TableColumn cCol = super.getColumn( column );
        if(column==0){
             cCol.setPreferredWidth(160);
             cCol.setMaxWidth(200);
        }
        else if(column ==1){
            cCol.setPreferredWidth(80);
            cCol.setMaxWidth(90);
        }
    
        
        return cCol;

}
}
 
}
