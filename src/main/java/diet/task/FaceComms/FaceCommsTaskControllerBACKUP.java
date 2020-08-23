/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.FaceComms;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.FaceComms2016_RAP_TURNBYTURNDyadic;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class FaceCommsTaskControllerBACKUP {

    DefaultConversationController cC;
    double probabOfSame= CustomDialog.getDouble("Enter the probability of SAME stimuli", 0, 1, 0.5);
    public Participant pA;
    public Participant pB;
    Random r = new Random();
    long durationOfStimulus = CustomDialog.getLong("How long should the stimuli be displayed for?", 4000);
    boolean blockTextEntryDuringStimulus = CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
    
    String pA_Imagename;
    String pB_Imagename;
    
    boolean currentsethasbeensolved = false;
    
    public HashtableWithDefaultvalue htscoreCORRECT = new HashtableWithDefaultvalue(0);
    public HashtableWithDefaultvalue htscoreINCORRECT = new HashtableWithDefaultvalue(0);
    
     public HashtableWithDefaultvalue htPOINTS = new HashtableWithDefaultvalue((double)0);
    long gamenumber=0;
    
    double correctscoreinrement = CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    double incorrectpenalty = CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
    
     final  String[] buttons = {"same","different"};
     String displayname = "instructions";
    
    public FaceCommsTaskControllerBACKUP(DefaultConversationController cC) {
        this.cC=cC;
    }
    
    public void startTask(Participant pA, Participant pB){
        this.pA=pA;
        this.pB=pB;
        
       
        
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, 500, 580, "",false,buttons);
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, 500, 580, "",false,buttons);
        cC.c.showStimulusImageEnableButtons(pB, buttons, false);
        cC.c.showStimulusImageEnableButtons(pA, buttons, false);
        doCountdowntoNextSet("Please start!","Next face in " );
    }
    
    
   
    private void loadNextFaceSeOLDt(){
                  String newpA_Imagename ="";
                  String newpB_Imagename ="";
            
                  int index = 1+r.nextInt(30);
           
                  long threshold = (long)(probabOfSame*1000);
                  long number = r.nextInt(1000);
                  if(number<threshold){
                  //do the same
                        Conversation.printWSln("Main", "SAME:");
                        newpA_Imagename = "faceset1/face"+index+".png";
                        newpB_Imagename = "faceset1/face"+index+".png";
                        System.err.println("SAME");
               
                  }
                  else{
                       Conversation.printWSln("Main", "DIFFERENT:");
                       newpA_Imagename = "faceset1/face"+index+".png";
                       newpB_Imagename = "faceset1/face"+getRandomNumberBetween(1, 30, index)+".png";
                       System.err.println("DIFFERENT");
                  }
                  pA_Imagename = newpA_Imagename;
                  pB_Imagename = newpB_Imagename;
                  
    }
    
    
     private void loadNextFaceSet(){
                  String newpA_Imagename ="";
                  String newpB_Imagename ="";
                  
                  
                  
                  long threshold = (long)(probabOfSame*1000);
                  long number = r.nextInt(1000);
                  String imgname = "faceset2/dim";
                  imgname = imgname + (1+r.nextInt(30));
                   boolean nOrp = r.nextBoolean();
                  if(nOrp)imgname = imgname + "_n";
                  if(!nOrp)imgname = imgname + "_p";
                  imgname = imgname + "_r0.png";
                  newpA_Imagename = imgname;
                  
                  if(number<threshold){   
                      newpB_Imagename = ""+imgname;
                  }
                  else{
                      
                      
                      String thenewimagename  = ""+newpA_Imagename;
                      while(thenewimagename.equalsIgnoreCase(newpA_Imagename)){
                               thenewimagename = "faceset2/dim";
                               thenewimagename = thenewimagename + (1+r.nextInt(30));
                               nOrp = r.nextBoolean();
                               if(nOrp)thenewimagename = thenewimagename + "_n";
                               if(!nOrp)thenewimagename = thenewimagename + "_p";
                               thenewimagename = thenewimagename + "_r0.png";
                                          
                      }
                       newpB_Imagename = ""+thenewimagename;
                      
                  }
           
           this.pA_Imagename=newpA_Imagename;   
           this.pB_Imagename=newpB_Imagename;   
           System.err.println("THE FACE SETS ARE: "+this.pA_Imagename);
     }
    
    
    
    
    
    private void doCountdowntoNextSet(final String  firstmessage,final  String countdownmessageprefix){
         Thread t = new Thread(){
             public void run(){
                 try{
                  
                
                  cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                  if(blockTextEntryDuringStimulus)cC.c.changeClientInterface_disableTextEntry(pA);
                  if(blockTextEntryDuringStimulus)cC.c.changeClientInterface_disableTextEntry(pB);
                  
                  
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase(""))cC.c.sendInstructionToParticipant(pA, firstmessage );
                  if(firstmessage!=null && !firstmessage.equalsIgnoreCase("")) cC.c.sendInstructionToParticipant(pB, firstmessage );
                
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase("") &&displayname!=null) cC.c.textOutputWindow_ChangeText("instructions", firstmessage +"\n",true, pA,pB );
                 
                  
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
                  
                 loadNextFaceSet();
                  
                  
                 
                  gamenumber++;
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false,durationOfStimulus);
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false, durationOfStimulus);
                  
                  
                  
                  currentsethasbeensolved = false;
                  
                  
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
                  
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                  
                  
                  if(!blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA, "Your score is: "+ (double)  htPOINTS.getObject(pA)  );
                  if(!blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB, "Your score is: "+ (double)  htPOINTS.getObject(pB)  );
                  
                  
                  if(!blockTextEntryDuringStimulus){
                     cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                     cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                     
                      if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                      if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                      if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                      
                 }
                  
                  if(!blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                  if(!blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                  if(!blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                  if(!blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
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
                             
                             if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                             if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                  
                            
                             if(blockTextEntryDuringStimulus){
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB );
                             }
                            
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            
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
    
   
    
    
    
   
    
    
    public void processChatText(Participant sender, String text){
        if(!text.startsWith("/"))return;
        if(this.currentsethasbeensolved){
            cC.c.sendInstructionToParticipant(sender,"The current face has already been solved");
            return;
            
        }
        text=text.replace("\n", "");
        text=text.replace(" ", "");
        if(text.equalsIgnoreCase("/s")){
            if(this.pA_Imagename.equalsIgnoreCase(this.pB_Imagename)){
                 cC.c.sendInstructionToParticipant(pA, "CORRECT! They are the SAME");
                 cC.c.sendInstructionToParticipant(pB, "CORRECT! They are the SAME");
                 this.currentsethasbeensolved=true;
                 doCountdowntoNextSet("CORRECT! They are the SAME", "Next face in "  );
                 this.updateScores(true);
            }
            else{
                cC.c.sendInstructionToParticipant(pA, "INCORRECT! They are DIFFERENT");
                cC.c.sendInstructionToParticipant(pB, "INCORRECT! They are DIFFERENT");
                this.currentsethasbeensolved=true;
                doCountdowntoNextSet("INCORRECT! They are  DIFFERENT","Next face in " );
                this.updateScores(false);
            }
        }
        else if(text.equalsIgnoreCase("/d")){
            if(!this.pA_Imagename.equalsIgnoreCase(this.pB_Imagename)){
                 cC.c.sendInstructionToParticipant(pA, "CORRECT! They are DIFFERENT");
                 cC.c.sendInstructionToParticipant(pB, "CORRECT! They are DIFFERENT");
                 this.currentsethasbeensolved=true;
                 doCountdowntoNextSet("CORRECT! They are DIFFERENT",  "Next face in " );
                 this.updateScores(true);
            }
            else{
                cC.c.sendInstructionToParticipant(pA, "INCORRECT! They are the SAME");
                cC.c.sendInstructionToParticipant(pB, "INCORRECT! They are the SAME");
                this.currentsethasbeensolved=true;
                doCountdowntoNextSet("INCORRECT! They are the SAME", "Next face in " );
                this.updateScores(false);
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
             AttribVal av0 = new AttribVal("gamenumber", this.gamenumber);
             AttribVal av1 = new AttribVal("correct", this.getScoreCORRECT(pA));
             AttribVal av2 = new AttribVal("incorrect", this.getScoreINCORRECT(pA));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",stimulusself  );
             AttribVal av5 = new AttribVal("points", (double)  this.htPOINTS.getObject(pA));
             avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5);
         }
         if(pB==p){
             stimulusself=this.pB_Imagename;
             stimulusother=this.pA_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", this.gamenumber);
             AttribVal av1 = new AttribVal("correct", this.getScoreCORRECT(pB));
             AttribVal av2 = new AttribVal("incorrect", this.getScoreINCORRECT(pB));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother",stimulusself  ); 
             AttribVal av5 = new AttribVal("points", (double)  this.htPOINTS.getObject(pB));
             avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4);avs.addElement(av5);
         }
    
         return avs;
    } 
     
     
}
