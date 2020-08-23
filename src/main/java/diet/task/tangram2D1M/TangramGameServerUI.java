/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Arash
 */
public class TangramGameServerUI extends JFrame{
    
    //TangramGameController tgc;
    JTabbedPane jtp = new JTabbedPane();
    
    MatcherServerPanel msp;
    DirectorServerPanel dspA;
    DirectorServerPanel dspB;
    
    public TangramGameServerUI(){
        //this.tgc=tgc;
        jtp.setVisible(true);
        this.getContentPane().add(jtp);
        int[] dimension=this.computeWidthHeightOfFrame();
        this.setSize(dimension[0], dimension[1]);
        this.setTitle("Tangram Game Monitor");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
    }
    protected int[] computeWidthHeightOfFrame()
    {
        //assuming that we want the empty slots to be ALWAYS arranged in 2 rows
        int w;
        w=Slot.serverWidth*MatcherServerPanel.emptySlotCount / 2 + MatcherServerPanel.horiDistance*(MatcherServerPanel.emptySlotCount/2+2);
        int h=Slot.serverHeight*(2+3) + MatcherServerPanel.vertiDistanceSourceTarget + MatcherServerPanel.vertiDistance*(4+2)+50;
        int[] result={w,h};
        System.out.println("width is "+w+" and height is "+h);
        return result;
    }
    
    public void initializeJTabbedPane( final String matcherName, final String directorAName, final String directorBName
            ,final TangramSequence curSeq, final Vector<Tangram> matcherTangrams, final Vector<Tangram> directorTangrams)
    {
        
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                  
                  
                  
                  
                  jtp.removeAll();
                  System.out.println("Sequence is:"+curSeq+" initialising server tabbed pane");
                  msp = new MatcherServerPanel(matcherName, matcherTangrams);
                  dspA = new DirectorServerPanel(directorAName, curSeq, 'A', directorTangrams);
                  dspB = new DirectorServerPanel(directorBName, curSeq, 'B', directorTangrams);
                  jtp.insertTab(matcherName, null, msp, null, 0);
                  jtp.insertTab(directorAName, null, dspA, null, 1);
                  jtp.insertTab(directorBName, null, dspB, null, 2);
              }    
           }); 
        }catch(Exception e){
            System.err.println("COULD NOT INITIALIZE TABBED PANE OF MAZES ON SERVER SIDE");
        }    
    }
    
    public void updateStatusTangramRevealed(int slotID)
    {
        msp.updateStatusTangramRevealed(slotID);
        dspA.updateStatusTangramRevealed(slotID);
        dspB.updateStatusTangramRevealed(slotID);
        
        
    }
    public void updateStatusTangramPlaced(int sourceSlotID, int targetSlotID, boolean directorMoveOn)
    {
        msp.updateStatusTangramPlaced(sourceSlotID, targetSlotID);
        
        dspA.updateStatusTangramPlaced(targetSlotID, directorMoveOn);
        dspB.updateStatusTangramPlaced(targetSlotID, directorMoveOn);
        
    }
    
   
    
    
    
    
    
    

}
