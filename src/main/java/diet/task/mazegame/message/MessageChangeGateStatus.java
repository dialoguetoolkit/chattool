/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame.message;

import java.io.Serializable;

import diet.message.MessageTask;

/**
 *
 * @author user
 */
public class MessageChangeGateStatus extends MessageTask implements Serializable{
    
   boolean openGates;
   String recipient;
    
   public MessageChangeGateStatus(String email, String username,boolean openTheGates, String recipients) {
      super(email,username);
      openGates=openTheGates;
  }

  public boolean gatesAreOpen() {
     return openGates;
  }

  
  public String getRecipient(){
      return recipient;
  }
   
}
