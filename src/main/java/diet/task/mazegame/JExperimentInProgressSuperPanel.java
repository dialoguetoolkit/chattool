/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

/**
 *
 * @author user
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;



public class JExperimentInProgressSuperPanel extends JPanel{
  private JMazePanel2 jPanel2 ;
  Vector mazes = new Vector();
  Maze currentmaze;
  JLabel jLabel1 = new JLabel();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel2 = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();

  public JExperimentInProgressSuperPanel(String name,Vector mazeslist) {
    mazes = mazeslist;
    currentmaze = (Maze)mazeslist.elementAt(0);
    jPanel2 = new JMazePanel2();
    jPanel2.changeMaze(currentmaze);
    jPanel2.repaint();

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void setMazes(Vector v){
      mazes = v;
  }
  
  
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jLabel1.setMinimumSize(new Dimension(34, 25));
    jLabel1.setPreferredSize(new Dimension(34, 25));
    jLabel1.setText("0 "+ " of "+ (mazes.size()-1) );
    jLabel1.setBounds(new Rectangle(4, 2, 77, 25));
    jPanel1.setLayout(null);
    jLabel2.setText("message");
    jLabel2.setBounds(new Rectangle(111, 6, 114, 18));
    jPanel1.setMinimumSize(new Dimension(400, 20));
    jPanel1.setPreferredSize(new Dimension(400, 30));
    this.add(jPanel2, BorderLayout.CENTER);
    this.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(jLabel1, null);
    jPanel1.add(jLabel2, null);
    this.repaint();
    this.setVisible(true);
  }
private void repaintmaze(){

}


 

 public void changeToMazeNo(int i){
     if(i+1<=mazes.size()){
       currentmaze = (Maze)mazes.elementAt(i);
       System.out.println("jefpaint2");
       jLabel1.setText(i+" of "+ (mazes.size()-1));
       jLabel1.repaint();

       jPanel2.changeMaze(currentmaze);
       jPanel2.resetCursorToBegin();
       jPanel2.resetCursorToBegin();
       jPanel2.maz.changeGates(false);
       jPanel2.paint(jPanel2.getGraphics());
       jPanel2.repaint();
    }
    
 }
 
public void updateCursor(Dimension d){
  jPanel2.moveCursorTo(d);
  jPanel2.painthis();
  jPanel2.paint(jPanel2.getGraphics());
  jPanel2.repaint();
}
public void changeGateStatus(boolean open){
  if(open) System.out.println("OPENING THE GATES , GARES ARE OPNEN");
  if(!open) System.out.println("CLOSING THE GATES , GARES ARE CLOSED");
  jPanel2.maz.changeGates(open);
  jPanel2.paint(jPanel2.getGraphics());
  jPanel2.repaint();
}
public void displayLabelMessage(String text){
    jLabel2.setText(text);
    jLabel2.repaint();
}

}

