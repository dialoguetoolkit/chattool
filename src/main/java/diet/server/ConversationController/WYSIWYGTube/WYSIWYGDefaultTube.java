/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.WYSIWYGTube;

import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.WYSIWYG_Dyadic_Artificial_Turn;
import diet.server.Participant;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class WYSIWYGDefaultTube extends Thread implements WYSIWYGTube{
          
         Vector<MessageWYSIWYGDocumentSyncFromClientInsert> incoming = new Vector();
         Vector<Participant> incoming_senders = new Vector();
         
         
         WYSIWYG_Dyadic_Artificial_Turn dw;
         //Hashtable<MessageWYSIWYGDocumentSyncFromClientInsert,Participant> ht = new Hashtable();
         
         public WYSIWYGDefaultTube(WYSIWYG_Dyadic_Artificial_Turn dw){
             this.dw=dw;
             this.start();
         }
         
         public synchronized void add(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert){
             incoming.addElement( mWYSIWYGInsert);
             incoming_senders.add(sender);
             
             //ht.put(mWYSIWYGInsert, sender);
             notifyAll();
         }
         
         
         public void run(){
             doloop();
         }
         private synchronized void doloop(){
             while(2<5){
                 try{
                     wait(); 
                     while (incoming.size()>0){
                         MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert = incoming.elementAt(0);
                         Participant sender = this.incoming_senders.elementAt(0);
                         
                         dw.processWYSIWYGTextInserted_AfterManipulationByQueue(sender, mWYSIWYGInsert);
                         incoming.removeElementAt(0);
                         incoming_senders.removeElementAt(0);
                     }
                     
                 }catch(Exception e){
                     e.printStackTrace();
                 }
             }
         }
         
     }
