/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController.obsoltebucket;

import diet.client.ClientInterfaceEvents.ClientInterfaceEvent;
import diet.client.ClientInterfaceEvents.ClientInterfaceEventStringPrettifier;
import diet.debug.Debug;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.TurnByTurn_Dyadic;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.IsTypingController.TypingImpulseRecorder;
import diet.server.Participant;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.text.MutableAttributeSet;

/**
 *
 * @author gj
 */
public class TestController extends TurnByTurn_Dyadic{

    public TestController(Conversation c) {
        super(c);
        DefaultConversationController.sett.login_numberOfParticipants=2;
    }

        
    IsTypingOrNotTyping itntAandB = new IsTypingOrNotTyping(this);
    IsTypingOrNotTyping itntAandC = new IsTypingOrNotTyping(this);
    IsTypingOrNotTyping itntBandC = new IsTypingOrNotTyping(this);
   TypingImpulseRecorder typingimpulser = new TypingImpulseRecorder(itntAandB);
    
    
   Participant pA;
    Participant pB;
     
    
   

   

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        //super.participantJoinedConversation(p);
        if(c.getParticipants().getAllParticipants().size()==2){
             this.pA= (Participant)c.getParticipants().getAllParticipants().elementAt(0);
             this.pB= (Participant)c.getParticipants().getAllParticipants().elementAt(1);
             pp.createNewSubdialogue(pA,pB);
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
         if(sender==pA){
             itntAandC.processDoingsByClient(pA);
         }
         else if(sender==pB){
             itntBandC.processDoingsByClient(pB);
         }
     
         
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
        super.processWYSIWYGTextRemoved(sender, mWYSIWYGkp);
       
       
         
        
    }

    
     @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        //itnt.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
        //c.newrelayTurnToPermittedParticipants(sender, mct);
        c.sendArtificialDelayedTurnToPermittedParticipants(sender, mct.getText(), 3000);
       
        if(2<5)return;
        
        if(sender ==pA || sender ==pB){
            this.itntAandB.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
       }
       c.relayTurnToPermittedParticipants(sender, mct);
        
      String[] newOptions = {"yes","no"}; 
       
      c.showPopupOnClientQueryInfo(""+new Date().getTime(), sender, "This is a yes/no question", newOptions, "This is a title", 0);
    }
    
    
    Participant previous;
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
       
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
    
       
       public static boolean showcCONGUI(){
        return false;
    }     
   
    
}

    
    
    