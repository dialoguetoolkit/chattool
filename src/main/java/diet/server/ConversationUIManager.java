//This class might have to be split in two, to deal with the conversation history and to deal with 
package diet.server;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;



import diet.message.Message;
import diet.server.ParticipantGroups.ui.JPanelCurrentGroupsAndConfiguration;
import diet.server.conversation.ui.JParticipantsPanel;


import diet.server.conversationhistory.ConversationHistory;

import diet.server.conversationhistory.turn.Turn;
import diet.server.conversation.ui.JTurnsHorizontalTable;
import diet.server.conversation.ui.JTurnsListTable;
import diet.server.experimentmanager.EMUI;
import diet.server.conversation.ui.JClientEnterFieldsFrame;
import diet.server.conversation.ui.JGroupsConversationHistories;
import diet.server.conversation.ui.JInterventionsPanel;
import diet.server.conversation.ui.JPollParticipant;
import diet.server.conversation.ui.JRulesBasedInterventions;
import diet.server.conversationhistory.turn.DataToBeSaved;
import java.util.Vector;

/**
 * Contains references to all the user interface objects associated with the Conversation and associated methods for updating the 
 * components.
 * @author user
 */
public class ConversationUIManager {
    
    boolean isUsingUI = false;
    
    private ConversationHistory cH;
    private Conversation c;
  
    
    private JClientEnterFieldsFrame jceff;   
    private JTurnsListTable jtlt ;
    private JTurnsHorizontalTable jtht;
    //private JInterventionsPanelNonTelegram jppi;
    JInterventionsPanel jpi;
    
    private JParticipantsPanel jpp;
    private JPanelCurrentGroupsAndConfiguration jppp;
        
    
    
   
    
    JScrollPane js1 = new JScrollPane();
    JScrollPane js2 = new JScrollPane();
    JScrollPane js3 = new JScrollPane();
    JScrollPane js4 = new JScrollPane();
    //JScrollPane js5 = new JScrollPane();
    JScrollPane js6 = new JScrollPane();
    
    
    
    
    JGroupsConversationHistories jgch;
    
    
    JRulesBasedInterventions jri;
    
    
    
    
    public ConversationUIManager(ConversationHistory cH,Conversation c) {
         this.cH = cH;
         this.c=c;
         
         
    }
    
    
    /**
     * 
     * @return a tabbed pane containing all the UI components.
     */
    public JTabbedPane createConversationTabbedPane(){
        if(this.c.getExpManager().emui==null)return null;
        isUsingUI=true;
        

  
        js2 = new JScrollPane();
        js2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jtlt = new JTurnsListTable(cH,false);
        js2.getViewport().add(jtlt);
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        JPanel jPanel2SouthPanel = new JPanel();
        jPanel2SouthPanel.setLayout(new BorderLayout());
        jPanel2.add(js2,BorderLayout.CENTER);     
        
        
        
        
        
        
        
        js4 = new JScrollPane();
        js4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jtht = new JTurnsHorizontalTable(cH);
        js4.getViewport().add(jtht);
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new BorderLayout());
        JPanel jPanel4SouthPanel = new JPanel();
        jPanel4SouthPanel.setLayout(new BorderLayout());
        jPanel4.add(js4,BorderLayout.CENTER);     
        
       
        
        
        jri = new JRulesBasedInterventions(c);
        
        
        //js5 = new JScrollPane();
        //js5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jpi = new JInterventionsPanel(c,false);
        
        
        
        //js5.getViewport().add(jppi);
        //JPanel jPanel5 = new JPanel();
        //jPanel5.setLayout(new BorderLayout());
        //JPanel jPanel5SouthPanel = new JPanel();
        //jPanel5SouthPanel.setLayout(new BorderLayout());
        //jPanel5.add(js5,BorderLayout.CENTER);     
        
        
      
        
        JTabbedPane jtp = new JTabbedPane();
       
        
        jpp = new JParticipantsPanel(cH,false);
        jtp.addTab("Participants",this.jpp);
        
        
        jtp.addTab("Turns",jPanel4);
        jtp.addTab("Turns detailed",jPanel2);
        //jtp.addTab("Lexicon",jPanel1);      
        //jtp.addTab("Contiguous Turns",jPanel3);
        //jtp.addTab("Window policy",jPanel5);
        
         jgch  = new JGroupsConversationHistories(this,cH,false);
         jtp.addTab("Turns by group", jgch);
        
        
        try{
            this.jppp = cH.getConversation().getController().pp.getJPanel();
            if(jppp!=null)jtp.addTab("Assign participants to groups", jppp);
        }catch (Exception e){
            e.printStackTrace();
            Conversation.printWSln("Main", "Could not create ParticipantPartneringPanel");
            
        }
        
        //jtp.addTab("Script console", console);
        
        jtp.addTab("Interventions (rules)", jri);
        
        jtp.addTab("Interventions (manual)",jpi);
       
        
        
        
        return jtp;
       
    }
                        
     public JTabbedPane createConversationTabbedPaneTelegram(){
        if(this.c.getExpManager().emui==null)return null;
        isUsingUI=true;
        

  
        js2 = new JScrollPane();
        js2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jtlt = new JTurnsListTable(cH,true);
        js2.getViewport().add(jtlt);
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        JPanel jPanel2SouthPanel = new JPanel();
        jPanel2SouthPanel.setLayout(new BorderLayout());
        jPanel2.add(js2,BorderLayout.CENTER);     
        
        
        
        
        
        
        
        js4 = new JScrollPane();
        js4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jtht = new JTurnsHorizontalTable(cH);
        js4.getViewport().add(jtht);
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new BorderLayout());
        JPanel jPanel4SouthPanel = new JPanel();
        jPanel4SouthPanel.setLayout(new BorderLayout());
        jPanel4.add(js4,BorderLayout.CENTER);     
        
       
        
          jri = new JRulesBasedInterventions(c);
                  
       
         
        //js5 = new JScrollPane();
        //js5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        //this.jppi = new JInterventionsPanelNonTelegram(c);
        this.jpi = new JInterventionsPanel(c , true);
        
        
        //js5.getViewport().add(jppi);
        //JPanel jPanel5 = new JPanel();
        //jPanel5.setLayout(new BorderLayout());
        //JPanel jPanel5SouthPanel = new JPanel();
        //jPanel5SouthPanel.setLayout(new BorderLayout());
        //jPanel5.add(js5,BorderLayout.CENTER);     
        
        
      
        
        JTabbedPane jtp = new JTabbedPane();
       
        
        jpp = new JParticipantsPanel(cH,true);
        jtp.addTab("Participants",this.jpp);
        
        
        jtp.addTab("Turns",jPanel4);
        jtp.addTab("Turns detailed",jPanel2);
        //jtp.addTab("Lexicon",jPanel1);      
        //jtp.addTab("Contiguous Turns",jPanel3);
        //jtp.addTab("Window policy",jPanel5);
        
        jgch  = new JGroupsConversationHistories(this,cH,true);
        jtp.addTab("Turns by group", jgch);
        
        try{
            this.jppp = cH.getConversation().getController().pp.getJPanel();
            if(jppp!=null)jtp.addTab("Assign participants to groups", jppp);
        }catch (Exception e){
            e.printStackTrace();
            Conversation.printWSln("Main", "Could not create ParticipantPartneringPanel");
            
        }
        
        //jtp.addTab("Script console", console);
        
        JPollParticipant jpp = new JPollParticipant(c,this);
        jtp.addTab("Get feedback from participants", jpp);
        
         jtp.addTab("Interventions (rules)", jri);
        
        
        jtp.addTab("Interventions (manual)",this.jpi);
        
        
        
        
        return jtp;
       
    }

    
    
    
    
    
    
    /**
    * Returns the collection of components displaying the text as it is typed in the chat window.
    * @return Component displaying text typed as it is typed
    */
    public JClientEnterFieldsFrame createClientTextEntryFields(){
        //jcp = new JControlPanel();
         jceff = new JClientEnterFieldsFrame(this);
         jceff.setVisible(true);        
         return jceff;
    }
     
    
    /**
     * Updates the user interface with the information contained in each message. Not currently implemented
     * in order to preserve clarity in the user interface.
     * @param m
     */
    public void updateControlPanel(Message m){
          Participant origin = cH.getConversation().getParticipants().findParticipantWithEmail(m.getEmail());
          
    }
    
    
    
    /**
     * Updates all the tables with the information contained in a turn.
     * @param t turn to be added
     */
    public void updateUIWithTurnInfo(Turn t){
        if(this.c.getExpManager().emui==null)return;
        try{
            if(t instanceof DataToBeSaved){
                jgch.addTurn(t);  //Want to display this
            }
            else{
                jgch.addTurn(t);
                jtht.refresh();        
                jtlt.addRow(t);
                this.jpp.updateData();
                this.doScrolling();
            }
            
            
            //this.jppp.updateUI();
        }catch(Exception e){
            System.err.println("ERROR UPDATING UI IN CONVERSATIONMANAGER"+e.getMessage());
            e.printStackTrace();
        }
    }
    
  
    public void updateParticipantPartneringHasChanged(){
        SwingUtilities.invokeLater(new Runnable(){   
           public void run(){
                   if(jppp!=null)jppp.updateUI();
           }
        });
    }
    
    
   
    
   
    
    
    /**
     * Scrolls the window on receiving a new turn
     */
    private void doScrolling(){
      
      try{      
       SwingUtilities.invokeLater(new Runnable(){   
           public void run(){
              if(EMUI.doScrollingOfConversations){
                 js4.validate();
                 js4.repaint();
                 js4.getVerticalScrollBar().setValue(js4.getVerticalScrollBar().getMaximum()); 
                 js2.validate();
                 js2.repaint();
                 js2.getVerticalScrollBar().setValue(js2.getVerticalScrollBar().getMaximum());
              }
              
           }
       });   
      }catch (Exception e){
          System.err.println("Problem with autoscrolling the tables: " +e.getMessage().toString());
      }
    }
    
    
    
    
   
    /*
    /**
     * Displays the text being typed by the participant
     * @param mkp
     *
    public void updateChatToolTextEntryFieldsUI(MessageKeypressed mkp){
       
       if(jceff!=null)this.jceff.displayText(mkp.getEmail()+"-"+mkp.getUsername(),mkp.getContentsOfTextEntryWindow());
       
   }
   */ 
   /**
     * Displays the text being typed by the participant
     * @param p
     */ 
   public void updateChatToolTextEntryFieldsUI(Participant p){
       if(jceff!=null){
           this.jceff.displayText(p.getParticipantID()+"--"+p.getUsername(),c.getTurnBeingConstructed(p).getParsedText());
          
       }
   }
   
  public void updateChatToolTextEntryFieldsUI(String participantID, String username, String text){
       if(jceff!=null)this.jceff.displayText(participantID+"--"+username,text);
       
   }
   
   

   public void updateChatToolTextEntryFieldsUI(String name, String text){
       if(jceff!=null)this.jceff.displayText(name,text);
       // if(jpp!=null)jpp.updateData();
   }

   
   public void updateParticipantsListChanged(Vector v){
           if(jpi!=null)jpi.updateParticipants(v);
           if(jpp!=null)jpp.updateData();
           if(this.jppp!=null)jppp.updateData();
   }
   
   public void updatePINGPONGInfo(){
       if(jpp!=null)jpp.updateData();
   }
   
   
   
   /**
    * 
    * @return the identifier of the conversation
    */
   public String getConvID(){
       return c.getController().getID();
   }
   
   /**
    * 
    * @return the BeanShell interpreter
    */
   
   
   /**
    * Closes down the resources associated with the Conversation
    * @throws java.lang.Exception
    */
   public void closedown() throws Exception{
       //This needs to be done methodically. References are not destroyed properly.
       SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            
                   
          } 
       });
       
   }
    
}
