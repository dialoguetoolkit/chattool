/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.gui.lookandfeel;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author gj
 */
public class JNimbusProgressBar extends JProgressBar{
    
    JProgressBarFillPainter jpbfp;
    
     public JNimbusProgressBar(){
         this.jpbfp=jpbfp;
         this.setIndeterminate(false);
        // UIDefaults defaults = new UIDefaults();
        // jpbfp = new JProgressBarFillPainter(col);
         //defaults.put("ProgressBar[Enabled].foregroundPainter",jpbfp);
     }
     
     public JNimbusProgressBar(int x,int y,int z){
         super(x,y,z);
     }
    
    
     
     public void changeJProgressBar( final Color col, final String text, int value){
          if(value<0)value =0;
          if(value>100)value =100;
          final int val = value;
          final JNimbusProgressBar thisjcpb = this;
          
          SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                 String currentlookandfeelname = UIManager.getLookAndFeel().getName();
                 if(currentlookandfeelname.contains("nimbus") || currentlookandfeelname.contains("Nimbus")){
                       thisjcpb.setEnabled(true);
                       UIDefaults defaults = new UIDefaults();
                      
                       if(jpbfp==null||  col!=jpbfp.color){
                           jpbfp = new JProgressBarFillPainter(col);
                           defaults.put("ProgressBar[Enabled].foregroundPainter",jpbfp);
                           System.err.println(col.toString());
                        }
                       
                       thisjcpb.setValue(val);
                       thisjcpb.setString(text);
                       thisjcpb.setStringPainted(true);
                       thisjcpb.putClientProperty("Nimbus.Overrides", defaults);
                       thisjcpb.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
                       SwingUtilities.updateComponentTreeUI(thisjcpb);
 
                 }
                 else{
                     thisjcpb.setForeground(col);
                     thisjcpb.setUI(new BasicProgressBarUI() {
                          protected Color getSelectionBackground() { return Color.black; }
                          protected Color getSelectionForeground() { return Color.black; }
                          
                     });
                     thisjcpb.setValue(val);
                     thisjcpb.setString(text);
                     thisjcpb.setStringPainted(true);
                 }
            }
          });  
    }
     
     
     
     
}
