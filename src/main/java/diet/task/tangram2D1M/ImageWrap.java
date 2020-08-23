/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

/**
 *
 * @author Arash
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
        


public class ImageWrap extends JFrame{
    
    MyPanel panel;
   
    public ImageWrap() throws IOException
    {
        panel=new MyPanel();
        this.getContentPane().add(panel);
        
        this.setVisible(true);
        System.out.println("setting visible");
        
       
        
        
        
    }
    
   
    public static void main(String args[])
    {
        System.out.println("what is wri");
        try{
            ImageWrap i=new ImageWrap();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
        
        
        
        
    }

}
class MyPanel extends JPanel
{
    BufferedImage img;
    public MyPanel() throws IOException
    {
        super();
        
       
        BufferedImage image=ImageIO.read(new File("D:\\UniWork\\chattool\\tangram_sets\\set_1\\a.jpg"));
        int h=image.getHeight(null);
        int w=image.getWidth(null);
        int[] imageRGBData=image.getRGB(0, 0, w, h, new int[w*h], 0, w);
        BufferedImage copy=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        copy.setRGB(0, 0, w, h, imageRGBData, 0, w);
        img=copy;
        
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g;
        g2.drawImage(img, 0,0,null);
        
    }
    
}
