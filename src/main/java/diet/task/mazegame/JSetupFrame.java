/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.mazegame;
import diet.server.ConversationController.ui.CustomDialog;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;





public class JSetupFrame extends JFrame {
  JMazePanel2 jPanel1 = new JMazePanel2();
  JMazePanel2 jPanel2 = new JMazePanel2();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 ;
  JComboBox jComboBox2 = new JComboBox();
  JLabel jLabel4 = new JLabel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JButton jButton4 = new JButton();
  SetupIO stpIO;
  JButton jButton5 = new JButton();
  //JTextField jTextField3 = new JTextField();
  Vector cl1mazes =new Vector();
  Vector cl2mazes =new Vector();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField4 = new JTextField();
  JSetupFrameEventHandler jsfeh;
  JSetupMazeSquareButtonHandler1  jsmsbh1;
  JSetupMazeSquareButtonHandler2  jsmsbh2;
  JLabel jLabel3 = new JLabel();
  JTextField jTextField5 = new JTextField();
  JTextField jTextField6 = new JTextField();
  JTextField jTextField7 = new JTextField();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField8 = new JTextField();
  JTextField jTextField9 = new JTextField();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JButton jButton8 = new JButton();
  JButton jButton9 = new JButton();
  JTextField jTextField10 = new JTextField();
  JButton jButton10 = new JButton();
  JButton jButton11 = new JButton();
  JTextField jTextField11 = new JTextField();
  JTextField jTextField12 = new JTextField();
  JButton jButton12 = new JButton();
  JTextField jTextField13 = new JTextField();
  JButton jButton13 = new JButton();
  JLabel jLabel6 = new JLabel();
  JButton jButton00 = new JButton("Load from external directory:");


  public JSetupFrame(SetupIO stIO) {
    stpIO = stIO;
    this.setPreferredSize(new Dimension(880,700));
    
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  
  
  
  private void jbInit() throws Exception {
    
    jsfeh = new   JSetupFrameEventHandler (this);
    jsmsbh1   = new   JSetupMazeSquareButtonHandler1  (this);
    jsmsbh2   = new   JSetupMazeSquareButtonHandler2  (this);
    this.getContentPane().setLayout(null);
    jComboBox1 = new JComboBox(stpIO.getListOfMazeConfigs());
    jPanel1.setBackground(Color.black);
    jPanel1.setOpaque(true);
    ////ORIGINALLYjPanel1.setBounds(new Rectangle(39, 109, 291, 331));..
    jPanel1.setBounds(new Rectangle(39, 109, 331, 331));
    
    jPanel2.setBackground(Color.black);
    ////ORIGINALLY jPanel2.setBounds(new Rectangle(481, 109, 291, 331)); ...
    jPanel2.setBounds(new Rectangle(481, 109, 331, 331)); 
    
    JLabel jLabel0 = new JLabel(" Apologies for the atrocious layout/design/usability of this interface - it's old code from a phd project. Important: Do NOT use during an experiment");
    jLabel0.setForeground(Color.red);
    jLabel0.setBounds(0, 0, 850, 20);
    this.getContentPane().add(jLabel0);
    
    jLabel1.setText("Load from internal library:");
    jLabel1.setBounds(new Rectangle(41, 30, 180, 24));
    jComboBox1.setBounds(new Rectangle(210, 30, 200, 24));
    jComboBox1.addActionListener(jsfeh);
    
    
    jButton00.setBounds(new Rectangle(460, 30, 280, 24));
    this.getContentPane().add(jButton00);
    jButton00.addActionListener(jsfeh);
    
    
    jComboBox2.setBounds(new Rectangle(132, 66, 73, 21));
    jComboBox2.addActionListener(jsfeh);
    //jComboBox2.addItem("0");
    jLabel4.setText("maze number");
    jLabel4.setBounds(new Rectangle(45, 66, 80, 21));
    jButton1.setBounds(new Rectangle(214, 68, 54, 21));
    jButton1.setText("up");
    jButton1.addActionListener(jsfeh);
    jButton2.setBounds(new Rectangle(270, 67, 57, 21));
    jButton2.setMargin(new Insets(0, 0, 0, 0));
    jButton2.setText("down");
    jButton2.addActionListener(jsfeh);
    jButton3.setBounds(new Rectangle(427, 69, 64, 20));
    jButton3.setMargin(new Insets(0, 0, 0, 0));
    jButton3.setText("generate");
    jButton3.addActionListener(jsfeh);
    jTextField1.setText("7");
    jTextField1.setBounds(new Rectangle(505, 71, 28, 20));
    jTextField2.setText("7");
    jTextField2.setBounds(new Rectangle(538, 71, 28, 20));
    jButton4.setBounds(new Rectangle(361, 69, 59, 19));
    jButton4.setForeground(Color.red);
    jButton4.setMargin(new Insets(0, 0, 0, 0));
    jButton4.setText("DELETE");
    jButton4.addActionListener(jsfeh);
    jButton5.setBounds(new Rectangle(11, 631, 87, 28));
    jButton5.setText("save");
    jButton5.addActionListener(jsfeh);
    //jTextField3.setText("Ignore this");
    //jTextField3.setBounds(new Rectangle(120, 634, 641, 23));
    jLabel2.setText("duplicate paths");
    jLabel2.setBounds(new Rectangle(579, 72, 78, 19));
    jTextField4.setText("0");
    jTextField4.setBounds(new Rectangle(661, 72, 40, 19));
    jLabel3.setText("gates");
    jLabel3.setBounds(new Rectangle(719, 74, 30, 18));
    jTextField5.setText("3");
    jTextField5.setBounds(new Rectangle(756, 73, 42, 20));
    jTextField6.setSelectionStart(11);
    jTextField6.setText("");
    jTextField6.setBounds(new Rectangle(41, 473, 34, 22));
    jTextField7.setSelectionStart(11);
    jTextField7.setText("");
    jTextField7.setBounds(new Rectangle(106, 473, 38, 22));
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 24));
    jLabel5.setText("/");
    jLabel5.setBounds(new Rectangle(177, 465, 19, 39));
    jTextField8.setSelectionStart(11);
    jTextField8.setText("");
    jTextField8.setBounds(new Rectangle(229, 473, 34, 22));
    jTextField9.setSelectionStart(11);
    jTextField9.setText("");
    jTextField9.setBounds(new Rectangle(282, 473, 43, 22));
    jButton6.setBounds(new Rectangle(41, 509, 284, 22));
    jButton6.setText("change link status");
    jButton6.addActionListener(jsmsbh1);
    jButton7.setBounds(new Rectangle(41, 539, 112, 22));
    jButton7.setText("start");
    jButton7.addActionListener(jsmsbh1);
    jButton8.setBounds(new Rectangle(41, 566, 112, 22));
    jButton8.setText("finish");
    jButton8.addActionListener(jsmsbh1);
    jButton9.setBounds(new Rectangle(41, 594, 112, 22));
    jButton9.setText("switch");
    jButton9.addActionListener(jsmsbh1);
    jTextField10.setBounds(new Rectangle(485, 467, 34, 22));
    jTextField10.setText("");
    jTextField10.setSelectionStart(11);
    jButton10.setText("finish");
    jButton10.addActionListener(jsmsbh2);
    jButton10.setBounds(new Rectangle(485, 560, 112, 22));
    jButton11.setText("change link status");
    jButton11.addActionListener(jsmsbh2);
    jButton11.setBounds(new Rectangle(485, 503, 284, 22));
    jTextField11.setBounds(new Rectangle(726, 467, 43, 22));
    jTextField11.setText("");
    jTextField11.setSelectionStart(11);
    jTextField12.setBounds(new Rectangle(673, 467, 34, 22));
    jTextField12.setText("");
    jTextField12.setSelectionStart(11);
    jButton12.setText("start");
    jButton12.addActionListener(jsmsbh2);
    jButton12.setBounds(new Rectangle(485, 533, 112, 22));
    jTextField13.setBounds(new Rectangle(550, 467, 38, 22));
    jTextField13.setText("");
    jTextField13.setSelectionStart(11);
    jButton13.setText("switch");
    jButton13.addActionListener(jsmsbh2);
    jButton13.setBounds(new Rectangle(485, 588, 112, 22));
    jLabel6.setBounds(new Rectangle(621, 459, 19, 39));
    jLabel6.setText("/");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 24));
  
    this.getContentPane().add(jLabel1, null);
    
    this.getContentPane().add(jComboBox1, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jComboBox2, null);
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jButton4, null);
    this.getContentPane().add(jTextField2, null);
    this.getContentPane().add(jPanel1, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jPanel2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jTextField4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jTextField5, null);
    this.getContentPane().add(jButton6, null);
    this.getContentPane().add(jTextField6, null);
    this.getContentPane().add(jTextField7, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jTextField8, null);
    this.getContentPane().add(jTextField9, null);
    //this.getContentPane().add(jTextField3, null);
    this.getContentPane().add(jButton5, null);
    this.getContentPane().add(jButton7, null);
    this.getContentPane().add(jButton8, null);
    this.getContentPane().add(jButton9, null);
    this.getContentPane().add(jButton11, null);
    this.getContentPane().add(jTextField10, null);
    this.getContentPane().add(jTextField13, null);
    this.getContentPane().add(jLabel6, null);
    this.getContentPane().add(jTextField12, null);
    this.getContentPane().add(jTextField11, null);
    this.getContentPane().add(jButton12, null);
    this.getContentPane().add(jButton10, null);
    this.getContentPane().add(jButton13, null);
    this.pack();
    this.setVisible(true);
  }












}
class JSetupMazeSquareButtonHandler1 extends JFrame implements java.awt.event.ActionListener{
  JSetupFrame jsp;

  JSetupMazeSquareButtonHandler1(JSetupFrame js){
    jsp = js;
  }




  public void actionPerformed(ActionEvent e){

    int mazeindex = jsp.jComboBox2.getSelectedIndex();
    Maze currentmaze = (Maze)jsp.cl1mazes.elementAt(mazeindex);
    int x1 = -5000;
    int y1 = -5000;
    int x2 = -5000;
    int y2 = -5000;

    try {
      x1 = Integer.parseInt(jsp.jTextField6.getText());
      y1 = Integer.parseInt(jsp.jTextField7.getText());
      x2 = Integer.parseInt(jsp.jTextField8.getText());
      y2 = Integer.parseInt(jsp.jTextField9.getText());

    }catch(Exception e2){
         JOptionPane.showMessageDialog(this, "HAS TO BE NUMBERS IN TEXTFIELD","HAS TO BE NUMBERS",JOptionPane.WARNING_MESSAGE);
         return;

    }

    if(!currentmaze.squareExists(new Dimension(x1,y1))){
      JOptionPane.showMessageDialog(this, "First Square DOESNT EXIST","SQUARE DOESNT EXIST",JOptionPane.WARNING_MESSAGE);
      return;
    }
    // check if first one is square


    if(e.getSource().equals(jsp.jButton6)){
      if(!currentmaze.squareExists(new Dimension(x2,y2))){
        JOptionPane.showMessageDialog(this, "Second Square DOESNT EXIST","SQUARE DOESNT EXIST",JOptionPane.WARNING_MESSAGE);
        return;
      }
     currentmaze.toggleLink(new Dimension(x1,y1),new Dimension(x2,y2));
     jsp.jPanel1.repaint();

    }
    else if(e.getSource().equals(jsp.jButton7)){
       currentmaze.changeStartPosTo(new Dimension(x1,y1));
       jsp.jPanel1.repaint();

    }
    else if(e.getSource().equals(jsp.jButton8)){
       currentmaze.changeFinishPosTo(new Dimension(x1,y1));
       jsp.jPanel1.repaint();

    }
    else if(e.getSource().equals(jsp.jButton9)){
        currentmaze.toggleSwitch(new Dimension(x1,y1));
        jsp.jPanel1.repaint();
    }


  }
}

class JSetupMazeSquareButtonHandler2 extends JFrame implements java.awt.event.ActionListener{
  JSetupFrame jsp;

  JSetupMazeSquareButtonHandler2(JSetupFrame js){
    jsp = js;
  }




  public void actionPerformed(ActionEvent e){

    int mazeindex = jsp.jComboBox2.getSelectedIndex();
    Maze currentmaze = (Maze)jsp.cl2mazes.elementAt(mazeindex);
    int x1 = -5000;
    int y1 = -5000;
    int x2 = -5000;
    int y2 = -5000;

    try {
      x1 = Integer.parseInt(jsp.jTextField10.getText());
      y1 = Integer.parseInt(jsp.jTextField13.getText());
      x2 = Integer.parseInt(jsp.jTextField12.getText());
      y2 = Integer.parseInt(jsp.jTextField11.getText());

    }catch(Exception e2){
         JOptionPane.showMessageDialog(this, "HAS TO BE NUMBERS IN TEXTFIELD","HAS TO BE NUMBERS",JOptionPane.WARNING_MESSAGE);
         return;

    }

    if(!currentmaze.squareExists(new Dimension(x1,y1))){
      JOptionPane.showMessageDialog(this, "First Square DOESNT EXIST","SQUARE DOESNT EXIST",JOptionPane.WARNING_MESSAGE);
      return;
    }
    // check if first one is square


    if(e.getSource().equals(jsp.jButton11)){
      if(!currentmaze.squareExists(new Dimension(x2,y2))){
        JOptionPane.showMessageDialog(this, "Second Square DOESNT EXIST","SQUARE DOESNT EXIST",JOptionPane.WARNING_MESSAGE);
        return;
      }
     currentmaze.toggleLink(new Dimension(x1,y1),new Dimension(x2,y2));
     jsp.jPanel2.repaint();

    }
    else if(e.getSource().equals(jsp.jButton12)){
       currentmaze.changeStartPosTo(new Dimension(x1,y1));
       jsp.jPanel2.repaint();

    }
    else if(e.getSource().equals(jsp.jButton10)){
       currentmaze.changeFinishPosTo(new Dimension(x1,y1));
       jsp.jPanel2.repaint();

    }
    else if(e.getSource().equals(jsp.jButton13)){
        currentmaze.toggleSwitch(new Dimension(x1,y1));
        jsp.jPanel2.repaint();
    }


  }
}













class JSetupFrameEventHandler extends JFrame implements java.awt.event.ActionListener{
  JSetupFrame jsp;
  int mode = 0 ; // 0 = creating new mazeset
                 // 10= editing

  JSetupFrameEventHandler(JSetupFrame jset){
    jsp =jset;
  }
  
  String name="unset";
  
  public void actionPerformed(ActionEvent e){
     int mazeindex;
     
     if(e.getSource().equals(jsp.jButton00)){
         final JFileChooser fc = new JFileChooser();
         fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         int returnVal = fc.showOpenDialog(jsp);
         File mazePairDir = fc.getSelectedFile();
         
         try{
           name = mazePairDir.getCanonicalPath();
         }catch(Exception ee){
             ee.printStackTrace();
         }
         jsp.cl1mazes = jsp.stpIO.getClient1MazesFromNameASTEXT(name,false);
         jsp.cl2mazes = jsp.stpIO.getClient2MazesFromNameASTEXT(name,false);
         jsp.jComboBox2.removeAllItems();
         System.err.println("MAZESIZE:"+jsp.cl2mazes.size());
         for (int i = 0; i < jsp.cl1mazes.size(); i++) {
           jsp.jComboBox2.addItem("" + i);
         }
         mazeindex = 0;
         jsp.jPanel1.maz = (Maze) jsp.cl1mazes.elementAt(0);
         jsp.jPanel2.maz = (Maze) jsp.cl2mazes.elementAt(0);
         jsp.jPanel1.repaint();
         jsp.jPanel2.repaint();
         
         
     }
     else if(e.getSource().equals(jsp.jComboBox1)){
       mode = 10;
       if(jsp.jComboBox1.getItemCount()>0){
         String name = (String) jsp.jComboBox1.getSelectedItem();
         jsp.cl1mazes = jsp.stpIO.getClient1MazesFromNameASTEXT(name,true);
         jsp.cl2mazes = jsp.stpIO.getClient2MazesFromNameASTEXT(name,true);
         jsp.jComboBox2.removeAllItems();
         for (int i = 0; i < jsp.cl1mazes.size(); i++) {
           jsp.jComboBox2.addItem("" + i);
         }
         mazeindex = 0;
         jsp.jPanel1.maz = (Maze) jsp.cl1mazes.elementAt(0);
         jsp.jPanel2.maz = (Maze) jsp.cl2mazes.elementAt(0);
         jsp.jPanel1.repaint();
         jsp.jPanel2.repaint();

  }



     }
     else if(e.getSource().equals(jsp.jComboBox2)){
       mazeindex = jsp.jComboBox2.getSelectedIndex();
        if(mazeindex==-1) mazeindex=0;
        if(jsp.cl1mazes.size()>0){
          jsp.jPanel1.maz = (Maze)jsp.cl1mazes.elementAt(mazeindex);
          jsp.jPanel2.maz = (Maze)jsp.cl2mazes.elementAt(mazeindex);
          jsp.jPanel1.repaint();
          jsp.jPanel2.repaint();
        }
        else {
          jsp.jPanel1.maz=new Maze(0);
          jsp.jPanel2.maz=new Maze(0);
          jsp.jPanel1.repaint();
          jsp.jPanel2.repaint();
        }


     }
     else if(e.getSource().equals(jsp.jButton1)){
       mazeindex = jsp.jComboBox2.getSelectedIndex();
       if(mazeindex+1<jsp.cl1mazes.size()){
         Maze tempMaze1 = (Maze)jsp.cl1mazes.elementAt(mazeindex);
         Maze tempMaze2 = (Maze)jsp.cl2mazes.elementAt(mazeindex);
         jsp.cl1mazes.remove(tempMaze1);
         jsp.cl2mazes.remove(tempMaze2);
         jsp.cl1mazes.insertElementAt(tempMaze1,mazeindex+1);
         jsp.cl2mazes.insertElementAt(tempMaze2,mazeindex+1);
         jsp.jComboBox2.setSelectedIndex(mazeindex+1);
       }

     }
     else if(e.getSource().equals(jsp.jButton2)){
       mazeindex = jsp.jComboBox2.getSelectedIndex();
        if(mazeindex-1>=0){
          Maze tempMaze1 = (Maze)jsp.cl1mazes.elementAt(mazeindex);
          Maze tempMaze2 = (Maze)jsp.cl2mazes.elementAt(mazeindex);
          jsp.cl1mazes.remove(tempMaze1);
          jsp.cl2mazes.remove(tempMaze2);
          jsp.cl1mazes.insertElementAt(tempMaze1,mazeindex-1);
          jsp.cl2mazes.insertElementAt(tempMaze2,mazeindex-1);
          jsp.jComboBox2.setSelectedIndex(mazeindex-1);
        }

     }
     else if(e.getSource().equals(jsp.jButton3)){
       mazeindex = jsp.jComboBox2.getSelectedIndex();
       if (mazeindex <0 ) mazeindex = 0;
       Maze m = new Maze(Integer.parseInt(jsp.jTextField1.getText()),Integer.parseInt(jsp.jTextField2.getText()),Double.parseDouble(jsp.jTextField4.getText()),Integer.parseInt(jsp.jTextField5.getText()));
       Maze m2 = m.getClone();
       m2.selectRandomStartingPosition();
       m2.selectRandomStartingPosition();
       m2.selectRandomFinishingPosition();
       m.selectRandomStartingPosition();
       m.selectRandomFinishingPosition();
       jsp.jPanel1.maz=m;
       jsp.jPanel2.maz=m2;
       jsp.cl1mazes.insertElementAt(m,mazeindex);
       jsp.cl2mazes.insertElementAt(m2,mazeindex);
       if(jsp.jComboBox2.getItemCount()==0){
          jsp.jComboBox2.addItem("0");
       }
       else {
         jsp.jComboBox2.addItem(jsp.jComboBox2.getItemCount() + "");
       }



       jsp.jPanel1.repaint();
       jsp.jPanel2.reCalculateWidthsandSizes();
       jsp.jPanel2.repaint();

     }
     else if(e.getSource().equals(jsp.jButton4)){
       int i = jsp.jComboBox2.getSelectedIndex();
       if(jsp.cl1mazes.size()>0&i>=0){
          Maze tempMaze1 = (Maze)jsp.cl1mazes.elementAt(i);
          Maze tempMaze2 = (Maze)jsp.cl2mazes.elementAt(i);
          jsp.cl1mazes.remove(tempMaze1);
          jsp.cl2mazes.remove(tempMaze2);
          if(i==jsp.cl1mazes.size())i=i-1;
          jsp.jComboBox2.removeAllItems();
          for(int j = 0 ; j< jsp.cl1mazes.size();j++){
              jsp.jComboBox2.addItem(""+j);
          }
          jsp.jComboBox2.setSelectedIndex(i);

        }


     }
     else if(e.getSource().equals(jsp.jButton5)){
       if (jsp.cl1mazes.size()<1){
          JOptionPane.showMessageDialog(this, "Can't save empty mazes");
          //return;
       }  
       final JFileChooser fc = new JFileChooser(name);
       
       
       fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       int retVal = fc.showSaveDialog(jsp);
       File f = fc.getSelectedFile();
       
       
       
       String fullFileName ="";
       if(!f.exists()){
           try{
              f.createNewFile();
             
           }catch (Exception e2){
               e2.printStackTrace();
               CustomDialog.showDialog("SOME ERROR: CAN'T CREATE THE DIRECTORY");
               return;
           }  
       }
       
       try{
             fullFileName =  f.getCanonicalPath();
           }catch (Exception e2){
               e2.printStackTrace();
               
      } 
       
       System.err.println("THE NAME IS:"+fc.getSelectedFile().getName());
       try{System.err.println("THE FOLDER IS:"+fc.getCurrentDirectory().getCanonicalPath());}catch (Exception eee){eee.printStackTrace();}
       System.err.println("THE FULLFILENAME IS:"+fullFileName);
       
       Date d = new Date();
       d.getTime();
       
       
       String longerFileName = fullFileName+File.separator+d.getTime();
               
          jsp.stpIO.saveClientMazesFromSetupNo(this.jsp.cl1mazes,this.jsp.cl2mazes,longerFileName);
          //jsp.jTextField3.setText("");
          //jsp.jComboBox1.removeAllItems();
          //Vector v = jsp.stpIO.getListOfMazeConfigs();
          //for(int i = 0 ; i < v.size();i++){
          //   jsp.jComboBox1.addItem(v.elementAt(i));
          //}
          //jsp.jComboBox2.removeAllItems();
          //jsp.jPanel1.maz=new Maze(0);
          //jsp.jPanel2.maz=new Maze(0);
          //jsp.jPanel1.repaint();
          //jsp.jPanel2.repaint();



      

     }



  }

 
  
  
}

