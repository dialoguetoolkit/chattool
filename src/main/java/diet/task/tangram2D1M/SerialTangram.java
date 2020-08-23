/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.tangram2D1M;

/**
 *
 * @author Arash
 */
import java.io.Serializable;
public class SerialTangram implements Serializable{
    
    public int w;
   
    public int h;
    public int[] imageData;
    public String name;
    
    public SerialTangram(String n, int[] i, int w, int h)
    {
        this.w=w;
        this.h=h;
        this.imageData=i;
        this.name=n;
    }
            

}
