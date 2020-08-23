package diet.task.tangram2D1M;

import java.io.IOException;
import java.util.Vector;
public class DirectorTangramFrame extends TangramFrame{

    
    
    
    
    
    protected int[] computeWidthHeightOfFrame()
    {
        //assuming that we want the empty slots to be ALWAYS arranged in 2 rows
        int w;
        w=Slot.clientWidth*DirectorTangramPanel.slotCount / 2 + DirectorTangramPanel.horiDistance*(DirectorTangramPanel.slotCount/2+1);
        int h=Slot.clientHeight*(2) + DirectorTangramPanel.vertiDistance*(4)+20;
        int[] result={w,h};
        System.out.println("width is "+w+" and height is "+h);
        return result;
    }
    
    //Which director is either 0 or 1.
    public DirectorTangramFrame(TangramSequence seq, char whichDirector, Vector<SerialTangram> v, String tangramSetName) throws IOException
    {
        super();
        //String randomSequence=RandomSeqGen.next();
        mainPanel=new DirectorTangramPanel(this, seq, whichDirector, v, tangramSetName);
        //mainPanel.setSize(800,500);
        getContentPane().add(mainPanel);
        int[] dimension=this.computeWidthHeightOfFrame();
        this.setSize(dimension[0], dimension[1]);
        this.setResizable(false);
        
    }
    
    
    
    
    

}