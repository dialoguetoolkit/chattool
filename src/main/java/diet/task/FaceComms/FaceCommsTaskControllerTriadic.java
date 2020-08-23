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
public class FaceCommsTaskControllerTriadic  {

    DefaultConversationController cC;
   // double probabOfSame= 0.5;//CustomDialog.getDouble("Enter the probability of SAME stimuli", 0, 1, 0.5);
    public Participant pA;
    public Participant pB;
    public Participant pC;
    
    Random r = new Random();
    long durationOfStimulus = 5000;//CustomDialog.getLong("How long should the stimuli be displayed for?", 5000);
    boolean blockTextEntryDuringStimulus = true;//CustomDialog.getBoolean("Block text entry while stimulus is displayed?", "block", "do not block");
    
    String pA_Imagename;
    String pB_Imagename;
    String pC_Imagename;
    
    boolean currentsethasbeensolved = false;
    
    public HashtableWithDefaultvalue htscoreCORRECT = new HashtableWithDefaultvalue(0);
    public HashtableWithDefaultvalue htscoreINCORRECT = new HashtableWithDefaultvalue(0);
    
     public HashtableWithDefaultvalue htPOINTS = new HashtableWithDefaultvalue((double)0);
    long gamenumber=0;
    
    double correctscoreinrement = 10;//CustomDialog.getDouble("What is the score increment for correct guesses?", 10);
    double incorrectpenalty = 5;//CustomDialog.getDouble("What is the point penalty for incorrect guesses?", 5);
    
     final  String[] buttons = {"same","different"};
     String displayname = "instructions";
    
    public FaceCommsTaskControllerTriadic(DefaultConversationController cC) {
       
        this.cC=cC;
        this.initFilenames();
    }
    
    Vector<String> vs = new Vector<String>();
    Vector<String> vs2 = new Vector<String>();
    
    private void initFilenames(){
        for(int i=1;i<91;i++){
            String pname = "dim"+i+"_p";
            String nname = "dim"+i+"_n";
            vs.addElement(nname);
            vs.addElement(pname);
        }
        vs2.addElement("_l6_s3");
        vs2.addElement("_l6_s2");
        vs2.addElement("_l6_s1");
        
        vs2.addElement("_l3_s3");
        vs2.addElement("_l3_s2");
        vs2.addElement("_l3_s1");
        
        vs2.addElement("_r6_s3");
        vs2.addElement("_r6_s2");
        vs2.addElement("_r6_s1");
        
        vs2.addElement("_r3_s3");
        vs2.addElement("_r3_s2");
        vs2.addElement("_r3_s1");
        
        
        vs2.addElement("_r0_s3");
        vs2.addElement("_r0_s2");
        vs2.addElement("_r0_s1");
   
    }
    
    
    
    public void startTask(Participant pA, Participant pB, Participant pC){
        this.pA=pA;
        this.pB=pB;
        this.pC=pC;
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pA, 500, 580, "",false,buttons);
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pB, 500, 580, "",false,buttons);
        cC.c.showStimulusImageFromJarFile_InitializeWindow(pC, 500, 580, "",false,buttons);
        cC.c.showStimulusImageEnableButtons(pC, buttons, false);
        cC.c.showStimulusImageEnableButtons(pB, buttons, false);
        cC.c.showStimulusImageEnableButtons(pA, buttons, false);
        doCountdowntoNextSet("Please start!","Next face in " );
    }
    
    
         
     
    // int indexOfLastUsedFace=1;
 
     
     private void loadNextfaceSetAllTheSame(){
         int indexOfFace = r.nextInt(vs.size());
         String imagePrefix = this.vs.get(indexOfFace);
         vs.remove(imagePrefix);         
         String imageName1 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
         String imageName2 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
         String imageName3 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
         this.pA_Imagename=    "facetask/stimuli/"  + imageName1 +".png";   
         this.pB_Imagename=    "facetask/stimuli/"  + imageName2 +".png";
         this.pC_Imagename=    "facetask/stimuli/" + imageName3 + ".png";
     }
     private void loadNextfaceSetAllDifferent(){
         int indexOfFace = r.nextInt(vs.size());
         String imagePrefix = this.vs.get(indexOfFace);
         vs.remove(imagePrefix);         
         String imageName1 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
  
         indexOfFace = r.nextInt(vs.size());
         imagePrefix = this.vs.get(indexOfFace);
         vs.remove(imagePrefix);          
         String imageName2 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
         
         indexOfFace = r.nextInt(vs.size());
         imagePrefix = this.vs.get(indexOfFace);
         vs.remove(imagePrefix);   
         String imageName3 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
         this.pA_Imagename=    "facetask/stimuli/"  + imageName1 +".png";   
         this.pB_Imagename=    "facetask/stimuli/"  + imageName2 +".png";
         this.pC_Imagename=    "facetask/stimuli/" + imageName3 + ".png";
     }
     
     private void loadNextFaceSet2PlusOne(Participant pSharing1, Participant pSharing2, Participant pNotsharing){
           this.pA_Imagename=null;
           this.pB_Imagename=null;
           this.pC_Imagename=null;
         
           int indexOfFace = r.nextInt(vs.size());
           String imagePrefix = this.vs.get(indexOfFace);
           vs.remove(imagePrefix);         
           String imageName1 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
           String imageName2 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
           
           indexOfFace = r.nextInt(vs.size());
           imagePrefix = this.vs.get(indexOfFace);
           vs.remove(imagePrefix);   
           String imageName3 =  imagePrefix+ this.vs2.get(r.nextInt(vs2.size()));
           this.pA_Imagename=    "facetask/stimuli/"  + imageName1 +".png";   
           this.pB_Imagename=    "facetask/stimuli/"  + imageName2 +".png";
           this.pC_Imagename=    "facetask/stimuli/" + imageName3 + ".png";
           
           
           if(pA==pSharing1){
                 this.pA_Imagename=    "facetask/stimuli/"  + imageName1 +".png";   
           }
           else if(pA==pSharing2){
                  this.pA_Imagename= "facetask/stimuli/"  + imageName2 +".png";
           }
           else if(pA==pNotsharing){
                  this.pA_Imagename= "facetask/stimuli/" + imageName3 + ".png";
           }
           
           if(pB==pSharing1){
                  this.pB_Imagename=    "facetask/stimuli/"  + imageName1 +".png";  
           }
           else if (pB==pSharing2){
                  this.pB_Imagename= "facetask/stimuli/"  + imageName2 +".png";
           }
           else if(pB==pNotsharing){
                  this.pB_Imagename= "facetask/stimuli/" + imageName3 + ".png";
           }
           
           if(pC==pSharing1){
                  this.pC_Imagename=    "facetask/stimuli/"  + imageName1 +".png";  
           }
           else if (pC==pSharing2){
                  this.pC_Imagename= "facetask/stimuli/"  + imageName2 +".png";
           }
           else if(pC==pNotsharing){
                  this.pC_Imagename= "facetask/stimuli/" + imageName3 + ".png";
           }
     }
     
     
       public boolean hasLoopedThroughAllFaces = false;
     private void checkIfHasLoopedThroughAll(){
          if(vs.size()==0){
                      this.hasLoopedThroughAllFaces=true;
                      vs2.removeAllElements();
                      vs.removeAllElements();
                      this.initFilenames();
                      System.err.println("HAS LOOPED THROUGH ALL OF THE FACES!");
                      Conversation.printWSln("Main", "Has looped through all the faces");
          }
     }
     
     
     private void loadNextFaceSet(){
                 
                  checkIfHasLoopedThroughAll();
                  boolean loadSame = r.nextBoolean();
                  if(loadSame){
                      this.loadNextfaceSetAllTheSame();
                      
                  }
                  else {
                      this.loadNextfaceSetAllDifferent();
                  }
         
                  /*if(this.gamenumber==30){
                      this.loadNextfaceSetAllTheSame();
                  }
                  if(this.gamenumber==60){
                      this.loadNextfaceSetAllTheSame();
                  }
                  if(this.gamenumber==90){
                      this.loadNextfaceSetAllTheSame();
                  }
         
                  int findPNotSharing = r.nextInt(3);
                  
                  if(findPNotSharing==0){
                      loadNextFaceSet2PlusOne(pA, pC, pB);
                  }
                  else if(findPNotSharing==1){
                      loadNextFaceSet2PlusOne(pC, pA, pB);
                  }
                  else {
                      loadNextFaceSet2PlusOne(pA, pB, pC);
                  }
         
                 */
     }
    
    
    
    
    
    private void doCountdowntoNextSet(final String  firstmessage,final  String countdownmessageprefix){
        final String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
        final  String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
        
        Thread t = new Thread(){
             public void run(){
                 try{
                  
                  
                     
                
                  cC.c.showStimulusImageEnableButtons(pB, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pA, buttons, false);
                  cC.c.showStimulusImageEnableButtons(pC, buttons, false);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                   cC.c.changeClientInterface_clearMainWindows(pC);
                  if(blockTextEntryDuringStimulus)cC.c.changeClientInterface_disableTextEntry(pA);
                  if(blockTextEntryDuringStimulus)cC.c.changeClientInterface_disableTextEntry(pB);
                  if(blockTextEntryDuringStimulus)cC.c.changeClientInterface_disableTextEntry(pC);
                  
                  
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase(""))cC.c.sendInstructionToParticipant(pA, firstmessage );
                  if(firstmessage!=null && !firstmessage.equalsIgnoreCase("")) cC.c.sendInstructionToParticipant(pB, firstmessage );
                  if(firstmessage!=null && !firstmessage.equalsIgnoreCase("")) cC.c.sendInstructionToParticipant(pC, firstmessage );
                
                  if(firstmessage!=null  && !firstmessage.equalsIgnoreCase("") &&displayname!=null){
                      cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreA+"\n" ,true, pA );
                      cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreB+"\n",true, pB );
                       cC.c.textOutputWindow_ChangeText("instructions", firstmessage +scoreB+"\n",true, pC );
                  }
                 
                  
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "5 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "5 secs" );
                  cC.c.sendInstructionToParticipant(pC, countdownmessageprefix + "5 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "5 secs" +"\n" ,true, pA,pB,pC );
                  
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "4 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "4 secs" );
                  cC.c.sendInstructionToParticipant(pC, countdownmessageprefix + "4 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "4 secs" +"\n" ,true, pA,pB,pC );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "3 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "3 secs" );
                  cC.c.sendInstructionToParticipant(pC, countdownmessageprefix + "3 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "3 secs" +"\n" ,true, pA,pB,pC );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.black);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.black);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "2 secs" );
                  cC.c.sendInstructionToParticipant(pB, countdownmessageprefix + "2 secs" );
                  cC.c.sendInstructionToParticipant(pC, countdownmessageprefix + "2 secs" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "2 secs"+"\n"  ,true, pA,pB,pC );
                  Thread.sleep(1000);
                  //cC.c.changeClientInterface_clearMainWindows(pA);
                  //cC.c.changeClientInterface_clearMainWindows(pB);
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.red);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.red);
                  cC.c.sendInstructionToParticipant(pA, countdownmessageprefix + "1 sec" );
                  cC.c.sendInstructionToParticipant(pB,countdownmessageprefix + "1 sec" );
                  cC.c.sendInstructionToParticipant(pC,countdownmessageprefix + "1 sec" );
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions",countdownmessageprefix + "1 secs"+"\n"  ,true, pA,pB );
                  Thread.sleep(1000);
                  cC.c.changeClientInterface_clearMainWindows(pA);
                  cC.c.changeClientInterface_clearMainWindows(pB);
                   cC.c.changeClientInterface_clearMainWindows(pC);
                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","" ,false, pA,pB, pC );
                  //cC.c.changeClientInterface_backgroundColour(pA, Color.white);
                  //cC.c.changeClientInterface_backgroundColour(pB, Color.white);
                  //cC.c.newsendInstructionToParticipant(pA, "Please " );
                   
                  //cC.c.newsendInstructionToParticipant(pB, messageprefix + "1 seconds" );
                  
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pA, "faceset1/face1.png", 4000);
                  //cC.c.showStimulusImageFromJarFile_ChangeImage(pB, "faceset1/face1.png", 4000);
                  
                 loadNextFaceSet();
                  
                  
                 
                  gamenumber++;
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pA, pA_Imagename, false,durationOfStimulus);
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pB, pB_Imagename, false,durationOfStimulus);
                  cC.c.showStimulusImageFromJarFile_ChangeImage(pC, pC_Imagename, false,durationOfStimulus);
                  if(pC_Imagename==null){
                     // System.exit(-56);
                  }
                  
                  
                  currentsethasbeensolved = false;
                  
                  
                  int pAPercentageCorrect=0;
                  int pANumberCorrect = getScoreCORRECT(pA);
                  int pANumberINCorrect = getScoreINCORRECT(pA);
                  
                  int pBPercentageCorrect=0;
                  int pBNumberCorrect = getScoreCORRECT(pB);
                  int pBNumberINCorrect = getScoreINCORRECT(pB);
                  
                  int pCPercentageCorrect=0;
                  int pCNumberCorrect = getScoreCORRECT(pC);
                  int pCNumberINCorrect = getScoreINCORRECT(pC);
                  
                  if(gamenumber>1){
                      pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                      pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                      pCPercentageCorrect= (100*pCNumberCorrect) / (pCNumberCorrect+pCNumberINCorrect);
                  }
                  
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                  //if(!blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                  
                  
                  if(!blockTextEntryDuringStimulus){
                      cC.c.sendInstructionToParticipant(pA, "Your score is: "+ (double)  htPOINTS.getObject(pA)  );
                      cC.c.sendInstructionToParticipant(pB, "Your score is: "+ (double)  htPOINTS.getObject(pB)  );
                      cC.c.sendInstructionToParticipant(pC, "Your score is: "+ (double)  htPOINTS.getObject(pC)  );
                      cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) ,true, pA);
                      cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) ,true, pB);
                      cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pC) ,true, pC);
                      
                  }
                  
                  if(!blockTextEntryDuringStimulus){
                     cC.c.showStimulusImageEnableButtons(pC, buttons, true);
                     cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                     cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                     
                      if(displayname!=null) {
                          
                          cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                          cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                          cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pC) +"\n",true, pC );
                          cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB, pC );
                          
                           cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) ,true, pA);
                           cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) ,true, pB); 
                           cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pC) ,true, pC); 
                           cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",true, pA,pB, pC); 
                      }    
                      
                 }
                  
                  if(!blockTextEntryDuringStimulus){
                      cC.c.sendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                      cC.c.sendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                      cC.c.sendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                      cC.c.sendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                      cC.c.sendInstructionToParticipant(pC,"enter '/s' if you saw the same face");
                      cC.c.sendInstructionToParticipant(pC,"enter '/d' if you saw different faces");
                      
                  }    
                  //if(cC instanceof FaceComms2016_RAP_TURNBYTURNDyadic)      ((FaceComms2016_RAP_TURNBYTURNDyadic)cC).changeInterfaceDelays();
                  
                  
                  
                  
                  Thread tt = new Thread(){
                     public void run(){
                       try{
                            Thread.sleep(durationOfStimulus);
                            cC.c.changeClientInterface_enableTextEntry(pA);
                            cC.c.changeClientInterface_enableTextEntry(pB);
                             cC.c.changeClientInterface_enableTextEntry(pC);
                            
                             int pAPercentageCorrect=0;
                             int pANumberCorrect = getScoreCORRECT(pA);
                             int pANumberINCorrect = getScoreINCORRECT(pA);
                             
                             int pBPercentageCorrect=0;
                             int pBNumberCorrect = getScoreCORRECT(pB);
                             int pBNumberINCorrect = getScoreINCORRECT(pB);
                             
                             int pCPercentageCorrect=0;
                             int pCNumberCorrect = getScoreCORRECT(pC);
                             int pCNumberINCorrect = getScoreINCORRECT(pC);
                             if(gamenumber>1){
                                 pAPercentageCorrect= (100*pANumberCorrect) / (pANumberCorrect+pANumberINCorrect);
                                 pBPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                                 pCPercentageCorrect= (100*pBNumberCorrect) / (pBNumberCorrect+pBNumberINCorrect);
                            }
                  
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pA, "Your success rate is: "+ pAPercentageCorrect+   "%"  );
                            //if(blockTextEntryDuringStimulus)cC.c.newsendInstructionToParticipant(pB, "Your success rate is: "+ pBPercentageCorrect+   "%"  );
                            
                              DecimalFormat myFormatter = new DecimalFormat("############.#");
                             String outputPA = myFormatter.format( (double)  htPOINTS.getObject(pA));
                             String outputPB = myFormatter.format( (double)  htPOINTS.getObject(pB));
                             String outputPC = myFormatter.format( (double)  htPOINTS.getObject(pC));
                             
                             if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA, "Your score is: "+ outputPA );
                             if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB, "Your score is: "+ outputPB );
                             if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pC, "Your score is: "+ outputPC );
                  
                             if(blockTextEntryDuringStimulus){
                         
                                  cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ outputPA ,true, pA);
                                  cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPB,true, pB);
                                  cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+  outputPC,true, pC);
                      
                             }
                             
                             
                             
                            
                             if(blockTextEntryDuringStimulus){
                                 cC.c.showStimulusImageEnableButtons(pC, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pB, buttons, true);
                                 cC.c.showStimulusImageEnableButtons(pA, buttons, true);
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pA) +"\n",true, pA );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pB) +"\n",true, pB );
                                  if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Your score is: "+ (double)  htPOINTS.getObject(pC) +"\n",true, pC );
                                 if(displayname!=null) cC.c.textOutputWindow_ChangeText("instructions","Choose same or different:"+"\n",false, pA,pB,pC );
                             }
                            
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA,"enter '/s' if you saw the same face");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pA,"enter '/d' if you saw different faces");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB,"enter '/s' if you saw the same face");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pB,"enter '/d' if you saw different faces");
                            
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pC,"enter '/s' if you saw the same face");
                            if(blockTextEntryDuringStimulus)cC.c.sendInstructionToParticipant(pC,"enter '/d' if you saw different faces");
                            
                            
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
    
   
    
    
    
    private boolean areTheyAllTheSame(){
        String prefixA = this.pA_Imagename.substring(0, this.pA_Imagename.length()-10);
        String prefixB =  this.pB_Imagename.substring(0, this.pB_Imagename.length()-10);
        String prefixC =  this.pC_Imagename.substring(0, this.pC_Imagename.length()-10);
        if(prefixA.equalsIgnoreCase(prefixB) && prefixB.equalsIgnoreCase(prefixC)) return true;
        return false;     
    }
    private boolean areTheyAllDifferent(){
        String prefixA = this.pA_Imagename.substring(0, this.pA_Imagename.length()-10);
        String prefixB =  this.pB_Imagename.substring(0, this.pB_Imagename.length()-10);
        String prefixC =  this.pC_Imagename.substring(0, this.pC_Imagename.length()-10);
        if(!prefixA.equalsIgnoreCase(prefixB) && !prefixB.equalsIgnoreCase(prefixC) && ! prefixA.equalsIgnoreCase(prefixC)  ) return true;
        return false;     
    }
    
    
    
    
   
    
   
    
    
    public boolean processChatText(Participant sender, String text){
        if(!text.startsWith("/"))return false;
        if(this.currentsethasbeensolved){
            cC.c.sendInstructionToParticipant(sender,"The current face has already been attempted");
            return false;
            
        }
        text=text.replace("\n", "");
        text=text.replace(" ", "");
        
        String scoreA = " Score: "+(Double)this.htPOINTS.getObject(pA);
        String scoreB = " Score: "+(Double)this.htPOINTS.getObject(pB);
        String scoreC = " Score: "+(Double)this.htPOINTS.getObject(pC);
        
        
        
        
        boolean correct = false;
        String prefixA = this.pA_Imagename.substring(0, this.pA_Imagename.length()-10);
        String prefixB =  this.pB_Imagename.substring(0, this.pB_Imagename.length()-10);
        String prefixC =  this.pC_Imagename.substring(0, this.pC_Imagename.length()-10);
        
        
         ///Cutting corners
         if(text.equalsIgnoreCase("/s")){
            correct= this.areTheyAllTheSame();
        }
        else if(text.equalsIgnoreCase("/d")){
             correct = this.areTheyAllDifferent();
        }
        
        
        
        
       
       
        
        if(correct){
            this.currentsethasbeensolved=true;
            this.updateScores(true); 
            cC.c.sendInstructionToParticipant(pA, "CORRECT!" + scoreA);
            cC.c.sendInstructionToParticipant(pB, "CORRECT!"+  scoreB);
            cC.c.sendInstructionToParticipant(pC, "CORRECT!"+  scoreC);
            doCountdowntoNextSet("CORRECT!",  "Next face in " );
        }
        else{
             this.updateScores(false);
             cC.c.sendInstructionToParticipant(pA, "INCORRECT!" + scoreA);
             cC.c.sendInstructionToParticipant(pB, "INCORRECT!" + scoreB);
             cC.c.sendInstructionToParticipant(pC, "INCORRECT!" + scoreC);
             this.currentsethasbeensolved=true;
             doCountdowntoNextSet("INCORRECT!", "Next face in " );
        }
        return true;
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
            
            int scorepCsuccess = this.getScoreCORRECT(pC);
            this.htscoreCORRECT.putObject(pC, scorepAsuccess+1);
            
            
            
            double score = (double)  this.htPOINTS.getObject(pA);
            score=score+correctscoreinrement;
            this.htPOINTS.putObject(pA,score);
            
            score = (double)  this.htPOINTS.getObject(pB);
            score=score+correctscoreinrement;
            this.htPOINTS.putObject(pB,score);
            
             score = (double)  this.htPOINTS.getObject(pC);
            score=score+correctscoreinrement;
            this.htPOINTS.putObject(pC,score);
            
        }
        else{
             int scorepAsuccess = this.getScoreINCORRECT(pA);
            this.htscoreINCORRECT.putObject(pA, scorepAsuccess+1);
            
            int scorepBsuccess = this.getScoreINCORRECT(pB);
            this.htscoreINCORRECT.putObject(pB, scorepAsuccess+1);
            
            int scorepCsuccess = this.getScoreINCORRECT(pC);
            this.htscoreINCORRECT.putObject(pC, scorepAsuccess+1);
            
            double score = (double)  this.htPOINTS.getObject(pA);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pA,score);
            
            score = (double)  this.htPOINTS.getObject(pB);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pB,score);
            
            score = (double)  this.htPOINTS.getObject(pC);
            score=score-this.incorrectpenalty;
            if(score<0)score=0;
            this.htPOINTS.putObject(pC,score);
            
            
        }
        
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pA, ""+this.getScoreCORRECT(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pB, ""+this.getScoreCORRECT(pB));
         cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_correct", pC, ""+this.getScoreCORRECT(pC));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pA, ""+this.getScoreINCORRECT(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pB, ""+this.getScoreINCORRECT(pB));
           cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_incorrect", pC, ""+this.getScoreINCORRECT(pC));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pA, ""+(double)  this.htPOINTS.getObject(pA));
        cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pB, ""+(double)  this.htPOINTS.getObject(pB));
         cC.c.saveAdditionalRowOfDataToSpreadsheetOfTurns("gamedata_score_points", pB, ""+(double)  this.htPOINTS.getObject(pC));
        
           
        Conversation.printWSln("Main", pA.getUsername()+" has "+(double)  this.htPOINTS.getObject(pA));
        Conversation.printWSln("Main", pB.getUsername()+" has "+(double)  this.htPOINTS.getObject(pB));
         Conversation.printWSln("Main", pC.getUsername()+" has "+(double)  this.htPOINTS.getObject(pC));
        
        
    }
    
   
    
    
    
    public int getScoreCORRECT(Participant p){
        return (int)this.htscoreCORRECT.getObject(p);
    }
     public int getScoreINCORRECT(Participant p){
        return (int)this.htscoreINCORRECT.getObject(p);
    }
    
    public Vector getAdditionalValues(Participant p){
        
         String stimulusself ="";
         String stimulusother1 ="";
         String stimulusother2 ="";
         Vector avs = new Vector();
         if(pA==p){
             stimulusself=this.pA_Imagename;
             stimulusother1=this.pB_Imagename;
             stimulusother2=this.pC_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", this.gamenumber);
             AttribVal av1 = new AttribVal("correct", this.getScoreCORRECT(pA));
             AttribVal av2 = new AttribVal("incorrect", this.getScoreINCORRECT(pA));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother1",stimulusother1  );
             AttribVal av5 = new AttribVal("stimulusother2",stimulusother1  );
             AttribVal av6 = new AttribVal("points", (double)  this.htPOINTS.getObject(pA));
             avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5); avs.addElement(av6);
             if(hasLoopedThroughAllFaces) {
                 AttribVal av7 = new AttribVal("haslooped","YES")  ;
                 avs.addElement(av7);
             } 
             else {
                 AttribVal av7 = new AttribVal("haslooped","NO");
                 avs.addElement(av7);
             }
         }
         if(pB==p){
             stimulusself=this.pB_Imagename;
             stimulusother1=this.pA_Imagename;
             stimulusother2=this.pC_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", this.gamenumber);
             AttribVal av1 = new AttribVal("correct", this.getScoreCORRECT(pB));
             AttribVal av2 = new AttribVal("incorrect", this.getScoreINCORRECT(pB));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother1",stimulusother1  );
             AttribVal av5 = new AttribVal("stimulusother2",stimulusother1  );
             AttribVal av6 = new AttribVal("points", (double)  this.htPOINTS.getObject(pB));
             avs.addElement(av0); avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5); avs.addElement(av6);
             if(hasLoopedThroughAllFaces) {
                 AttribVal av7 = new AttribVal("haslooped","YES")  ;
                 avs.addElement(av7);
             } 
             else {
                 AttribVal av7 = new AttribVal("haslooped","NO");
                 avs.addElement(av7);
             }
         }
         if(pC==p){
             stimulusself=this.pC_Imagename;
             stimulusother1=this.pA_Imagename;
             stimulusother2=this.pB_Imagename;
             AttribVal av0 = new AttribVal("gamenumber", this.gamenumber);
             AttribVal av1 = new AttribVal("correct", this.getScoreCORRECT(pC));
             AttribVal av2 = new AttribVal("incorrect", this.getScoreINCORRECT(pC));
             AttribVal av3 = new AttribVal("stimulusself",stimulusself  );
             AttribVal av4 = new AttribVal("stimulusother1",stimulusother1  );
             AttribVal av5 = new AttribVal("stimulusother2",stimulusother1  );
             AttribVal av6 = new AttribVal("points", (double)  this.htPOINTS.getObject(pC));
             
              avs.addElement(av0);avs.addElement(av1); avs.addElement(av2); avs.addElement(av3); avs.addElement(av4); avs.addElement(av5); avs.addElement(av6);
             if(hasLoopedThroughAllFaces) {
                 AttribVal av7 = new AttribVal("haslooped","YES")  ;
                 avs.addElement(av7);
             } 
             else {
                 AttribVal av7 = new AttribVal("haslooped","NO");
                 avs.addElement(av7);
             }
         }
    
         return avs;
    } 
     
      public long getTrialNo(){
          return this.gamenumber;
      }
}
