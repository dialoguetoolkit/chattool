/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server;

import diet.message.Message;
import diet.message.MessageChangeClientInterfaceProperties;
import diet.message.MessageChatTextFromClient;
import java.util.Date;

/**
 *
 * @author gj
 */
public class Metrics {
    
    Conversation c;
    long numberOfOverruns = 0;
    
    
    
    public Metrics(Conversation c){
        this.c=c;
    }
    
    
    
   
    
    public void registerLoadingNextMessage(Message m){
        try{
        ExperimentManager em = c.getExpManager();
        if(em==null)return;
        if(em.emui ==null)return;
        if(em.emui.getJMF()==null)return;
        
        if( m instanceof MessageChatTextFromClient  || 
            m instanceof diet.message.MessageWYSIWYGDocumentSyncFromClientInsert || 
            m instanceof diet.message.MessageWYSIWYGDocumentSyncFromClientRemove ||
            m instanceof diet.message.MessageKeypressed ){
                long turnaroundTime = new Date().getTime()-timeOfReceiptOfMostRecentMessage;
                if(turnaroundTime>this.longestProcessingTimeByServer){
                    longestProcessingTimeByServer = turnaroundTime;
                    em.emui.getJMF().jsb.setMaxProcessingTime(""+turnaroundTime+ " m.s.");     
                }
                if(turnaroundTime > this.c.getController().sett.debug_logging_of_OverrunTime_threshold){
                    numberOfOverruns++;
                    em.emui.getJMF().jsb.setOverrun(""+numberOfOverruns);
                }
            
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    long timeOfReceiptOfMostRecentMessage = new Date().getTime();
    long longestProcessingTimeByServer =0;
    
    long messagesReceived = 0;
    long messagesSent =0;
    
    public void registerIncomingMessage(Message m){  
       timeOfReceiptOfMostRecentMessage = new Date().getTime();
       messagesReceived = messagesReceived +1;
       ExperimentManager em = c.getExpManager();
       if(em==null)return;
       if(em.emui ==null)return;
       if(em.emui.getJMF()==null)return;
       em.emui.getJMF().jsb.setMessagesReceived(""+messagesReceived);  
    }
    
    public void registerSendingMessage(Message m){
      
       if(m instanceof diet.message.MessageChangeClientInterfaceProperties){
           MessageChangeClientInterfaceProperties mccip = (MessageChangeClientInterfaceProperties)m;
           //System.err.println("CHANGE:    ID:"+mccip.idToConfirm+": IPROPERTIES:"+mccip.getInterfaceproperties()+"   VALUE1"+mccip.getValue());
           
       }
        
        
       messagesSent = messagesSent+1;
       ExperimentManager em = c.getExpManager();
       if(em==null)return;
       if(em.emui ==null)return;
       if(em.emui.getJMF()==null)return;
       em.emui.getJMF().jsb.setMessagesSent(""+messagesSent);  
      
        
    }
    
    long numberOfDisconnections =0;
    public void registerDisconnection(){
       numberOfDisconnections = numberOfDisconnections+1;
       ExperimentManager em = c.getExpManager();
       if(em==null)return;
       if(em.emui ==null)return;
       if(em.emui.getJMF()==null)return;
       em.emui.getJMF().jsb.setNumberOfDisconnects(""+numberOfDisconnections);  
    }
    
    public void updateParticipants(int numberOfParticipants){
        ExperimentManager em = c.getExpManager();
       if(em==null)return;
       if(em.emui ==null)return;
       if(em.emui.getJMF()==null)return;
       if(em.emui.getJMF().jsb==null)return;
       em.emui.getJMF().jsb.setNumberOfParticipants(""+numberOfParticipants);  
    }
    
    
    
    long maxPingPong =-10;
    int pingpongoverruns =0;
    
    public void registerPINGPONG(Participant p, long timeOnServerOfSending, long timeOnServerOfReceipt){
        ExperimentManager em = c.getExpManager();
       if(em==null)return;
       if(em.emui ==null)return;
       if(em.emui.getJMF()==null)return;
       if(em.emui.getJMF().jsb==null)return;
        
        
        long pingpong = timeOnServerOfReceipt - timeOnServerOfSending;
       
         if(pingpong>maxPingPong) {
             maxPingPong = pingpong;   
                  em.emui.getJMF().jsb.setRoundtripMax(""+maxPingPong);
         }
         if(pingpong>this.c.getController().sett.debug_logging_of_OverrunPINGPONG) {
             pingpongoverruns++;
                  em.emui.getJMF().jsb.setRoundtripOverruns(""+pingpongoverruns);
         }
         
    }
    
    
}
