/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import diet.message.Message;
import diet.message.MessageChatTextFromClient;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class ConnectionToServerOutgoingQueue extends Thread{
    
    Vector messages = new Vector();
    ConnectionToServer cts;
    
    public ConnectionToServerOutgoingQueue(ConnectionToServer cts){
        this.cts=cts;
        this.start();
    }
    
    public synchronized void addMessage(Message m){
        messages.addElement(m);
        //System.err.println("SAVING MESSAGE:");
        if(m instanceof MessageChatTextFromClient){
            MessageChatTextFromClient mctfc = (MessageChatTextFromClient)m;
            //System.err.println("SAVING MESSAGE IS: "+mctfc.getText());
        }
        
        this.notifyAll();
    }
    
    public void run(){
         this.sendMessages();
        
    }
    
    
    private synchronized void sendMessages(){
        while(2<5){
            try{
                this.wait(10000);
                if(messages.size()>0){
                    for(int i=0;i<messages.size();i++){
                        Message m = (Message)messages.elementAt(i);
                        cts.sendMessageFromOutgoingQueue(m);
                         ///System.err.println("SENDING MESSAGE:");
                         if(m instanceof MessageChatTextFromClient){
                             MessageChatTextFromClient mctfc = (MessageChatTextFromClient)m;
                            /// System.err.println("SENDING MESSAGE IS: "+mctfc.getText());
                         }
                        
                        
                    }
                    messages.removeAllElements();
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    
    
}
