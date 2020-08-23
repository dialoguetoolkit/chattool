/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.IsTypingController;

import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TypingImpulseRecorder {
    //public Vector typingImpulses = new Vector();
    
    HashtableWithDefaultvalue ht_typingImpulses = new HashtableWithDefaultvalue(new Vector());
    
    
    //DefaultConversationController cC;
    IsTypingOrNotTyping itnt;

    public TypingImpulseRecorder(IsTypingOrNotTyping itnt) {
        this.itnt=itnt;
    }
    
    
    
    public synchronized void addTypingImpulse(Participant p, diet.message.MessageWYSIWYGDocumentSyncFromClientInsert mdsfci){
        Vector  vt = (Vector)this.ht_typingImpulses.getObject(p);
        vt.add(mdsfci);
        this.ht_typingImpulses.putObject(p, vt);
    }
    
    public synchronized void stopFaketyping(Participant apparentOrigin){
        itnt.removeSpoofTypingInfoAfterThreshold(apparentOrigin, new Date().getTime());
    }
    
    public synchronized void startFakeTyping(Participant origin, Participant apparentOrigin){
        Vector  vt = (Vector)this.ht_typingImpulses.getObject(origin);
        if(vt.size()==0)return;
        long timeOfLastFakeSpoof = new Date().getTime();
        itnt.addSpoofTypingInfo(apparentOrigin, timeOfLastFakeSpoof);
        for(int i=1;i<vt.size();i++){
             System.err.println("ADDINGFAKEVTPLUS:"+i);
             MessageWYSIWYGDocumentSyncFromClientInsert previousInsert = (MessageWYSIWYGDocumentSyncFromClientInsert)vt.elementAt(i-1);
             MessageWYSIWYGDocumentSyncFromClientInsert currentInsert = (MessageWYSIWYGDocumentSyncFromClientInsert)vt.elementAt(i);
             long timesinceprevious = currentInsert.getTimeOfSending().getTime()-previousInsert.getTimeOfSending().getTime();
             
             timeOfLastFakeSpoof = timeOfLastFakeSpoof + timesinceprevious;
             //cC.itnt.addSpoofTypingInfo(apparentOrigin, startTimeOnServer+timesincefirst);
             itnt.addSpoofTypingInfo(apparentOrigin, timeOfLastFakeSpoof);
        }
       
    }
    
    public void clearStoredImpulsesProducedByOrigin(Participant producer){
         this.ht_typingImpulses.putObject(producer, new Vector());   
    }
    
   
    
    
}
