/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.gui.lookandfeel;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author gj
 */
public class NimbusLookAndFeelUtilities {
    
    
    static public void changeTextPaneBackground(final JTextPane jtp, final Color bgColor){
        
        ///Nimbus
        SwingUtilities.invokeLater(new Runnable(){
        
            public void run(){
               UIDefaults defaults = new UIDefaults();
               defaults.put("TextPane[Enabled].backgroundPainter", bgColor);
               jtp.putClientProperty("Nimbus.Overrides", defaults);
               jtp.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
               jtp.setBackground(bgColor);
               SwingUtilities.updateComponentTreeUI(jtp);
               
            }
        });
        
    }
        
    static public void changeJProgressBar(final JNimbusProgressBar jpb, final Color col, final String text, int value){
          if(value<0)value =0;
          if(value>100)value =100;
          final int val = value;
          SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                 String currentlookandfeelname = UIManager.getLookAndFeel().getName();
                 if(currentlookandfeelname.contains("nimbus") || currentlookandfeelname.contains("Nimbus")){
                       jpb.setEnabled(true);
                       UIDefaults defaults = new UIDefaults();
                       //defaults.put("ProgressBar.foreground", Color.blue);
                       if(jpb instanceof JNimbusProgressBar){
                           //jpb.changeNimbusColor(col);
                       }
                       else{
                          //defaults.put("ProgressBar[Enabled].foregroundPainter",new JProgressBarFillPainter(col));    
                       }
                       
                       jpb.setValue(val);
                       jpb.setString(text);
                       jpb.setStringPainted(true);
                       jpb.putClientProperty("Nimbus.Overrides", defaults);
                       jpb.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
                       SwingUtilities.updateComponentTreeUI(jpb);
 
                 }
                 else{
                     jpb.setForeground(col);
                     jpb.setUI(new BasicProgressBarUI() {
                          protected Color getSelectionBackground() { return Color.black; }
                          protected Color getSelectionForeground() { return Color.black; }
                          
                     });
                     jpb.setValue(val);
                     jpb.setString(text);
                     jpb.setStringPainted(true);
                 }
            }
          });  
    }
        
    
}
