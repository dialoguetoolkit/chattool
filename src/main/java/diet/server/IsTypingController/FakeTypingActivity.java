/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.IsTypingController;

import diet.server.Participant;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class FakeTypingActivity extends Thread {
    
     Vector fakeactivity = new Vector();
     IsTypingOrNotTyping itnt;
     
     public FakeTypingActivity(IsTypingOrNotTyping itnt){
         this.itnt=itnt;
         this.start();
     }
     
     //10
     
     
     
     public synchronized void addIsTypingInfo(Participant pOrigin, long timeOfEvent, Participant[] recripients){
          
         FakeTyping ftNEW = new FakeTyping(pOrigin, timeOfEvent);
         if(fakeactivity.size()==0){
             fakeactivity.add(ftNEW);
             notifyAll();
             return;
         }
          
          int indexToInsert =0;
          
          for(int i=0;i<fakeactivity.size();i++){
              Object o = fakeactivity.elementAt(i);
              if(o instanceof FakeTyping){
                  FakeTyping ft = (FakeTyping)o;
                  indexToInsert = i;
                  if(timeOfEvent > ft.timeOfTyping){
                      
                     
                      break;
                  }
                  
              }
              else if(o instanceof FakeNotTyping){
                  FakeNotTyping fnt = (FakeNotTyping)o;
                  indexToInsert = i;
                  if(timeOfEvent > fnt.timeOfNotTyping){
                      
                      break;
                  }
              }
              
              
          }
         //  System.err.println("INSERTING AT: "+indexToInsert+"....size is"+fakeactivity.size());
          this.fakeactivity.insertElementAt(ftNEW,indexToInsert );
          //System.err.println("ADDING TIME: "+ftNEW.timeOfTyping);
         
         // diet.debug.Debug.showDebug2("------------------------------------------\n");
          for(int i=0;i<this.fakeactivity.size();i++){
              Object o = this.fakeactivity.elementAt(i);
              if(o instanceof FakeTyping){
                  FakeTyping ft = (FakeTyping)o;
                 // diet.debug.Debug.showDebug2(""+ft.timeOfTyping+"\n");
              }
                else if(o instanceof FakeNotTyping){
                  FakeNotTyping fnt = (FakeNotTyping)o;
                 //  diet.debug.Debug.showDebug2(""+fnt.timeOfNotTyping+"\n");
                }  
          }
          
          
          notifyAll();
     }
     
     
     
     
         
     
     
     
     
     
     private synchronized void getNextLoop(){
          while(2<5){
              try{
                  if(this.fakeactivity.size()==0) wait();
                  else{
                      FakeTyping ft = (FakeTyping)fakeactivity.lastElement();
                      long durationTillNext = ft.timeOfTyping - new Date().getTime();
                      if(durationTillNext>0){
                           long timeOfCalling = new Date().getTime();
                           if(durationTillNext<100)durationTillNext=100;
                          //wait(5000);
                             wait(durationTillNext+1);
                           long timeOfExiting = new Date().getTime();
                           System.err.println("SLEEPING TIME IS:::"+ (timeOfExiting-timeOfCalling)+" but should have been::"+durationTillNext);
                         
                      }
                      else{
                          
                          itnt.processDoingsByClient(ft.apparentOrigin);
                          this.fakeactivity.remove(ft);
                      }
                      
                  }
              }catch (Exception e){e.printStackTrace();}
              
               
          }
     }
     
     
     public synchronized void removeFakeTypingAfterThreshold(Participant p, long threshold){
         Vector vToRemove = new Vector();
         
         for(int i=0;i<this.fakeactivity.size();i++){
             FakeTyping ft = (FakeTyping)fakeactivity.elementAt(i);
             if(ft.apparentOrigin==p && ft.timeOfTyping > threshold) vToRemove.addElement(ft);
         }
         fakeactivity.removeAll(vToRemove);
     }
     
     
     
     
     public void run(){
        getNextLoop();
     }
    
    
     
     
     public void printQueue(){
         for(int i=0;i<fakeactivity.size();i++){
                 Object o = this.fakeactivity.elementAt(i);
                         
                 if(o instanceof FakeTyping){
                              FakeTyping fttt = (FakeTyping)o;
                            ///  this.itnt.cC.c.newsendInstructionToParticipant(fttt.apparentOrigin,""+fttt.timeOfTyping);
                 }
         }
     }
     
     
     
     
     
     public   synchronized boolean checkIfParticipantStillHasFakeTypingEnqueued(Participant apparentOrigin){
        for(int i=0;i<fakeactivity.size();i++){
            Object o = this.fakeactivity.elementAt(i);
            if(o instanceof FakeTyping ){
                FakeTyping ft = (FakeTyping)o;
                if(ft.apparentOrigin==apparentOrigin) return true;
            }
        }  
        
        return false;
    }
     
     
     
     
     class FakeAction{
         
     }
     
     
     class FakeTyping extends FakeAction {
         
         
         
         public FakeTyping(Participant apparentOrigin, long timeOfTyping){
            
             this.apparentOrigin=apparentOrigin;
             this.timeOfTyping=timeOfTyping;
         }
         
         public long timeOfTyping ;
         public Participant apparentOrigin;
     }
     
     class FakeNotTyping extends FakeAction{
         
          public FakeNotTyping(Participant apparentOrigin, long timeOfTyping){
              
              this.apparentOrigin=apparentOrigin;
             this.timeOfNotTyping=timeOfNotTyping;
         }
         
         public long timeOfNotTyping;
         public Participant apparentOrigin;
     }
    
}
