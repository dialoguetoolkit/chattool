/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;
/**
 *
 * @author Arash
 */



public class SlotUtterance {
    
    public String utterance;
    public int slotUD; //slot under discussion
    public int parseTimeOut;
    
    public SlotUtterance(String utterance, int slotID)
    {
        this.utterance=utterance;
        
        this.slotUD=slotID;
        this.parseTimeOut=1800;
    }
    public void increaseTimeOut(int n)
    {
        parseTimeOut+=n;
    }

}
