/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.CustomizableReferentialTask;

import diet.server.Conversation;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author LX1C
 */
public class JCustomizableReferentialTaslkImagePanel extends JPanel{

    BufferedImage bi;

    public JCustomizableReferentialTaslkImagePanel() {
         this.setBackground(Color.GRAY);
    }
    
    
     private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height){

	BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, width, height, null);
	g.dispose();
	g.setComposite(AlphaComposite.Src);

	//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	//RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	//g.setRenderingHint(RenderingHints.KEY_RENDERING,
	//RenderingHints.VALUE_RENDER_QUALITY);
	//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	//RenderingHints.VALUE_ANTIALIAS_ON);

	return resizedImage;
     }
    
    
    
     public void setBufferedImage(BufferedImage b){
         if(b==null){
             Conversation.saveErr("One of the bufferedimages displayed on server was not found. Check that all images are correctly named");
            
         }
        // BufferedImage bbi = resizeImage(b,super.getWidth(),super.getHeight());
         
         
         
         SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                bi=  b;
                repaint();
            }
         });
         
        
     }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if(bi!=null){
            int imageHeight = bi.getHeight();
            int imageWidth = bi.getWidth();
            
            
            
            Dimension d = conv(bi.getWidth(),bi.getHeight(), this.getWidth(), this.getHeight() );
            
            int heightdiff = this.getHeight()-d.height;
            int heightadditional = Math.abs(heightdiff/2);
            
            
            int widthdiff = this.getWidth()-d.width;
            int widthadditional = Math.abs(widthdiff/2);
            
            
            g.drawImage(bi,widthadditional,heightadditional,d.width,d.height,null);
            
            
            System.err.println("bi.gedwidth"+bi.getWidth());
            System.err.println("bi.gedheight"+bi.getHeight());
             
            System.err.println("this.gedwidth"+this.getWidth());
            System.err.println("this.gedheight"+this.getHeight());
            
            
            System.err.println("heightadditional: "+heightadditional);
            System.err.println("widthadditional: "+widthadditional);
            
           
        } 
    }
    
    
    //
    //
    //g.dispose();
    
    
    //public static void calc(int xoriginal , int y original, int w, int h){
      // return null;
    //}

    public static Dimension conv(int xsource, int ysource, int wdest, int hdest){
        Dimension d = new Dimension();
        double xscale = (double)wdest/(double)xsource;
        double yscale = (double)hdest/(double)ysource;
        
        if(xscale>1 && yscale >1){
             //Image is smaller than panel
             //Need to find which needs to be scaled least.
             if(xscale<yscale){
                 d=new Dimension((int)((double)xsource*xscale),(int)((double)ysource*(double)xscale));
             } 
             else{
                 d=new Dimension((int)((double)xsource*yscale),(int)((double)ysource*(double)yscale));
             }       
        }
        else if(xscale>=1 && yscale <=1){
               d=new Dimension((int)((double)xsource*yscale),(int)((double)ysource*(double)yscale));
        }
        else if (xscale<=1 && yscale >=1 ){
              d=new Dimension((int)((double)xsource*xscale),(int)((double)ysource*(double)xscale));
        }
        else if (xscale<1 && yscale <1 ){
              d=new Dimension((int)((double)xsource*xscale),(int)((double)ysource*(double)xscale));
              if(xscale<yscale){
                  d=new Dimension((int)((double)xsource*xscale),(int)((double)ysource*(double)xscale));
              }
              else{
                  d=new Dimension((int)((double)xsource*yscale),(int)((double)ysource*(double)yscale));
              }
        }
        return d;
    }
    
    
    public static void main(String[] args){
         
    }
    
    
}
