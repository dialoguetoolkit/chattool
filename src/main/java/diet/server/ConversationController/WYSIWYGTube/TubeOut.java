/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.WYSIWYGTube;

import diet.server.ConversationController.WYSIWYGTube.Content.TubeFakeInsertedText;
import diet.server.ConversationController.WYSIWYGTube.Content.TubeControlFakeDelay;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.WYSIWYG_Dyadic_Artificial_Turn;
import diet.server.Participant;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class TubeOut extends Thread{

   
    
        public int maxDelayBetweenCharactersWhenFlushingBufferPostIntervention = 300;
        public int minDelayBetweenCharactersWhenFlushingBufferPostIntervention = 50;

    public int getMaxDelayBetweenCharactersWhenFlushingBufferPostIntervention() {
        return maxDelayBetweenCharactersWhenFlushingBufferPostIntervention;
    }

    public void setMaxDelayBetweenCharactersWhenFlushingBufferPostIntervention(int maxDelayBetweenCharactersWhenFlushingBufferPostIntervention) {
        this.maxDelayBetweenCharactersWhenFlushingBufferPostIntervention = maxDelayBetweenCharactersWhenFlushingBufferPostIntervention;
    }

    public int getMinDelayBetweenCharactersWhenFlushingBufferPostIntervention() {
        return minDelayBetweenCharactersWhenFlushingBufferPostIntervention;
    }

    public void setMinDelayBetweenCharactersWhenFlushingBufferPostIntervention(int minDelayBetweenCharactersWhenFlushingBufferPostIntervention) {
        this.minDelayBetweenCharactersWhenFlushingBufferPostIntervention = minDelayBetweenCharactersWhenFlushingBufferPostIntervention;
    }
         
    
    
    
         public Vector incoming = new Vector();
         public Vector<Participant> incoming_senders = new Vector();
         
         
         public WYSIWYG_Dyadic_Artificial_Turn dw;
         //Hashtable<MessageWYSIWYGDocumentSyncFromClientInsert,Participant> ht = new Hashtable();
         
    
         
         
         
         public TubeOut(WYSIWYG_Dyadic_Artificial_Turn dw){
             this.dw=dw;
             this.start();
         }
         
         public synchronized void add(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert){
             
             
             //ht.put(mWYSIWYGInsert, sender);
             checkIfDelayNeedsTobeAdded();
             incoming.addElement( mWYSIWYGInsert);
             incoming_senders.add(sender);
             notifyAll();
         }
         
         public synchronized void addFakeTurn( TubeFakeInsertedText wfi){
              incoming.addElement( wfi);
              incoming_senders.add(new Participant(null,"server","server")); //This is a hack!  
         }
         
         
         public void checkIfDelayNeedsTobeAdded(){
             if(incoming.size()==0){
                 isTailEndOfIntervention = false;
                 System.err.println("TAIL END: FALSE");
             }  
             else if(incoming.lastElement() instanceof TubeFakeInsertedText){
                 isTailEndOfIntervention = true;
                 System.err.println("TAIL END: TRUE");
             }
             if(isTailEndOfIntervention){
                 long gap =(long) this.minDelayBetweenCharactersWhenFlushingBufferPostIntervention+ r.nextInt(this.maxDelayBetweenCharactersWhenFlushingBufferPostIntervention-this.minDelayBetweenCharactersWhenFlushingBufferPostIntervention);                            
                 TubeControlFakeDelay fd = new TubeControlFakeDelay();
                 fd.delay=gap;
                 incoming.add(fd);
                 incoming_senders.add(new Participant(null,"server","server")); //This is a hack!  
                 System.err.println("TAIL END: ADD");
             }
             displayTube();
         }
         
         public void displayTube(){
             for(int i=0;i<incoming.size();i++){
                 Object o = incoming.elementAt(i);
                 if(o instanceof TubeFakeInsertedText){
                     System.err.println("TUBECONTENTS(tfi): "+   ((TubeFakeInsertedText)o).text      );
                 }
                 else if (o instanceof MessageWYSIWYGDocumentSyncFromClientInsert){
                    MessageWYSIWYGDocumentSyncFromClientInsert mW = (MessageWYSIWYGDocumentSyncFromClientInsert)o;
                     System.err.println("TUBECONTENTS(mWY): "+   mW.getTextToAppendToWindow()      );
                 }
             }
         }
         
         
         
         public boolean isTailEndOfIntervention = false;
         
         
         
         public void run(){
             doloop();
         }
         
         
         //boolean processingintervention=false;
         
         Random r = new Random();
         
          TubeFakeInsertedText mostRecentIntervention = null;
         
         
         private synchronized void doloop(){
             while(2<5){
                 try{
                     wait(); 
                     System.err.println("CHECKING TO SEE IF BIGGER THAN 0");
                     while (incoming.size()>0){
                         System.err.println("BIGGER THAN.."+incoming.size());
                         Object o = incoming.elementAt(0);
                         if(o instanceof  MessageWYSIWYGDocumentSyncFromClientInsert){
                              
                              
                              
                              MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert = (MessageWYSIWYGDocumentSyncFromClientInsert)o;
                              Participant sender = this.incoming_senders.elementAt(0);
                             
                              System.err.println(mWYSIWYGInsert.getTimeOfReceipt());
                              
                              
                              dw.processWYSIWYGTextInserted_AfterManipulationByQueue(sender, mWYSIWYGInsert);
                              incoming.removeElementAt(0);
                              incoming_senders.removeElementAt(0);
                              
                             
                              
                              
                         }
                         else if (o instanceof TubeFakeInsertedText ){
                             
                             //System.exit(-567);
                             TubeFakeInsertedText wfi = (TubeFakeInsertedText )o;
                             mostRecentIntervention = wfi;
                             long wakeUpTime = new Date().getTime()+wfi.delayBeforeSending;
                             
                             try{
                                  while(new Date().getTime()<wakeUpTime){
                                      long timeRemaining = wakeUpTime-new Date().getTime();
                                      if(timeRemaining<1)timeRemaining=1;
                                      wait(timeRemaining);
                                  }
                                 
                                 
                             }catch(Exception e){
                                 e.printStackTrace();
                             }
                             
                             
                             dw.processWYSIWYGTextInserted_AfterManipulationByQueue(wfi);
                             incoming.removeElementAt(0);
                             incoming_senders.removeElementAt(0);
                             
                             //Now need to add delays...
                             
                             
                             
                         }
                         else if (o instanceof TubeControlFakeDelay){
                             // System.exit(-567);

                             TubeControlFakeDelay wfd = (TubeControlFakeDelay )o;
                             long wakeUpTime = new Date().getTime()+wfd.delay;
                             
                             try{
                                  while(new Date().getTime()<wakeUpTime){
                                      long timeRemaining = wakeUpTime-new Date().getTime();
                                      if(timeRemaining<1)timeRemaining=1;
                                      //System.exit(-56);
                                      wait(timeRemaining);
                                  }
                                 
                                 
                             }catch(Exception e){
                                 e.printStackTrace();
                             }
                             incoming.removeElementAt(0);
                             incoming_senders.removeElementAt(0);
                         }
                         else{
                             System.err.println(o.getClass().toString());
                         }
                         
                              
                        
                     }
                     
                 }catch(Exception e){
                     e.printStackTrace();
                 }
             }
         }
         
     }

    
