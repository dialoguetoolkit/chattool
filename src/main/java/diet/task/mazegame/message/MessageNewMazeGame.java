/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame.message;

import java.io.Serializable;

import diet.task.mazegame.Game;
import diet.message.MessageTask;

/**
 *
 * @author user
 */
public class MessageNewMazeGame extends MessageTask implements Serializable{

    Game gm;
    
    public MessageNewMazeGame(String email, String username,Game g) {
       super(email,username);
       this.gm=g;
    }
    
    public Game getGame(){
        return gm;
    }
}
