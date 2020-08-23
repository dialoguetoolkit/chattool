/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame.message;

import diet.task.mazegame.Maze;
import java.io.Serializable;

import diet.message.MessageTask;

/**
 *
 * @author user
 */
public class MessageNextMaze_SentAsMaze extends MessageTask implements Serializable{

    Maze nextMaze;
    
    public MessageNextMaze_SentAsMaze(String email, String username,Maze m) {
      super(email,username);
      this.nextMaze=m;
    }
    
  public Maze getNextMaze(){
    return nextMaze;
  }

}
