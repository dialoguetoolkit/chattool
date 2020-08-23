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
public class FaceComms2016_TURNBYTURN_TRIADIC_NICK extends TurnByTurn_Triadic_ReferringToFaces{

    
    
     IsTypingOrNotTyping itntAandB = new IsTypingOrNotTyping(this);
    
    
    IsTypingOrNotTyping itntAandC = new IsTypingOrNotTyping(this);
    IsTypingOrNotTyping itntBandC = new IsTypingOrNotTyping(this);
    //TypingImpulseRecorder typingimpulser = new TypingImpulseRecorder(itntAandB);
    
    
   
    
    
    public FaceComms2016_TURNBYTURN_TRIADIC_NICK(Conversation c) {
        super(c);
        this.setID( "FACECOMMSTBYT_TRIADIC_NICK");
        this.itntAandB.setInactivityThreshold(1000);
        this.itntAandC.setInactivityThreshold(1000);
        this.itntBandC.setInactivityThreshold(1000);
    }

    
   // public boolean step1MessageHasBeenSentBySomeone = false;
    
    long delayTime = 1000;
    
    public void calculateNewDelayTime(){
       delayTime = (long)1000+ r.nextInt(3000);
       System.err.println("CALCULATING NEW DELAY TIME AS: "+delayTime);
    }
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
      
        
       
        
        
        
        if(sender == this.pA){
            this.itntAandC.processTurnSentByClient(sender);
            this.itntAandB.processTurnSentByClient(pA);
            c.relayTurnToPermittedParticipants(sender, mct);
            
        }
        else if (sender == this.pB){
            this.itntBandC.processTurnSentByClient(sender);
            this.itntAandB.processTurnSentByClient(pB);
            c.relayTurnToPermittedParticipants(sender, mct);
        }
        else if (sender ==this.pCExcluded){
             this.itntAandC.processTurnSentByClient(sender);
             this.itntBandC.processTurnSentByClient(sender);
             this.itntAandC.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
             this.itntBandC.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
             c.relayTurnToPermittedParticipants(sender, mct);
             this.calculateNewDelayTime();
        }  
       
        fctct.processChatText(sender, mct.getText());  
        
        
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
        Vector vAttribVals = super.getAdditionalInformationForParticipant(p);
        AttribVal av1 = new AttribVal("delaytime",""+itnt.getIsTypingTimeout());
        vAttribVals.addElement(av1);
        return  vAttribVals;
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
             this.itntAandB.processDoingsByClient(pA);
         }
         else if(sender==pB){
             itntBandC.processDoingsByClient(pB);
              this.itntAandB.processDoingsByClient(pB);
         }
         else if(sender==pCExcluded){
             if(this.performManipulation()){
                itntAandC.addSpoofTypingInfo(this.pCExcluded, new Date().getTime()+this.delayTime);
                itntBandC.addSpoofTypingInfo(this.pCExcluded, new Date().getTime()+this.delayTime);
             }
             else{
                 itntAandC.processDoingsByClient(pCExcluded);
                 itntBandC.processDoingsByClient(pCExcluded);
             }
         }
       
        
        
       
       
    
       
    }
    
   
    public static boolean showcCONGUI() {
        return false;
    }
    
}
