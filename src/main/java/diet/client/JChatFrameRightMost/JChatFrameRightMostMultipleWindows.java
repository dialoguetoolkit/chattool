/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client.JChatFrameRightMost;

import diet.attribval.AttribVal;
import diet.client.ClientEventHandler;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventTracker;
import diet.client.JChatFrame;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.JChatFrameMultipleWindowsWithSendButtonWidthByHeight;
import diet.client.JChatFrameMultipleWindowWithSendButtonWidthByHeight.JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument;
import diet.gui.lookandfeel.JNimbusProgressBar;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author gj
 */
public class JChatFrameRightMostMultipleWindows extends JChatFrame {

    //JTextPaneRightJustified textPane;
    BoxLayout blayout;
    Vector<JTextPaneRightJustified> textPanes = new Vector();
    JPanel mainPane = new JPanel();
    JPanel cards = new JPanel(new CardLayout()) ;
    CardLayout cl = new CardLayout();;
    JNimbusProgressBar jnp;
    
    public JChatFrameRightMostMultipleWindows(ClientEventHandler clevh, int width, int height, long texttimeout, int state, int numberofwindows) throws HeadlessException {
       /* super(clevh);
        BoxLayout blayout = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
        this.getContentPane().setLayout(blayout);
        for(int i=0;i<numberofwindows;i++){
            JTextPaneRightJustified textPane = new JTextPaneRightJustified(clevh, texttimeout, state);
            textPane.setPreferredSize(new Dimension(width,height));
            textPane.setMinimumSize(new Dimension(width,height));
            this.getContentPane().add(textPane);
            textPanes.insertElementAt(textPane, 0);
            textPane.doTesting3();
            if(i==numberofwindows-1){
                textPane.setFocusable(true);
                textPane.listenForKeyEvents();
            }
            else{
                textPane.setFocusable(false);
            }
            //this.getContentPane().add(new JButton("button"));
        }
        */
     
        super(clevh);
        this.getContentPane().setLayout(new BorderLayout());
        BoxLayout blayout = new BoxLayout(mainPane,BoxLayout.Y_AXIS);
        mainPane.setLayout(blayout);
        for(int i=0;i<numberofwindows;i++){
            JTextPaneRightJustified textPane = new JTextPaneRightJustified(clevh,i, texttimeout, state);
            
           
            
            textPane.setPreferredSize(new Dimension(width,height));
            textPane.setMinimumSize(new Dimension(width,height));
            mainPane.add(textPane);
            textPanes.insertElementAt(textPane, 0);
            textPane.doTesting3();
            if(i==numberofwindows-1){
                textPane.setFocusable(true);
                textPane.listenForKeyEvents();
            }
            else{
                textPane.setFocusable(false);
            }
            //this.getContentPane().add(new JButton("button"));
        }
        
        
        
        try {
           cards = new JPanel (new CardLayout());
           cards.add(mainPane,"mp");
           JPanel jp = new JPanel();
           cards.add(jp,"grey");  
        }catch (Exception e){
            e.printStackTrace();
        }
        this.getContentPane().add(cards,BorderLayout.CENTER);
        
        // cl = (CardLayout)(cards.getLayout());
        
        
        
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        
       
        
        
    }

    @Override
    public void changeInterfaceProperties(final String uniqueIDGeneratedByServer, final int newInterfaceproperties, final Object...values) {
         final Object value1 = values[0];
         final Object value2 = values[1];
     
        
        
        //super.changeInterfaceProperties(uniqueIDGeneratedByServer, newInterfaceproperties, value1, value2); //To 
        
        //final JChatFrameRightMostMultipleWindows jfrmmw = this;
       
       final AttribVal avServerId = new AttribVal("serverid",uniqueIDGeneratedByServer);
       
       SwingUtilities.invokeLater( new Runnable(){ public void run(){ 
           
                if (newInterfaceproperties==ClientInterfaceEventTracker.disableTextEntry){    
                    JTextPaneRightJustified jtprj = ( JTextPaneRightJustified)textPanes.elementAt(0);
                    jtprj.disableWindow();
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "grey");         
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.enableTextEntry){        
                   JTextPaneRightJustified jtprj = ( JTextPaneRightJustified)textPanes.elementAt(0);
                   jtprj.enableWindow();
                   CardLayout cl = (CardLayout)(cards.getLayout());
                   cl.show(cards, "mp");         
                   jtprj.requestFocus();
                }
                else if (newInterfaceproperties==ClientInterfaceEventTracker.disableTextPane){
                    JTextPaneRightJustified jtprj = ( JTextPaneRightJustified)textPanes.elementAt(0);
                    jtprj.disableWindow();
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "grey"); 
                    
                }      
                else if (newInterfaceproperties==ClientInterfaceEventTracker.enableTextPane){
                   JTextPaneRightJustified jtprj = ( JTextPaneRightJustified)textPanes.elementAt(0);
                   jtprj.enableWindow();
                   CardLayout cl = (CardLayout)(cards.getLayout());
                   cl.show(cards, "mp"); 
                    jtprj.requestFocus();
                }      
                else if (newInterfaceproperties == ClientInterfaceEventTracker.dodebugrobot){
                    debugrobot(value1, value2);
                }
                else if (newInterfaceproperties == ClientInterfaceEventTracker.setCharWhitelist){
                     System.err.println("WHITELIST IS: "+value1);
                     
                     textPanes.lastElement().setWhitelist((String)value1);
                     textPanes.firstElement().setWhitelist((String)value1);
                }
                
                else{
                    
                    
                }
        }
       });
      }
    
    
    public void changeJProgressBar(final String text,final Color colorForeground, int value){
    final JFrame jf2 = this;
    
    if(value<0)value =0;
        if(value>100)value=100;
        final int valCorr=value;
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){
              try{

                if(jnp==null){
                      jnp = new JNimbusProgressBar(JProgressBar.HORIZONTAL,0,100);
                      jf2.getContentPane().add(jnp,BorderLayout.SOUTH);
                      jnp.setPreferredSize(new Dimension(jf2.getWidth(),25));
                      jnp.changeJProgressBar(colorForeground, text, valCorr);                
                      jf2.pack();
                }
                else{
                      jnp.changeJProgressBar(colorForeground, text, valCorr); 
                   
                        
                }
                

             
            }catch (Exception e){
                    System.err.println("\n1"                            + "\n2"                            + "\n3"                            + "\n4"                            + "\n5"                            + "\n6"                            + "\n7"                            + "\n8"                            + "\n9"                            + "\n10"
                            + "\n11Error displaying progressBar in CHATFRAME");
                    e.printStackTrace();

               }
           }
       });

    }    
    
    
    
   
    
     public void wYSIWYGUpdateDocumentAppend(int wysiwygWindowNumber, String appendedText, AttributeSet as, String usernameOther) {
         
       JTextPaneRightJustified textPane = (JTextPaneRightJustified)this.textPanes.elementAt(wysiwygWindowNumber);
         
         Color cForeground =  StyleConstants.getForeground(as);
         if(cForeground==null){
             System.err.println("ERROR WITH FONT FOREGROUND BEING NULL");
             cForeground = Color.BLACK;
         }
         System.err.println("RED:"+cForeground.getRed());
         System.err.println("GREEN:"+cForeground.getGreen());
         System.err.println("BLUE:"+cForeground.getBlue());
         
         textPane.jtprjfd.insertStringFromOther(appendedText, cForeground, usernameOther);
    }
    
    

   
    public void debugrobot(Object param1, Object param2){
          System.err.println("STARTING DEBUG ROBOT");
          System.exit(-5);
    }
    
    
    class JChatFrameKeyEventListener extends java.awt.event.KeyAdapter {

   JChatFrameKeyEventListener() {
   }

   public void keyPressed(KeyEvent e) {
     getClientEventHandler().keyPressFilter(e);
     System.out.println("EVENTLISTENER DETERMINES KEYPRESSED");
   }

   public void keyReleased(KeyEvent e) {
     getClientEventHandler().keyReleaseFilter(e);
     System.out.println("EVENTLISTENER DETERMINES KEYRELEASED");
   }
}

    
}
