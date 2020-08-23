/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

/**
 *
 * @author Arash
 */
import javax.swing.JFrame;

public abstract class TangramFrame extends JFrame{
    
    ClientTangramGameComms comms;
    TangramPanel mainPanel;
    
    public TangramFrame()
    {
        super();
        //this.setEnabled(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //this.setName(comms.cts.getUsername());
        
    }
    
    public void setComms(ClientTangramGameComms c)
    {
        comms=c;
    }
    protected abstract int[] computeWidthHeightOfFrame();
    
    public void enableFrame()
    {
        this.setEnabled(true);
        
    }
    public void disableFrame()
    {
        this.setEnabled(false);
    }
    public void updateStatusTangramPlaced(int id, boolean directorMoveOn)
    {
        mainPanel.updateStatusTangramPlaced(id, directorMoveOn);
    }
    public void updateStatusTangramRevealed(int slotID)
    {
        mainPanel.updateStatusTangramRevealed(slotID);
    }
   

}
