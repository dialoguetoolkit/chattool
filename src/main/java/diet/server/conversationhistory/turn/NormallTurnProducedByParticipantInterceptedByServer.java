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
public class NormallTurnProducedByParticipantInterceptedByServer extends NormalTurnProducedByParticipant implements Serializable{

    public NormallTurnProducedByParticipantInterceptedByServer(ConversationHistory cH, String subdialogueID, long timeOnClientOfStartTyping, long timeOnClientOfSending, long timeOnServerOfReceipt, String senderID, Conversant sender, Conversant apparentSender, String text, Vector recipients, boolean wasBlocked, Vector keyPresses, Vector documentUpdates, Vector clientInterfaceEvents, Vector additionalStrings) {
        super(cH, subdialogueID, timeOnClientOfStartTyping, timeOnClientOfSending, timeOnServerOfReceipt, senderID, sender, apparentSender, text, recipients, wasBlocked, keyPresses, documentUpdates,clientInterfaceEvents, additionalStrings);
    }
    
    
 
    
    

    public Vector getRecipients(){
        return new Vector();
    }
 
    
   
    
    
    

    
    
    
   
    
    
    
    
    

  

    
    
    
    
    
}

    
    
    

