/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

import diet.task.stimuliset.SerializableImage;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author sre
 */
public class JClientTangramP extends JPanel implements MouseListener{
    
    JFrameGridImagesStimuli jfgs;
    SerializableImage si;
    int borderpixels = 20;
    
    public JClientTangramP(JFrameGridImagesStimuli jfgs){
        this.jfgs=jfgs;
        this.setBackground(Color.WHITE);
        initialize();
    }
    
    
    public void initialize(){
        //setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), borderpixels, false));
       
        this.addMouseListener(this);
        Border margin0 = new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 255), borderpixels, false);
        Border margin1 = new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), borderpixels, false);
        Border margin2 = new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, false);
        //this.setBorder(new CompoundBorder(margin1, margin2));
        
        this.setSelection(Color.WHITE, Color.WHITE);
        
    }
    
    public void setImage(SerializableImage sii){
        Graphics g = this.getGraphics();
         this.si=sii; 
         if(si!=null ){
            
             g.drawImage(si.getImage(), borderpixels, borderpixels, null);
         }  
    }
    
    public void setPreferredSize(Dimension d){
        super.setPreferredSize(new Dimension(d.width+borderpixels+borderpixels,d.height+borderpixels+borderpixels));
    }
    
    public void paint(Graphics g){
      super.paint(g);
     
      try{
      if(si!=null ){
             BufferedImage bi = si.getImage();        
             if(bi!=null)g.drawImage(bi, borderpixels, borderpixels, null);
         }  
        
         //Graphics2D g2d = (Graphics2D)g;
         //int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
         //int fontSize = (int)Math.round(25.0 * screenRes / 72.0);
         //Font font = new Font("Arial", Font.PLAIN, fontSize);   
         //g2d.setFont(font);
         //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         //g.setColor(textColour);
         //g.drawString(text, positionX, positionY);
        
       }catch (Exception e){
           e.printStackTrace();
       }
      //System.err.println("PAINT1");
      //g.d
  }

    
    public void setSelection(Color innermostColor, Color innerColor){
       //System.exit(-55);
      // setBorder(new javax.swing.border.LineBorder(c, borderpixels, false));
       //Border margin1 = new javax.swing.border.LineBorder(Color.RED,2 , false);
       //Border margin2 = new javax.swing.border.LineBorder(Color.GREEN,10, false);
       //this.setBorder(new CompoundBorder(margin1, margin2));
       // setBorder(new javax.swing.border.LineBorder(c, borderpixels, false));
        Border innermost = new javax.swing.border.LineBorder(innermostColor, (borderpixels/2)-1, false);
        Border outer = new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, false);
        Border inner = new javax.swing.border.LineBorder(innerColor, ((borderpixels)/2)-1, false);
        //this.setBorder(new CompoundBorder(margin1, margin2));
       
        CompoundBorder cb = new CompoundBorder(inner,innermost);
        CompoundBorder cb2 = new CompoundBorder(outer,cb);
        this.setBorder(cb2);
        
        
       
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
         System.err.println("MOuSEPRESSED");
         jfgs.setImageSelected(this, si, new Date().getTime());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    
    
    
}
