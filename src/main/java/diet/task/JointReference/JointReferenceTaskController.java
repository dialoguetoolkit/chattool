/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.JointReference;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.ProceduralComms.JTrialTimer;
import diet.task.ProceduralComms.JTrialTimerActionRecipientInterface;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class JointReferenceTaskController implements JTrialTimerActionRecipientInterface {

    JJPauser jjp = new JJPauser();

    DefaultConversationController cC;
    
    JTrialTimer  jtt;// = new JTrialTimer(this, 10000);
    
   // double probabOfSame= 0.5;//CustomDialog.getDouble("Enter the probability of SAME stimuli", 0, 1, 0.5);
    public Participant pA;
    public Participant pB;
    Random r = new Random();
    final long durationOfStimulus = 500000;// = CustomDialog.getLong("How long should the stimuli be displayed for?", 5000);
    //boolean blockTextEntryDuringStimulus = true;// = CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
    
    String pA_Imagename;
    String pB_Imagename;
    
    boolean currentsethasbeensolved = false;
    
    public HashtableWithDefaultvalue htscoreCORRECT = new HashtableWithDefaultvalue(0);
    public HashtableWithDefaultvalue htscoreINCORRECT = new HashtableWithDefaultvalue(0);
    
     public HashtableWithDefaultvalue htPOINTS = new HashtableWithDefaultvalue((double)0);
    long gamenumber=0;
    
    double correctscoreinrement = 10;//CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    double incorrectpenalty = 5;//CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
    
     final  String[] buttons = {"same","different"};
     String displayname = "instructions";
    // String directoryname = "faceset4and5";
     
      
      String option="tangramlist01.txt";
      String directoryname = "tangramset01";
      
      boolean showFeedbackToDirector = false;
    
    public JointReferenceTaskController(DefaultConversationController cC) {
        //durationOfStimulus = 5000;//CustomDialog.getLong("How long should the stimuli be displayed for?", 5000);
        //blockTextEntryDuringStimulus = CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
        this.cC=cC;
        this.initFilenames();
        jtt = new JTrialTimer(this, 10000);
    }
    public JointReferenceTaskController(DefaultConversationController cC, long durationOfStimulus){
         //this.durationOfStimulus=durationOfStimulus;
         this.cC=cC;
         this.loadReferentlist(option);
         //jtt = new JTrialTimer(this, 10000);
         //this.initFilenames();
         //this.loadListsFor2017Experiment();
         
         
         
        
    }
    
    private void loadReferentlist(String option){
      
       InputStream inp = this.getClass().getResourceAsStream("/"+option);  
       try {    
        BufferedReader br = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            String[] splitline = line.split(" ");
            this.vpairs.addElement(splitline);
             
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
       }
        catch (Exception e){
        e.printStackTrace();
    }
    
    this.vpairsFULL=(Vector)vpairs.clone();
    }
    
    
    
    private void loadReferents2019(){
        String option="6";
        InputStream inp = this.getClass().getResourceAsStream("/"+option+".txt");  
       try {    
        BufferedReader br = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            String[] splitline = line.split(" ");
            this.vpairs.addElement(splitline);
             
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
       }
        catch (Exception e){
        e.printStackTrace();
    }
    
    this.vpairsFULL=(Vector)vpairs.clone();
    }
    
    
    
    private void loadListsFor2017Experiment(){
       //Vector allPairs= new Vector();
        
        String[] options = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21", "22","23","24","25",
        "26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
        
       
       String option= CustomDialog.showComboBoxDialog("options", "choose set", options, true);
        
       
        
        
       InputStream inp = this.getClass().getResourceAsStream("/"+option+".txt");  
       try {    
        BufferedReader br = new BufferedReader(new InputStreamReader(inp, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            String[] splitline = line.split(" ");
            System.err.println("FIRSTPART"+splitline[0]);
            System.err.println("SECONDPART"+splitline[1]);
            this.vpairs.addElement(splitline);
             
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        String everything = sb.toString();
        
    }
       
       
    catch (Exception e){
        e.printStackTrace();
    }
    
    this.vpairsFULL=(Vector)vpairs.clone();
    
        
    
    }
    
    
    public Vector<String[]> vpairs = new Vector();
    public Vector<String[]> vpairsFULL = new Vector();
    
    
    
   // Vector<String> vs = new Vector<String>();
   // Vector<String> vs2 = new Vector<String>();
    
    private void initFilenames(){
        
   
    }
    
    
    
    public void startTask(Participant pA, Participant pB){
        this.pA=pA;
        this.pB=pB;
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, 500, 580, "",false,buttons);
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, 500, 580, "",false, buttons);
        cC.c.showStimulusImageEnableButtons(pB, buttons, false);
        cC.c.showStimulusImageEnableButtons(pA, buttons, false);
        //doCountdowntoNextSet_DEPRECATED("Please start!","Next face in " );
        
        Thread t = new Thread(){
            public void run(){
                gameloop();
            }
        };
        t.start();
    }
    
    
    
   
  
    
     
     
     
     
     public boolean hasLoopedThroughAllFaces = false;
     private void checkIfHasLoopedThroughAll(){
          if(vpairs.size()==0){
                      this.hasLoopedThroughAllFaces=true;
                      vpairs=(Vector)vpairsFULL.clone();
                      System.err.println("HAS LOOPED THROUGH ALL OF THE FACES!");
                      Conversation.printWSln("Main", "Has looped through all the faces");
          }
     }
     
     private void loadNextStimulusSetSet(String directoryname){
                  checkIfHasLoopedThroughAll();   
                 
                  String[] pair = this.vpairs.elementAt(r.nextInt(vpairs.size()));
                  vpairs.remove(pair);
                  
                 
                      String imageName1 = directoryname+"/"+pair[0];
                      String imageName2 = directoryname+"/"+pair[1];
                      this.pA_Imagename=imageName1;   
                      this.pB_Imagename=imageName2;  
                                   
                
           Conversation.printWSln("Main", "THE FACES ARE:"  + this.pA_Imagename+"----"+this.pB_Imagename);
           
            
           System.err.println("THE FACE SETS ARE: "+this.pA_Imagename );
     }
    
    
    
     
     
     //boolean isInTransitionBetweenGames = false;
     
     
     long startOfCurrentGame = new Date().getTime();
     long durationOfGame = 10000;
     
     
     
     public synchronized void gameloop(){
          try{
             //this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ",false);
             this.doCountdowntoNextSet_Step2_LoadNextSet();
             //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
             this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
          }catch(Exception e){
              e.printStackTrace();
          }   
          while(2<5){
              //
              try{
                  
                 wait(500);
                 long durationOfGameSoFar = new Date().getTime()-startOfCurrentGame;
                 long timeRemaining = durationOfGame - durationOfGameSoFar;
                 if(timeRemaining<0)timeRemaining =0;
                 
                 Color progressBar = Color.red;
                 long percentage = (100*timeRemaining)/durationOfGame;
                 
                 System.err.println("durationOfGameSoFar "+durationOfGameSoFar);
                 System.err.println("timeremaining "+timeRemaining);
                 System.err.println("durationofgame "+durationOfGame);
                 
                  int timeRemainingSeconds = (int)timeRemaining/1000;
                  
                  cC.c.changeJProgressBar(pA, "CHATFRAME",  timeRemainingSeconds+"", new Color(150,150,255), (int)percentage);
                  cC.c.changeJProgressBar(pB, "CHATFRAME",  timeRemainingSeconds+"", new Color(150,150,255), (int)percentage);
                 
                 
                 if(this.currentsethasbeensolved){                    
                     this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ", false, false);
                     this.doCountdowntoNextSet_Step2_LoadNextSet();
                     this.startOfCurrentGame= new Date().getTime();
                     //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
                     this.doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial();
                 }   
                 else if(timeRemaining<=0){
                        cC.c.sendInstructionToParticipant(pA, "You ran out of time!" );
                        cC.c.sendInstructionToParticipant(pB, "You ran out of time!" );
                      this.doCountdowntoNextSet_Step1_Countdown("Your score is: ","Time till next image: ",false, false);
                     this.doCountdowntoNextSet_Step2_LoadNextSet();
                     this.startOfCurrentGame= new Date().getTime();
                     //this.doCountdowntoNextSet_Step3_StopShowingStimulus();
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
     
     
     
     
     
     
     
     
     
     public void doCountdowntoNextSet_Step1_Countdown(final String  firstmessage,final  String countdownmessageprefix, boolean showTimerInChatWindow  ,boolean blocktextentry) throws Exception{
       
                
                  cC.c.sendInstructionToParticipant(pA, "Loading next set");
                  cC.c.sendInstructionToParticipant(pB, "Loading next set");
                 
                     
               
                  cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                  
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false, 1); //Making the image disappear
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false,1); //Making the image disappear
                  
                  
                  
                  if(blocktextentry){
                     cC.c.changeClientInterface_clearMainWindows(pA);
                     cC.c.changeClientInterface_clearMainWindows(pB);
                     cC.c.changeClientInterface_disableTextEntry(pA);
                     cC.c.changeClientInterface_disableTextEntry(pB);
                  } 
                  
                
                  
                  
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "5 secs" );
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "5 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "5 secs" +"\n" ,true, pA,pB );
                  
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "4 secs" );
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "4 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "4 secs" +"\n" ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "3 secs" );
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "3 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "3 secs" +"\n" ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "2 secs" );
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "2 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "2 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "1 sec" );
                  if(showTimerInChatWindow)cC.c.sendInstructionToParticipant(pB,countdownmessageprefix + "1 sec" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "1 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  
                  if(blocktextentry)   cC.c.changeClientInterface_clearMainWindows(pA);
                   if(blocktextentry) cC.c.changeClientInterface_clearMainWindows(pB);
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","" ,false, pA,pB );
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.white);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.white);
                  //cC.c.newsendInstructionToParticipant(pA, "Please " );
                   
                  //cC.c.newsendInstructionToParticipant(pB, messageprefix + "1 seconds" );
                  
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pA, "faceset1/face1.png", 4000);
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pB, "faceset1/face1.png", 4000);
                  
                 
         
     }
     
     
     public void doCountdowntoNextSet_Step2_LoadNextSet(){
         loadNextStimulusSetSet(this.directoryname);     
         gamenumber++;
         cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false,durationOfStimulus);
         cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false,durationOfStimulus);        
         currentsethasbeensolved = false;         
     }
     
     
     
     public void doCountdowntoNextSet_Step3_StopShowingStimulus() throws Exception{
                 
                  
                       try{
                            //Thread.sleep(durationOfStimulus);
                            cC.c.changeClientInterface_enableTextEntry(pA);
                            cC.c.changeClientInterface_enableTextEntry(pB);
                            
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
                      
                             
                             
                             
                             
                            
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, true);
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
     
     
     
     
      public void doCountdowntoNextSet_Step4_ShowMessageAtStartOfTrial() throws Exception{
                 
                  
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                        
                            
                              DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             
                            // cC.c.newsendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                            // cC.c.newsendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                         
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                      
                             
                             
                             
                             
                            
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                // if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                             //cC.c.newsendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            // cC.c.newsendInstructionToParticipant(pA,"enter / followed by the number");
                            //cC.c.newsendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            //cC.c.newsendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
                    
                  
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
    
    private synchronized void doCountdowntoNextSet_DEPRECATED(final String  firstmessage,final  String countdownmessageprefix){
        if(2<5)return;
        final String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
        final  String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
        
        Thread t = new Thread(){
             public void run(){
                 try{
                  
                  
                     
                
                  cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                  cC.c.changeClientInterface_disableTextEntry(pA);
                  cC.c.changeClientInterface_disableTextEntry(pB);
                  
                  
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase(""))cC.c.sendInstructionToParticipant(pA, firstmessage );
                  if(firstmessage!=null && !firstmessage.equalsIgnoreCase("")) cC.c.sendInstructionToParticipant(pB, firstmessage );
                
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase("") &&displayname!=null){
                      cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreA+"\n" ,true, pA );
                      cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreB+"\n",true, pB );
                  }
                 
                  
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "5 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "5 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "5 secs" +"\n" ,true, pA,pB );
                  
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "4 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "4 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "4 secs" +"\n" ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "3 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "3 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "3 secs" +"\n" ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "2 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "2 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "2 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "1 sec" );
                  cC.c.sendInstructionToParticipant(pB,countdownmessageprefix + "1 sec" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "1 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","" ,false, pA,pB );
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.white);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.white);
                  //cC.c.newsendInstructionToParticipant(pA, "Please " );
                   
                  //cC.c.newsendInstructionToParticipant(pB, messageprefix + "1 seconds" );
                  
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pA, "faceset1/face1.png", 4000);
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pB, "faceset1/face1.png", 4000);
                  
                  loadNextStimulusSetSet(directoryname);  
                  
                  
                 
                  gamenumber++;
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false,durationOfStimulus);
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false,durationOfStimulus);
                  
                  
                  
                  currentsethasbeensolved = false;
                  
                  
                  int pAPercentageCorrect=0;
                  int pANumberCorrect = getScoreCORRECT(pA);
                  int pANumberINCorrect = getScoreINCORRECT(pA);
                  int pBPercentageCorrect=0;
                  int pBNumberCorrect = getScoreCORRECT(pB);
                  int pBNumberINCorrect = getScoreINCORRECT(pB);
                  try{
                  if(gamenumber>1){
                      pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                      pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                  }
                  }catch (Exception e){
                      e.printStackTrace();
                  }
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                  
                  
                  
                  
                 
                  
                 
                  //if(cC instanceof FaceComms2016_RAP_TURNBYTURNDyadic)      ((FaceComms2016_RAP_TURNBYTURNDyadic)cC).changeInterfaceDelays();
                  
                  
                  
                  
                  Thread tt = new Thread(){
                     public void run(){
                       try{
                            Thread.sleep(durationOfStimulus);
                            cC.c.changeClientInterface_enableTextEntry(pA);
                            cC.c.changeClientInterface_enableTextEntry(pB);
                            
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
                             
                             cC.c.sendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                             cC.c.sendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                         
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                            cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                      
                             
                             
                             
                             
                            
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                            
                            cC.c.sendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            cC.c.sendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                            cC.c.sendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            cC.c.sendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
                       }catch(Exception eee){
                           eee.printStackTrace();
                       }
                     }
                  };
                  tt.start();
                  
                 }catch (Exception e){
                     e.printStackTrace();
                 }
                 
                 
                 
             }  
         };
         t.start();
    }
    
   
    //private boolean isGuessCorrec
    
    
    private boolean isSelectionCorrect(String text){
        String selection = text.substring(1);
        String actualImage =   pB_Imagename.substring(this.pB_Imagename.length()-6, this.pB_Imagename.length()-4);
        System.err.println("Actualimage: "+actualImage);
        System.err.println("TEXT: "+text);
        System.err.println("selection: "+selection);
        System.err.println("imagename: "+this.pB_Imagename);
        
        
        if(selection.equalsIgnoreCase(actualImage))return true; 
        if(2<5)return false;
        
        
        
        String prefixA = this.pA_Imagename.substring(0, this.pA_Imagename.length()-10);
        String prefixB =  this.pB_Imagename.substring(0, this.pB_Imagename.length()-10);
        if(prefixA.equalsIgnoreCase(prefixB)) return true;
        return false;
        
    }
    
    
    public synchronized void processChatText(Participant sender, String text){
        if(!text.startsWith("/"))return;
        if(this.currentsethasbeensolved){
            cC.c.sendInstructionToParticipant(sender,"The current face has already been solved");
            return;
            
        }
        text=text.replace("\n", "");
        text=text.replace(" ", "");
        
       
        
        if(text.startsWith("/")){
            if(isSelectionCorrect(text)){
                 this.updateScores(true);
                  String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
                  String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
                  if(showFeedbackToDirector) cC.c.sendInstructionToParticipant(pA, "CORRECT! " + scoreA);
                 cC.c.sendInstructionToParticipant(pB, "CORRECT! " + scoreB);
                 this.currentsethasbeensolved=true;
                 //doCountdowntoNextSet_DEPRECATED("CORRECT! They are the SAME", "Next face in "  );
                
            }
            else{
                this.updateScores(false);
                 String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
                 String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
                 if(showFeedbackToDirector)cC.c.sendInstructionToParticipant(pA, "INCORRECT! " + scoreA);
                cC.c.sendInstructionToParticipant(pB, "INCORRECT! " + scoreB);
                this.currentsethasbeensolved=true;
                //doCountdowntoNextSet_DEPRECATED("INCORRECT! They are  DIFFERENT","Next face in " );
                
            }
        
        }
        else{
            cC.c.sendInstructionToParticipant(sender,"Incorrect command:");
            cC.c.sendInstructionToParticipant(sender,"Choose '/s' if you think they are the same");
            cC.c.sendInstructionToParticipant(sender,"Choose '/d' if you think they are different");        
        }
    }
    
    
    
    
    private int getRandomNumberBetween(int lower_inclusive, int upper_exclusive, int exlusive){
        int value =   lower_inclusive+ r.nextInt(upper_exclusive-lower_inclusive);
        while(value==exlusive){
             value =   lower_inclusive+ r.nextInt(upper_exclusive-lower_inclusive);
        }
        return value;
    }
    
    
    public void updateScores(boolean success){
        if(success){
            int scorepAsuccess = this.getScoreCORRECT(pA);
            this.htscoreCORRECT.putObject(pA, scorepAsuccess+1);
            
            int scorepBsuccess = this.getScoreCORRECT(pB);
            this.htscoreCORRECT.putObject(pB, scorepAsuccess+1);
            
            
            double score = (double)  this.htPOINTS.getObject(pA);
            score=score+correctscoreinrement;
            
            this.htPOINTS.putObject(pA,score);
            score = (double)  this.htPOINTS.getObject(pB);
            score=score+correctscoreinrement;
            this.htPOINTS.putObject(pB,score);
            
        }
        else{
             int scorepAsuccess = this.getScoreINCORRECT(pA);
            this.htscoreINCORRECT.putObject(pA, scorepAsuccess+1);
            
            int scorepBsuccess = this.getScoreINCORRECT(pB);
            this.htscoreINCORRECT.putObject(pB, scorepAsuccess+1);
            
            double score = (double)  this.htPOINTS.getObject(pA);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pA,score);
            score = (double)  this.htPOINTS.getObject(pB);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pB,score);
            
            
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
             stimulusself=this.pA_Imagename;
             stimulusother=this.pB_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", ""+this.gamenumber);
             AttribVal av1 = new AttribVal("correct", ""+this.getScoreCORRECT(pA));
             AttribVal av2 = new AttribVal("incorrect", ""+this.getScoreINCORRECT(pA));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",stimulusself  );
             AttribVal av5 = new AttribVal("points", ""+(double)  this.htPOINTS.getObject(pA));
             AttribVal av6 =null; 
             if(hasLoopedThroughAllFaces) {
                 av6 = new AttribVal("haslooped","YES")  ;
             } 
             else {
                 av6 = new AttribVal("haslooped","NO");
             }
             avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5);avs.addElement(av6);
         }
         if(pB==p){
             stimulusself=this.pB_Imagename;
             stimulusother=this.pA_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", ""+this.gamenumber);
             AttribVal av1 = new AttribVal("correct", ""+this.getScoreCORRECT(pB));
             AttribVal av2 = new AttribVal("incorrect", ""+this.getScoreINCORRECT(pB));
             AttribVal av3 = new AttribVal("stimulusself",""+stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",""+stimulusself  ); 
             AttribVal av5 = new AttribVal("points", ""+(double)  this.htPOINTS.getObject(pB));
             AttribVal av6 =null;
             if(hasLoopedThroughAllFaces) {
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
            this.doCountdowntoNextSet_DEPRECATED("FIRSTMESSAGE", "FIRSTMESSAGE");
        }
    }

    @Override
    public void changeClientProgressBars(int value, String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
