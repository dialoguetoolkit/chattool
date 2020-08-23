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
public class ArtificialTurnProducedByServer extends Turn implements Serializable{
    
    
  
  
 
  private long timeOnServerOfSending = -999999; 
  
 
  
    
    public ArtificialTurnProducedByServer (ConversationHistory cH, String subdialogueID,  long timeOnServerOfSending,  Conversant sender, String senderID, Conversant apparentSender,String text,
          Vector recipients, Vector<AttribVal> additionalValues){
          this.subdialogueID=subdialogueID; 
        this.senderID=senderID;
         this.subdialogueID=subdialogueID;
         this.timeOnServerOfSending=timeOnServerOfSending;
          
          this.sender=sender;
          super.apparentSender = apparentSender;
          this.text=text;
          this.recipients = recipients;
         
          super.cH=cH;
         
         
         this.additionalValues=additionalValues;
         
          
          
          //Calculate typing onset and enter of the turn
          
          
    }
    
    
    
   
    
    public long getTimeOnServerOfSending(){
        return this.timeOnServerOfSending;
    }
    
    
    /**
     * Returns all the recipients of the turn as a vector of {@link Conversant}  
     * @return
     */
    public Vector getRecipients(){
        return recipients;
    }
    
    public Conversant getSender(){
        return sender;
    }
    
    public Conversant getApparentSender(){
        return super.apparentSender;
    }
    public String getTextString(){
        return text;
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

   
    
  
    
   
    
   
    
   
    
   

    
    
    
    
    
}

    
    
    

