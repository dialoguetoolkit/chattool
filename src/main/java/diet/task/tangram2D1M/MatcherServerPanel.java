/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Arash
 */
public class MatcherServerPanel extends JPanel{
    
    public final static int horiDistance=5;//pixels between slots
    
    public final static int vertiDistanceSourceTarget=15;
    public final static int vertiDistance=10;
    public final static int yOfSourceSlots=vertiDistanceSourceTarget+Slot.serverHeight+vertiDistance;
    public final static int emptySlotCount=12;
    public final static int distractorItemCount=3; //Shouldn't be more than emptySlotCount/2. will fail otherwise
    
    //private TangramLoader tl;
    private Vector<Slot> components=new Vector<Slot>();
    private Vector<Tangram> curTangrams;
    
    private int currentActiveDropSlot;
 
    private Random r=new Random();
    
    public MatcherServerPanel(String matcherName, Vector<Tangram> v)
    {
        
        
        this.currentActiveDropSlot=-1;
        
        
        curTangrams=v;
        addEmptySlots();
        addFilledSlots();
        
        
    }
    
    public void loadNext(Vector<Tangram> v, String setName, String sequence, char whichDirector)
    {
        
        
        if (v==null) {
            System.out.println("null vector in load next in MatcherTangramPanel"
                    );
            System.exit(1);
        }
        components=new Vector<Slot>();
        currentActiveDropSlot=0;
        curTangrams=(Vector<Tangram>)v.clone();
      
        
        addEmptySlots();
        addFilledSlots();
        repaint();
        
        
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
                if (components==null) System.out.println("components null");
                components.add(cur);
                j+=(horiDistance+Slot.serverWidth);
            }
            m+=(vertiDistance+Slot.serverHeight);
            j=horiDistance;
            
        }
        
        
      
    }
    public void updateStatusTangramPlaced(int sourceSlotID, int targetSlotID)
    {
        Slot draggedSlot=components.elementAt(sourceSlotID);
        Slot droppedOnSlot=components.elementAt(targetSlotID);
        droppedOnSlot.tangram=draggedSlot.tangram;
        draggedSlot.tangram=null;
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                System.out.println("doing update repaint");
                repaint();
                
            }
        });
        
    }
    public void updateStatusTangramRevealed(int slotID)
    {
        components.elementAt(slotID).enabled=true;
        if (slotID>0) components.elementAt(slotID-1).enabled=false;
        this.currentActiveDropSlot=slotID;
        System.out.println(slotID+" enabling");
        
        
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                System.out.println("doing update repaint");
                repaint();
                
            }
        });
    }
    //this should be called once and only once in the constructor
    
    private void addFilledSlots()
    {
        
        Vector<Tangram> tangrams=(Vector<Tangram>)curTangrams.clone();
        System.out.println("tangrams contains "+tangrams.size());
        int counter=0;
        int j=horiDistance;
        int m=2*vertiDistance+2*Slot.serverHeight+vertiDistanceSourceTarget;
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
                j+=(horiDistance+Slot.serverWidth);
                counter++;
            }
            m+=(vertiDistance+Slot.serverHeight);
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
                j+=(horiDistance+Slot.serverWidth); 
                counter++;
            
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

