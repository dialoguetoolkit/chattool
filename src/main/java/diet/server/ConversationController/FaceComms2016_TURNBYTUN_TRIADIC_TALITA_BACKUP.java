/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.IsTypingController.TypingImpulseRecorder;
import diet.server.Participant;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class FaceComms2016_TURNBYTUN_TRIADIC_TALITA_BACKUP extends TurnByTurn_Triadic_ReferringToFaces{

    
    
    IsTypingOrNotTyping itntAandB = new IsTypingOrNotTyping(this);
    IsTypingOrNotTyping itntAandC = new IsTypingOrNotTyping(this);
    IsTypingOrNotTyping itntBandC = new IsTypingOrNotTyping(this);
    TypingImpulseRecorder typingimpulser = new TypingImpulseRecorder(itntAandB);
    
    
   
    
    
    public FaceComms2016_TURNBYTUN_TRIADIC_TALITA_BACKUP(Conversation c) {
        super(c);
        this.setID( "FACECOMMSTBYT_TRIADIC_TALITA");
    }

    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        //itnt.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
        if(sender == this.pA){
            this.itntAandC.processTurnSentByClient(sender);
            
        }
        else if (sender == this.pB){
            this.itntBandC.processTurnSentByClient(sender);
        }
        else if (sender ==this.pCExcluded){
             this.itntAandC.processTurnSentByClient(sender);
             this.itntBandC.processTurnSentByClient(sender);
        }
        itnt.processTurnSentByClient(sender);
        c.relayTurnToPermittedParticipants(sender, mct);
        fctct.processChatText(sender, mct.getText());  
        if(sender ==pA || sender ==pB){
            this.itntAandB.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
       }
        
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        if(c.getParticipants().getAllParticipants().size()==3){
             itntAandC.addPairWhoAreMutuallyInformedOfTyping(pA, pCExcluded);
             itntBandC.addPairWhoAreMutuallyInformedOfTyping(pB, pCExcluded);
             itntAandB.addPairWhoAreMutuallyInformedOfTyping(pA, pB);
        }
     
    }
    
    
    
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        return super.getAdditionalInformationForParticipant(p);
    }

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         super.processKeyPress(sender, mkp); //To change body of generated methods, choose Tools | Templates.
         
         
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
        super.processWYSIWYGTextRemoved(sender, mWYSIWYGkp);
       
       
         
        
    }

    
    
    Participant previous;
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
       //// Interacting with C
        
        if(sender==pA){
             itntAandC.processDoingsByClient(pA);
         }
         else if(sender==pB){
             itntBandC.processDoingsByClient(pB);
         }
         else if(sender==pCExcluded){
             itntAandC.processDoingsByClient(pCExcluded);
             itntBandC.processDoingsByClient(pCExcluded);
         }
       
        
        
       ///// A and B's conversational interaction 
       if(previous == pA && sender == pB){
            this.typingimpulser.startFakeTyping(pA, pB);
            this.typingimpulser.clearStoredImpulsesProducedByOrigin(pB);
        }
        if(previous == pB && sender == pA){
            this.typingimpulser.startFakeTyping(pB, pA);
            this.typingimpulser.clearStoredImpulsesProducedByOrigin(pA);
            
        }
        if(sender == this.pA){
            this.typingimpulser.addTypingImpulse(sender,mWYSIWYGkp);
            if(!this.itntAandB.checkIfParticipantStillHasFakeTypingEnqueued(pA)){
                this.typingimpulser.startFakeTyping(pB, pA); //Start again spoofing the sequence of keypresses
            }
               
        }
        if(sender == this.pB){
            this.typingimpulser.addTypingImpulse(sender,mWYSIWYGkp);
            if(!this.itntAandB.checkIfParticipantStillHasFakeTypingEnqueued(pB)){
                this.typingimpulser.startFakeTyping(pA, pB); //Start again spoofing the sequence of keypresses
            }
        }
      
        previous =sender;
       
       
       
    }
    
     static public boolean showcCONGUI(){
         return false;
     }
   
    
}
