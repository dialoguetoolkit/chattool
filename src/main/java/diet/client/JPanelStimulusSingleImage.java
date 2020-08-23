/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import diet.attribval.AttribVal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author gj
 */
public class JPanelStimulusSingleImage extends JPanel{
    
 
   ConnectionToServer cts;


    private BufferedImage image;
    private String imagename;
    
    
    

    public JPanelStimulusSingleImage(ConnectionToServer cts) {
       this.setBackground(Color.GRAY);
       this.cts=cts;
       this.setMaximumSize(new Dimension (300,1200));
    }

     public void changeImage(BufferedImage bi, String imagename){
         this.image=bi;
         this.imagename=imagename;
         final AttribVal av1 = new AttribVal("name",imagename);
         long timeOfChange = new Date().getTime();
         System.err.println("DEBUG1");
         this.repaint();
          System.err.println("DEBUG2");
         cts.cEventHandler.reportInterfaceEvent("singlestimulusimage", timeOfChange, av1);
     }
    
     public void changeImage(final Color backgroundcolour){
          image=null;
          imagename ="";
          String rgb = "RGB: "+ backgroundcolour.getRed()    +","+  backgroundcolour.getGreen() +","+ backgroundcolour.getBlue();
          final AttribVal av1 = new AttribVal("name",rgb);
          SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                 setBackground(backgroundcolour);
                 long timeOfChange = new Date().getTime();      
                 cts.cEventHandler.reportInterfaceEvent("stimulusimage_change_confirm", timeOfChange, av1);
                 System.err.println("Reporting the event");
                 repaint();
             }
          
          });
          
          
          
         
          
     }
     
     JFrameStimulusSingleImageCountdownThread jfssictCurrent;
     
     public void changeImage(BufferedImage bi, String imagename,long duration){
         if(jfssictCurrent!=null)jfssictCurrent.kill();
         this.image=bi;
         this.imagename=imagename;
         JFrameStimulusSingleImageCountdownThread jfssict = new JFrameStimulusSingleImageCountdownThread();
         jfssict.startCountdown(duration, Color.gray, this);
         
         
         jfssictCurrent = jfssict;
         
         final AttribVal av1 = new AttribVal("name",imagename);
         long timeOfChange = new Date().getTime();
         System.err.println("DEBUG1");
         
         this.repaint();
         cts.cEventHandler.reportInterfaceEvent("stimulusimage_change_confirm", timeOfChange, av1);
         System.err.println("Reporting event2:" + duration);
     }
     
     
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.err.println("DEBUG3");
        if(image!=null)g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
        
           
        
        
    }

}
    
