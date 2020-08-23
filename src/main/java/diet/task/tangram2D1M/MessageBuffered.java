/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import diet.message.MessageChatTextFromClient;
import diet.server.Participant;

/**
 *
 * @author Arash
 */
public class MessageBuffered {
    
    public Participant sender;
    public MessageChatTextFromClient mct;
    
    public MessageBuffered(Participant sender, MessageChatTextFromClient mct)

    {
        this.sender=sender;
        this.mct=mct;
    }
}
