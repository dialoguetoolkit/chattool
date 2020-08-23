package diet.server.conversation.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;


public class JClientEnterFieldPanel extends JPanel{
    
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextPane jTextPane1 = new JTextPane();
    
    
    JLabel jLabel1 = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    
    public JClientEnterFieldPanel(String participantname) {
        
        if(participantname==null){
            System.exit(-567);
        }
        
         this.setLayout(borderLayout1);
         jTextPane1.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 11));
         //jTextPane1.setLineWrap(true);
         //jTextArea1.setText("jChat text");
         //jLabel1.setToolTipText("");
         jLabel1.setText(participantname);
         jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                              HORIZONTAL_SCROLLBAR_NEVER);
         
         jScrollPane1.setPreferredSize(new Dimension(200, 40));
         this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
         jScrollPane1.getViewport().add(jTextPane1);
         this.add(jLabel1, java.awt.BorderLayout.NORTH);      
         //jTextArea1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
         this.validate();
         this.setVisible(true);
         super.setPreferredSize(new Dimension(300,50));
         super.setMinimumSize(new Dimension(300,50));
    }
    
    public String getName(){
        //return jLabel1.getText();
        if(jLabel1==null)return "NO NAME ENTERED DURING LOGIN";
        return jLabel1.getText();
    }
    
    public void setText(String s){
        final String s2 = s;
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jTextPane1.setText(s2);
                jTextPane1.validate();
                jTextPane1.repaint();
                jScrollPane1.repaint();
            }
            
        });
    }
    
    
}
