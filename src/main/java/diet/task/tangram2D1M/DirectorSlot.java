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
public class DirectorSlot extends Slot{
    
     //unlike matcher slot this will not have -1 as its slotID
    //slotID is always positive here
    
    public boolean showImage;
    public boolean bothDirectors;
    public DirectorSlot(Tangram img, int slotID, int x,int y, boolean bothDirectors)
    {
        super(img,slotID,x,y);
        showImage=false;
       this.bothDirectors=bothDirectors;
        
    }
    
    
    public void draw(Graphics2D g2, char slotType)
    {
        
        if (enabled) g2.setColor(Color.BLUE);
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
        g2.drawRect(x, y, width, height);
        
       String label=(new Integer(slotID+1)).toString();
       g2.drawString(label, x+width/2, y+height+12);
       
        
        
        if (tangram!=null)
        {
            
            if(showImage)
            {
                g2.drawImage(tangram.img, x+imageBorder, y+imageBorder, null);
            }
            else
            {
                g2.setColor(Color.RED);
                g2.drawString("Click to", x+width/2-23, y+height/2);
                g2.drawString("reveal",x+width/2-20, y+height/2+10);
                if (bothDirectors)
                {
                    g2.drawString("(shared)", x+width/2-25,y+height/2+30);
                }
                g2.setColor(Color.BLACK);
            }
        }
        else if (slotType=='c')
        {
            g2.setColor(Color.BLACK);
            g2.drawString("OTHER", x+width/2-23, y+height/2);
            g2.drawString("DIRECTOR",x+width/2-33, y+height/2+10);
        }
        else
        {
           g2.setColor(Color.BLACK);
           g2.drawString("other", x+width/2-15, y+height/2);
          
           
        }
       g2.setColor(Color.BLACK);
       
        
        
    }
   
    

}
