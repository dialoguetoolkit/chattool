/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ParticipantGroups.ui;

import diet.server.Conversation;
import diet.server.ParticipantGroups.ParticipantGroups;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author gj
 */
public class JPanelCurrentGroups extends JPanel implements ListSelectionListener{

    ParticipantGroups pp;
    JPanelCurrentGroupsTable jppt;
    JScrollPane jsp = new JScrollPane();
    public JPanelCurrentGroupsSouth jppps ;//= new JParticipantsPanelSouth(cH.getConversation());
    
    
    
    public JPanelCurrentGroups(ParticipantGroups pp)  {
        this.pp=pp;
        
        
        
        jppps = new JPanelCurrentGroupsSouth(pp, this);
       
        this.setLayout(new BorderLayout());
        jppt = new JPanelCurrentGroupsTable (pp.c.getHistory());
        jsp.getViewport().add(jppt);
        this.add(jsp, BorderLayout.CENTER);
       
        this.add(jppps, BorderLayout.SOUTH);
        
         jsp.setBorder(javax.swing.BorderFactory.createTitledBorder("Current group(s)"));
        
        jppt.getSelectionModel().addListSelectionListener(this);
        
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        jppps.validateButtonEnabled();
    }
    
    
    
    
     public void updateData(){
       
        final JPanelGroupsTableModel jpptm = (JPanelGroupsTableModel)jppt.getModel();
       
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){
                jpptm.updateData();        
           }
        });
       
        
        
    }
   
    
}
