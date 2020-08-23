/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M.message;

/**
 *
 * @author Arash
 */


import java.io.Serializable;

import diet.message.MessageTask;

/**
 *
 * @author user
 */
public class MessageStartGame extends MessageTask implements Serializable{
    
    public MessageStartGame(String email, String username) {
       super(email,username);      
    }
}