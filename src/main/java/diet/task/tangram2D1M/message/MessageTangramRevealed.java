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

public class MessageTangramRevealed extends MessageTask implements Serializable{
    
    String tangramName;
    int slotID;
    
    
    public MessageTangramRevealed(String email, String userName, int slotID, String tangramName)
    {
        super(email, userName);
        this.tangramName=tangramName;
        this.slotID=slotID;
        
    }
    public String getTangramName()
    {
        return tangramName;
    }
    public int getSlotID()
    {
        return slotID;
        
    }

}
