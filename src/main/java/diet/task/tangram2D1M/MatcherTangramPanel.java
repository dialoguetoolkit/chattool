package diet.task.tangram2D1M;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MatcherTangramPanel extends TangramPanel implements MouseMotionListener, MouseListener{
    
   
   
    
    
    
    public final static int horiDistance=10;//pixels between slots
    
    public final static int vertiDistanceSourceTarget=30;
    public final static int vertiDistance=20;
    public final static int yOfSourceSlots=vertiDistanceSourceTarget+Slot.clientHeight+vertiDistance;
    public final static int emptySlotCount=12;
    public final static int distractorItemCount=3; //Shouldn't be more than emptySlotCount/2. will fail otherwise
    
    //private TangramLoader tl;
 
    
    private int currentActiveDropSlot;
 
    private Random r=new Random();
    
    public MatcherTangramPanel(MatcherTangramFrame containingFrame, Vector<SerialTangram> v, String setName) throws IOException
    {
        super(containingFrame, v,setName);
        
        this.currentActiveDropSlot=-1;
        
        
        curTangramSetName=setName;
        System.out.println("Current set is "+curTangramSetName);
        addEmptySlots();
        addFilledSlots();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        
    }
    
    public void loadNext(Vector<SerialTangram> v2, String setName, TangramSequence sequence, char whichDirector)
    {
        
        Vector<Tangram> v=getTangramsFromSerialTangrams(v2);
        if (v==null) {
            System.out.println("null vector in load next in MatcherTangramPanel"
                    );
            System.exit(1);
        }
        components=new Vector<Slot>();
        currentActiveDropSlot=0;
        curTangrams=(Vector<Tangram>)v.clone();
        curTangramSetName=setName;
        //System.out.println("Current set is "+curTangramSetName);
        addEmptySlots();
        addFilledSlots();
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                
                repaint();
                
            }
        });
        
        
    }
    
    private void addEmptySlots()
    {
       // System.out.println("Width of panel is " + this.getWidth());
        int j=horiDistance;
        int m=vertiDistance;
        for(int l=0;l<2;l++){
            
            for(int i=0;i<emptySlotCount/2;i++)
            {
                int slotID=(l*emptySlotCount/2)+i+1;
                Slot cur=new MatcherSlot(null,slotID,j,m);
                components.add(cur);
                j+=(horiDistance+Slot.clientWidth);
            }
            m+=(vertiDistance+Slot.clientHeight);
            j=horiDistance;
            
        }
        
        
      
    }
    public void updateStatusTangramPlaced(int slotID, boolean directorMoveOn)
    {
        
        
    }
    public void updateStatusTangramRevealed(int slotID)
    {
        components.elementAt(slotID).enabled=true;
        if (slotID>0) components.elementAt(slotID-1).enabled=false;
        this.currentActiveDropSlot=slotID;
        //System.out.println(slotID+" enabling");
        
        
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                
                repaint();
                
            }
        });
    }
    //this should be called once and only once in the constructor
    
    private void addFilledSlots()
    {
        
        Vector<Tangram> tangrams=(Vector<Tangram>)curTangrams.clone();
        int counter=0;
        int j=horiDistance;
        int m=2*vertiDistance+2*Slot.clientHeight+vertiDistanceSourceTarget;
        for(int l=0;l<2;l++){
            
            for(int i=0;i<emptySlotCount/2;i++)
            {
                
                
                if (counter<tangrams.size())
                {
                    Tangram img = tangrams.elementAt(counter); 
                    Slot cur=new MatcherSlot(img,j,m);
                    components.add(cur);
                    
                    
                                
                    
                }
                else
                {
                    System.out.println("ran out of images");
                    Slot cur=new MatcherSlot(null,j,m);
                    components.add(cur);
                    
                }
                j+=(horiDistance+Slot.clientWidth);
                counter++;
            }
            m+=(vertiDistance+Slot.clientHeight);
            j=horiDistance;
            
        }
        for(int o=0;o<distractorItemCount;o++)
        {
            if (counter<tangrams.size())
                {
                    
                    Tangram img = tangrams.elementAt(counter);
                    Slot cur=new MatcherSlot(img,j,m);
                    components.add(cur);
                    
                    
                    
                }
                else
                {
                    System.out.println("ran out of images on distractors");
                    Slot cur=new MatcherSlot(null,j,m);
                    components.add(cur);
                    
                }
                j+=(horiDistance+Slot.clientWidth); 
                counter++;
            
        }
        
    }
    
    
   
    
   
   
    
    
    //Mouse Listener:
    
    public void mouseMoved(MouseEvent e)
    {
        //System.out.println("("+e.getX()+","+e.getY()+")");
        
    }
    public void mouseDragged(MouseEvent e)
    {
        //System.out.println("("+e.getX()+","+e.getY()+")");
        
        
    }
    public void mouseClicked(MouseEvent e){};
          
    public void	mouseEntered(MouseEvent e){};
          
    public void	mouseExited(MouseEvent e){};
    
    private int curDraggedSlotIndex=-1;
    public void mousePressed(MouseEvent e)
    {
        //System.out.println("Mouse pressed!");
        int x=e.getX();
        int y=e.getY();
        setDraggedSlotIndex(x,y);
        if (curDraggedSlotIndex<0) return;
        else if (components.elementAt(curDraggedSlotIndex).isEmpty()||(this.curDraggedSlotIndex<this.emptySlotCount&&this.curDraggedSlotIndex>=this.currentActiveDropSlot))
        {
            curDraggedSlotIndex=-1;
        }
        
        
    }
    private int curDroppedOnSlotIndex=-1;
    
    public void	mouseReleased(MouseEvent e) 
    {
        System.out.println("Mouse released at:"+e.getX()+" "+e.getY());
        if (curDraggedSlotIndex<0) {
            //System.out.println("image index less 0");
            return;
        }
        int x=e.getX();
        int y=e.getY();
        setCurDroppedOnSlotIndex(x, y);
        if (curDroppedOnSlotIndex<0) 
        {
            
            //ClientTangramMessageOutgoing.tangramReleased(curDraggedSlotIndex, curDroppedOnSlotIndex, successful);
            return;
        }
        else
        {
            if (!components.elementAt(curDroppedOnSlotIndex).isEmpty())
            {
                //JOptionPane.showMessageDialog(this, new String("Slot not empty!"));
                
                curDroppedOnSlotIndex=-1;
                curDraggedSlotIndex=-1;
                return;
            }
            
            else if (curDroppedOnSlotIndex>this.currentActiveDropSlot
                    &&
                    curDroppedOnSlotIndex<emptySlotCount)
            {
                
                
                if (this.currentActiveDropSlot<0)
                    JOptionPane.showMessageDialog(this, new String("Directors need to start the game."));
                                  
                curDroppedOnSlotIndex=-1;
                curDraggedSlotIndex=-1;
                return;
            }
            else
            {
            
                Slot draggedSlot=components.elementAt(curDraggedSlotIndex);
                Slot droppedOnSlot=components.elementAt(curDroppedOnSlotIndex);
                droppedOnSlot.tangram=draggedSlot.tangram;
                draggedSlot.tangram=null;
                //sending message to enable directors to reveal next tangram
                //System.out.println("tangram moved from: "+ curDraggedSlotIndex + "to: "+ curDroppedOnSlotIndex+". Name is:"+droppedOnSlot.tangram.name);
                containingFrame.comms.tangramPlaced(currentActiveDropSlot, curDraggedSlotIndex, curDroppedOnSlotIndex, allPreviousTangramsInPlace(currentActiveDropSlot), droppedOnSlot.tangram.name);
                
                
                
                SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                
                    repaint();
                
                }
                });
                
                if (allPreviousTangramsInPlace(this.emptySlotCount-1))
                {
                    //JOptionPane.showMessageDialog(this, "Well Done! Moving to next set");
                    //requesting next set of tangrams
                    containingFrame.comms.endOfCurrentTangramSet();
                }
                curDroppedOnSlotIndex=-1;
                curDraggedSlotIndex=-1;
            }
        }
        
        
        
    }
    
    private boolean allPreviousTangramsInPlace(int slotID)
    {
        
        for(int i=0;i<=slotID;i++)
        {
            if (components.elementAt(i).isEmpty()) return false;
        }
        return true;
        
    }
    
    private void setCurDroppedOnSlotIndex(int x, int y)
    {
        //System.out.println("SetcurDropped called"+x+" "+y);
        for(int i=0;i<components.size();i++)
        {
            Slot current=(Slot)components.elementAt(i);
            
            
            
            if ( (x>current.x) && (x<current.x+current.clientWidth) 
                    && (y>current.y) && (y<current.y+current.clientHeight))
                
            {
                //System.out.println("Dropped on index="+i);
                curDroppedOnSlotIndex=i;
                return;
            }
            
        }
        //System.out.println("returns -1");
        curDroppedOnSlotIndex=-1; 
        
    }
    private void setDraggedSlotIndex(int x, int y)
            
    {
        //System.out.println("setDraggedImage called.");
        for(int i=0;i<components.size();i++)
        {
            Slot cur=components.elementAt(i);
           
            if ( (x>cur.x) && (x<(cur.x + Slot.clientWidth - Slot.clientImageBorder)) 
                    && (y>cur.y) && (y<cur.y + Slot.clientHeight-Slot.clientImageBorder))
            {
                curDraggedSlotIndex=i;
                //System.out.println("Dragged Image Index="+i);
                return;
            
            }
            
        }
     
        curDraggedSlotIndex=-1;   
    }
    

}
