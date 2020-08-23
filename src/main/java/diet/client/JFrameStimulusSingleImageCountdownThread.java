/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import java.awt.Color;
import java.util.Date;

/**
 *
 * @author gj
 */
public class JFrameStimulusSingleImageCountdownThread extends Thread{

     JPanelStimulusSingleImage sjpssi;
     boolean active = false;
     long startTime = new Date().getTime();
     long duration;
     Color ctoChangeTo;

    public JFrameStimulusSingleImageCountdownThread() {
        
        
    }
     
    public void startCountdown(long duration, Color newcol, JPanelStimulusSingleImage jpssi ){
        this.sjpssi=jpssi;
        this.startTime= new Date().getTime();
        this.duration=duration;
        this.ctoChangeTo=newcol;
        this.start();
        
    } 
    
    
     public void run(){
         this.active=true;
         System.err.println("Duration: "+duration);
         System.err.println("currentminusstart: "+(new Date().getTime()-startTime)   );
         
          while(new Date().getTime()-startTime<duration){
           try{
            Thread.sleep(100);
            //System.err.println("WAKING");
           }
           catch(Exception e){
               e.printStackTrace();
           }
        }
        if(this.active)doChange();
        
     }
     
     public synchronized void doChange(){
        if(active){
            sjpssi.changeImage(ctoChangeTo);
           
        }
     }
   
     
    
        
    public synchronized void kill(){
         this.active=false;
         
    }
    
    
    
    
    
    
    
}
