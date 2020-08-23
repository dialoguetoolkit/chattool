/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;
import diet.server.Participant;
/**
 *
 * @author Arash
 */



public class TangramUtterance {
    
    public String utterance;
    public int slotUD; //slot under discussion
    public Participant origin;
    
    public TangramUtterance(String utterance, Participant origin, int slotID)
    {
        this.utterance=utterance;
        this.origin=origin;
        this.slotUD=slotID;
    }

}
