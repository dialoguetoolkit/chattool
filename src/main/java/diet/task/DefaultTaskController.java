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
 * @author user
 */
public class DefaultTaskController implements TaskControllerInterface {

    
    public void initialize(){
        //This is called once max. number of participants has been reached.
    }
    
    public void participantJoinedConversation_StartingTask(Participant pJoined){
        
    }
    
    public void processTaskMove(MessageTask mtm, Participant origin){
     
    }
    
    /**
     * This method is called from Conversation.closeDown() Any threads instantiated in a custom
     * TaskController object should be stoppable using this method.
     */
    public void closeDown(){
        
    }

    @Override
    public JPanel getJPanel(JPanel jp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
