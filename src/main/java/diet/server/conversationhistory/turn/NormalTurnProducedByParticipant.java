/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.conversationhistory.turn;

import diet.attribval.AttribVal;
import diet.message.Keypress;
import diet.server.DocChangesIncomingSequenceFIFO;
import diet.server.conversationhistory.Conversant;
import diet.server.conversationhistory.ConversationHistory;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class NormalTurnProducedByParticipant extends Turn implements Serializable{
    
    
  
  
  
  
  
 
   
   
   
   
   
  private boolean wasBlockedDuringSending;
  private transient ConversationHistory cH;
  private long timeOnClientOfStartTyping = -999999;
  private long timeOnClientOfSending = -999999;
  private long timeOnServerOfReceipt = -999999; 
 
  
  
 
  
    
    public NormalTurnProducedByParticipant (ConversationHistory cH, String subdialogueID, long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfReceipt, String senderID, Conversant sender, Conversant apparentSender,String text,
          Vector recipients,boolean wasBlocked,Vector keyPresses,Vector documentUpdates, Vector clientinterfaceevents, Vector additionalStrings){
         this.senderID=senderID;
         super.subdialogueID = subdialogueID;
         this.timeOnClientOfStartTyping = timeOnClientOfStartTyping;
         this.timeOnClientOfSending = timeOnClientOfSending;
         this.timeOnServerOfReceipt = timeOnServerOfReceipt;
          
          this.sender=sender;
          super.apparentSender = apparentSender;
          this.text=text;
          this.recipients = recipients;
          this.wasBlockedDuringSending=wasBlocked;
          this.keyPresses = keyPresses;
          this.documentUpdates=documentUpdates;
         
          this.cH=cH;
         
          this.clientinterfaceevents=clientinterfaceevents;
          this.additionalValues=additionalStrings;
         
          
          
          //Calculate typing onset and enter of the turn
          
          
    }
    
   
  
    
    
   
    
    public Conversant getApparentSender(){
        return super.apparentSender;
    }
    public String getTextString(){
        return text;
    }
    public boolean getTypingWasBlockedDuringTyping(){
        return this.wasBlockedDuringSending;
    }
    
    public int getDocDeletes(){
        if(documentUpdates==null)return 0;
        return DocChangesIncomingSequenceFIFO.getTotalCharsDeleted(documentUpdates);
    }
    

    
    public int getKeypressDeletes(){ 
        if(keyPresses == null)return 0;
        int delCount=0;
        for (int i=0;i<keyPresses.size();i++){
            Keypress kp = (Keypress)keyPresses.elementAt(i);
            if (kp.isDel())delCount++;
        }
        return delCount;
    }
    
    public String getSenderID(){
       return this.senderID;
    }
    
    
    
    
    
      public ConversationHistory getCH() {
        return cH;
    }

    public void setCH(ConversationHistory cH) {
        this.cH = cH;
    }

   

    

   

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

   

    public boolean isWasBlockedDuringSending() {
        return wasBlockedDuringSending;
    }

    public void setWasBlockedDuringSending(boolean wasBlockedDuringSending) {
        this.wasBlockedDuringSending = wasBlockedDuringSending;
    }
    
   
    
            
    

    public long getTimeOnClientOfStartTyping() {
        return timeOnClientOfStartTyping;
    }

    public long getTimeOnClientOfSending() {
        return timeOnClientOfSending;
    }

    public long getTimeOnServerOfReceipt() {
        return timeOnServerOfReceipt;
    }
    
   
    
   
    
    

   
    
    
    
    
    
}

    
    
    

