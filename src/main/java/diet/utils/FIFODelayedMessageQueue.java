/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils;

import diet.message.Message;
import diet.server.Participant;
import diet.server.Participants;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class FIFODelayedMessageQueue extends Thread{
    
    Vector<Object[]> v = new Vector();
    Participants ps;
    
    
    public FIFODelayedMessageQueue(Participants ps){
        this.ps=ps;
        start();
    }
    
    
    public synchronized void addItem(Message m, Participant recipient, long delay){
    
       Object[] om = new Object[3];
       om[0] =m;
       om[1]=recipient;
       om[2]=delay+ new Date().getTime();
       v.addElement(om);
       System.err.println("ADDING TO QUEUE ADDING TO QUEUE");
       notifyAll();
      
    }
    
    public synchronized void removeAllEnqueuedMessages(){
        this.v = new Vector();
        this.notifyAll();
    }
    
    
    public void run(){
        nextItemLoop();
    }
    
    
    
    
    boolean doLoop = true;
    public synchronized void nextItemLoop(){
        while(doLoop){
             try{
                 if(v.size()==0){
                     System.err.println("DURATIONOFSLEEP:FOREVER");
                     wait();
                 }
                 else{
                     Vector<Object[]> vsending = new Vector();
                     long timeToSleepTill = new Date().getTime()+60000;
                     long thresholdTime = new Date().getTime();
                     for(int i=0;i<v.size();i++){
                         Object[] om = v.elementAt(i);
                         if( (long) om[2] < (thresholdTime)){
                             vsending.addElement(om);
                             System.err.println("FOUND THING TO SEND: "+timeToSleepTill);
                         }
                         if ((long) om[2] < timeToSleepTill){
                             timeToSleepTill =       (long) om[2];
                             System.err.println("UPDATING TIMETOSLEEPTILL: "+timeToSleepTill);
                         }
                     }
                     long durationOfSleep =   timeToSleepTill - new Date().getTime();
                     if(durationOfSleep<1) durationOfSleep =1;
                     if(durationOfSleep>0) durationOfSleep =1;
                    
                     if(vsending.size()==0){
                         System.err.println("DURATIONOFSLEEP: "+durationOfSleep);
                         
                         wait(durationOfSleep+1);
                         System.err.println("DURATIONOFSLEEP NOW WOKEN UP AFTER: "+durationOfSleep);
                     }
                     
                     for(int i=0;i<vsending.size();i++){
                        
                          Object[] om = vsending.elementAt(i);
                          Participant recipient = (Participant)om[1];
                          Message m = (Message)om[0];
                          v.remove(om);
                          ps.sendMessageToParticipant(recipient, m);
                          
                     }
                     
                     
                 }
                 
             }catch(Exception e){
                 e.printStackTrace();
             }
            
        }
    }
    
    
}
