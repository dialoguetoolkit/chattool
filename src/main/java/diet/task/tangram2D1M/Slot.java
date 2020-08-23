package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
import java.awt.Graphics2D;

public abstract class Slot{
    public final static int clientImageBorder=3;
    public final static int clientWidth=96; //pixels; Images should be the same size or smaller
    public final static int clientHeight=126;//pixels
    public final static int serverImageBorder=1;
    public final static int serverWidth=48; //pixels; Images should be the same size or smaller
    public final static int serverHeight=63;//pixels
    public int x;
    public int y;
    
    public int slotID;
    public Tangram tangram=null;
    public boolean enabled;
    
    public Slot(Tangram img, int slotID, int x,int y)
    {
        this.x=x;
        this.y=y;
        
        
        this.slotID=slotID;
        this.tangram=img;
         enabled=false;
    }
    public Slot(Tangram img, int x,int y)
    {
        this.x=x;
        this.y=y;
        
        
        this.slotID=-1;
        this.tangram=img;
        
    }
   
    public abstract void draw(Graphics2D g2, char slotType);
   
    public boolean isEmpty()
    {
        return tangram==null;
    }
    
   
    

}
