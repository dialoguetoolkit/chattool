/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author gj
 */
public class JFrameGlassPane extends JPanel {
    
    public int pixelborder = 0;
    Color colourOfBorder = Color.LIGHT_GRAY  ;
    public boolean showBorder = true;
    
    
    
    
    
    public JFrameGlassPane(int pixelwidth){
        this.pixelborder=pixelwidth;
    }
    
    public void changeBorder(int pixels, Color c){
        this.pixelborder=pixels;
        colourOfBorder=c;
        this.repaint();
        
    }
    
     
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!showBorder)return;
        if(pixelborder==0)return;
        int width = this.getWidth();
        int height = this.getHeight();
        
        
        // Draw Text
        Color c = g.getColor();
        g.setColor(colourOfBorder);
        //g.drawString("This is my custom Panel!",10,20);
        
        //g.drawRect(0, 0, 200, 200);
        //g.setColor(new Color(1, 0, 0, 0.5f));  
        
        g.fillRect(0, 0, width,  pixelborder );  
        g.fillRect(0, height-pixelborder, width,  pixelborder );
        g.fillRect(0, 0, pixelborder, height);
        g.fillRect(width-pixelborder, 0, pixelborder, height);
        
        g.setColor(c);
        
        
    }  
    
}
