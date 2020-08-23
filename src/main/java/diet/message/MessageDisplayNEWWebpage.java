/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;
import java.io.Serializable;

public class MessageDisplayNEWWebpage extends Message implements Serializable {

    private String id;
    private String url;
    private String header;

   
    private int width;
    private int height;
    private boolean vScrollBar;
    private boolean progressBar;
    private boolean forceCourier =true;

    public MessageDisplayNEWWebpage(String id, String header, String url, int width, int height, boolean vScrollBar,boolean forceCourier) {
        super("server","server");
        this.id=id;
        this.header=header;
        this.url=url;
        this.width=width;
        this.height=height;
        this.vScrollBar=vScrollBar;
        
    }

     public String getHeader() {
        return header;
    }
    
     public int getHeight() {
        return height;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean isVScrollBar() {
        return vScrollBar;
    }
    public boolean isJProgressBar() {
        return this.progressBar;
    }

    public int getWidth() {
        return width;
    }

    public boolean isForceCourier() {
        return forceCourier;
    }
    
}