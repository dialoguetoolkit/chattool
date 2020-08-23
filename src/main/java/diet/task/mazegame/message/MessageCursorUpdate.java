/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame.message;

import java.awt.Dimension;
import java.io.Serializable;

import diet.message.MessageTask;

/**
 *
 * @author user
 */
public class MessageCursorUpdate extends MessageTask implements Serializable{

    public Dimension newPos;
    private boolean isASwitch=false;
    private int mazeNo;
    
    public MessageCursorUpdate(String email, String username, Dimension newPs, int mazeNo) {
      super(email,username);
      newPos=newPs;
      this.mazeNo=mazeNo;
  }
  public boolean isAswitch(){
      return isASwitch;
  }

  public void setASwitch(boolean switchstatus){
     isASwitch = switchstatus;
}
  public int getMazeNo(){
      return mazeNo;
  }
    
}
