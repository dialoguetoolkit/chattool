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
public class MessageNextTangramPlaced extends MessageTask implements Serializable{
    
    int sourceSlotID;
    int targetSlotID;
    int activeDropSlot;
    boolean directorMoveOn;
    String tangramName;
    
    public MessageNextTangramPlaced(String email, String userName, int activeDropSlot, int sourceSlotID, int targetSlotID, boolean directorMoveOn, String tangramName)
    {
        super(email,userName);
        this.sourceSlotID=sourceSlotID;
        this.targetSlotID=targetSlotID;
        this.activeDropSlot=activeDropSlot;
        this.tangramName=tangramName;
        this.directorMoveOn=directorMoveOn;
        
    }
    public int getSourceSlotID()
    {
        return sourceSlotID;
    }
    public int getTargetSlotID()
    {
        return targetSlotID;
    }
    public int getActiveDropSlot()
    {
        return activeDropSlot;
    }
    public String getTangramName()
    {
        return tangramName;
    }
    public boolean directorMoveOn()
    {
        return this.directorMoveOn;
    }

}
