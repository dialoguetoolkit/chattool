/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.CustomizableReferentialTask;

import diet.attribval.AttribVal;
import diet.message.MessageTask;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.ProceduralComms.JTrialTimerActionRecipientInterface;
import diet.task.TaskControllerInterface;
import diet.tg.TelegramParticipant;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author gj
 */
public class CustomizableReferentialTask implements JTrialTimerActionRecipientInterface, TaskControllerInterface {

   

    DefaultConversationController cC;  //ok
    
    public Participant pA;             //ok
    public Participant pB;            //ok
    Random r = new Random();          //ok
    
    long durationOfStimulus=600000; //ok
    
    String[] currentTrial;               //ok
    
    boolean currentsethasbeensolved = false;  //ok
    
    public HashtableWithDefaultvalue htscoreCORRECT = new HashtableWithDefaultvalue(0);  //ok
    public HashtableWithDefaultvalue htscoreINCORRECT = new HashtableWithDefaultvalue(0);  //ok
    
    public HashtableWithDefaultvalue htPOINTS = new HashtableWithDefaultvalue((double)0);  //ok
    long gamenumber=0;                                                                     //ok
    
    double correctscoreinrement = 10;                                                      //ok
    double incorrectpenalty = 5;                                                           //ok
    
     long durationOfGame = 60000;//CustomDialog.getLong("How long is a game?", 60000);
    
   
    boolean showButtons = true;               //ok 
    
    String displayname = "instructions";     //ok
    
   // boolean showFeedbackToDirector = CustomDialog.getBoolean("Does the director receive feedback from the task about success/failure?", "Receives feedback", "No feedback");
    //boolean showFeedbackToMatcher = CustomDialog.getBoolean("Does the matcher receive feedback from the task about success/failure?", "Receives feedback", "No feedback");
      
    String option="tangramlist01.txt";      //ok
    String directoryname =  "tangramset01";  //oktangramset02directortraining";
      
    int stimuluswidth = -1;
    int stimulusheight=-1;   
    
    boolean isinphysicalfolder = true;        //ok
    
    public Vector<String[]> vstimuli = new Vector();
    public Vector<String[]> vstimuliFULL = new Vector();
    Hashtable htIMAGE = new Hashtable();
    
    int timeouts=0;
    
    JCustomizableReferentialTask jcrt;
    boolean telegram = false;
    
    org.telegram.telegrambots.meta.api.objects.Message p1PM;
     org.telegram.telegrambots.meta.api.objects.Message p2PM;
     
     boolean deleteStimulusAfterEachTrial =true;
    
    
   /* public CustomizableReferentialTask(DefaultConversationController cC, boolean telegram) {
        //durationOfStimulus = 5000;//CustomDialog.getLong("How long should the stimuli be displayed for?", 5000);
        //blockTextEntryDuringStimulus = CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
        this.cC=cC;
        this.telegram=telegram;
        //jtt = new JTrialTimer(this, 10000);
    }
    
    */
    
   boolean showScoreOnEachGame = false;
   boolean showIfSelectionWasCorrrectOrIncorrect = false;
   boolean advanceToNextManually = false;
    
    
    
   public CustomizableReferentialTask(DefaultConversationController cC, CustomizableReferentialTaskSettings crts){
         //this.durationOfStimulus=durationOfStimulus;
         this.cC=cC;      
         
         final CustomizableReferentialTask crtthis = this;
         
         this.correctscoreinrement = crts.correctscoreinrement;
         this.deleteStimulusAfterEachTrial = crts.deleteStimulusAfterEachTrial;
         this.directoryname = crts.directoryname;
         this.durationOfGame = crts.durationOfGame;
         this.durationOfStimulus= crts.durationOfStimulus;
         this.htIMAGE= crts.htIMAGE;
         this.incorrectpenalty= crts.incorrectpenalty;
         this.isinphysicalfolder=crts.isinphysicalfolder;
         this.showButtons=crts.showButtons;
         this.stimulusheight=crts.stimulusheight;
         this.stimuluswidth=crts.stimuluswidth;
         this.telegram=crts.telegram;
         this.vstimuli=crts.vstimuli;
         this.vstimuliFULL=crts.vstimuliFULL;
         
         
         this. showScoreOnEachGame = crts.showScoreOnEachGame;
        this.showIfSelectionWasCorrrectOrIncorrect = crts.showIfSelectionWasCorrrectOrIncorrect;

        this.advanceToNextManually = crts.advanceToNextManually;
         
        
         
         
         
         
         
         
         
         SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                   jcrt = new JCustomizableReferentialTask(crtthis);
                   JFrame jf = new JFrame();
                   jf.getContentPane().add(jcrt);
                   jf.setVisible(true);
                   jf.pack();
             }
         });
         
         
    }
 
            
            
            
    
    

    
      
  
    
    
    
    Dimension getImageHeights(){
        return this.getImageHeightsFILE();
    }
    
    private Dimension getImageHeightsFILE(){   
         String userdir = System.getProperty("user.dir");
         String dir = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli"+ File.separator+directoryname);
         String firstfilename = dir+"/"+vstimuliFULL.elementAt(0)[0];
         System.err.println("Trying to get image heights for: "+firstfilename);
         BufferedImage bimg =null ;
         try{
             File f = new File(firstfilename);
             bimg = ImageIO.read(f);
             int width = bimg.getWidth();
             int height = bimg.getHeight();
             System.err.println("Returning size as: ("+width+","+height+")");
             return (new Dimension(width,height)); 
         }
         catch(Exception e){
             e.printStackTrace();
         }
         return new Dimension(-1,-1);
    }
    
    
    private Dimension getImageHeightsJAR(){   
        String firstfilename = directoryname+"/"+vstimuliFULL.elementAt(0)[0];
        System.err.println("Trying to get image heights for: "+firstfilename);
        BufferedImage bimg =null ;
        try {   
             ClassLoader cldr = CustomizableReferentialTask.class.getClassLoader();
             URL url = cldr.getResource(firstfilename);
             bimg = ImageIO.read(url);
             int width = bimg.getWidth();
             int height = bimg.getHeight();
             return (new Dimension(width,height)); 
       } catch (IOException ex) {
            // handle exception...
           ex.printStackTrace();   
       } 
        return new Dimension(-1,-1); 
    }   
    
    
    
  
    
    
    
    
    
    
  
    public boolean participantCanMakeChoice(Participant p){
        //if(2<5)return true;
        if(currentTrial[2].equalsIgnoreCase("B")) return true;
        if(p==pA && currentTrial[2].equalsIgnoreCase("1")) return true;
        if(p==pB && currentTrial[2].equalsIgnoreCase("2")) return true;
        return false;
    }
    
    public String[] getButtonsFromOptions(){
        String validTokens = currentTrial[3];
        validTokens=validTokens.replace(" ", "");
        String[] validTokensArray = validTokens.split(",");
        return validTokensArray;
    }
   
    
    public void  participantRejoined(Participant p){
        if(p==pA){
             cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, this.stimuluswidth, this.stimulusheight, "",false, emptybuttons);
             if(this.showButtons){
                 cC.c.showStimulusImageEnableButtons(pA, this.getButtonsFromOptions(), false);
             }else{
                 cC.c.showStimulusImageEnableButtons(pA,emptybuttons, false);
             }
             
             
             
        }
        else{
              cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, stimuluswidth, stimulusheight, "",false, emptybuttons);
              if(this.showButtons){
                 cC.c.showStimulusImageEnableButtons(pB, this.getButtonsFromOptions(), false);
             }else{
                 cC.c.showStimulusImageEnableButtons(pB,emptybuttons, false);
             }
        }
    }
    
    
    String[] emptybuttons ={};
    
    public void startTask(Participant pA, Participant pB){
        
            if(pA.getParticipantID().contains("1")){
               this.pA=pA;
               this.pB=pB;    
            }
            else if(pB.getParticipantID().contains("1")){
               this.pB=pA;
               this.pA=pB; 
            }
            else{
                boolean order = r.nextBoolean();
                if(order){
                      this.pA=pA;
                      this.pB=pB;     
                }
                else{
                      this.pB=pA;
                      this.pA=pB;  
                }             
            }
        try{
            while(jcrt==null){
                try{
                   Thread.sleep(50);
                   System.err.println("Waiting for Java component to initialize");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
            
            
            this.jcrt.setLeftParticipantName(this.pA.getParticipantID(),this.pA.getUsername());
            this.jcrt.setRightParticipantName(this.pB.getParticipantID(),this.pB.getUsername());
        }catch(Exception e){
            e.printStackTrace();
        }
                
        if(!telegram){
           cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, stimuluswidth, stimulusheight, "",this.isinphysicalfolder,emptybuttons);
           cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, stimuluswidth, stimulusheight, "",this.isinphysicalfolder,emptybuttons);
        }
        
        try{
            //Thread.sleep(10000);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //cC.c.showStimulusImageEnableButtons(pB, emptybuttons,  true);
        //cC.c.showStimulusImageEnableButtons(pA, emptybuttons, true);
       
        
//doCountdowntoNextSet_DEPRECATED("Please start!","Next face in " );
        
        Thread t = new Thread(){
            public void run(){
                gameloop();
            }
        };
      
        
        t.start();
    }
    
    
    
   
  
    
     
     
     
     
     public boolean hasLoopedThroughAllStimuli = false;
    
     
     
      private void checkIfHasLoopedThroughAll(){
          
          
         while(vstimuli.size()==0){
                      CustomDialog.showDialog("Experiment finished!!");                     
                      //this.hasLoopedThroughAllFaces=true;
                      //vpairs=(Vector)vpairsFULL.clone();
                      System.err.println("HAS LOOPED THROUGH ALL OF THE STIMULI!");
                      Conversation.printWSln("Main", "Has looped through all the stimuli");
          }
     }
     
     
      
      
      
      
     
     private void loadNextStimulusSetSet(String directoryname){
                  checkIfHasLoopedThroughAll();   
                  this.currentTrial = this.vstimuli.elementAt(0);
                  vstimuli.remove(currentTrial);
                  
                  if(!telegram){
                     if(participantCanMakeChoice(pA)){
                         if(showButtons)cC.c.showStimulusImageReplaceWithNewButtons(pA, this.getButtonsFromOptions(), true );    
                     }
                     else{
                         if(showButtons) cC.c.showStimulusImageReplaceWithNewButtons(pA, emptybuttons, true );  
                     }
                     if(participantCanMakeChoice(pB)){
                         if(showButtons) cC.c.showStimulusImageReplaceWithNewButtons(pB, this.getButtonsFromOptions(), true );
                     }
                     else{
                         if(showButtons) cC.c.showStimulusImageReplaceWithNewButtons(pB, emptybuttons, true );  
                     }
                     Conversation.printWSln("Main", "THE STIMULI ARE:"  + this.currentTrial[0]+"----"+this.currentTrial[1]);
                  }
                  else{
                      
                  }
                  
                  
                  
          
     }
    
    
    
     
     
     //boolean isInTransitionBetweenGames = false;
     
     
     long startOfCurrentGame = new Date().getTime();
    
     
     
     
     public synchronized void gameloop(){
          try{
             //this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ",false);
             this.doCountdowntoNextSet_Step2_LoadNextSet();
             //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
             this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
          }catch(Exception e){
              e.printStackTrace();
          }   
          
          long bonustimethisgame = 0;
          startOfCurrentGame = new Date().getTime();
          while(2<5){
              //
              try{
                  
                 wait(250);
                 
                 long time1 = new Date().getTime();
                 boolean ispaused = this.jcrt.isPausePressed();
                 
                 boolean jumpToNext = jcrt.goToNext();
                 
                 while(ispaused&!jcrt.goToNext()){
                     ispaused = this.jcrt.isPausePressed();
                     wait(500);
                     bonustimethisgame=bonustimethisgame+500;
                     if(jcrt.goToNext()){
                         bonustimethisgame=0;
                         jumpToNext=true;
                         break;
                     }
                     
                 }
                 
                 
                 long durationOfGameSoFar = new Date().getTime()-startOfCurrentGame;
                 long timeRemaining =  bonustimethisgame +   durationOfGame - durationOfGameSoFar;
                 
                 if(jumpToNext)timeRemaining=0;
                 if(timeRemaining<0)timeRemaining =0;
                 
                 Color progressBar = Color.red;
                 long percentage = (100*timeRemaining)/durationOfGame;
                 
                 //System.err.println("durationOfGameSoFar "+durationOfGameSoFar);
                 //System.err.println("timeremaining "+timeRemaining);
                 //System.err.println("durationofgame "+durationOfGame);
                 
                  int timeRemainingSeconds = (int)timeRemaining/1000;
                  
                  if(!telegram){
                     cC.c.changeJProgressBar(pA, "CHATFRAME",  timeRemainingSeconds+"", new Color(150,150,255), (int)percentage);
                     cC.c.changeJProgressBar(pB, "CHATFRAME",  timeRemainingSeconds+"", new Color(150,150,255), (int)percentage);
                  }
                 this.jcrt.changeJProgressBar( timeRemainingSeconds+"",(int)percentage, new Color(150,150,255));
                        
                 if(this.currentsethasbeensolved){                    
                     this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ", false, false);
                     this.doCountdowntoNextSet_Step2_LoadNextSet();
                     this.startOfCurrentGame= new Date().getTime();
                      bonustimethisgame=0;
                     //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
                     this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
                 }   
                 else if(timeRemaining<=0){
                     //this.jjp.addTextln("TIMEOUT");
                     
                     if(telegram){
                        cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pA, "You ran out of time!");
                        cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pB, "You ran out of time!");
                     }
                     else{
                         cC.c.sendInstructionToParticipant(pA, "You ran out of time!" );
                         cC.c.sendInstructionToParticipant(pB, "You ran out of time!" );    
                     }
                     this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ",false, false);
                     this.doCountdowntoNextSet_Step2_LoadNextSet();
                     this.startOfCurrentGame= new Date().getTime();
                      bonustimethisgame=0;
                     //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
                     this.timeouts=this.timeouts+1;
                     this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
                     
                 }
                 
                 
                 //check if game is complete
                 //if game is not complete
              }
              catch(Exception e){
                  e.printStackTrace();
              }
                             
          }
         
         
     }
     
     
     
     
     
     
     
     
     
     public void doCountdowntoNextSet_Step1_Countdown(final String  deprecatedfirstmessage,final  String deprecatedcountdownmessageprefix, boolean showTimerInChatWindow  ,boolean blocktextentry) throws Exception{
                  
                  //String imageName1 = directoryname+"/"+currentTrial[0];
                  //String imageName2 = directoryname+"/"+currentTrial[1];
                
                  if(!telegram){
                     cC.c.sendInstructionToParticipant(pA, "Loading next set");
                     cC.c.sendInstructionToParticipant(pB, "Loading next set");
                  
                     
                      if(this.showButtons){
                          cC.c.showStimulusImageEnableButtons(pB, this.getButtonsFromOptions(), false);
                          cC.c.showStimulusImageEnableButtons(pA, this.getButtonsFromOptions(), false);
                      }
                      else{
                          cC.c.showStimulusImageEnableButtons(pB, emptybuttons, false);
                          cC.c.showStimulusImageEnableButtons(pA, emptybuttons, false);
                      }
                      
                      
                  if(blocktextentry){
                     cC.c.changeClientInterface_clearMainWindows(pA);
                     cC.c.changeClientInterface_clearMainWindows(pB);
                     cC.c.changeClientInterface_disableTextEntry(pA);
                     cC.c.changeClientInterface_disableTextEntry(pB);
                   }
                  
                       Thread.sleep(1000);
                  
                      //if(blocktextentry)   cC.c.changeClientInterface_clearMainWindows(pA);
                      //if(blocktextentry) cC.c.changeClientInterface_clearMainWindows(pB);
                      if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","" ,false, pA,pB );
                
                  }
                 
                  if(telegram){
                      try{
                          if(this.deleteStimulusAfterEachTrial  ) cC.c.telegram_deleteMessageNow((TelegramParticipant)pA, this.p1PM.getMessageId());
                          if(this.deleteStimulusAfterEachTrial  )cC.c.telegram_deleteMessageNow((TelegramParticipant)pB, this.p2PM.getMessageId());
                      }catch(Exception e){
                          e.printStackTrace();
                          Conversation.saveErr(e);
                      }
                  }
         
     }
     
     
     
     
     public void doCountdowntoNextSet_Step2_LoadNextSet(){
         loadNextStimulusSetSet(this.directoryname);     
         gamenumber++;
         
         if(!telegram){
             cC.c.showStimulusImageFromJarFile_ChangeImage(pA, directoryname+"/"+this.currentTrial[0], this.isinphysicalfolder,durationOfStimulus);
             cC.c.showStimulusImageFromJarFile_ChangeImage(pB, directoryname+"/"+this.currentTrial[1], this.isinphysicalfolder,durationOfStimulus);  
         }
         else{
             String userdir = System.getProperty("user.dir");
             
             
             File fA = new File(userdir+File.separator+"experimentresources"+File.separator+"stimuli"+File.separator+   directoryname+ File.separator    +this.currentTrial[0]);
             File fB = new File(userdir+File.separator+"experimentresources"+File.separator+"stimuli"+File.separator+   directoryname+ File.separator    +this.currentTrial[1]);
             
             //
            
             
             
             //cC.c.sendPhoto_By_File_DeleteAfter((TelegramParticipant)pA, fA, durationOfStimulus, tbo.buttons, tbo.actions);
            // cC.c.sendPhoto_By_File_DeleteAfter((TelegramParticipant)pB, fB, durationOfStimulus, tbo.buttons, tbo.actions);
             
            // try{Thread.sleep(3000);}catch(Exception e){e.printStackTrace();}
             
             //
             
             
             if(telegram){
                     telegrambuttonoptions tbo = getButtons();
                 
                     if(participantCanMakeChoice(pA)){
                         if(showButtons) {
                             this.p1PM=cC.c.telegram_sendPhoto_By_File_DeleteAfter((TelegramParticipant)pA, fA, durationOfStimulus, tbo.buttons, tbo.actions);
                         }
                         else{
                             this.p1PM=cC.c.telegram_sendPhoto_By_File_DeleteAfter((TelegramParticipant)pA, fA, durationOfStimulus, null, null);
                         }
                             
                     }
                     else{
                          this.p1PM=cC.c.telegram_sendPhoto_By_File_DeleteAfter((TelegramParticipant)pA, fA, durationOfStimulus, null, null);
                     }
                     if(participantCanMakeChoice(pB)){
                         if(showButtons) {
                             this.p2PM=cC.c.telegram_sendPhoto_By_File_DeleteAfter((TelegramParticipant)pB, fB, durationOfStimulus, tbo.buttons, tbo.actions);
                         }
                         else{
                             this.p2PM=cC.c.telegram_sendPhoto_By_File_DeleteAfter((TelegramParticipant)pB, fB, durationOfStimulus, null, null);
                         }
                             
                     }
                     else{
                          this.p2PM=cC.c.telegram_sendPhoto_By_File_DeleteAfter((TelegramParticipant)pB, fB, durationOfStimulus, null, null);
                     }
                     Conversation.printWSln("Main", "THE STIMULI ARE:"  + this.currentTrial[0]+"----"+this.currentTrial[1]);
            }
             
             
         }
        
         currentsethasbeensolved = false;         
     }
     
     
     
     
     
     private  telegrambuttonoptions getButtons(){
        
        String gamenumberPadded5 = diet.utils.StringOperations.appChar_ToRight( ""+ this.gamenumber, "*", 5);
        
        String[] buttons = this.getButtonsFromOptions();
        
        String[] actions = new String[buttons.length];
        for(int i=0;i<buttons.length;i++){
            actions[i]=gamenumberPadded5+buttons[i];
        }
        
        String[][]buttonrow = new String[][]{buttons};
        String[][]actionrow = new String[][]{actions};
        
        telegrambuttonoptions tbo = new telegrambuttonoptions();
        tbo.buttons=buttonrow;
        tbo.actions=actionrow;
        return tbo;
        
    }
     
     
       
    public class telegrambuttonoptions {
        String[][] buttons;
        String[][] actions;
    }
    
    
     
     
     
     
     
     
     
     
     public void doCountdowntoNextSet_Step3_StopShowingStimulus() throws Exception{
                 
                  
                       try{
                            //Thread.sleep(durationOfStimulus);
                            
                            if(!telegram){
                               cC.c.changeClientInterface_enableTextEntry(pA);
                               cC.c.changeClientInterface_enableTextEntry(pB);
                            }
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                             if(gamenumber>1){
                                 pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                                 pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                            }
                  
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                            
                              DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             
                            // cC.c.newsendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                            // cC.c.newsendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                         
                            //cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                            //cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                      
                           if(!telegram){
                             if(this.showButtons){
                                 cC.c.showStimulusImageEnableButtons(pB, this.getButtonsFromOptions(), false);
                                 cC.c.showStimulusImageEnableButtons(pA, this.getButtonsFromOptions(), false);
                             }
                             else{
                                 cC.c.showStimulusImageEnableButtons(pB, emptybuttons, false);
                                 cC.c.showStimulusImageEnableButtons(pA, emptybuttons, false);
                             }
                            } 
                             
                            
                                 //cC.c.showStimulusImageEnableButtons(pB, buttons2, true);
                                 //cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                                 //if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                 //if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                 //if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                           // cC.c.newsendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                           // cC.c.newsendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                           //  cC.c.newsendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                           // cC.c.newsendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
                       }catch(Exception eee){
                           eee.printStackTrace();
                       }
                    
                  
     }
     
     public void updateSERVERGUI(){
         int pANumberCorrect = getScoreCORRECT(pA);
         int pANumberINCorrect = getScoreINCORRECT(pA);
         int pBNumberCorrect = getScoreCORRECT(pB);
         int pBNumberINCorrect = getScoreINCORRECT(pB);
         
         this.jcrt.setTimeoutLabel(this.timeouts+"");
         this.jcrt.setGameNoLabel("" +this.gamenumber);
                             
         this.jcrt.setLeftCorrectLabel(""+pANumberCorrect);
         this.jcrt.setLeftIncorrectLabel(""+pANumberINCorrect);
                             
         this.jcrt.setRightCorrectLabel(""+pBNumberCorrect);
         this.jcrt.setRightIncorrectLabel(""+pBNumberINCorrect);
         
         updateSERVERGUIMAGES();
      } 
     
       public void updateSERVERGUIMAGES(){
           String userdir = System.getProperty("user.dir");
           String dir = (userdir+File.separator+"experimentresources"+ File.separator+ "stimuli"+ File.separator+directoryname);
           File f1 = new File(dir, (String)currentTrial[0]);
           File f2 = new File(dir, (String)currentTrial[1]);
           try{
               BufferedImage image1 = ImageIO.read(f1);  
               BufferedImage image2 = ImageIO.read(f2);  
               
               this.jcrt.setLeftBufferedImage(image1);
               this.jcrt.setRightBufferedImage(image2);
               
           }catch(Exception e){
               e.printStackTrace();
           }
           
           jcrt.setLeftImageName(f1.getName());
           jcrt.setrightImageName(f2.getName());
           
           
           String[] buttons= this.getButtonsFromOptions();
           String[] valids =   new String[]{currentTrial[4]};
           
            String correctoptions = currentTrial[4];
            correctoptions = correctoptions.replace(" ", "");
            String[] correctoptionsArray = correctoptions.split(",");
            
           
           jcrt.setOptions(buttons,correctoptionsArray );
           
           
       }
           
       
       
     
     
     
     
     
     
     
     
     
     
     
     
      public void doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial() throws Exception{
                 
                  
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                        
                            
                             this.updateSERVERGUI();
                             
                             
                             DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             
                            // cC.c.newsendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                            // cC.c.newsendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                            if(!telegram){
                               cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                               cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                               
                               
                               if(this.currentTrial.length>6){
                                   String msgToP1 = currentTrial[5];
                                   System.err.println("CRT MSGTOP1A: "+msgToP1);
                                   msgToP1 = msgToP1.trim();
                                   String msgToP2 = currentTrial[6];
                                   System.err.println("CRT MSGTOP1B: "+msgToP2);
                                   msgToP2 = msgToP2.trim();
                                   //cC.c.telegram_sendInstructionToParticipant((TelegramParticipant)pA, msgToP1 );
                                   //cC.c.telegram_sendInstructionToParticipant((TelegramParticipant)pB, msgToP2 );
                                   cC.c.sendInstructionToParticipant(pA, msgToP1);
                                   cC.c.sendInstructionToParticipant(pB, msgToP2);
                                   
                                   
                                   System.err.println("CRT MSGTOP1B: "+msgToP1);
                                   System.err.println("CRT MSGTOP2B: "+msgToP1);
                               }
                               
                               
                               
                            }
                            else{
                                if(this.showScoreOnEachGame)  cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pA, "Your score is: "+ outputPA );
                                if(this.showScoreOnEachGame)  cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pB, "Your score is: "+ outputPB );
                                
                                if(this.currentTrial.length>6){
                                   String msgToP1 = currentTrial[5];
                                   msgToP1 = msgToP1.trim();
                                   String msgToP2 = currentTrial[6];
                                   msgToP2 = msgToP2.trim();
                                   cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pA, msgToP1 );
                                   cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pB, msgToP2 );
                                   System.err.println("CRTTELEGRAM MSGTOP1B: "+msgToP1);
                                   System.err.println("CRTTELEGRAM MSGTOP2B: "+msgToP2);
                               }
                                
                                
                            }
                             
                            if(!telegram){
                                if(this.showButtons){
                                    cC.c.showStimulusImageEnableButtons(pB, this.getButtonsFromOptions(), true);
                                    cC.c.showStimulusImageEnableButtons(pA, this.getButtonsFromOptions(), true);
                                }
                                else{
                                    cC.c.showStimulusImageEnableButtons(pB, emptybuttons, false);
                                    cC.c.showStimulusImageEnableButtons(pA, emptybuttons, false);
                                }
                            }
                             
                            
                                  
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                             //cC.c.newsendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            // cC.c.newsendInstructionToParticipant(pA,"enter / followed by the number");
                            //cC.c.newsendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            //cC.c.newsendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
         
     }
     
     
    
      
    
    public void dbg(Participant sender, String text){
        if(2<5)return;
        cC.c.sendInstructionToParticipant(sender,text);
    }
    
    
    public synchronized void processButtonPress(Participant p, String choice){
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("buttonpress", p, choice);
        this.processChatText(p, "/"  + choice);
    }
    
    
   
    public synchronized void telegram_processButtonPress(TelegramParticipant p, Update updateWithCallback){
        try{
           CallbackQuery cbq = updateWithCallback.getCallbackQuery();
           Message  m =cbq.getMessage();
           String callbackData =   cbq.getData();
           System.err.println("callbackdata: "+callbackData);
           String leftID = callbackData.substring(0, 5);
           String leftIDGameNumber = leftID.replace("*", "");
           System.err.println("LEFTID:"+leftID+" "+leftIDGameNumber);
           
           int gameNumberOfButtonPress = Integer.parseInt(leftIDGameNumber);
           
           if(gameNumberOfButtonPress!=this.gamenumber){
                 cC.c.telegram_sendInstructionToParticipant_MonospaceFont(p, "You selected an old option. Please select an option from the most recent image!");
                 cC.c.telegram_sendEditMessageReplyMarkup(p, updateWithCallback, null,null);
                 return;
           }
           else{
                 cC.c.telegram_sendEditMessageReplyMarkup(p, updateWithCallback, null,null);
                 String rightID = callbackData.substring( 5);
                 System.err.println("RIGHTID:"+rightID+" "+rightID);
                 this.processChatText(p, "/"  + rightID);
                 cC.c.telegram_sendInstructionToParticipant_MonospaceFont(p, "You selected "+rightID);
                 
                 cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("buttonpress", p, rightID);
           }
           
        }catch(Exception e){
            e.printStackTrace();
            Conversation.saveErr(e);
        }   
           
           
           
          
           
           
           
    }
    
    
    
    
    public synchronized void processChatText(Participant sender, String txt){
        this.jcrt.processChatText(sender.getUsername(),txt);
        
        if(!txt.startsWith("/"))return;     
        
        if(txt.startsWith("/NEXT" )|| txt.startsWith("/Next") || txt.startsWith("/next") && this.advanceToNextManually){
            this.currentsethasbeensolved = true;
        }
        
        
        if(this.currentsethasbeensolved){
            cC.c.sendInstructionToParticipant(sender,"The current set has already been solved");
            return;
            
        }
        String command=txt.replace("\n", "");
        command=command.replace(" ", "");
        command=command.toUpperCase();
        command = command.replace("/", "");
        
        //this.jjp.addTextln(sender.getParticipantID()+" "+ sender.getUsername() + " "+ "Selected: "+command);
        
        String permittedSender = currentTrial[2];
        
        //First check if participant is allowed to make the selection
        
        this.dbg(sender, "Permittedsender: "+permittedSender);
        this.dbg(sender,"command: "+command);
         
        if(permittedSender.equalsIgnoreCase("1") && sender==pB) {
            if(!telegram){
                cC.c.sendInstructionToParticipant(sender,"The other participant needs to make the selection!");
            }
            else{
                cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)sender, "The other participant needs to make the selection!");
            }
            return;
        }
        if(permittedSender.equalsIgnoreCase("2") && sender==pA) {
            if(!telegram){
                cC.c.sendInstructionToParticipant(sender,"The other participant needs to make the selection!");
            }
            else{
                 cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)sender, "The other participant needs to make the selection!");
            }
            
            return;
        }
        
        
        
        //Next check if the command is permitted
        
        String validTokens = currentTrial[3];
        validTokens=validTokens.replace(" ", "");
        String[] validTokensArray = validTokens.split(",");
        boolean isValidToken = false;
        for(int i=0;i<validTokensArray.length;i++){
            if(validTokensArray[i].equalsIgnoreCase(command))isValidToken = true;
            this.dbg(sender, "validtokens: "+validTokensArray[i]);
        }  
       
        
        if(isValidToken){   
            boolean selectionCorrect=false;
            
            String correctoptions = currentTrial[4];
            correctoptions = correctoptions.replace(" ", "");
            String[] correctoptionsArray = correctoptions.split(",");
            for(int i=0;i<correctoptionsArray.length;i++){
                if(correctoptionsArray[i].equalsIgnoreCase(command )) {
                    selectionCorrect = true;
                    break;
                }
            }
                 
            
            if(selectionCorrect){
                
                
                 this.updateScores(sender,true);
                  String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
                  String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
                 
                      if(telegram){
                         if(this.showIfSelectionWasCorrrectOrIncorrect)cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pA, "CORRECT! " + scoreA);
                         if(this.showIfSelectionWasCorrrectOrIncorrect)cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pB, "CORRECT! " + scoreB);
                      }
                      else{
                        if(this.showIfSelectionWasCorrrectOrIncorrect) cC.c.sendInstructionToParticipant(pA, "CORRECT! " + scoreA);
                        if(this.showIfSelectionWasCorrrectOrIncorrect) cC.c.sendInstructionToParticipant(pB, "CORRECT! " + scoreB);
                      }   
                  
                  
                 this.currentsethasbeensolved=true;
                 //doCountdowntoNextSet_DEPRECATED("CORRECT! They are the SAME", "Next face in "  );
                 
                 
                 
                
            }
            else{
                this.updateScores(sender,false);
                 String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
                 String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
                 if(telegram){
                    if(this.showIfSelectionWasCorrrectOrIncorrect) cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pA, "INCORRECT! " + scoreA);
                    if(this.showIfSelectionWasCorrrectOrIncorrect) cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pB, "INCORRECT! " + scoreB);
                 }
                 else{
                    if(this.showIfSelectionWasCorrrectOrIncorrect) cC.c.sendInstructionToParticipant(pA, "INCORRECT! " + scoreA);
                    if(this.showIfSelectionWasCorrrectOrIncorrect) cC.c.sendInstructionToParticipant(pB, "INCORRECT! " + scoreB);
                 }
                 
                 
                
                this.currentsethasbeensolved=true;
                //doCountdowntoNextSet_DEPRECATED("INCORRECT! They are  DIFFERENT","Next face in " );
                
            }
        
        }
        else{
            if(telegram){
                  cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)sender, "Invalid command.");
                  cC.c.telegram_sendInstructionToParticipant_MonospaceFont((TelegramParticipant)pB, "Please reread the experiment istructions");
            }
            else{
                cC.c.sendInstructionToParticipant(sender,"Invalid command.");
                cC.c.sendInstructionToParticipant(sender,"Please reread the experiment istructions");     
            }
            
            
           
                   
        }
    }
    
    
    
    
   
    
    
    public void updateScores(Participant sender,boolean success){
        if(success){
            //this.jjp.addTextln("CORRECT");
            
            
            if(sender==pA){
               int scorepAsuccess = this.getScoreCORRECT(pA);
                this.htscoreCORRECT.putObject(pA, scorepAsuccess+1);   
                double score = (double)  this.htPOINTS.getObject(pA);
                score=score+correctscoreinrement;
                this.htPOINTS.putObject(pA,score);
            
            }
            else if (sender ==pB){
                int scorepBsuccess = this.getScoreCORRECT(pB);
                this.htscoreCORRECT.putObject(pB, scorepBsuccess+1);
                double score = (double)  this.htPOINTS.getObject(pB);
                score=score+correctscoreinrement;
                this.htPOINTS.putObject(pB,score);
            }         
        }
        else{
            //this.jjp.addTextln("INCORRECT");
            
            if(sender==pA){
               int scorepAINC = this.getScoreINCORRECT(pA);
                this.htscoreINCORRECT.putObject(pA, scorepAINC+1);   
                double score = (double)  this.htPOINTS.getObject(pA);
                score=score-this.incorrectpenalty;
                if(score<0)score=0;
                this.htPOINTS.putObject(pA,score);
            
            }
            else if (sender ==pB){
                int scorepBINC = this.getScoreCORRECT(pB);
                this.htscoreINCORRECT.putObject(pB, scorepBINC+1);
                double score = (double)  this.htPOINTS.getObject(pB);
                score=score-this.incorrectpenalty;
                if(score<0)score=0;
                this.htPOINTS.putObject(pB,score);
            }
        }
        
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pA, ""+this.getScoreCORRECT(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pB, ""+this.getScoreCORRECT(pB));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pA, ""+this.getScoreINCORRECT(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pB, ""+this.getScoreINCORRECT(pB));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pA, ""+(double)  this.htPOINTS.getObject(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pB, ""+(double)  this.htPOINTS.getObject(pB));
        
        Conversation.printWSln("Main", pA.getUsername()+" has "+(double)  this.htPOINTS.getObject(pA));
        Conversation.printWSln("Main", pB.getUsername()+" has "+(double)  this.htPOINTS.getObject(pB));
        
    }
    
   
    
    
    
    public int getScoreCORRECT(Participant p){
        return (int)this.htscoreCORRECT.getObject(p);
    }
     public int getScoreINCORRECT(Participant p){
        return (int)this.htscoreINCORRECT.getObject(p);
    }
    
    public Vector getAdditionalValues(Participant p){
        
         String stimulusself ="";
         String stimulusother ="";
         Vector avs = new Vector();
         if(pA==p){
             stimulusself=directoryname+"/"+this.currentTrial[0];
             stimulusother=directoryname+"/"+this.currentTrial[1];
             AttribVal av0 = new AttribVal("gamenumber", ""+this.gamenumber);
             AttribVal av1 = new AttribVal("correct", ""+this.getScoreCORRECT(pA));
             AttribVal av2 = new AttribVal("incorrect", ""+this.getScoreINCORRECT(pA));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",stimulusself  );
             AttribVal av5 = new AttribVal("points", ""+(double)  this.htPOINTS.getObject(pA));
             AttribVal av6 =null; 
             if(hasLoopedThroughAllStimuli) {
                 av6 = new AttribVal("haslooped","YES")  ;
             } 
             else {
                 av6 = new AttribVal("haslooped","NO");
             }
             avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5);avs.addElement(av6);
         }
         if(pB==p){
             stimulusself=directoryname+"/"+this.currentTrial[0];
             stimulusother=directoryname+"/"+this.currentTrial[1];
             AttribVal av0 = new AttribVal("gamenumber", ""+this.gamenumber);
             AttribVal av1 = new AttribVal("correct", ""+this.getScoreCORRECT(pB));
             AttribVal av2 = new AttribVal("incorrect", ""+this.getScoreINCORRECT(pB));
             AttribVal av3 = new AttribVal("stimulusself",""+stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",""+stimulusself  ); 
             AttribVal av5 = new AttribVal("points", ""+(double)  this.htPOINTS.getObject(pB));
             AttribVal av6 =null;
             if(hasLoopedThroughAllStimuli) {
                 av6 = new AttribVal("haslooped","YES")  ; 
             }else {
                 av6 = new AttribVal("haslooped","NO");
             }
             avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4);avs.addElement(av5);avs.addElement(av6);
         }
    
         return avs;
    } 
     
     
    @Override
    public void processNotification(String nameOfEvent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(nameOfEvent.equalsIgnoreCase("timeout")){
            //this.jjp.addTextln("CORRECT");
        }
    }

    @Override
    public void changeClientProgressBars(int value, String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     
    
    
    public void closeDown(){
        
    }
    
    public JPanel getJPanel(JPanel jp){
         return jcrt;
    }

    @Override
    public void processTaskMove(MessageTask mtm, Participant origin) {
         //DOn't need this in this controller
    }
    
     
     
     
     
     
     
}
