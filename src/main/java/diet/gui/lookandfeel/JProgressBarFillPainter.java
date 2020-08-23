/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.gui.lookandfeel;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.Painter;

/**
 *
 * @author gj
 */
public class JProgressBarFillPainter implements Painter <JComponent>{
 
    public final Color color;
    
            
            
    public JProgressBarFillPainter(Color c) {
        color = c;
        
    }

    @Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillRect(3, 3, width - 1, height - 6);
        g.setColor(oldColor);
       
    }
}


