/*
 * JTurnsListTableModel.java
 *
 * Created on 06 October 2007, 16:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.ParticipantGroups.ui;

import diet.server.Conversation;
import diet.server.Participant;
import diet.server.ParticipantConnection;
import diet.server.ParticipantGroups.ParticipantGroupConfigurations;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.util.Vector;
import javax.swing.SwingUtilities;

public class JPanelConfigurationTriadTableModel extends AbstractTableModel {

  
  private Conversation c;
  
  TableModel thisTableModel;
  JTable jt;
  String triad = ParticipantGroupConfigurations.triads;
  
  
  public JPanelConfigurationTriadTableModel(JTable jt) {
     super();   
     this.jt = jt;
     this.thisTableModel = this;
     //uit = new UIUpdatingThread();
     //uit.start();
     this.generateTable();
     
  }

  
  Vector<String[]> v = new Vector();
  
  public void generateTable(){
      //String triad = ParticipantGroupConfigurations.triads;
      
      String[] lines=  triad.split("\n");
      
      for(int i=0;i<lines.length;i++){
          String[] line = lines[i].split("[|]");
          v.add(line);
          System.err.print("line: ");
          
          for(int j=0;j<line.length;j++){
              System.err.print(line[j]);
          }
          System.err.println("");
          
      }
      
  }  
  


  public boolean isCellEditable(int x, int y){
    return false;
  }

   public String getColumnName(int column){
     if(column<50){
         return "";
     }
     return "";
   }

  public Object getValueAt(int x, int y){
    //if(2<5) return "THIS";  
    
    try{  
        String[] row = v.elementAt(x);
        return row[y];
    
    
   
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
     // if(2<5)return 50;   
     return v.size();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    return 0;
  }
  public int getColumnCount(){
     return 10;
  }

 
  
  
    
    
    
  
}