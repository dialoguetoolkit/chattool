package diet.task.tangram2D1M;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arash
 */
import java.awt.image.BufferedImage;
public class Tangram {
    
    public String name;
    public BufferedImage img;
    
    public Tangram(String name, BufferedImage img)
    {
        this.name=name;
        this.img=img;
    }
    public Tangram(SerialTangram t)
    {
        this.name=t.name;
        BufferedImage copy=new BufferedImage(t.w,t.h,BufferedImage.TYPE_INT_ARGB);
        copy.setRGB(0, 0, t.w, t.h, t.imageData, 0, t.w);
        img=copy;
        copy.flush();
    }

}
