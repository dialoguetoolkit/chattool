/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuliold;

import diet.task.gridstimuli.JGridStimuli2016;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author sre
 */
public class JClientTextP2016BACKUP extends JPanel implements MouseListener{
    
    JGridStimuli2016 jfgts;
    String text ="hello";
    int borderpixels = 20;
    
    Color innerMostColor = Color.WHITE;
    Color innerColor = Color.WHITE;
    
    
    public JClientTextP2016BACKUP(JGridStimuli2016 jfgts){
        this.jfgts=jfgts;
        this.setBackground(Color.WHITE);
        this.add(new JButton(""));
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
    
    public void setText(String text){
        this.text=text; 
        Graphics g = this.getGraphics();
         
         if(text!=null ){
            
             drawText(g);
            
         }  
         
         
        
    }
    
    public String getText(){
        if(text==null){
            text="";
        }
        return this.text;
    }
    
    
    
    
    private void drawText(Graphics g){
         Color oldColor = g.getColor();
             //g.setColor(Color.red);
             g.fillRect(0, 0, 10, 10);
             g.setColor(Color.BLACK);
             g.drawString(text, 5, 20);
             g.setColor(oldColor);
              System.err.println("DRAWING: "+text);
              //System.exit(-56);
              //Graphics2D g2d = (Graphics2D)g;
         //int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
         //int fontSize = (int)Math.round(25.0 * screenRes / 72.0);
         //Font font = new Font("Arial", Font.PLAIN, fontSize);   
         //g2d.setFont(font);
         //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         //g.setColor(textColour);
         //g.drawString(text, positionX, positionY);
    }
    
    
    public void setPreferredSize(Dimension d){
        super.setPreferredSize(new Dimension(d.width+borderpixels+borderpixels,d.height+borderpixels+borderpixels));
    }
    
    public void paint(Graphics g){
      super.paint(g);
     
      try{
      
        
         drawText(g);
        
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
       
        this.innerColor=innerColor;
        this.innerMostColor=innerMostColor;
        
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
         jfgts.setTextSelected(this, text, new Date().getTime(),this.innerColor, this.innerMostColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    
    
    
}
