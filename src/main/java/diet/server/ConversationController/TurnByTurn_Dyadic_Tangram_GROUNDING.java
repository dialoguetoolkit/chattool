/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.JointReference.JointReferenceTaskTwoStages;

/**
 *
 * @author LX1C
 */
public class TurnByTurn_Dyadic_Tangram_GROUNDING extends DefaultConversationController{

    JointReferenceTaskTwoStages jrtc = new JointReferenceTaskTwoStages(this, 5000);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public TurnByTurn_Dyadic_Tangram_GROUNDING(Conversation c) {
        super(c);
    }

    public TurnByTurn_Dyadic_Tangram_GROUNDING(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
    }

    
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        
        if(mct.getText().startsWith("/")){
            
                jrtc.processChatText(sender, mct.getText());
          
        
        }
        else{
            
             itnt.processTurnSentByClient(sender);
             c.relayTurnToPermittedParticipants(sender, mct);
        }
        
        
       
        
       
    }
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p); 
        
       
        if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
             this.itnt.addPairWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants().elementAt(0), c.getParticipants().getAllParticipants().elementAt(1));
             
             
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             jrtc.startTask((Participant)c.getParticipants().getAllOtherParticipants(p).elementAt(0), p);
             //c.changeJProgressBar(pDirector, "CHATFRAME", "text", Color.red, 50);
             //c.changeJProgressBar(pMatcher, "CHATFRAME", "text", Color.red, 50);
        }
        
        
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p); 
        jrtc.participantRejoined(p);
        
    }
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
