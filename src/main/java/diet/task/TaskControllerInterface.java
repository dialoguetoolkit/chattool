/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task;

import diet.message.MessageTask;
import diet.server.Participant;
import javax.swing.JPanel;

/**
 *
 * @author sre
 */
public interface TaskControllerInterface {
    
    
    //public void participantJoinedConversation_StartingTask(Participant pJoined);
    
    public void processTaskMove(MessageTask mtm, Participant origin);
    
      public JPanel getJPanel(JPanel jp);
    
    public void closeDown();
    
  
    
    
}
