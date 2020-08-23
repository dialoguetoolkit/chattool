/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.Participant;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TurnByTurn_Triadic_ReferringToFaces_ArtificialIsTypingStatusIndicator extends TurnByTurn_Triadic_ReferringToFaces{

    
    
   
    //TypingImpulseRecorder typingimpulser = new TypingImpulseRecorder(itntAandB);
    
    
   
    
    
    public TurnByTurn_Triadic_ReferringToFaces_ArtificialIsTypingStatusIndicator(Conversation c) {
        super(c);
        this.setID( "FACECOMMSTBYT_TRIADIC_TALITA");
        this.itnt.setInactivityThreshold(1000);
    }

    
   // public boolean step1MessageHasBeenSentBySomeone = false;
    
   
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        //itnt.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
        
        if(sender == this.pA){
            itnt.processTurnSentByClient(pA);
            itnt.removeSpoofTypingInfoAfterThreshold(pCExcluded, new Date().getTime());
            c.relayTurnToPermittedParticipants(sender, mct);
            
        }
        else if (sender == this.pB){
            itnt.processTurnSentByClient(pB);
            itnt.removeSpoofTypingInfoAfterThreshold(pCExcluded, new Date().getTime());
            c.relayTurnToPermittedParticipants(sender, mct);
        }
        else if (sender ==this.pCExcluded){
             itnt.processTurnSentByClient(pCExcluded);
             itnt.removeSpoofTypingInfoAfterThreshold(pCExcluded, new Date().getTime());
             c.relayTurnToPermittedParticipants(sender, mct);
             
        }  
       
        fctct.processChatText(sender, mct.getText());  
        
        
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        if(this.getC().getParticipants().getAllParticipants().size()==3){
            this.itnt.addGroupWhoAreMutuallyInformedOfTyping(this.getC().getParticipants().getAllParticipants());
        }
     
    }
    
    
    
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        
        return super.getAdditionalInformationForParticipant(p);
    }

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         super.processKeyPress(sender, mkp); //To change body of generated methods, choose Tools | Templates.
         
          if(sender==pA){
             
             if(!mkp.isENTER())  doSpoofTypingByC();
            
         }
         else if(sender==pB){
            
          if(!mkp.isENTER())     doSpoofTypingByC();
         }
        
       
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
        super.processWYSIWYGTextRemoved(sender, mWYSIWYGkp);
       
       
         
        
    }

    public void doSpoofTypingByC(){
        long spoofTime = (long) r.nextInt(4000);
        
        itnt.addSpoofTypingInfo(pCExcluded, new Date().getTime()+spoofTime);
    }
    
    
    Participant previous;
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
       //// Interacting with C
        
     
        
       
        
        
       
       
    
       
    }
    
   
   
    
}
