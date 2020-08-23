/*
 * QueeBlockingForRecipientSortsIncomingMessages.java
 *
 * Created on 21 January 2008, 12:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server;
/*
 * QueueBlockingForRecipientSortsIncomingMessages.java
 *
 * Created on 21 January 2008, 12:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import java.util.Date;
import java.util.Vector;

import diet.message.Message;
import diet.message.MessageChatTextFromClient;
import diet.server.ConversationController.DefaultConversationController;
import java.util.Random;

/**
 * This is an implementation of a blocking queue for incoming messages. When it adds a message it attempts
 * to insert the message in the correct position in the queue.
 * @author user
 */
public class QueueBlockingForRecipientSortsIncomingMessages {
    
    /**
     * Creates a new instance of QueueBlockingForRecipientSortsIncomingMessages
     */
    private Vector incomingMessages = new Vector();
    
    public QueueBlockingForRecipientSortsIncomingMessages() {
    }
    
  
        
        
    /**
     * Adds message to the incoming queue, scanning through the messages in the queue to insert it at the
     * correct index.
     * 
     * @param m Message to be added to the queue
     */
    public synchronized void addMessageToIncomingQueue(Message m){
         
  /*      for(int i=0;i<incomingMessages.size();i++){
            Message m2 = (Message)incomingMessages.elementAt(i);
            if(m.getTimeStamp().before(m2.getTimeStamp())){
                incomingMessages.insertElementAt(m,i);
                return;
            }
      }  */
         if(m instanceof MessageChatTextFromClient && diet.debug.Debug.debugtimers ){    
                         ((MessageChatTextFromClient)m).saveTime("QUEUBLOCKING-PREADDING");
                     }
        incomingMessages.addElement(m);
        //System.out.println("NOTIFYING "+new Date().getTime());
        notifyAll();
        if(m instanceof MessageChatTextFromClient && diet.debug.Debug.debugtimers ){    
                         ((MessageChatTextFromClient)m).saveTime("QUEUBLOCKING-POSTNOTIFYING");
                     }
    }
    
    /**
     * Non-blocking
     * 
     * @return null if empty| first message in the queue
     */
    synchronized public Message getNextMessageNonBlocking(){
        if(incomingMessages.size()!=0){
            Message firstMessage= (Message) incomingMessages.elementAt(0);
            incomingMessages.remove(firstMessage);
            


            return firstMessage;  
        }
        return null;
    }
    
    /**
     * Blocking 
     * @return first message in the queue
     */
    synchronized public Message getNextMessageBlocking(){
       if(DefaultConversationController.sett.debug_debugTime)Conversation.statC.debug_SaveEvent("C");
       while(incomingMessages.size()==0){
            if(DefaultConversationController.sett.debug_debugTime)Conversation.statC.debug_SaveEvent("D-SLEEPING");
           try{
             
             wait();
             if(DefaultConversationController.sett.debug_debugTime)Conversation.statC.debug_SaveEvent("E-WOKEN");
           }catch(Exception e){
               System.out.println("QueueBlockingForRecipient WOKEN UP: "+new Date().getTime());
               if(DefaultConversationController.sett.debug_debugTime)Conversation.statC.debug_SaveEvent("F");
           }  
       }
       if(DefaultConversationController.sett.debug_debugTime)Conversation.statC.debug_SaveEvent("G");

       Message firstObject= (Message)incomingMessages.elementAt(0);
       if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("MCT4957");System.out.flush();}
       incomingMessages.remove(firstObject);
       if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("MCT4958");System.out.flush();}
       
       if(DefaultConversationController.sett.debug_debugMESSAGEBLOCKAGE){System.out.println("MCT4959");System.out.flush();}
       return firstObject;  
    }

    Random r = new Random();
}
