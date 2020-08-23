package diet.server.conversation.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import diet.server.ConversationUIManager;


public class JClientEnterFieldsFrame extends JFrame{
    
    Vector panels = new Vector();
    JPanel mainPanel= new JPanel();
    BoxLayout bxLayout;
    ConversationUIManager convUIManager;
    
    
    public JClientEnterFieldsFrame(ConversationUIManager c) {
        super(c.getConvID());
        convUIManager = c;
        bxLayout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
        mainPanel.setLayout(bxLayout);
        this.getContentPane().add(mainPanel);
        
        mainPanel.setPreferredSize(new Dimension(280,180));
        this.pack();
        this.validate();
        this.setVisible(true);
        
        
    }
    
    
    public void displayBackground(String username,final Color c){
      
       final String username2 =username;
          
       SwingUtilities.invokeLater(new Runnable(){  
            public void run(){
                for(int i=0;i<panels.size();i++){
                    JClientEnterFieldPanel jefp = (JClientEnterFieldPanel)panels.elementAt(i);
                    if(jefp.getName().equalsIgnoreCase(username2)){
                        jefp.setBackground(c);
                        return;
                     }
                }
                
                JClientEnterFieldPanel jcfp = new JClientEnterFieldPanel(username2);
                jcfp.setText("");
                mainPanel.add(jcfp);
                mainPanel.validate();
                mainPanel.repaint();
                panels.addElement(jcfp);
            }
            
        });
       
       
    }
    
    
    
    public void displayText(String username,String text){
       /*for(int i=0;i<panels.size();i++){
           JClientEnterFieldPanel jefp = (JClientEnterFieldPanel)panels.elementAt(i);
           if(jefp.getName().equalsIgnoreCase(username)){
               jefp.setText(text);
               return;
           }
       }*/
       final String username2 =username;
       final String text2 = text;     
       SwingUtilities.invokeLater(new Runnable(){  
            public void run(){
                for(int i=0;i<panels.size();i++){
                    JClientEnterFieldPanel jefp = (JClientEnterFieldPanel)panels.elementAt(i);
                    if(jefp.getName().equalsIgnoreCase(username2)){
                        jefp.setText(text2);
                        return;
                     }
                }
                
                JClientEnterFieldPanel jcfp = new JClientEnterFieldPanel(username2);
                jcfp.setText(text2);
                mainPanel.add(jcfp);
                mainPanel.validate();
                mainPanel.repaint();
                panels.addElement(jcfp);
            }
            
        });
       
       
    }
    
    
}
