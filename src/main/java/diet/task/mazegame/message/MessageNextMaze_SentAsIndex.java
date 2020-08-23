/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame.message;

import diet.task.mazegame.Game;
import java.io.Serializable;

import diet.message.MessageTask;

/**
 *
 * @author user
 */
public class MessageNextMaze_SentAsIndex extends MessageTask implements Serializable{

    int next;
    String displayText ="";
    Game g;
    
    public MessageNextMaze_SentAsIndex(String email, String username,int next, String displayMessage) {
      super(email,username);
      this.next=next;
      this.displayText=displayMessage;
    }
    
    public MessageNextMaze_SentAsIndex(String email, String username,int next, Game g, String displayMessage) {
      super(email,username);
      this.next=next;
      this.g=g;
      this.displayText=displayMessage;
    }
    
    
  public int getNext(){
    return next;
  }

    public Game getGame() {
        return g;
    }
    public String getDisplayText(){
        return displayText;
    }

}
