/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Arash
 */
public class DirectorServerPanel extends JPanel{
    
    
    
    public final static int horiDistance=5;//pixels between slots
    
    public final static int vertiDistanceSourceTarget=15;
    public final static int vertiDistance=10;
    public final static int yOfSourceSlots=vertiDistanceSourceTarget+Slot.serverHeight+vertiDistance;
    public final static int slotCount=12;
    private char whichDirector;//'A' or 'B'
    private TangramSequence sequence;
    
    public Vector<Slot> components=new Vector<Slot>();
    public Vector<Tangram> curTangrams;
    
    
    

   
 
    
    public DirectorServerPanel(String directorName, TangramSequence curSeq, char whichDirector, Vector<Tangram> v)
    {
        curTangrams=v;
        
        this.whichDirector=whichDirector;
        System.out.println("Setting seq to"+ curSeq);
        this.sequence=curSeq;
        
      
        addFilledSlots();
       
            ((DirectorSlot)components.elementAt(0)).enabled=true;
            curSlotToBeRevealed=0;
        
       
       
        
        
    }
    //this should get another set of tangrams and load them
    
  
    
    int curSlotToBeRevealed;
    public void updateStatusTangramPlaced(int slotID, boolean directorMoveOn)
    {
        if (slotID==slotCount-1) return;
        if (directorMoveOn)
        {
            if (curSlotToBeRevealed==slotID) curSlotToBeRevealed++;
            ((DirectorSlot)components.elementAt(curSlotToBeRevealed)).enabled=true;
            ((DirectorSlot)components.elementAt(curSlotToBeRevealed-1)).enabled=false;
            
            
        }
        else
        {
            ((DirectorSlot)components.elementAt(curSlotToBeRevealed)).enabled=false;
            
        }
        
        SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    repaint();
                
                }
            
            });
        
    }
    public void updateStatusTangramRevealed(int slotID){
        
        ((DirectorSlot)components.elementAt(slotID)).showImage=true;
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run(){
                repaint();
            }
            
        });
    
    
    }
    
    public void loadNext(Vector<SerialTangram> v, String setName, String sequence, char whichDirector)
    {
       
        
        if (v==null) {
            System.out.println("null tangram vector in loadNext");
            System.exit(1);
        }
        components=new Vector<Slot>();
        
        
        
        curTangrams=(Vector<Tangram>)v.clone();
        
        
        addFilledSlots();
        
            ((DirectorSlot)components.elementAt(0)).enabled=true;
            curSlotToBeRevealed=0;
       
        repaint();
        
    }
        
    
    
   //this should be called once and only once in the constructor
    
    private void addFilledSlots()
    {
        
        System.out.println("there are"+curTangrams.size()+"tangrams in vector");
        
        int j=horiDistance;
        int m=vertiDistance;
        for(int l=0;l<2;l++){
            
            for(int i=0;i<slotCount/2;i++)
            {
                int index=slotCount/2*l+i;
                boolean bothDirectors;
                if (index<curTangrams.size())
                {
                    if (sequence.whichDirectors(index).equalsIgnoreCase("AB"))
                        bothDirectors=true;
                    else bothDirectors=false;
                    //System.out.println(sequence.length());
                    if(sequence.whichDirectors(index).indexOf(whichDirector)>=0)
                    {
                    
                        Tangram img = curTangrams.elementAt(index);
                        System.out.println("Image at:"+index);
                        Slot cur=new DirectorSlot(img,index,j,m,bothDirectors);
                        components.add(cur);
                        
                    }
                    else
                    {
                         Slot cur=new DirectorSlot(null,index,j,m,false);
                         components.add(cur);
                        
                    }
                    
                    
                }
                else
                {
                    System.out.println("ran out of images");
                    Slot cur=new DirectorSlot(null,index,j,m,false);
                    components.add(cur);
                    
                }
                j+=(horiDistance+Slot.serverWidth);             
            }
            m+=(vertiDistance+Slot.serverHeight);
            j=horiDistance;
            
        }
        
        
        
        
    }
    
   
    
    
    
    
  
   
    
    
   
    
   
    


    
    
    protected void paintComponents(Graphics2D g2)
    {       
        Iterator<Slot> i=components.iterator();
        System.out.println("there are "+ components.size());
        while(i.hasNext())
        {
            Slot cur=i.next();
            cur.draw(g2, 's');
        }
        
    
       
    }
    
    protected void paintComponent(Graphics g)
    {
        System.out.println("this is called");
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        paintComponents(g2);
        
        
        
    }

}
