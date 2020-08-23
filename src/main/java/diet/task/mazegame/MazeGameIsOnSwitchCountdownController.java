/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import diet.server.Participant;
import diet.task.mazegame.message.MessageChangeGateStatus;
import java.util.Date;

/**
 *
 * @author gj
 */
public class MazeGameIsOnSwitchCountdownController extends Thread{
    
    boolean abort =false;
    long timeParticipantMustStayStill =2500;
    MazeGameController2WAY mgc;
    Participant pOnSwitch;
    Participant pOther;
    
    public MazeGameIsOnSwitchCountdownController( MazeGameController2WAY mgc, Participant pOnSwitch, Participant pOther, long timeParticipantMustStayStill){
        this.timeParticipantMustStayStill=timeParticipantMustStayStill;
        this.mgc=mgc;
        this.pOnSwitch=pOnSwitch;
        this.pOther=pOther;
        this.start();
    }
    
    public void run(){
        
        suspendedState();     
    }
    
    public synchronized void suspendedState(){
       while(!abort){  
          checkIfHasBeenOnSwitchLongEnough();
       } 
       System.err.println("THREAD HAS ABORTED");
    }
    
    
     public synchronized void checkIfHasBeenOnSwitchLongEnough(){
        if(!participantIsOnSwitch) {
            try{ 
                System.err.println("CAN SLEEP WITHOUT TIMING IT - THE PARTICIPANT IS NOT ON THE SWITCH - SHOULD BE ABLE TO SLEEP TILL NOTIFIED");
                mgc.mgcUI.changeProgressBarOfName(this.pOnSwitch.getUsername(),0);
                wait();
                System.err.println("WOKEN UP....PRESUMABLY THE PARTICIPANT IS ON THE SWITCH");
                if(participantIsOnSwitch)System.err.println("WOKEN UP....CORRECT....THE PARTICIPANT IS ON THE SWITCH");
                if(!participantIsOnSwitch)System.err.println("WOKEN UP.INCORRECT..THE PARTICIPANT ISN'T ON THE SWITCH BUT SHOULD BE!");
            }catch (Exception e){
                e.printStackTrace();
           } 
        }
        else{
             long currentTime = new Date().getTime();
             long lengthOnSwitch = currentTime-timeOfInitialSwitchPress;
             
             float percentagecomplete = 0;
             if(timeParticipantMustStayStill>0)percentagecomplete =(((float)lengthOnSwitch)/((float)timeParticipantMustStayStill))*100;
             mgc.mgcUI.changeProgressBarOfName(this.pOnSwitch.getUsername(),(int)percentagecomplete);
             
             //mgc.mgcUI.changeProgressBarOfName(this.pOnSwitch.getUsername(),(int) percentagecomplete);
             System.err.println("Participant is presumably STILL ON SWITCH..has been on switch for: "+lengthOnSwitch+" milliseconds....and the percentagecomplete:"+(percentagecomplete));
             if(lengthOnSwitch>this.timeParticipantMustStayStill& !openGatesMessageHasBeenSent){
                  System.err.println("OPENING GATES");
                  openGates();
                  try{
                      this.mgc.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gatesopen", pOther, "gatesopen");
                      this.mgc.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("switchactivated", this.pOnSwitch, "switchactivated");
                     
                      
                  }catch (Exception e){
                      e.printStackTrace();
                  }
                  openGatesMessageHasBeenSent=true;
                  System.err.println("CAN NOW SAFELY SLEEP WITHOUT TIMER BECAUSE MESSAGE HAS BEEN SENT");
                  try{
                    wait();
                  }catch(Exception e){
                      e.printStackTrace();
                  }  
             }
             else if (!openGatesMessageHasBeenSent){
                  System.err.println("HASN'T BEEN ON SWITCH LONG ENOUGH...sleeping for 500 msecs....");
                  try{
                      wait(500);
                     
                  }catch(Exception e){
                      e.printStackTrace();
                  }
             }
             else{
                 System.err.println("IT SHOULDN'T GET TO HERE....WAITING FOR ONE SECOND!");
                 try{
                      wait(100);
                  }catch(Exception e){
                      e.printStackTrace();
                  }
             }    
            
            
        }
    }
    
    
    
    boolean openGatesMessageHasBeenSent=false;
    boolean participantIsOnSwitch = false;
    long timeOfInitialSwitchPress = new Date().getTime();
    
    public synchronized void updateIsOnSwitch(){
        this.participantIsOnSwitch=true;
        this.openGatesMessageHasBeenSent=false;
        timeOfInitialSwitchPress = new Date().getTime();
        this.notify();
    }
    public synchronized void updateIsOffSwitch(){
        participantIsOnSwitch=false;
        mgc.mgcUI.changeProgressBarOfName(this.pOnSwitch.getUsername(),(int)0);
    }
    
   
    
    
    
    
    
    
    public void abort(){
        this.participantIsOnSwitch=false;
        this.abort=true;
        
    }
    
    public synchronized void openGates(){
        if(abort)return;
         System.out.println("SENDING OPEN GATE INSTRUCTION");
                MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pOther.getUsername());
                mgc.c.sendTaskMoveToParticipant(pOther,mscg);
                mgc.sIOWriting.saveMessage(mscg);
                mgc.mgcUI.changeGateStatusOfName(pOther.getUsername(),true); 
                
    }
    
    
    
}
