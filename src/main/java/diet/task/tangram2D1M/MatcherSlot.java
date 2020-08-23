package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
public class MatcherSlot extends Slot{
    
   
    
    
   
    public MatcherSlot(Tangram img, int slotID, int x,int y)
    {
        super(img,slotID,x,y);
        
        
    }
    public MatcherSlot(Tangram img, int x,int y)
    {
        super(img,x,y);
        
        
    }
    public void draw(Graphics2D g2, char slotType)
    {
        
        g2.setColor(Color.BLACK);
        
        int width;
        int height;
        int imageBorder;
        if (slotType=='c')
        {
            g2.setStroke(new BasicStroke(3));
            width=clientWidth;
            height=clientHeight;
            imageBorder=clientImageBorder;
        }
        else
        {
            
            g2.setStroke(new BasicStroke(1));
            g2.setFont(new Font("serif", Font.PLAIN, 10));
            width=serverWidth;
            height=serverHeight;
            imageBorder=serverImageBorder;
            
        }
        if (this.enabled&&this.tangram==null)
        {
            g2.setColor(Color.BLUE);
            g2.drawString("Drag Here", x+width/2-20, y+height/2+20);
        }
        g2.drawRect(x, y, width, height);
        if (this.slotID>0)
        {
            String label=(new Integer(slotID)).toString();
            g2.drawString(label, x+width/2, y+height/2);
        }
        
        
        if (tangram!=null)
            g2.drawImage(tangram.img, x+imageBorder, y+imageBorder, null);
       
        
        
    }
   

}
