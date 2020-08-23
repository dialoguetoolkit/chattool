package diet.task.tangram2D1M;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class DirectorTangramPanel extends TangramPanel implements MouseMotionListener, MouseListener{
    
    public final static int horiDistance=10;//pixels between slots
    
    public final static int vertiDistanceSourceTarget=30;
    public final static int vertiDistance=20;
    public final static int yOfSourceSlots=vertiDistanceSourceTarget+Slot.clientHeight+vertiDistance;
    public final static int slotCount=12;
    private char whichDirector;//'A' or 'B'
    private TangramSequence sequence;
    
    
    

   
 
    
    public DirectorTangramPanel(DirectorTangramFrame containingFrame, TangramSequence seq, char whichDirector, Vector<SerialTangram> v, String tangramSetName) throws IOException
    {
        super(containingFrame, v, tangramSetName);//containingFrame, v, tangramSetName);
        
        this.whichDirector=whichDirector;
        this.sequence=seq;
        curTangramSetName=tangramSetName;
      
        addFilledSlots();
        
            ((DirectorSlot)components.elementAt(0)).enabled=true;
            curSlotToBeRevealed=0;
        
       
        this.addMouseListener(this);
        
        
    }
    //this should get another set of tangrams and load them
    
    
    
    
    int curSlotToBeRevealed;
    public void updateStatusTangramPlaced(int activeDropSlot, boolean directorMoveOn)
    {
        if (activeDropSlot==slotCount-1&&directorMoveOn) return;
        if (directorMoveOn)
        {
            if (curSlotToBeRevealed==activeDropSlot) curSlotToBeRevealed++;
            ((DirectorSlot)components.elementAt(curSlotToBeRevealed)).enabled=true;
            ((DirectorSlot)components.elementAt(curSlotToBeRevealed-1)).enabled=false;
            
            
        }
        /*
        else
        {
            ((DirectorSlot)components.elementAt(curSlotToBeRevealed)).enabled=false;
            
        }*/
        
        SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    repaint();
                
                }
            
            });
        
    }

    public void updateStatusTangramRevealed(int slotID)
    {
        if (!components.elementAt(slotID).isEmpty())
        {
            ((DirectorSlot)components.elementAt(slotID)).showImage=true;
            SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                repaint();
                
                }
            
            });
            
        }


    }
    
    public void loadNext(Vector<SerialTangram> v1, String setName, TangramSequence sequence, char whichDirector)
    {
       
        Vector<Tangram> v=getTangramsFromSerialTangrams(v1);
        if (v==null) {
            System.out.println("null tangram vector in loadNext");
            System.exit(1);
        }
        components=new Vector<Slot>();
        this.sequence=sequence;
        this.whichDirector=whichDirector;
        
        curTangramSetName=setName;
        curTangrams=(Vector<Tangram>)v.clone();
        
        System.out.println("Current set is "+curTangramSetName);
        addFilledSlots();
        
            ((DirectorSlot)components.elementAt(0)).enabled=true;
            this.curSlotToBeRevealed=0;
       
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                repaint();
                
            }
            
        });
        
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

                    if (sequence.whichDirectors(index).indexOf(whichDirector)>=0)
                    {

                    
                        Tangram img = curTangrams.elementAt(index);
                        //System.out.println("Image at:"+index);
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
                j+=(horiDistance+Slot.clientWidth);             
            }
            m+=(vertiDistance+Slot.clientHeight);
            j=horiDistance;
            
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
    
    public void mouseClicked(MouseEvent e){
    
        int x=e.getX();
        int y=e.getY();        
        
        int slotClicked=findSlotClicked(x,y);
        
        System.out.println("mouse clicked on:"+slotClicked);
        if (slotClicked>=0)
        {
            DirectorSlot clicked=(DirectorSlot)components.elementAt(slotClicked);
            if (clicked.tangram==null) return;
            else if (clicked.enabled) 
            {
                //Show image and send message to enable corresponding matcher slot
                clicked.showImage=true;
                containingFrame.comms.tangramRevealed(slotClicked, clicked.tangram.name);
            }
            else JOptionPane.showMessageDialog(this, new String("Matcher is not finished with the previous tangrams yet."));
            
            SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                repaint();
                
                }
            
            });
        }
        else
        {
            //JOptionPane.showMessageDialog(this, new String("You cannot reveal this tangram yet."));
            return;
        }
        
        
    }
    
    private int findSlotClicked(int x, int y)
    {
        for(int i=0;i<components.size();i++)
        {
            Slot cur=components.elementAt(i);
           
            if ( (x>cur.x) && (x<(cur.x + Slot.clientWidth - Slot.clientImageBorder)) 
                    && (y>cur.y) && (y<cur.y + Slot.clientHeight-Slot.clientImageBorder))
            {
                return i;
                
            
            }
            
        }
        return -1;
        
    }
          
    public void	mouseEntered(MouseEvent e){};
          
    public void	mouseExited(MouseEvent e){};
    
    
    public void mousePressed(MouseEvent e)
    {
       
        
        
    }
    
    public void	mouseReleased(MouseEvent e) 
    {
        
        
    }
    
   
    
   
    

}
