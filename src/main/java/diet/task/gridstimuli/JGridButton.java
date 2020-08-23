/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

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
public class JGridButton extends JButton implements MouseListener{
    
    JGridStimuli2016 jfgts;
   
    int borderpixels = 5;
    
    Color innerMostColor = Color.RED;
    Color innerColor = Color.GREEN;
    String filename ="";
    
    int x;
    int y;
    
    public JGridButton(JGridStimuli2016 jfgts, String filename, int x, int y){
        this.jfgts=jfgts;
        this.setSelection(Color.red, Color.green);
        this.addMouseListener(this);
        this.x=x;
        this.y=y;
        this.filename=filename;
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
         this.setBorder(new CompoundBorder(inner, outer));
       
        //this.innerColor=innerColor;
        //this.innerMostColor=innerMostColor;
        
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
         jfgts.setTextSelected(null, "", new Date().getTime(),this.innerColor, this.innerMostColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    
    
    
}
