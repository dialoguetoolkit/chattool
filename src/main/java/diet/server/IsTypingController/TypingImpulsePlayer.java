/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.IsTypingController;

import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class TypingImpulsePlayer {
    
      
    public Vector typingImpulses = new Vector();
    Participant p;
    DefaultConversationController cC;

    public TypingImpulsePlayer( DefaultConversationController cC, Participant p, Vector newimpulses) {
        this.p = p;
        this.cC = cC;
        this.typingImpulses=newimpulses;
    }
    
  /*  
    private boolean dofaketyping = true;
    
    public synchronized void doFakeTyping(){
         for(int i=0;i<typingImpulses.size()-1;i++){
              try{
                        MessageWYSIWYGDocumentSyncFromClientInsert mwdsfci = (MessageWYSIWYGDocumentSyncFromClientInsert)v.elementAt(i); 
                        MessageWYSIWYGDocumentSyncFromClientInsert mwdsfciNEXT = (MessageWYSIWYGDocumentSyncFromClientInsert)v.elementAt(i+1); 
                        long timeTosleep = mwdsfciNEXT.getTimeOfSending().getTime() - mwdsfci.getTimeOfSending().getTime();
                        wait(timeTosleep);
                        if(dofaketyping){
                            cC.itnt.addSpoofTypingInfo(p, new Date());
                        }
                        else{
                            return;
                        }
                    }catch(Exception e){
                          e.printStackTrace();
                     }
              
         }
    }
    
    */
    
    
    
      
       public synchronized void fakeTyping(Vector<MessageWYSIWYGDocumentSyncFromClientInsert> v1){
        Vector<MessageWYSIWYGDocumentSyncFromClientInsert> v =(Vector<MessageWYSIWYGDocumentSyncFromClientInsert>) v1.clone();
        
      
             for(int i=0;i<v.size()-1;i++){
                    
                    try{
                        MessageWYSIWYGDocumentSyncFromClientInsert mwdsfci = (MessageWYSIWYGDocumentSyncFromClientInsert)v.elementAt(i); 
                        MessageWYSIWYGDocumentSyncFromClientInsert mwdsfciNEXT = (MessageWYSIWYGDocumentSyncFromClientInsert)v.elementAt(i+1); 
                        long timeTosleep = mwdsfciNEXT.getTimeOfSending().getTime() - mwdsfci.getTimeOfSending().getTime();
                        wait(timeTosleep);
                       // if(dofakeTyping){
                            
                       // }   
                    }catch(Exception e){
                          e.printStackTrace();
                     }
              
             }
    }
    
}
