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

public class MessageEndOfGame extends MessageTask implements Serializable{

    public MessageEndOfGame(String email, String userName)
    {
        super(email, userName);
    }
}
