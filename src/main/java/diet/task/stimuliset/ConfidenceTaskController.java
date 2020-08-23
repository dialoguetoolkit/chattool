/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.stimuliset;

import diet.message.MessageChatTextFromClient;
import diet.server.Conversation;
import diet.server.ConversationController.CCCONFIDENCE;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.utils.ArrayToolkit;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author sre
 */
public class ConfidenceTaskController extends DefaultTaskController{
    
    Random r = new Random();
    File fDIR;
    //JSDMConversationControllerINTERFACE cC; 
    CCCONFIDENCE cC;
    Vector images = new Vector();
    
    public Participant pA = null;
    public Participant pB = null;
    
    public int individualTrialNumber = 0;
    public int individualTrialsPerStage = 2;
    
    static Hashtable allIndividualSuccess = new Hashtable();
    static Hashtable allIndividualFailure = new Hashtable();
    static Hashtable allJointSuccessOfSelector = new Hashtable();
    static Hashtable allJointFailureOfSelector = new Hashtable();
    static Hashtable allJointSuccessOfBoth = new Hashtable();
    static Hashtable allJointFailureOfBoth = new Hashtable();
    static Hashtable numberOfIndividualSelections = new Hashtable();
    static Hashtable numberOfJointSelections = new Hashtable();
    
    String experimentCondition ="UNSET";
    String identifierOfClass ="unset";
    
    public boolean participantIsInConversation(Participant p){
        if(p==pA)return true;
        if(p==pB)return true;
        return false;
    }
    
    public void displayPleaseWait(){
        cC.getC().subliminalstimuliset_displayText(pA, "", "pleasewait", Color.yellow, 0, 0, 0, cC.getDescriptionForP(pA)+" DISPLAYINGPLEASEWAIT");
        cC.getC().subliminalstimuliset_displayText(pB, "", "pleasewait", Color.yellow, 0, 0, 0, cC.getDescriptionForP(pB)+" DISPLAYINGPLEASEWAIT");
    }
    
    
    public synchronized void nextState_assignNEWSPEAKERS(Participant pA, Participant pB,ConfidenceTaskControllerSequence ctcs){
        this.pA = pA;
        this.pB=pB;
        this.ctcs=ctcs;
        this.gotoSTATE1_SendNewStimulusSet();
    }
    
    public synchronized void nextState_assignNEWSPEAKERS_STARTINGAGAIN(Participant pA, Participant pB,ConfidenceTaskControllerSequence ctcs){
        this.pA = pA;
        this.pB=pB;
        this.ctcs=ctcs;
        this.resetToStart();
    }
    
    
    public void saveScoreForParticipant(Participant p){
        
        String score =  "Total number of individual selections made "+ this.getNumberOfIndividualSelectionsMade(p) +
                        "Total number of joint selections made "+ this.getNumberOfJointSelectionsMade(p) +
                " IndividualCorrect: "+this.getIndividualSuccesses(p)+" IndividualFailure: "+this.getIndividualFailures(p) +" Joint success AS SELECTOR: "+this.getJointSuccessesOfSelector(p)+"   joint failures AS SELECTOR "+this.getJointFailuresOfSelector(p)+" Joint success OVERALLL: "+this.getJointSuccessOfBoth(p)+" Joint failure OVERALL: "+this.getJointFailuresOfBoth(p);
     
        cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(p,cC.getDescriptionForP(p)+"_SCORES" , p.getParticipantID() +p.getUsername()+"_"+ score);
        Conversation.printWSln("Main", "("+p.getParticipantID()+","+p.getUsername()+"): "+score);
    }
    
    
    public void updateIndividualScore(Participant p,boolean success){
        int score=0;
        
        if(success){
             Object o = (Integer)allIndividualSuccess.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             allIndividualSuccess.put(p, score);      
        }
        else{
             Object o = (Integer)allIndividualFailure.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             allIndividualFailure.put(p, score); 
        }
    }
    public void updateJointScoreOfSelector(Participant p,boolean success){
        int score=0;
        if(success){
              Object o = (Integer)allJointSuccessOfSelector.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             allJointSuccessOfSelector.put(p, score); 
        }
        else{
             Object o = (Integer)allJointFailureOfSelector.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             allJointFailureOfSelector.put(p, score); 
        }
    }
    public void updateJointScoreOfBoth(Participant p,boolean success){
        int score=0;
        if(success){
              Object o = (Integer)allJointSuccessOfBoth.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             allJointSuccessOfBoth.put(p, score); 
        }
        else{
             Object o = (Integer)allJointFailureOfBoth.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             allJointFailureOfBoth.put(p, score); 
        }
    }
    
    public void updateIndividualSelectionMade(Participant p){
        int score=0;
       
             Object o = (Integer)numberOfIndividualSelections.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             numberOfIndividualSelections.put(p, score);      
    }
    public void updateJointSelectionMade(Participant p){
        int score=0;
       
             Object o = (Integer)numberOfJointSelections.get(p);
             if(o!=null)score=(Integer)o;
             score++;
             numberOfJointSelections.put(p, score);      
    }
    
    
    
    public int getIndividualSuccesses(Participant p){
             Object o = (Integer)allIndividualSuccess.get(p);
             if(o==null){
                 allIndividualSuccess.put(p, 0);
                 return 0;
             }
             return (Integer)o;
    }
    
    public int getIndividualFailures(Participant p){
          Object o = (Integer)allIndividualFailure.get(p);
          if(o==null){
                 allIndividualFailure.put(p, 0);
                 return 0;
             }
           return (Integer)o;
     }        
     
     public int getJointSuccessesOfSelector(Participant p){
             Object o = (Integer)allJointSuccessOfSelector.get(p);
             if(o==null){
                 allJointSuccessOfSelector.put(p, 0);
                 return 0;
             }
             return (Integer)o;
    }
    
    public int getJointFailuresOfSelector(Participant p){
          Object o = (Integer)allJointFailureOfSelector.get(p);
          if(o==null){
                 allJointFailureOfSelector.put(p, 0);
                 return 0;
             }
           return (Integer)o;
     }  
       
    public int getJointSuccessOfBoth(Participant p){
          Object o = (Integer)allJointSuccessOfBoth.get(p);
          if(o==null){
                 allJointSuccessOfBoth.put(p, 0);
                 return 0;
             }
           return (Integer)o;
    }
    public int getJointFailuresOfBoth(Participant p){
          Object o = (Integer)allJointFailureOfBoth.get(p);
          if(o==null){
                 allJointFailureOfBoth.put(p, 0);
                 return 0;
             }
           return (Integer)o;
    }
    public int getNumberOfIndividualSelectionsMade(Participant p){
          Object o = (Integer)numberOfIndividualSelections.get(p);
          if(o==null){
                 numberOfIndividualSelections.put(p, 0);
                 return 0;
           }
           return (Integer)o;
    }
    public int getNumberOfJointSelectionsMade(Participant p){
          Object o = (Integer)numberOfJointSelections.get(p);
          if(o==null){
                 numberOfJointSelections.put(p, 0);
                 return 0;
           }
           return (Integer)o;
    }
            
            
            
     //PrintWriter pwOUT;
    
    ConfidenceTaskControllerSequence ctcs;
    
    public ConfidenceTaskController(CCCONFIDENCE cC, ConfidenceTaskControllerSequence ctcs,String identifierOfCTC, String experimentalCondition, int numberOfIndividualTrialsPerStage, long stimulusTimeLONG){
        this.cC=cC;
        this.ctcs=ctcs;
        this.identifierOfClass=identifierOfCTC;
        this.experimentCondition=experimentalCondition;
        this.individualTrialsPerStage=numberOfIndividualTrialsPerStage;
        this.stimulustime=stimulusTimeLONG;
        
        try{
           //cC.getC().get
          // File directory = cC.getC().getConversationDirectory();
           //File outputFile = new File(directory,filename);
           //pwOUT = new PrintWriter(new BufferedWriter(new FileWriter(outputFile))); 
           //pwOUT.write("STRING");
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-5);
        }
        //String indstimTEMP = "INDIVIDUALSTIMULUS 1378895914803_1378895917303_1378895918403_1378895920903_1378895922003";
        //this.processIndividualStimulusInfo(null, indstimTEMP);
        
        //System.exit(-45);
        initializeImages();
    }
    
    
    
    
    
    
    public String getID(){
        try{
          return "("+pA.getParticipantID()+","+pA.getUsername()+")("+pB.getParticipantID()+","+pB.getUsername()+")";
        }catch(Exception e){
            return "no ID yet";
        }  
    }
    
    
    public void setSTime(long stimulitime ){
        this.stimulustime=stimulitime;
    }
    public void setBTime(long backgroundtime ){
        this.backgroundtime=backgroundtime;
    }
    public void setFTime(long fixationtime ){
        this.fixationtime=fixationtime;
    }
    
    
   
    public long fixationtime =2500;
    public long stimulustime =100;
    //long fixation2time =1000;
    //long stimulus2time =2000; 
    //long background2time=1000;
    long backgroundtime=1000;
                                                               
    //long timeShowingInitialAnswerIsSameAndCorrect = 2000;
    //long timeShowingInitialAnswerIsSameButWrong = 2000;
    
    long timeShowingEvaluationOfIndividualtrial = 2000;
    long timeShowingEvaluationOfJointtrial = 2000;
    
    String messageForCorrectJoint = "";
    String messageForIncorrectJoint = "";
    String messaageForStart = "";
    
    int state = state0WAITINGFORSTART;
    
    static final int state0WAITINGFORSTART=0;
    static final int state1ANSWERINGINDIVIDUALPROBLEMS =1;
    static final int state2DISPLAYINGINCONGRUENTMESSAGE =2;
    static final int state3INCONGRUENTINDIIVIDUALANSWERSWAITINGFORJOINT =3;
    static final int state4PERFORMINGEVALUATION =4;
    //tatic final int state3INCONGRUENT=3;
    
    int state1_PatchIsInFirstOrSecond = -1;
    int state1_PatchIndexInHexagram = -1;
    int state1_PatchIntensity = -1;
    long state1ProjectedTimeOfSendingResponse = new Date().getTime();
    
    Participant participant__state2PersonWhoChooses = null;
    
    boolean state0ParticipantAHasSentStart = false;
    boolean state0ParticipantBHasSentStart = false;
    
    
    
    int pA__state1SelectionOfA = -1;
    int pB__state1SelectionOfB = -1;
    //long state1TimeOfStimulusSend = new Date().getTime();
    
    
    
    //int correctAnswer;
    //int levelofContrast;
     
    
   
    
    long pA__TimeOnClientFixation1Time = -1;
    long pA__TimeOnClientStimulus1Time = -1;
    long pA__TimeOnClientFixation2Time = -1;
    long pA__TimeOnClientStimulus2Time = -1;
    long pA__TimeOnClientOfQuestionTime =  -1;
   
    long pA__TimeOnServerOfCommandToClientToCarryOutNextTrial =-1;
    long pA__TimeOnClientOfIndividualSelection = -1;
    long pA__TimeOnServerOfIndividualSelection = -1;
    //int  pA_IndividualDecision = -1;
    
    
    
    long pA__timeOnClientOfJointPrompt =-1;
    long pA__timeOnServerOfJointPrompt =-1;
    long pA__timeOnClientOfJointSelection = -1;
    long pA__timeOnServerOfJointSelection = -1;
    int pA__JointSelection = -1;
   
    long pA__TimeOnClientOfSelectionDoubleSlash =-1;
    long pA__TimeOnServerOfSelectionDoubleSlash =-1;     
    
     long pB__TimeOnClientFixation1Time = -1;
     long pB__TimeOnClientStimulus1Time = -1;
     long pB__TimeOnClientFixation2Time = -1;
     long pB__TimeOnClientStimulus2Time = -1;
     long pB__TimeOnClientQuestionTime =  -1;
    
     
     
     long pB__TimeOnServerOfCommandToClientToCarryOutNextTrial =-1;
     long pB__TimeOnClientOfIndividualSelection = -1;
     long pB__TimeOnServerOfIndividualSelection = -1;
     //int  pB_IndividualDecision = -1;
   
     long pB__timeOnClientOfJointPrompt =-1;
     long pB__timeOnServerOfJointPrompt =-1;
     long pB__timeOnClientOfJointSelection = -1;
     long pB__timeOnServerOfJointSelection = -1;
     int pB__JointSelection = -1;
    
     long pB__TimeOnClientOfSelectionDoubleSlash =-1;
     long pB__TimeOnServerOfSelectionDoubleSlash =-1;  
     
     
     
    public Object[] getDataFromTrial(){
        
           //Need to do this before as there might be an error.
           String participantWhoChoosesInJointTrialID ="";
           String participantWhoChoosesInJointTrialUsername ="";
           if(this.participant__state2PersonWhoChooses!=null){
                participantWhoChoosesInJointTrialID =participant__state2PersonWhoChooses.getParticipantID();
                participantWhoChoosesInJointTrialUsername =participant__state2PersonWhoChooses.getUsername();
           }
           
           
        
           Object[] dataFromTrial = {
                
                "Patch_is_in_first_or_second:",
                this.state1_PatchIsInFirstOrSecond, //saved
                "Patch_Index_in_hexagram:",
                this.state1_PatchIndexInHexagram,  //saved
                "Patch_Intensity:",
                this.state1_PatchIntensity,        //saved
                //saved
                 
                "PA_ID:",
                this.pA.getParticipantID(),         //saved
                "PA_Username:",
                this.pA.getUsername(),              //saved
                
                "PA_TimeOnServerOfStartOfTrial:",
                this.pA__TimeOnServerOfCommandToClientToCarryOutNextTrial, //saved
                
                "PA_TimeOnServerOfINDIVIDUALSelection:",
                this.pA__TimeOnServerOfIndividualSelection,    //saved
                
                "PA_TimeOnClientOfFixation1Time:",
                pA__TimeOnClientFixation1Time,  //saved
                "PA_TimeOnClientOfStimulus1Time:",
                pA__TimeOnClientStimulus1Time,  //saved
                "PA_TimeOnClientOfFixation2Time:",
                pA__TimeOnClientFixation2Time,  //saved
                "PA_TimeOnClientOfStimulus2Time:",
                pA__TimeOnClientStimulus2Time,  //saved
                
                "PA_TimeOnClientOfIndividualQuestion:",
                this.pA__TimeOnClientOfQuestionTime,    //saved //prompt on client
                "PA_TimeOnClientOfIndividualSelection:",
                this.pA__TimeOnClientOfIndividualSelection, //saved
                "PA_IndividualSelection",
                this.pA__state1SelectionOfA, //saved
                
                "PA_TimeOnServerOfJointPrompt",
                pA__timeOnServerOfJointPrompt,  //saved
                "PA_TimeOnServerOfJointSelection",
                pA__timeOnServerOfJointSelection,//saved
                
                "PA_TimeOnClientOfJointPrompt",
                pA__timeOnClientOfJointPrompt,   //saved     
                "PA_TimeOnClientOfJointSelection",
                pA__timeOnClientOfJointSelection,  //saved
                "PA_JointSelection",
                pA__JointSelection,                //saved
                "PA_TimeOnClientOfSelectionDoubleSlash",
                pA__TimeOnClientOfSelectionDoubleSlash,
                 "PA_TimeOnServerOfSelectionDoubleSlash",
                pA__TimeOnServerOfSelectionDoubleSlash,
                
                
                 "PB_ID:",
                this.pB.getParticipantID(),
                "PB_Username:",
                this.pB.getUsername(),
                
                "PB_TimeOnServerOfStartOfTrial:",
                this.pB__TimeOnServerOfCommandToClientToCarryOutNextTrial, //saved
               "PB_TimeOnServerOfINDIVIDUALSelection:",
                this.pB__TimeOnServerOfIndividualSelection, //saved
                
                "PB_TimeOnClientOfFixation1Time:",
                pB__TimeOnClientFixation1Time,  //saved
                
                "PB_TimeOnClientOfStimulus1Time:",
                pB__TimeOnClientStimulus1Time,  //saved
                
                "PB_TimeOnClientOfFixation2Time:",
                pB__TimeOnClientFixation2Time,  //saved
                
                "PB_TimeOnClientOfStimulus2Time:",
                pB__TimeOnClientStimulus2Time,  //saved
                
                "PB_TimeOnClientOfIndividualQuestion:",
                this.pB__TimeOnClientQuestionTime,  
                
                 "PB_TimeOnClientOfIndividualSelection:",
                this.pB__TimeOnClientOfIndividualSelection,  //saved
                
                 "PB_IndividualSelection",
                this.pB__state1SelectionOfB,   //saved
               
                 "PB_TimeOnServerOfJointPrompt",
                pB__timeOnServerOfJointPrompt, //saved
                
                "PB_TimeOnServerOfJointSelection",
                pB__timeOnServerOfJointSelection, //saved
                
                "PB_TimeOnClientOfJointPrompt",
                pB__timeOnClientOfJointPrompt,    
                
                 "PB_TimeOnClientOfJointSelection",
                pB__timeOnClientOfJointSelection,   //saved
               
                "PB_JointSelection",
                pB__JointSelection,         //saved
                
                 "PB_TimeOnClientOfSelectionDoubleSlash",
                pB__TimeOnClientOfSelectionDoubleSlash,
                
                 "PB_TimeOnServerOfSelectionDoubleSlash",
                pB__TimeOnServerOfSelectionDoubleSlash,
                
                
                participantWhoChoosesInJointTrialID,
                participantWhoChoosesInJointTrialUsername,
                
                
                
               
                
                
           };
        
           
           
           
           
           
         
            //save SAME/DIFF  or practice.
            //the session
            //trial number
            //the right answer
            //level of contrast in the stimuli
            //A's individual decision
            //A's reaction time measured as time from question mark appearance on the screen and pressing "enter" after the answer
            //B's individual decision
            //B's reaction time measured as time from question mark appearance on the screen and pressing "enter" after the answer
            //Joint decision (either the actual joint decision if they disagreed or the response they both gave if they agreed)
            //Joint reaction time measured as time from prompt for joint decision and pressing "enter" after the joint answer
            
        
           return dataFromTrial;
     }
    
    
    
    
    
    public synchronized void resetDataForTrial(){
                this.state1_PatchIndexInHexagram=-1; //saved
                this.state1_PatchIntensity=-1;        //saved
                this.state1_PatchIsInFirstOrSecond=-1; //saved
          
                this.pA__TimeOnServerOfCommandToClientToCarryOutNextTrial=-1;
                this.pA__TimeOnServerOfIndividualSelection=-1;   //saved
                pA__TimeOnClientFixation1Time=-1; //saved
                pA__TimeOnClientStimulus1Time=-1;  //saved
                pA__TimeOnClientFixation2Time=-1;//saved
                pA__TimeOnClientStimulus2Time=-1; //saved
                
                this.pA__TimeOnClientOfQuestionTime=-1;    //saved //prompt on client
                this.pA__TimeOnClientOfIndividualSelection=-1; //saved
                this.pA__state1SelectionOfA=-1; //saved
               
                pA__timeOnServerOfJointPrompt=-1;  //saved
                pA__timeOnServerOfJointSelection=-1;//saved
                
                pA__timeOnClientOfJointPrompt=-1;   //saved            
                pA__timeOnClientOfJointSelection=-1;  //saved
                pA__JointSelection=-1;               //saved
                
                pA__TimeOnClientOfSelectionDoubleSlash=-1;
                pA__TimeOnServerOfSelectionDoubleSlash=-1;
                
                
                
                
                
                this.pB__TimeOnServerOfCommandToClientToCarryOutNextTrial=-1;
                this.pB__TimeOnServerOfIndividualSelection=-1;
                
                pB__TimeOnClientFixation1Time=-1;
                pB__TimeOnClientStimulus1Time=-1;
                pB__TimeOnClientFixation2Time=-1;
                pB__TimeOnClientStimulus2Time=-1;
                
                
                this.pB__TimeOnClientQuestionTime=-1;
                this.pB__TimeOnClientOfIndividualSelection=-1;
                this.pB__state1SelectionOfB=-1;
               
                pB__timeOnServerOfJointPrompt=-1;
                pB__timeOnServerOfJointSelection=-1;
                
                
                pB__timeOnClientOfJointPrompt=-1;             
                pB__timeOnClientOfJointSelection=-1;
                pB__JointSelection=-1;     //saved
                
                pB__TimeOnClientOfSelectionDoubleSlash=-1;
                pB__TimeOnServerOfSelectionDoubleSlash=-1;
                
                this.participant__state2PersonWhoChooses=null;
               
                
                
               
                
                
           };
    
    
    
    
    
    
     public synchronized void saveState(){
           Object[] state = this.getDataFromTrial();
           String stateAsString = ArrayToolkit.convertArrayOfObjectsToString(state,"//");
           cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(cC.getDescriptionForP(pA)+"INTERVENTION", identifierOfClass+"//"+experimentCondition+"//"+individualTrialNumber +"//"+stateAsString);
      
          
           
           
     }
    
    
    
     //PB_TimeOnServerOfJointSelection,PB_TimeOnClientOfJointPrompt,PB_TimeOnClientOfJointSelection  aren't stored, nor are the pA versions'
    
    
    
    
    
    
     public void updateWithClientEvent_JointStimulusInfo(Participant p, String stimulusInfo){
         //
         //DISPLAYEDBUFFEREDIMAGE _jointquestionresponder_1378895930548_0
         //DISPLAYEDBUFFEREDIMAGE _jointquestionnonresponder_1378895930548_0
         if(stimulusInfo.contains("jointquestionresponder")){
            
             //just error checking
             if(p!=this.participant__state2PersonWhoChooses){
                   cC.getC().printWln("ERROR299", p.getUsername()+"...."+stimulusInfo+"..."+participant__state2PersonWhoChooses.getUsername());
                   cC.getC().saveErrorLog("ERROR299"+  p.getUsername()+"...."+stimulusInfo+"..."+participant__state2PersonWhoChooses.getUsername());
                   System.err.println("ERROR299"+  p.getUsername()+"...."+stimulusInfo+"..."+participant__state2PersonWhoChooses.getUsername());
                   return;
             }
         }
         else if(stimulusInfo.contains("jointquestionnonresponder")){
             //Just error checking
             if(p==this.participant__state2PersonWhoChooses){
                   cC.getC().printWln("ERROR299", p.getUsername()+"...."+stimulusInfo+"..."+participant__state2PersonWhoChooses.getUsername());
                   cC.getC().saveErrorLog("ERROR299"+  p.getUsername()+"...."+stimulusInfo+"..."+participant__state2PersonWhoChooses.getUsername());
                   System.err.println("ERROR299"+  p.getUsername()+"...."+stimulusInfo+"..."+participant__state2PersonWhoChooses.getUsername());
                   return;
             }
         }
         else{
             cC.getC().printWln("ERROR234", p.getUsername()+"...."+stimulusInfo);
             cC.getC().saveErrorLog("ERROR234"+  p.getUsername()+"...."+stimulusInfo);
             System.err.println("THERE IS AN ERROR "+2341325);
             return;
         }
         
         String jointStimulusInfo = stimulusInfo.replace("DISPLAYEDBUFFEREDIMAGE _jointquestionresponder_", "");
                                  
         jointStimulusInfo = jointStimulusInfo.replace("DISPLAYEDBUFFEREDIMAGE _jointquestionnonresponder_", "");
         jointStimulusInfo = jointStimulusInfo.replace("_0", "");
         try{
              Long jointprompt = Long.parseLong(jointStimulusInfo);
              if(p==pA){
                  this.pA__timeOnClientOfJointPrompt=jointprompt;
              }
              else if (p==pB){
                  this.pB__timeOnClientOfJointPrompt=jointprompt;
              }
              else{
                  cC.getC().printWln("ERROR236", p.getUsername()+"...."+stimulusInfo);
                  cC.getC().saveErrorLog("ERROR236"+  p.getUsername()+"...."+stimulusInfo);
                  System.err.println("THERE IS AN ERROR "+2341326);
              }
         }catch(Exception e){
                       e.printStackTrace();
         }
               
        
         

     }
    
    
    
     public String[] updateWithClientEvent_IndividualStimulusInfo(Participant recipientOfStimulus, String stimulusInfo){
        String indstim = stimulusInfo;
        
        indstim = indstim.replace("INDIVIDUALSTIMULUS ", "");
        String[] indstimValues = indstim.split("_");
        
        String actualFixation1Time =  "unset";
        String actualStimulus1Time =  "unset";
        String actualFixation2Time =  "unset";
        String actualStimulus2Time =  "unset";
        String actualQuestionTime  =  "unset";
                 long actualFixation1TimeAsLong = -1;
        long actualStimulus1TimeAsLong = -1;
        long actualFixation2TimeAsLong = -1;
        long actualStimulus2TimeAsLong = -1;
        long actualQuestionTimeAsLong =  -1;
          
        try{
             actualFixation1Time = indstimValues[0];
             actualStimulus1Time = indstimValues[1];
             actualFixation2Time = indstimValues[2];
             actualStimulus2Time = indstimValues[3];
             actualQuestionTime =  indstimValues[4];
            
             actualFixation1TimeAsLong = Long.parseLong(indstimValues[0]);
             actualStimulus1TimeAsLong = Long.parseLong(indstimValues[1]);
             actualFixation2TimeAsLong = Long.parseLong(indstimValues[2]);
             actualStimulus2TimeAsLong = Long.parseLong(indstimValues[3]);
             actualQuestionTimeAsLong =  Long.parseLong(indstimValues[4]);
            
             if(recipientOfStimulus == this.pA){
                 this.pA__TimeOnClientFixation1Time = actualFixation1TimeAsLong;
                 this.pA__TimeOnClientStimulus1Time = actualStimulus1TimeAsLong;
                 this.pA__TimeOnClientFixation2Time = actualFixation2TimeAsLong;
                 this.pA__TimeOnClientStimulus2Time = actualStimulus2TimeAsLong;
                 this.pA__TimeOnClientOfQuestionTime  = actualQuestionTimeAsLong;
             }
             else if(recipientOfStimulus == this.pB){
                 this.pB__TimeOnClientFixation1Time = actualFixation1TimeAsLong;
                 this.pB__TimeOnClientStimulus1Time = actualStimulus1TimeAsLong;
                 this.pB__TimeOnClientFixation2Time = actualFixation2TimeAsLong;
                 this.pB__TimeOnClientStimulus2Time = actualStimulus2TimeAsLong;
                 this.pB__TimeOnClientQuestionTime  = actualQuestionTimeAsLong;
             }
             else{
                 System.out.println("ERRROR2324324 "+recipientOfStimulus+"..."+stimulusInfo);
                 this.cC.getC().saveErrorLog("ERRROR2324324 "+recipientOfStimulus+"..."+stimulusInfo);
                 Conversation.printWSln("Main", "REALLY SERIOUS ERROR "+2324324+" processing individualstimulusinfo from the wrong participant "+recipientOfStimulus.getParticipantID()+".."+recipientOfStimulus.getUsername());
             }
             
            
            
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] value = {actualFixation1Time,actualStimulus1Time,actualFixation2Time,actualStimulus2Time,actualQuestionTime};     
        return value;  
     }
    
    
    
    
    
    
    
    public void processChatText(final Participant p, MessageChatTextFromClient mct){
         String text = ""+mct.getText();
        
         cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(p, cC.getDescriptionForP(p)   +"TASKMOVE",text);
        
         if(pA==null||pB==null)return;
        
         if(state==state0WAITINGFORSTART){
              if(!text.equalsIgnoreCase("/start")) return;
              if(p==pA & text.equalsIgnoreCase("/start")) {
                  state0ParticipantAHasSentStart = true;
                  cC.getC().deprecated_sendArtificialTurnToRecipient(p, "Thankyou...please wait for other participant", 0);
              }
              if(p==pB & text.equalsIgnoreCase("/start")) {
                  state0ParticipantBHasSentStart = true;
                  cC.getC().deprecated_sendArtificialTurnToRecipient(p, "Thankyou...please wait for other participant", 0);
              }
              if(this.state0ParticipantAHasSentStart&&this.state0ParticipantBHasSentStart){
                  
                  cC.getC().changeClientInterface_clearMainWindows(pA);
                  cC.getC().changeClientInterface_clearMainWindows(pB);
                  gotoSTATE1_SendNewStimulusSet();
                  
              }
         }
         
         
         
         
         else if(state==state1ANSWERINGINDIVIDUALPROBLEMS){
             if(new Date().getTime()<state1ProjectedTimeOfSendingResponse){
                 cC.getC().deprecated_sendArtificialTurnToRecipient(p, "****YOU SHOULD WAIT TILL YOU HAVE SEEN ALL THE IMAGES!***", 0, cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTTYPEDTOOSOON");
                 return;
             }
             if(! (text.equalsIgnoreCase("/1")|(text.equalsIgnoreCase("/2")))){
                 cC.getC().deprecated_sendArtificialTurnToRecipient(p, "****YOU NEED TO TYPE EITHER  /1 or /2 ***", 0,cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADETYPE");
                 return;
             }
             if(text.equalsIgnoreCase("/1")&&p==pA){
                  pA__state1SelectionOfA=1;
                  this.pA__TimeOnServerOfIndividualSelection=mct.getTimeOfReceipt().getTime();
                  this.pA__TimeOnClientOfIndividualSelection=mct.getTimeOfSending().getTime();
                  if(pB__state1SelectionOfB<1){
                      cC.getC().subliminalstimuliset_displayText(pA, "","individualdecisionmade",Color.BLACK, 20,30,-9999,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                  
                  }
                      
             }
             if(text.equalsIgnoreCase("/2")&&p==pA){
                 pA__state1SelectionOfA=2;
                 this.pA__TimeOnServerOfIndividualSelection=mct.getTimeOfReceipt().getTime();
                 this.pA__TimeOnClientOfIndividualSelection=mct.getTimeOfSending().getTime();
                  if(pB__state1SelectionOfB<1){
                      cC.getC().subliminalstimuliset_displayText(pA, "","individualdecisionmade",Color.BLACK, 20,30,-9999,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                  }
                 
             }
             if(text.equalsIgnoreCase("/1")&&p==pB){
                 pB__state1SelectionOfB=1;
                 this.pB__TimeOnServerOfIndividualSelection=mct.getTimeOfReceipt().getTime();
                 this.pB__TimeOnClientOfIndividualSelection=mct.getTimeOfSending().getTime();
                  if(pA__state1SelectionOfA<1){
                      cC.getC().subliminalstimuliset_displayText(pB, "","individualdecisionmade",Color.BLACK, 20,30,-9999,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                  }
                 
             }
             if(text.equalsIgnoreCase("/2")&&p==pB){
                 pB__state1SelectionOfB=2;
                 this.pB__TimeOnServerOfIndividualSelection=mct.getTimeOfReceipt().getTime();
                 this.pB__TimeOnClientOfIndividualSelection=mct.getTimeOfSending().getTime();
                 if(pA__state1SelectionOfA<1){
                     cC.getC().subliminalstimuliset_displayText(pB, "","individualdecisionmade",Color.BLACK, 20,30,-9999,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                 }
                 
             }
             //if(state1SelectionOfB<1)cC.getC().subliminalstimuliset_displayText(pA, "","individualdecisionmade",Color.BLACK, 20,30,1000,this.getID()+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
             //if(state1SelectionOfA<1)cC.getC().subliminalstimuliset_displayText(pB, "","individualdecisionmade",Color.BLACK, 20,30,1000,this.getID()+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                  
             
             if(pA__state1SelectionOfA!=-1 && pB__state1SelectionOfB!=-1){
                  if(pA__state1SelectionOfA==this.state1_PatchIsInFirstOrSecond&&pB__state1SelectionOfB==this.state1_PatchIsInFirstOrSecond){
                      cC.getC().deprecated_sendArtificialTurnToRecipient(pA, "****CORRECT***", 0,cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADETYPE_SENTTOCHATFRAME");
                      cC.getC().deprecated_sendArtificialTurnToRecipient(pB, "****CORRECT***", 0,cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADETYPE_SENTTOCHATFRAME");
                      cC.getC().subliminalstimuliset_displayText(pA, "","individualdecisioncorrect",Color.BLACK, 20,30,this. timeShowingEvaluationOfIndividualtrial,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                      cC.getC().subliminalstimuliset_displayText(pB, "","individualdecisioncorrect",Color.BLACK, 20,30,this. timeShowingEvaluationOfIndividualtrial,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDCORRECT_SENTTOGUI");
                      Conversation.printWSln("Main", cC.getDescriptionForP(p)+" Individual decisions are BOTH correct");
                     // gotoSTATE1_SendNewStimulusSet();
                      
                      this.updateIndividualScore(pA, true);
                      this.updateIndividualScore(pB, true);
                      this.updateIndividualSelectionMade(pA);
                      this.updateIndividualSelectionMade(pB);
                      saveScoreForParticipant(pA);
                      saveScoreForParticipant(pB);
                      postTrialCalculations();
                      
                  }
                  else if(pA__state1SelectionOfA==pB__state1SelectionOfB&&pA__state1SelectionOfA!=state1_PatchIsInFirstOrSecond){
                     cC.getC().deprecated_sendArtificialTurnToRecipient(pA, "****WRONG***", 0,cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADETYPE_SENTTOCHATFRAME");
                     cC.getC().deprecated_sendArtificialTurnToRecipient(pB, "****WRONG***", 0,cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADETYPE_SENTTOCHATFRAME");
                     cC.getC().subliminalstimuliset_displayText(pA,  "","individualdecisionwrong",Color.BLACK, 20,30,this. timeShowingEvaluationOfIndividualtrial,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDWRONG");
                     cC.getC().subliminalstimuliset_displayText(pB, "","individualdecisionwrong",Color.BLACK, 20,30,this. timeShowingEvaluationOfIndividualtrial,cC.getDescriptionForP(p)+"INDIVIDUALSAMEANDWRONG");
                      Conversation.printWSln("Main", cC.getDescriptionForP(p)+" Individual decisions are BOTH wrong");
                      this.updateIndividualScore(pA, false);
                      this.updateIndividualScore(pB, false);
                      this.updateIndividualSelectionMade(pA);
                      this.updateIndividualSelectionMade(pB);
                      saveScoreForParticipant(pA);
                      saveScoreForParticipant(pB);
                      //gotoSTATE1_SendNewStimulusSet();
                      postTrialCalculations();
                  }
                  else if(pA__state1SelectionOfA!=pB__state1SelectionOfB){
                      
                    int pWhoChooses = r.nextInt(2);
                    if(pWhoChooses==0){
                        this.participant__state2PersonWhoChooses=pA;
                    }
                    else{
                        this.participant__state2PersonWhoChooses=pB;
                    }
                                       
                    
                    
                    if(pA__state1SelectionOfA==state1_PatchIsInFirstOrSecond){
                        cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(pA,cC.getDescriptionForP(p)+"SELECTIONINFO" , "MADE CORRECT SELECTION: "+pA__state1SelectionOfA );
                        cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(pB,cC.getDescriptionForP(p)+"SELECTIONINFO" , "MADE WRONG SELECTION: "+pB__state1SelectionOfB );
                        Conversation.printWSln("Main", cC.getDescriptionForP(p)+"Individual decisions are DIFFERENT: "+pA.getUsername()+" is correct");
                        this.updateIndividualScore(pA, true);
                        this.updateIndividualScore(pB, false);
                        this.updateIndividualSelectionMade(pA);
                        this.updateIndividualSelectionMade(pB);
                        saveScoreForParticipant(pA);
                        saveScoreForParticipant(pB);
                    }
                    else{
                        Conversation.printWSln("Main", cC.getDescriptionForP(p)+"Individual decisions are DIFFERENT");
                        cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(pA,cC.getDescriptionForP(p)+"SELECTIONINFO" , "MADE WRONG SELECTION: "+pA__state1SelectionOfA );
                        cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(pB,cC.getDescriptionForP(p)+"SELECTIONINFO" , "MADE CORRECT SELECTION: "+pB__state1SelectionOfB );
                        Conversation.printWSln("Main", cC.getDescriptionForP(p)+"Individual decisions are DIFFERENT: "+pB.getUsername()+" is correct");
                        this.updateIndividualScore(pA, false);
                        this.updateIndividualScore(pB, true);
                        this.updateIndividualSelectionMade(pA);
                        this.updateIndividualSelectionMade(pB);
                        saveScoreForParticipant(pA);
                        saveScoreForParticipant(pB);;
                    }
                    cC.getC().changeClientInterface_enableConversationHistory(pA);
                    cC.getC().changeClientInterface_enableConversationHistory(pB);
                    //cC.getC().changeClientInterface_clearMaintextEntryWindow(pA);
                    //cC.getC().changeClientInterface_clearMaintextEntryWindow(pB);
                    
                    
                    state = state2DISPLAYINGINCONGRUENTMESSAGE;
                    
                    
                    //SAVE INFO OF WHO MADE RIGHT DECISION AND WHO MADE WRONG DECISION
                    //Go to next state
                    //choose randomly who selects
                    //save info of whether 
                    final ConfidenceTaskController cTC = this;
                   
                    Thread t = new Thread(){
                        public void run(){
                            cTC.cC.getC().subliminalstimuliset_displayText(pA, "","different",Color.BLACK, 20,30,0,cC.getDescriptionForP(p)+"MADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                            cTC.cC.getC().subliminalstimuliset_displayText(pB, "","different",Color.BLACK, 20,30,0,cC.getDescriptionForP(p)+"MADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");
                            try{Thread.sleep(cTC.timeShowingEvaluationOfIndividualtrial);}catch (Exception e){e.printStackTrace();};
                            if(pA==cTC.participant__state2PersonWhoChooses){
                                    long timeOfJointPrompt = new Date().getTime();
                                   // cTC.cC.getC().sendArtificialTurnFromApparentOriginToRecipient_NoUsername(pA,pA, "**************WHAT IS YOUR JOINT DECISION?****************", cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                                    //cTC.cC.getC().sendArtificialTurnFromApparentOriginToRecipient_NoUsername(pA, pB, "**************WHAT IS YOUR JOINT DECISION?****************", cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");
                                   
                                    //
                                   
                                   
                                     cTC.cC.getC().deprecated_sendArtificialTurnToRecipient(pA, "**************WHAT IS YOUR JOINT DECISION?****************", 0,cC.sm.defaultFONTSETTINGSSELF,  cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                                     cTC.cC.getC().deprecated_sendArtificialTurnToRecipient(pB, "**************WHAT IS YOUR JOINT DECISION?****************", 0,cC.sm.defaultFONTSETTINGSOTHER1,  cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");
                                    
                                    //
                                    cTC.cC.getC().subliminalstimuliset_displayText(pA, "","jointquestionresponder",Color.BLACK, 20,30,0,cC.getDescriptionForP(p)+"MADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                                    cTC.cC.getC().subliminalstimuliset_displayText(pB, "","jointquestionnonresponder",Color.BLACK, 20,30,0,cC.getDescriptionForP(p)+"MADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");  
                                    pA__timeOnServerOfJointPrompt=timeOfJointPrompt;
                                    pB__timeOnServerOfJointPrompt=timeOfJointPrompt;
                             }
                             else{
                                    long timeOfJointPrompt = new Date().getTime();
                                    //cTC.cC.getC().sendArtificialTurnFromApparentOriginToRecipient_NoUsername(pB,pB, "**************WHAT IS YOUR JOINT DECISION?****************", cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                                    //cTC.cC.getC().sendArtificialTurnFromApparentOriginToRecipient_NoUsername(pB,pA, "**************WHAT IS YOUR JOINT DECISION?****************",cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");
                                    //
                                    
                                        cTC.cC.getC().deprecated_sendArtificialTurnToRecipient(pB, "**************WHAT IS YOUR JOINT DECISION?****************", 0,cC.sm.defaultFONTSETTINGSSELF,  cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                                        cTC.cC.getC().deprecated_sendArtificialTurnToRecipient(pA, "**************WHAT IS YOUR JOINT DECISION?****************", 0,cC.sm.defaultFONTSETTINGSOTHER1,  cC.getDescriptionForP(p)+"TASKMOVE_PARTICIPANTMADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");
                                    
                                    //
                                    cTC.cC.getC().subliminalstimuliset_displayText(pB, "","jointquestionresponder",Color.BLACK, 20,30,0,cC.getDescriptionForP(p)+"MADEDIFFERENTSELECTIONS_MUSTMAKECHOICE");
                                    cTC.cC.getC().subliminalstimuliset_displayText(pA, "","jointquestionnonresponder",Color.BLACK, 20,30,0,cC.getDescriptionForP(p)+"MADEDIFFERENTSELECTIONS_DOESNTMAKECHOICE");  
                                    pA__timeOnServerOfJointPrompt=timeOfJointPrompt;
                                    pB__timeOnServerOfJointPrompt=timeOfJointPrompt;
                    }
                    state = cTC.state3INCONGRUENTINDIIVIDUALANSWERSWAITINGFORJOINT;      
                    }  
                    };
                    t.start();
                   
                    
                    
                  }
                  
             }
         }
         else if (state==state3INCONGRUENTINDIIVIDUALANSWERSWAITINGFORJOINT){
             if(p!=this.participant__state2PersonWhoChooses){
                 cC.getC().deprecated_sendArtificialTurnToRecipient(p, "****You can't choose. Your partner has to choose***", 0,cC.getDescriptionForP(p)+"TASKMOVE_JOINTDECISION_WRONGPARTICIPANTTRIEDTOANSWER");        
                 return;
             }
             else if(p==this.participant__state2PersonWhoChooses){
                 String id = ""; 
                 if(! (text.equalsIgnoreCase("/1")|(text.equalsIgnoreCase("/2")))){
                        cC.getC().deprecated_sendArtificialTurnToRecipient(p, "****YOU NEED TO TYPE EITHER  /1 or /2 ***", 0,cC.getDescriptionForP(p)+"TASKMOVEJOINTDECISION_PARTICIPANTMADETYPEO");
                        return;
                  }
                  else if(text.equalsIgnoreCase("/1")&&this.state1_PatchIsInFirstOrSecond==1){
                       if(p==pA) { 
                           
                           
                           id = "("+this.pA__state1SelectionOfA+","+1+", CORRECT)";
                           this.updateJointScoreOfSelector(pA, true);
                           this.updateJointScoreOfBoth(pA, true);
                           this.updateJointScoreOfBoth(pB, true);
                           this.updateJointSelectionMade(participant__state2PersonWhoChooses);
                       }
                       if(p==pB){

                           
                           id = "("+this.pB__state1SelectionOfB+","+1+", CORRECT)";
                           this.updateJointScoreOfSelector(pB, true);
                           this.updateJointScoreOfBoth(pA, true);
                           this.updateJointScoreOfBoth(pB, true);
                           this.updateJointSelectionMade(participant__state2PersonWhoChooses);
                       }
                  }
                  else if(text.equalsIgnoreCase("/2")&&this.state1_PatchIsInFirstOrSecond==2){
                      if(p==pA){

                          id = "("+this.pA__state1SelectionOfA+","+2+", CORRECT)";
                          this.updateJointScoreOfSelector(pA, true);
                          this.updateJointScoreOfBoth(pA, true);
                           this.updateJointScoreOfBoth(pB, true);
                           this.updateJointSelectionMade(participant__state2PersonWhoChooses);
                      }
                      if(p==pB){
                          id = "("+this.pB__state1SelectionOfB+","+2+", CORRECT)";
                          this.updateJointScoreOfSelector(pB, true);
                          this.updateJointScoreOfBoth(pA, true);
                           this.updateJointScoreOfBoth(pB, true);
                           this.updateJointSelectionMade(participant__state2PersonWhoChooses);
                      }
                  }
                  else{ //MISTAKE
                      if(p==pA){
                          id = "("+this.pA__state1SelectionOfA+","+2+", WRONG)";                
                          this.updateJointScoreOfSelector(pA, false);
                          this.updateJointScoreOfBoth(pA, false);
                          this.updateJointScoreOfBoth(pB, false);
                          this.updateJointSelectionMade(participant__state2PersonWhoChooses);
                      }
                      if(p==pB){
                          id = "("+this.pB__state1SelectionOfB+","+2+", WRONG)";
                          this.updateJointScoreOfSelector(pB, false);
                          this.updateJointScoreOfBoth(pA, false);
                          this.updateJointScoreOfBoth(pB, false);
                          this.updateJointSelectionMade(participant__state2PersonWhoChooses);
                      }
                  }
                   //updating joint scores
                   if(p==pA){
                       this.pA__timeOnServerOfJointSelection=mct.getTimeOfReceipt().getTime();
                       this.pA__timeOnClientOfJointSelection=mct.getTimeOfSending().getTime();
                       if(text.equalsIgnoreCase("/1")){
                           this.pA__JointSelection=1;               
                       }
                       else if (text.equalsIgnoreCase("/2")){
                           this.pA__JointSelection=2;   
                       }
                   }
                   else if (p==pB){
                       this.pB__timeOnServerOfJointSelection=mct.getTimeOfReceipt().getTime();
                       this.pB__timeOnClientOfJointSelection=mct.getTimeOfSending().getTime();
                       if(text.equalsIgnoreCase("/1")){
                            this.pA__JointSelection=1;         
                       }
                       else if (text.equalsIgnoreCase("/2")){
                             this.pA__JointSelection=2;   
                       }
                   }
                   
                 
                  this.updateDisplayPostJointDecision(text);
                  cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(p,cC.getDescriptionForP(p)+"JOINTSELECTIONINFO" , id);
                  Conversation.printWSln("Main", cC.getDescriptionForP(p)+"JOINTSELECTIONINFO "+id);
                   
                  //gotoSTATE1_SendNewStimulusSet();
                  state=this.state4PERFORMINGEVALUATION;
                  saveScoreForParticipant(pA);
                  saveScoreForParticipant(pB);
                  
             }
             
             
         }
         else if (state == this.state4PERFORMINGEVALUATION && text.equalsIgnoreCase("//")){
                 //gotoSTATE1_SendNewStimulusSet();
                   if(p==pA){
                       this.pA__TimeOnServerOfSelectionDoubleSlash=mct.getTimeOfReceipt().getTime();
                       this.pA__TimeOnClientOfSelectionDoubleSlash=mct.getTimeOfSending().getTime();
                   }
                   else if(p==pB){
                       this.pB__TimeOnServerOfSelectionDoubleSlash=mct.getTimeOfReceipt().getTime();
                       this.pB__TimeOnClientOfSelectionDoubleSlash=mct.getTimeOfSending().getTime();
                   }
                
             
             
                 postTrialCalculations();
        }
         
        //Conversation.printWSln(this.getID(), ""); 
         
    }
    
    
     
    public void updateDisplayPostJointDecision(String decision){
        //pA
        //pB
        //this.state1SelectionOfA
        //this.state1SelectionOfB
        //this.state1PatchIsInFirstOrSecond //1 or 2
        //this.state2PersonWhoChooses
        
        boolean jointdecisionIsCorrect = false;
        if(decision.equalsIgnoreCase("/1")&&state1_PatchIsInFirstOrSecond ==1)jointdecisionIsCorrect=true;
        if(decision.equalsIgnoreCase("/2")&&state1_PatchIsInFirstOrSecond ==2)jointdecisionIsCorrect=true;
        
        boolean individualdecisionOfAIsOriginallyCorrect = false;
        if(state1_PatchIsInFirstOrSecond ==1&&pA__state1SelectionOfA==1)individualdecisionOfAIsOriginallyCorrect=true;
        if(state1_PatchIsInFirstOrSecond ==2&&pA__state1SelectionOfA==2)individualdecisionOfAIsOriginallyCorrect=true;
        
        boolean individualdecisionOfBIsOriginallyCorrect = false;
        if(state1_PatchIsInFirstOrSecond ==1&&pB__state1SelectionOfB==1)individualdecisionOfBIsOriginallyCorrect=true;
        if(state1_PatchIsInFirstOrSecond ==2&&pB__state1SelectionOfB==2)individualdecisionOfBIsOriginallyCorrect=true;
       
        String idForParticipantA ="jointdecision_";
        String messageForA = "";
        
        if(individualdecisionOfAIsOriginallyCorrect){
            idForParticipantA=idForParticipantA+"correct_";
        }
        else{
            idForParticipantA=idForParticipantA+"wrong_";
        }
        if(jointdecisionIsCorrect){
            idForParticipantA=idForParticipantA+"correct_";
        }
        else{
            idForParticipantA=idForParticipantA+"wrong_";
        }
        if(individualdecisionOfBIsOriginallyCorrect){
            idForParticipantA=idForParticipantA+"correct";
        }
        else{
            idForParticipantA=idForParticipantA+"wrong";
        }
        
        
        String idForParticipantB ="jointdecision_";
        
        if(individualdecisionOfBIsOriginallyCorrect){
            idForParticipantB=idForParticipantB+"correct_";
        }
        else{
            idForParticipantB=idForParticipantB+"wrong_";
        }
        if(jointdecisionIsCorrect){
            idForParticipantB=idForParticipantB+"correct_";
        }
        else{
            idForParticipantB=idForParticipantB+"wrong_";
        }
        if(individualdecisionOfAIsOriginallyCorrect){
            idForParticipantB=idForParticipantB+"correct";
        }
        else{
            idForParticipantB=idForParticipantB+"wrong";
        }
        
        cC.getC().subliminalstimuliset_displayText(pA, "",idForParticipantA,Color.BLACK, 20,30,this.timeShowingEvaluationOfJointtrial,cC.getDescriptionForP(pA)+"TOGUI_JOINTDECISIONCORRECT");
        cC.getC().subliminalstimuliset_displayText(pB, "",idForParticipantB,Color.BLACK, 20,30,this.timeShowingEvaluationOfJointtrial,cC.getDescriptionForP(pB)+"TOGUI_JOINTDECISIONCORRECT");
        System.err.println(idForParticipantA);
        System.err.println(idForParticipantB);
       // System.exit(-5);
        //cC.getC().sendArtificialTurnToRecipient(pA, "", 0,this.getID()+"TOCHATFRAME_JOINTDECISIONCORRECT");
        //cC.getC().sendArtificialTurnToRecipient(pB, "****CORRECT!***", 0,this.getID()+"TOCHATFRAME_JOINTDECISIONCORRECT");
        
        
        
        
    }
    
    
     
    
    
           
    public void postTrialCalculations(){
        this.individualTrialNumber++;
        int numberOfTrialsLeft = individualTrialsPerStage - individualTrialNumber;
        
        //Conversation.printWSln("Main", "INDIVIDUAL TRIAL NUMBER: "+this.individualTrialNumber + "   Each stage has "+this.individualTrialsPerStage);
        Conversation.printWSln("Main", "Number of trials left in current stage: "+numberOfTrialsLeft);
        
        if(this.individualTrialNumber<this.individualTrialsPerStage){
           
            gotoSTATE1_SendNewStimulusSet();
            
        }
        else{
            //this.resetToStart();
            this.saveState();
            cC.getC().subliminalstimuliset_displayText(pA, "Stage 1 completed! Please take a break.","backgroundorange",Color.BLACK, 20,30,0,cC.getDescriptionForP(pA));
            cC.getC().subliminalstimuliset_displayText(pB, "Stage 1 completed! Please take a break.","backgroundorange",Color.BLACK, 20,30,0,cC.getDescriptionForP(pB));
    
            JOptionPane.showMessageDialog(null, "Stage 1 Completed. PRESS OK TO START STAGE 2",  "Message 1 of 2",  JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(null, "PRESS OK AGAIN TO START STAGE 2",  "Message 2 of 2",  JOptionPane.PLAIN_MESSAGE);
            this.individualTrialNumber =0;
            this.resetToStart();
            
        }
    }
    
    
    public void disableTextDisplayOfClients(){
        cC.getC().changeClientInterface_disableConversationHistory(pA);
        cC.getC().changeClientInterface_disableConversationHistory(pB); 
    }
    
    public void gotoSTATE1_SendNewStimulusSet(){
        this.saveState();
        resetDataForTrial();
        cC.getC().changeClientInterface_disableConversationHistory(pA);
        cC.getC().changeClientInterface_disableConversationHistory(pB); 
        
        
        this.state=state1ANSWERINGINDIVIDUALPROBLEMS;
        pA__state1SelectionOfA = -1;
        pB__state1SelectionOfB = -1; 
        participant__state2PersonWhoChooses = null;
    
   
    
        int[] stimuliData = ctcs.getPositionInHexagon_PatchContrast_PatchIsIn1or2();
         
         
         
        
         //this.state1_PatchIndexInHexagram = 1+r.nextInt(6);
        // this.state1_PatchIntensity = 1+r.nextInt(4);
        // this.state1_PatchIsInFirstOrSecond= 1+r.nextInt(2);
        
         
          this.state1_PatchIndexInHexagram = stimuliData[0];
          this.state1_PatchIntensity = stimuliData[1];
          this.state1_PatchIsInFirstOrSecond= stimuliData[2];
        
         
          String stimulus1ID = "notset";
          String stimulus2ID = "notset";
          String stimulusTARGET = "stimulus"+state1_PatchIndexInHexagram+"_"+state1_PatchIntensity;
          String stimulusDISTRACTOR = "";
         
          
          int indexOfDISTRACTOR  = 1+r.nextInt(25);
         
           stimulusDISTRACTOR = "stimulus_base"+indexOfDISTRACTOR;
          
          if(state1_PatchIsInFirstOrSecond==1){
              stimulus1ID=stimulusTARGET;
              stimulus2ID=stimulusDISTRACTOR;
          }
          else if(state1_PatchIsInFirstOrSecond==2){
              stimulus2ID=stimulusTARGET;
              stimulus1ID=stimulusDISTRACTOR;
          }
          
         System.err.println("...."+stimulusDISTRACTOR);
        
        long nextSetInstructionToClient = new Date().getTime();
        cC.getC().subliminalstimuliset_displaySet(pA, fixationtime, stimulustime, backgroundtime,fixationtime, stimulustime,backgroundtime, stimulus1ID, stimulus2ID, cC.getDescriptionForP(pA)+"TASKMOVE:STIMULUS ("+stimulus1ID+","+stimulus2ID+")");
        cC.getC().subliminalstimuliset_displaySet(pB, fixationtime, stimulustime, backgroundtime,fixationtime, stimulustime,backgroundtime, stimulus1ID, stimulus2ID, cC.getDescriptionForP(pB)+"TASKMOVE:STIMULUS ("+stimulus1ID+","+stimulus2ID+")") ;
        long totalTime =  fixationtime+stimulustime+backgroundtime+fixationtime+stimulustime+backgroundtime;
        this.state1ProjectedTimeOfSendingResponse = new Date().getTime()+totalTime;
        this.pA__TimeOnServerOfCommandToClientToCarryOutNextTrial=nextSetInstructionToClient;
        this.pB__TimeOnServerOfCommandToClientToCarryOutNextTrial=nextSetInstructionToClient;
     }
    
    
    
   
    
    
    
   public void initializeImages(){
        try {
          String s = System.getProperty("user.dir");
          fDIR = new File(s+File.separatorChar+"experimentresources"+File.separatorChar+"confidence"+File.separatorChar+"stimuliset");
          
          Vector imageFiles = new Vector();
          File[] files  = fDIR.listFiles();
          for(int i=0;i<files.length;i++){
              File file = files[i];
              if(file.getName().endsWith("png")){
                  imageFiles.add(file);
              }
          }
          
          for(int i=0;i<imageFiles.size();i++){
              File f = (File)imageFiles.elementAt(i);
              BufferedImage bi = ImageIO.read(f);
              String name = f.getName().substring(0, f.getName().length()-".png".length());
              System.err.println(name);
              images.addElement(new SerializableImage(bi,name));
          }
       } catch (IOException e) {
            e.printStackTrace();
          }
      }
    
   
   public void participantRejoinedConversation(Participant p){
       cC.getC().subliminalstimuliset_SendSet(p, images, 800, 600, cC.getDescriptionForP(pA));
       if(pA!=null)cC.getC().subliminalstimuliset_displayText(pA, "Resetting because participant logged back in","backgroundwhite",Color.BLACK, 20,30,2000,cC.getDescriptionForP(pA));
       if(pB!=null)cC.getC().subliminalstimuliset_displayText(pB, "Resetting because participant logged back in","backgroundwhite",Color.BLACK, 20,30,2000,cC.getDescriptionForP(pB));
       cC.getC().deprecated_saveAdditionalRowOfDataToSpreadsheetOfTurns(cC.getDescriptionForP(p), "CRASH RECOVERY "+p.getUsername()+"" );
       this.gotoSTATE1_SendNewStimulusSet();
   }
   
   
    public void participantJoinedConversation(Participant p){
        if(pA==null){
            pA=p;
        }
        else if(pB==null){
            pB=p;
        }
        cC.getC().subliminalstimuliset_SendSet(p, images, 800, 600, cC.getDescriptionForP(p));
        cC.getC().subliminalstimuliset_displayText(p, "Please wait for the other participant to log in","backgroundwhite",Color.BLACK, 20,30,0,cC.getDescriptionForP(p));
    }
    
    
    public void participantJoinedConversationSetAsA(Participant p){
        pA=p;
        cC.getC().subliminalstimuliset_SendSet(p, images, 800, 600, cC.getDescriptionForP(p));
        cC.getC().subliminalstimuliset_displayText(p, "Please wait for the other participant to log in","backgroundwhite",Color.BLACK, 20,30,0,cC.getDescriptionForP(p));
    }
     public void participantJoinedConversationSetAsB(Participant p){
        pB=p;
        cC.getC().subliminalstimuliset_SendSet(p, images, 800, 600, cC.getDescriptionForP(p));
        cC.getC().subliminalstimuliset_displayText(p, "Please wait for the other participant to log in","backgroundwhite",Color.BLACK, 20,30,0,cC.getDescriptionForP(p));
    }
    
    
    
   
    public void sendStartMessageToParticipants(){
        
        
        cC.getC().subliminalstimuliset_displayText(pA, "Please type '/start' ","backgroundwhite",Color.BLACK, 20,30,0,cC.getDescriptionForP(pA));
        cC.getC().subliminalstimuliset_displayText(pB, "Please type '/start' ","backgroundwhite",Color.BLACK, 20,30,0,cC.getDescriptionForP(pB));
    }
   
   
   
    
    public void resetToStart(){
        this.state0ParticipantAHasSentStart=false;
        this.state0ParticipantBHasSentStart=false;
        this.state=ConfidenceTaskController.state0WAITINGFORSTART;
        sendStartMessageToParticipants();
    }
    
    public void resetToStartwithNoStartMessage(){
        this.state0ParticipantAHasSentStart=false;
        this.state0ParticipantBHasSentStart=false;
        this.state=ConfidenceTaskController.state0WAITINGFORSTART;
       
    }
    
         
    
    
}
