package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
import java.io.IOException;
import java.util.Vector;

public class MatcherTangramFrame extends TangramFrame{

   
    
    
    
    
    protected int[] computeWidthHeightOfFrame()
    {
        //assuming that we want the empty slots to be ALWAYS arranged in 2 rows
        int w;
        w=Slot.clientWidth*MatcherTangramPanel.emptySlotCount / 2 + MatcherTangramPanel.horiDistance*(MatcherTangramPanel.emptySlotCount/2+1);
        int h=Slot.clientHeight*(2+3) + MatcherTangramPanel.vertiDistanceSourceTarget + MatcherTangramPanel.vertiDistance*(4+2)+50;
        int[] result={w,h};
        System.out.println("width is "+w+" and height is "+h);
        return result;
    }
    public MatcherTangramFrame(Vector<SerialTangram> v, String tangramSetName) throws IOException
    {
        super();
        mainPanel=new MatcherTangramPanel(this, v, tangramSetName);
        //mainPanel.setSize(800,500);
        getContentPane().add(mainPanel);
        int[] dimension=this.computeWidthHeightOfFrame();
        this.setSize(dimension[0], dimension[1]);
        this.setResizable(false);
        
    }
    
    
    
    

}
