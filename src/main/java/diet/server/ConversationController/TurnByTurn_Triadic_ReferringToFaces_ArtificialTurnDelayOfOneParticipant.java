/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.message.MessageButtonPressFromClient;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.Participant;
import java.util.Date;

/**
 *
 * @author gj
 */
public class TurnByTurn_Triadic_ReferringToFaces_ArtificialTurnDelayOfOneParticipant extends TurnByTurn_Triadic_ReferringToFaces{

    public IsTypingOrNotTyping itntDelayed = new IsTypingOrNotTyping(this);
   
    
     long delayOfC = 3000;
    
    
    public TurnByTurn_Triadic_ReferringToFaces_ArtificialTurnDelayOfOneParticipant(Conversation c) {
        super(c);
        itntDelayed.setInactivityThreshold(1000);
        this.itnt.setInactivityThreshold(1000);
    }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
          if(diet.debug.Debug.debugIO){
                           
                            diet.debug.Debug.showDebug2("\n----processchattext:"+ mct.getText() +    mct.keyPresses.size());
                           
                       
             }
        
        
        
        if(sender == this.pA){
            this.itnt.processTurnSentByClient(pA);
             c.relayTurnToPermittedParticipants(sender, mct);
        }
        else if (sender == this.pB){
             this.itnt.processTurnSentByClient(pB);
             c.relayTurnToPermittedParticipants(sender, mct);
        }
        else if (sender ==this.pCExcluded){
            if (this.performManipulation()){
               this.c.sendArtificialDelayedTurnToPermittedParticipants(sender, mct.getText(), delayOfC);
               this.itnt.removeSpoofTypingInfoAfterThreshold(pA, new Date().getTime()+this.delayOfC+1);
            }
            else{
                c.relayTurnToPermittedParticipants(sender, mct);
            }
        }
        //itnt.processTurnSentByClient(sender);
        //c.newrelayTurnToPermittedParticipants(sender, mct);
        
        boolean wentToNextTrial = fctct.processChatText(sender, mct.getText());  
        if(wentToNextTrial){
            this.itntDelayed.removeSpoofTypingInfoAfterThreshold(this.pCExcluded, new Date().getTime());
            this.c.removeAllDelayedMessages();
        }
        
        //c.newDelayedRelayTurnToPermittedParticipants(sender, mct, 2000);
        
    }
    
   
    
    

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
       c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        
         if(c.getParticipants().getAllParticipants().size()==3){
            pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
           
            int choiceOfGroup =r.nextInt(3);
            if(choiceOfGroup==0){
                pCExcluded = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
                pA = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
                pB = (Participant)c.getParticipants().getAllParticipants().elementAt(2);
            }
            else if(choiceOfGroup==1){
                pCExcluded = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
                pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
                pB = (Participant)c.getParticipants().getAllParticipants().elementAt(2);
            }
            else if(choiceOfGroup==2){
                pCExcluded = (Participant)c.getParticipants().getAllParticipants().elementAt(2);
                pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
                pB = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
                
            }
            CustomDialog.showDialog("START EXPERIMENT");
            this.experimentHasStarted=true;
            fctct.startTask(pA, pB, pCExcluded);    
         }
        
        if(c.getParticipants().getAllParticipants().size()==3){
            this.itntDelayed.addRecipientOfTypingInformation( this.pCExcluded , pA);
            this.itntDelayed.addRecipientOfTypingInformation( this.pCExcluded , pB);
            
            this.itnt.addPairWhoAreMutuallyInformedOfTyping(pA, pB);
            this.itnt.addRecipientOfTypingInformation(pA, pCExcluded);
            this.itnt.addRecipientOfTypingInformation(pB, pCExcluded);
        }
        
    }
    
    
    
    
    

    
    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         if(sender==this.pA  || sender==this.pB){
             super.processKeyPress(sender, mkp);
         } 
         else{
             if(!mkp.isENTER()){
                 this.itntDelayed.addSpoofTypingInfo(sender, new Date().getTime()+this.delayOfC);
             }
         }
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
       // itnt.processDoingsByClient(sender);  
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
      
    }
  
   

    
     @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
       // this.fctc.processChatText(sender, "/"+mbfc.buttonname.substring(1));
    }
    
}
