/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.conversation.ui;

import diet.server.conversationhistory.ConversationHistory;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author gj
 */
public class JParticipantsPanel extends JPanel {
    
    ConversationHistory cH;
    JParticipantsTable jpt;
    JScrollPane jsp = new JScrollPane();
    public JParticipantsPanelSouth jpps ;//= new JParticipantsPanelSouth(cH.getConversation());
    
    public JParticipantsPanel(ConversationHistory cH, boolean telegram){
        this.cH=cH;
        jpps = new JParticipantsPanelSouth(cH.getConversation(), this, telegram);
        //this.add(new JLabel("THIS"));
        //this.add(new JButton("THIS"));
        this.setLayout(new BorderLayout());
        jpt = new JParticipantsTable (cH, telegram);
        jsp.getViewport().add(jpt);
        this.add(jsp, BorderLayout.CENTER);
        //this.add(new JLabel("SOUTH"), BorderLayout.SOUTH);
        //this.add(new JLabel("NORTH"), BorderLayout.SOUTH);
        this.add(jpps, BorderLayout.SOUTH);
      
    }
    
    
    public void updateData(){
       
        final JParticipantsTableModel jptm = (JParticipantsTableModel)jpt.getModel();
       
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){
                jptm.updateData(); 
                repaint();
           }
        });
       
        
        
    }
}
