/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.mazegame;

import diet.server.Participant;
import diet.utils.Conversion;
import java.awt.Color;
import java.util.Date;

/**
 *
 * @author gj
 */
public class MazeGameController2WAYTimer extends Thread{
    
    MazeGameController2WAYUI mgcUI;
  //   MazeGameController2WAY mgc;
    long timePerMaze =5 * 60 * 1000;
    long timeOfLatestChange = new Date().getTime();
    public boolean doTiming = false;
    boolean pauseTiming = false;
    
    public MazeGameController2WAYTimer(MazeGameController2WAYUI mgcUI){
        this.mgcUI=mgcUI;
       
        
    }
    
    public void startTiming(){
        timeOfLatestChange = new Date().getTime();
        this.start();
        
    }
    
    public void run(){
        doTiming = true;
        waitForNotification();
    }
    
    
    
    
    public synchronized void waitForNotification(){
        while(doTiming){
              try{
                 
                  boolean hasTimedOut = doCalculationsForTimeOut();
                  if(hasTimedOut){
                      //It might be that in the time interval the participants solved the maze. If this is the case then the method call update below
                      //doesn't actually set the timeout (see the method in mgc...
                      mgcUI.mgc.setTimedOut(mgcUI.mgc.mazeNumber); 
                  }
                  
                  System.err.println("TESTING IF COMPLETED: ");
                  
                  if(mgcUI.mgc.isCompleted()){
                      
                        MazeGameController2WAY mgc = mgcUI.mgc;
                        System.err.println("TESTING IF COMPLETED: FINISHEDMAZE - HASDETERMINED THAT THE MGC OF: "+mgc.pDirector.getUsername()+" AND "+mgc.pMatcher.getUsername()+"...has completed the maze" );
                        mgc.c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Congratulations! Please wait", Color.GRAY, 0); 
                        mgc.c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Congratulations! Please wait", Color.GRAY, 0);
                        mgc.saveDataForMaze();
                        mgc.saveDataForMazeNEWv4();
                        mgc.saveDataForMazev5();
                        doCountdownToNextPartner(mgc.pDirector, mgc.pMatcher,5, "Congratulations! Next maze","");
                        mgc.moveToNextMaze("NEXT MAZE: Completed!");
                        timeOfLatestChange= new Date().getTime();
                  }
                  if(mgcUI.mgc.isTimedOut(mgcUI.mgc.mazeNumber)){
                       {
                         MazeGameController2WAY mgc = mgcUI.mgc;
                          System.err.println("TESTING IF TIMED OUT - HASDETERMINED THAT THE MGC OF: "+mgc.pDirector.getUsername()+" AND "+mgc.pMatcher.getUsername()+"...has timed out" );
                       
                         mgc.c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Out of time! Please wait", Color.GRAY, 0); 
                         mgc.c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Out of time! Please wait", Color.GRAY, 0);
                         doCountdownToNextPartner(mgc.pDirector, mgc.pMatcher,5, "Out of time. Next maze","");
                         mgc.saveDataForMaze();
                         mgc.saveDataForMazeNEWv4();
                         mgc.saveDataForMazev5();
                         mgc.moveToNextMaze("NEXT MAZE: Ran out of time");
                         timeOfLatestChange= new Date().getTime();
                                  
                 }   
                  }
                   
                       
              }
              catch(Exception e){
                  e.printStackTrace();
              }
        }
        
    }
    
    public boolean doCalculationsForTimeOut() throws Exception{
                MazeGameController2WAY mgc = mgcUI.mgc;
                long timeOfStartOfSleep = new Date().getTime();
                wait(1000);
                long timeOfEndOfSleep = new Date().getTime();
                if(pauseTiming){
                    //timeOfLatestChange = timeOfLatestChange+ (timeOfEndOfSleep-timeOfStartOfSleep);
                    timeOfLatestChange = timeOfLatestChange+ (timeOfEndOfSleep-timeOfStartOfSleep);
                    
                } 
               
                 long timeSinceLastChange = new Date().getTime()-timeOfLatestChange;
                 long timeRemaining = timePerMaze - timeSinceLastChange;
                 String timeLeft = Conversion.convertMillisecondsIntoText(timeRemaining);
                 Color background = Color.GREEN;
                 if(timeRemaining<120000) background = Color.ORANGE;
                 if(timeRemaining<60000) background = Color.RED;
                      
                 float proportionleft = ((float) timeRemaining)/timePerMaze;
                
                 mgc.c.changeJProgressBar(mgc.pDirector, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                 mgc.c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                 mgc.updateJProgressBar((int)(100*proportionleft), timeLeft, background);
                 
                 if(timeRemaining<=0) return true;
                 
                 return false;      
           
    }
    
    
    
    
    
  
    
    
    
    
    
    
    
    
    public void doCountdownToNextPartner(Participant a, Participant b,int steps, String message, String cvsPREFIX){
         for(int k=steps;k>0;k--){
            try{
                MazeGameController2WAY mgc = mgcUI.mgc;
                mgc.c.changeClientInterface_backgroundColour(a, Color.red);
                mgc.c.changeClientInterface_clearMainWindows(a);
                mgc.c.changeClientInterface_DisplayTextInMazeGameWindow(a,message+ " IN "+k+" secs",-1000); 
                
                mgc.c.changeClientInterface_backgroundColour(b, Color.red);
                mgc.c.changeClientInterface_clearMainWindows(b);
                mgc.c.changeClientInterface_DisplayTextInMazeGameWindow(b,message+ " IN "+k+" secs",-1000); 
                
                mgc.c.deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(a, message+" in "+k+" secs", 0,mgc.c.getController().getStyleManager().defaultFONTSETTINGSSERVER,cvsPREFIX);
                mgc.c.deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(b, message+" in "+k+" secs", 0,mgc.c.getController().getStyleManager().defaultFONTSETTINGSSERVER,cvsPREFIX);
                
                Thread.sleep(500);
                mgc.c.changeClientInterface_backgroundColour(a, Color.white);
                mgc.c.changeClientInterface_backgroundColour(b, Color.white);
                
                Thread.sleep(500);
                mgc.c.changeClientInterface_clearMainWindows(a);
                mgc.c.changeClientInterface_clearMainWindows(b);
                mgc.c.deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(a, "Please start", 0,mgc.c.getController().getStyleManager().defaultFONTSETTINGSSERVER,cvsPREFIX);
                mgc.c.deprecatedsendArtificialTurnToRecipientWithEnforcedTextColour(b,"Please start", 0,mgc.c.getController().getStyleManager().defaultFONTSETTINGSSERVER,cvsPREFIX);
                
                Color config_client_backgroundcolour = new Color(mgc.c.getController().sett.client_backgroundcolour_rgb[0],mgc.c.getController().sett.client_backgroundcolour_rgb[1],mgc.c.getController().sett.client_backgroundcolour_rgb[2]);
                mgc.c.changeClientInterface_backgroundColour(a,config_client_backgroundcolour);
                mgc.c.changeClientInterface_backgroundColour(b, config_client_backgroundcolour);
                
            }catch (Exception e){
                e.printStackTrace();
            }
         }  
    }
    
    
    
    
    
    
    
}
