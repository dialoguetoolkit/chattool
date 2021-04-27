/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralComms;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.Telegram_Dyadic_PROCOMM;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import diet.utils.HashtableWithDefaultvalue;
import diet.utils.VectorToolkit;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class PCTaskTG implements JTrialTimerActionRecipientInterface{
      
      JPCTaskTG jpct;
      Random r =new Random();
    
      static HashtableWithDefaultvalue htwdcSCORE = new HashtableWithDefaultvalue((long)0);
      static HashtableWithDefaultvalue htwdcLARGESTSUCCESS = new HashtableWithDefaultvalue((long)0);
      static HashtableWithDefaultvalue htwdcCORRECTMINUSINCORRECT = new HashtableWithDefaultvalue((long)0);
      //static HashtableWithDefaultvalue htwdcLARGESTSETSIZETOBEASSIGNED = new HashtableWithDefaultvalue((long)1);
      static HashtableWithDefaultvalue htwnumberOfSets = new HashtableWithDefaultvalue((long)0);
      public  static HashtableWithDefaultvalue htCurrentLevel = new HashtableWithDefaultvalue((int)0);
      
      //static HashtableWithDefaultvalue htwdcLARGESTMOSTRECENTSUCCES = new HashtableWithDefaultvalue(0);
    
     //CustomDialog.getString("What is the META character?", "?");//"?";//?yn"; 
    
      PCSetOfMoves pcset ;
      public TelegramParticipant pA;
      public TelegramParticipant pB;
      
      
      TelegramParticipant director;
      TelegramParticipant matcher;
      
      //public Participant mostRecentDirector=null;
      Telegram_Dyadic_PROCOMM cC;
      
      long durationOfTrial = 90 *1000;
      
      JTrialTimer jt;
      
      DifficultySettings ds = new DifficultySettings();
      
      //model = only items that are in shared
      
       int level =0;
       
       boolean ispracticestage = true;
       int maxLengthPracticeStageSequence = 5;
       long maxLengthPracticeStageAchievedByA= 0;
       long maxLengthPracticeStageAchievedByB= 0;
       
       public static int windowForJointSelection = 10000;
       
        static int numberOfLevelsToDecreaseOnError = 1;//CustomDialog.getInteger("How many levels to decrease on error?", 1);
       static int streakofsuccessesbeforegoinguplevel = 3;
       static int currentstreak=0;
      
      static  int difficultysettings_maxSwitchCost =  2;
      static int difficulty_settings_singleNotesCoef = 1;
      static int difficultysettings_simulNotesCoef = 4; 
       
       // double singlenotes_probabilityshared,  simultaneousnotes_probabilityshared =0.5;
        double probabilityshared = 0.5;
       
       public boolean debug = true;
       
      
      public PCTaskTG(Telegram_Dyadic_PROCOMM cC, TelegramParticipant pA, TelegramParticipant pB, boolean startTimer){
          this( cC,  pA,  pB);
          if(startTimer){
              jt.startTimer();
          }
      }
      
      
      public PCTaskTG(Telegram_Dyadic_PROCOMM cC, TelegramParticipant pA, TelegramParticipant pB){
          super(); 
          //cC.c.textOutputWindow_ChangeText("instructions", "",false, pA );
          //cC.c.textOutputWindow_ChangeText("instructions", "",false, pB );
          jt = new JTrialTimer("Timer: "+pA.getParticipantID()+","+pA.getUsername()+"--"+pB.getParticipantID()+","+pB.getUsername(),this, durationOfTrial);     
          this.cC=cC;
         
          //boolean defaultSettings = CustomDialog.getBoolean("Do you want to use default settings?", "yes", "no");
          
          boolean defaultSettings=true;
          if(!defaultSettings){
                  ///  this.sharedWhitelist = CustomDialog.getString("SHAREDWHITELIST", this.sharedWhitelist);
                  ///  this.pAWhitelist= CustomDialog.getString("PAWHITELIST", pAWhitelist);
                  ///  this.pBWhitelist= CustomDialog.getString("PBWHITELIST", pBWhitelist);
                   // this.allowedMetaChars = CustomDialog.getString("ALLOWEDMETACHARACTERS", this.allowedMetaChars);
                    
                    ///numberONLYOtherBothShared =  CustomDialog.getInteger("How many ONLY By Other shared?",  2);
                    ///numberONLYOtherNotShared= CustomDialog.getInteger("How many ONLY By Other not shared?",  2);
                    ///numberONLYSelf = CustomDialog.getInteger("How many ONLY By Self?",  2);
                    ///numberANDSame= CustomDialog.getInteger("How many AND Same",  2);
                    ///numberANDDifferent=CustomDialog.getInteger("How many AND Different?",  2);
          } 
            this.pA=pA;
            this.pB=pB;
            this.director=pA;
            this.matcher=pB;
            
            cC.c.telegram_sendInstructionToParticipantWithForcedKeyboardButtons(pA, VectorToolkit.getVectorOfCharactersFromString(this.translateFromSystemToGUI(pA,   (pAWhitelist+sharedWhitelist+allowedMetaChars))));
            cC.c.telegram_sendInstructionToParticipantWithForcedKeyboardButtons(pB, VectorToolkit.getVectorOfCharactersFromString(this.translateFromSystemToGUI(pB,  (pBWhitelist+sharedWhitelist+allowedMetaChars))));

            
            
            jpct = new JPCTaskTG(this); 
      
            int pALevel = (int)PCTaskTG.htCurrentLevel.getObject(pA);
            int pBLevel = (int)PCTaskTG.htCurrentLevel.getObject(pB);
            
            int minLevel = Math.min(pALevel, pBLevel);
           
            if(debug)this.level=(int)minLevel;
            if(debug)this.jpct.setLevel((int)this.level);
          
          
          pcset = new PCSetOfMoves(this);
          
          
          
          
          
          this.createNewSequence(false);
          displayMovesOnServer();     
          
          Thread t = new Thread(){public void run(){checkForTimeouts();}};t.start();
      }
      
      //boolean displayDebug = false;//CustomDialog.getBoolean("Display debug info");
      
      //"abcdefghijklmnopqrstuvwxyz"
      
      public String sharedWhitelist     = "pq";
      public String sharedWhitelist_GUI = "io";
      
      public String pAWhitelist          = "rs";
         
      public String pBWhitelist         = "tu";
     
      public String whitelist_SelfGUI  = "fd";
      public String whitelist_OtherGUI = "xv";
      
      static public String allowedMetaChars =  "";
      
      
      /*
      public String sharedWhitelist     = "wx";
      public String sharedWhitelist_GUI = "cd";
      
      public String pAWhitelist          = "uv";
         
      public String pBWhitelist         = "yz";
     
      public String whitelist_SelfGUI  = "ab";
      public String whitelist_OtherGUI = "ef";
     
      
      
      
      //        System        PB
      // PA a   -> u    ->    e
      // PA b   -> v    ->    f
      // PA c   -> w    ->    c
      // PA d   -> x    ->    d
      
      // PB a   -> y    ->         e
      // PB b   -> z    ->         f
      //
      */
      
      
    public String translateCharFromSystemToGUI(Participant recipient, char textfromsystem) {
          
          
           char textfromsystemlowercase =  Character.toLowerCase(textfromsystem);
          
           int index = sharedWhitelist.indexOf(textfromsystemlowercase);
           if(index>=0){
                if( Character.isUpperCase(textfromsystem)) return (""+(sharedWhitelist_GUI.charAt(index))).toUpperCase();
                return (""+(sharedWhitelist_GUI.charAt(index))).toLowerCase();     
           }
           
           if(recipient==pA ){
                index = pAWhitelist.indexOf(textfromsystemlowercase);
                if(index>=0){
                     if( Character.isUpperCase(textfromsystem)) return (""+(whitelist_SelfGUI.charAt(index))).toUpperCase();
                     return (""+(whitelist_SelfGUI.charAt(index))).toLowerCase();     
                } 

                index = pBWhitelist.indexOf(textfromsystemlowercase);
                if(index>=0){
                     if( Character.isUpperCase(textfromsystem)) return (""+(whitelist_OtherGUI.charAt(index))).toUpperCase();
                     return (""+(whitelist_OtherGUI.charAt(index))).toLowerCase();     
                } 
           }
          
           if(recipient==pB ){
                index = pBWhitelist.indexOf(textfromsystemlowercase);
                if(index>=0){
                     if( Character.isUpperCase(textfromsystem)) return (""+(whitelist_SelfGUI.charAt(index))).toUpperCase();
                     return (""+(whitelist_SelfGUI.charAt(index))).toLowerCase();     
                } 

                index = pAWhitelist.indexOf(textfromsystemlowercase);
                if(index>=0){
                     if( Character.isUpperCase(textfromsystem)) return (""+(whitelist_OtherGUI.charAt(index))).toUpperCase();
                     return (""+(whitelist_OtherGUI.charAt(index))).toLowerCase();     
                } 
           }
           
           return ""+textfromsystemlowercase;
      
      
    }    
            
            
            
            
            
            
            
      
       public String translateCharFromGUIToSystem(Participant sender, String textSentByP){
           String textSentByParticipant = textSentByP.toLowerCase();
           int index = sharedWhitelist_GUI.indexOf(textSentByParticipant);
           if(index>=0) return ""+sharedWhitelist.charAt(index);    
           
           if(sender==pA){
              index = whitelist_SelfGUI.indexOf(textSentByParticipant);
              if(index>=0) return ""+pAWhitelist.charAt(index);
           }
           if(sender==pB){
              index = whitelist_SelfGUI.indexOf(textSentByParticipant);
              if(index>=0) return ""+pBWhitelist.charAt(index);
           }
           
           return textSentByParticipant;

      }
     
      
      
      
      
      
      
      
      
      
      public String translateFromGUIToSystem(Participant sender, String textSentByParticipant){
          String output ="";
          for(int i=0;i<textSentByParticipant.length();i++){
              output = output + this.translateCharFromGUIToSystem(sender, ""+textSentByParticipant.charAt(i));
          }
          return output;
      }
      public String translateFromSystemToGUI(Participant recipient, String textSentToParticipant){
          System.err.println("TRANSLATETEXTSENTTOPARTICIPANT:"+textSentToParticipant+"----");
          String output ="";
          for(int i=0;i<textSentToParticipant.length();i++){
              output = output + this.translateCharFromSystemToGUI(recipient,textSentToParticipant.charAt(i));
          }
          return output;
      }
      
      
      
      
      public synchronized void evaluate(TelegramParticipant p,TelegramMessageFromClient tmfc){         
          synchronized(this.jt){
              
              if(tmfc==null|| !tmfc.u.hasMessage()){
                   cC.c.telegram_sendInstructionToParticipant_MonospaceFont(p, "Message NOT sent. Your message must be a single letter. Please try again!");
                  return;
              }
              
              
              String textanycase=tmfc.u.getMessage().getText();
             
              
              String text = this.translateFromGUIToSystem(p, textanycase).toUpperCase();
                      
                      
             
              
             
              
              String pAPossibleChars = this.translateFromGUIToSystem(pA,this.pAWhitelist+sharedWhitelist).toUpperCase();
              String pBPossibleChars = this.translateFromGUIToSystem(pB,this.pBWhitelist+sharedWhitelist).toUpperCase();
              
              
              
              
              if(p==pA && !pAPossibleChars.contains(text.toUpperCase())){
                  String permittedchars = "";
                  for(int i=0;i<pAPossibleChars.length();i++){
                      
                       permittedchars = permittedchars+pAPossibleChars.charAt(i); 
                  }
                                   
                  cC.c.telegram_sendInstructionToParticipant_MonospaceFont(p, "Message NOT sent. Your message may only contain one of the following letters: "+    this.translateFromSystemToGUI(p,permittedchars)    .toUpperCase());
                  return ; 
              }
              else  if(p==pB && !pBPossibleChars.contains(text.toUpperCase())){
                  String permittedchars = "";
                  for(int i=0;i<pBPossibleChars.length();i++){
                      
                       permittedchars = permittedchars+pBPossibleChars.charAt(i); 
                  }
                                   
                  cC.c.telegram_sendInstructionToParticipant_MonospaceFont(p, "Message NOT sent. Your message may only contain one of the following letters: "+this.translateFromSystemToGUI(p,permittedchars).toUpperCase());
                  return ; 
              }
              
              
              //cC.c.telegram_relayMessageTextToOtherParticipants(p, tmfc);
               
             
              
              TelegramParticipant recip = pA;
              if(p==pA)recip = pB;
              if(p==pB)recip = pA;
              
               String textToSend = this.translateFromSystemToGUI(recip,text);
             // cC.c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(p, textanycase+"!!!!"+text+"!!!!"+textToSend);
             // cC.c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(director, text);
              cC.c.telegram_sendArtificialTurnFromApparentOriginToParticipant(p,recip, textToSend.toLowerCase());
             
               boolean success = pcset.evaluate(p, text);
               
               displayMovesOnServer();     
               displayMovesOnClients();      
                          
      
               if(success) {
                    
                    System.err.println("LEVEL:");
                    int sizesucc = pcset.moves.size();
                    updateScoresSuccess(pA,pB, sizesucc);
                    
                 
                                       
                    cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA,"CORRECT!");
                    cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB,"CORRECT!" );
       
                    
                    createNewSequence(true);
                    this.jt.nextTrial();
               }    
               else{
                   if(debug) {
                        //cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA,"INCORRECT!");
                        //cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB,"INCORRECT!" );
                   }
                   
                   
               }
          }
           return ;
      }
    
      
      
      public void updateScoresSuccess_PRACTICE(Participant pAA, Participant pBB, long sizeOfSet){
          if(director==pA) {
              this.maxLengthPracticeStageAchievedByA = sizeOfSet;
          }
          else{
              this.maxLengthPracticeStageAchievedByB = sizeOfSet;
          }
      }
    
      
      
      
    
      public void updateScoresSuccess(Participant pAA, Participant pBB, long sizeOfSet){  
            if(ispracticestage){
                this.updateScoresSuccess_PRACTICE(pAA, pBB, sizeOfSet);
                return;
            }
           
          
             long pALargest = (long)htwdcLARGESTSUCCESS.getObject(pAA);
             long pBLargest = (long)htwdcLARGESTSUCCESS.getObject(pBB);
             
             htwdcLARGESTSUCCESS.putObject(pAA, Math.max(pALargest, sizeOfSet));
             htwdcLARGESTSUCCESS.putObject(pBB, Math.max(pBLargest, sizeOfSet));    
             
             long pACORRECTMINUSINCORRECT= (long)htwdcCORRECTMINUSINCORRECT.getObject(pAA)+1;
             long pBCORRECTMINUSINCORRECT= (long)htwdcCORRECTMINUSINCORRECT.getObject(pBB)+1;
             
             htwdcCORRECTMINUSINCORRECT.putObject(pAA, (long)(pACORRECTMINUSINCORRECT));
             htwdcCORRECTMINUSINCORRECT.putObject(pBB, (long)(pBCORRECTMINUSINCORRECT));
             
             
             
             
             long pAScore = (long)this.htwdcSCORE.getObject(pAA); 
             long pBScore = (long)this.htwdcSCORE.getObject(pBB);
             
             pAScore = (long)  (pAScore+ 10*(sizeOfSet));
             pBScore = (long)  (pBScore+ 10*(sizeOfSet));
             
             htwdcSCORE.putObject(pAA, (long)pAScore);
             htwdcSCORE.putObject(pBB, (long)pBScore);       
      }
      
       
      
       public void decreaseScoresTimeout(Participant pAA, Participant pBB, long sizeOfSet){           
             
              
             if(sizeOfSet>1)sizeOfSet=sizeOfSet-1;  
             
             long pACORRECTMINUSINCORRECT= (long)htwdcCORRECTMINUSINCORRECT.getObject(pAA)-1;
             long pBCORRECTMINUSINCORRECT= (long)htwdcCORRECTMINUSINCORRECT.getObject(pBB)-1;
             
             if(pACORRECTMINUSINCORRECT<0)pACORRECTMINUSINCORRECT=0;
             if(pBCORRECTMINUSINCORRECT<0)pBCORRECTMINUSINCORRECT=0;
             
             htwdcCORRECTMINUSINCORRECT.putObject(pAA, (long)(pACORRECTMINUSINCORRECT));
             htwdcCORRECTMINUSINCORRECT.putObject(pBB, (long)(pBCORRECTMINUSINCORRECT));
             
             
            
             
              
             //long pAScore = (long)this.htwdcSCORE.getObject(pA); 
            // long pBScore = (long)this.htwdcSCORE.getObject(pB);
             
            // pAScore = pAScore+ 10*(sizeOfSet);
            // pBScore = pBScore+ 10*(sizeOfSet);
             
             //htwdcSCORE.putObject(pA, (long)pAScore);
             //htwdcSCORE.putObject(pB, (long)pBScore);       
      }
      
      
     
      
      public synchronized void createNewSequence(boolean previousWasSuccess ){
              
          
            if(previousWasSuccess&& !this.ispracticestage){
                currentstreak++;
                if(currentstreak>=streakofsuccessesbeforegoinguplevel){
                    this.level++;
                    this.jpct.setLevel(this.level);
                    currentstreak=0;
                }
                
                
                
               
            }
            else if ( !ispracticestage){
                //this.level--;
                currentstreak=0;
                this.level=this.level-numberOfLevelsToDecreaseOnError;
                if(this.level<=0)this.level=0;
                this.jpct.setLevel(this.level);
            }
             
            
          ;
            //this.level=19;
            //this.jpct.setLevel(2);
            ispracticestage=false;
            
            
            
            
             PCTaskTG.htCurrentLevel.putObject(pA, this.level);
             PCTaskTG.htCurrentLevel.putObject(pB, this.level);
          
             long pAScore = (long)this.htwdcSCORE.getObject(pA);
             long pBScore = (long)this.htwdcSCORE.getObject(pB);
             
             System.err.println("SAVINGLEVELOF:"+pA.getUsername()+" is "+level);
             System.err.println("SAVINGLEVELOF:"+pB.getUsername()+" is "+level);
                       
            cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, "Your score is: "+  (Long)PCTaskTG.htwdcSCORE.getObject(pA));
            cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, "Your score is: "+  (Long)PCTaskTG.htwdcSCORE.getObject(pB));
            
                
             long pANumberOfTrials = (long)PCTaskTG.htwnumberOfSets.getObject(pA)+1;
             long pBNumberOfTrials = (long)PCTaskTG.htwnumberOfSets.getObject(pB)+1;
             
             
            PCTaskTG.htwnumberOfSets.putObject(pA, pANumberOfTrials);
            PCTaskTG.htwnumberOfSets.putObject(pB, pBNumberOfTrials);
             
            
            //Decide if create new sequence using the values entered on the GUI or using LEVEL
            //The values can only be nonzero if they are set by the GUI (JPCTask)          
            if(numberONLYOtherBothShared !=0 | numberONLYOtherNotShared!=0 |   numberONLYSelf!=0  |   numberANDSame!=0 |  numberANDDifferent!=0){
                this.createRandom_MoveSequenceFromGUI();
            }
            else{
                this.createRandom_MoveSequence();
            }
            
            if(!ispracticestage){
            if(pA==director){
                cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, "Next game: You are the instructor");
                cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, "Next game: Follow your partner`s instructions");
                
                if(sendinstructions)cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, this.pcset.generateDescription(pA));
                
            }
            else {
                cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, "Next game: You are the instructor");
                cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, "Next game: Follow your partner`s instructions");
                
                if(sendinstructions)cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, this.pcset.generateDescription(pB));
            }
            }
            else{
                if(pA==director){
                    cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, "Complete the sequence");
                    cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, "Your partner is practising");
                    
                    if(sendinstructions)cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, this.pcset.generateDescription(pA));
                }
                else{
                    cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, "Complete the sequence");
                    cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, "Your partner is practising");
                    
                    if(sendinstructions)cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, this.pcset.generateDescription(pB));
                }
                
            }
            
            
            
            displayMovesOnServer(); 
            displayMovesOnClients();
            
            
           
            
           
      }
     
      
    
     boolean sendinstructions = CustomDialog.getBoolean("Do you want to send instructions?");
      

      
     
     
      
      public void setLevel(int lev){
          this.level=lev;
      }
     
      
       

      
       
       public void createRandom_MoveSequence_PracticeStage(){
            
            String directorWhitelist;
            String matcherWhitelist;
            if(director==pB || director==null){
                director=pA; matcher=pB;
                directorWhitelist = this.pAWhitelist+this.sharedWhitelist;
                matcherWhitelist = this.pBWhitelist+this.sharedWhitelist;
            }
            else{
                director=pB; matcher=pA;
                directorWhitelist = this.pBWhitelist+this.sharedWhitelist;
                matcherWhitelist = this.pAWhitelist+this.sharedWhitelist;
            }
            
            long maxLengthAchievedByBoth = Math.min(this.maxLengthPracticeStageAchievedByA, this.maxLengthPracticeStageAchievedByB);
            
            
            Vector<MoveONLY> numberONLYSelfV = new Vector();
            
            
           
            for(int i=0;i<maxLengthAchievedByBoth+1;i++){
                int indexOfChar = r.nextInt(directorWhitelist.length());
                String s = ""+directorWhitelist.charAt(indexOfChar);
                MoveONLY mo = new MoveONLY(this.pcset,director,s);
                numberONLYSelfV.add(mo);
            }
                
            
            Vector finalMoveSet = new Vector();
            
            for(int i=0;i< numberONLYSelfV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYSelfV.elementAt(i) ,index  );
            }
            
            this.pcset.moves=finalMoveSet;
            this.displayMovesOnClients();
           
       }
      
      
      
       public void createRandom_MoveSequence(){
           if(ispracticestage & Math.min(this.maxLengthPracticeStageAchievedByA, this.maxLengthPracticeStageAchievedByB)>=maxLengthPracticeStageSequence){
               this.ispracticestage=false;
               CustomDialog.showDialog("START THE EXPERIMENT!");
               cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB, "The game starts!");
               cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA, "The game starts!");
               createRandom_MoveSequence_Experiment();
           }
           else if(this.ispracticestage){
               createRandom_MoveSequence_PracticeStage();
           }
           else{
               createRandom_MoveSequence_Experiment();
           }
               
           
       }
       
      
      
        public void createRandom_MoveSequence_Experiment(){
              
            
            if(director==pB || director==null){
                director=pA; matcher=pB;     
            }
            else{
                director=pB; matcher=pA;
              
            }
            
            
                   Vector vseq = (Vector) ds.returnSequenceForLevelOrLower(level);
                  
                   Vector<String> directorpermittedkeys = new Vector();
                   Vector<String> matcherpermittedkeys = new Vector();
                   
                   String pANotes = this.pAWhitelist+this.sharedWhitelist;
                   String pBNotes = this.pBWhitelist+this.sharedWhitelist;
                   
                   if(pA==director){
                       for(int i=0;i<pANotes.length();i++){
                            directorpermittedkeys.add(pANotes.charAt(i)+"");
                       }
                        for(int i=0;i<pBNotes.length();i++){
                            matcherpermittedkeys.add(pBNotes.charAt(i)+"");
                       }
                   }
                   else{
                       
                       for(int i=0;i<pANotes.length();i++){
                            matcherpermittedkeys.add(pANotes.charAt(i)+"");
                       }
                        for(int i=0;i<pBNotes.length();i++){
                            directorpermittedkeys.add(pBNotes.charAt(i)+"");
                       }
                   
                   }
                   
                   double adjustedprobabilityshared = probabilityshared;
                   if(this.level==0 | this.level==1){
                        adjustedprobabilityshared = 1;
                    }
                   
                   Vector newAllMoves = this.generateNotesFromSequence(vseq ,adjustedprobabilityshared, directorpermittedkeys, matcherpermittedkeys);

                   Vector finalMoveSet = new Vector();
                   
                   for(int i=0;i<newAllMoves.size();i++){
                       int index = 0;
                       if(i>0)index = r.nextInt(finalMoveSet.size());
                       finalMoveSet.insertElementAt(newAllMoves.elementAt(i), index);
                   }
                   this.pcset.moves=finalMoveSet;
                   
                   
        }
       
        public Vector<Move> generateNotesFromSequence(    Vector<String> seq, double probOfSharedNotes, Vector<String> directorpermittedkeys, Vector<String> matcherpermittedkeys){

        //public Vector<Move> generateNotesFromSequence(    Vector<String> seq, double probOfSharedNotesDirector, double probOfSharedNotesMatcher, double probOfSharedNotesSimultaneous, Vector<String> directorpermittedkeys, Vector<String> matcherpermittedkeys){
          
          
        
          if(probabilityshared>1)probabilityshared=1;
          if(probabilityshared<0)probabilityshared=0;
       
        
         Vector<String> sharednotes = new Vector();
        for(int i = 0;i<directorpermittedkeys.size();i++){
            //System.err.println("idirectorpermittedkeys"+directorpermittedkeys.elementAt(i));
            
            for(int j=0;j<matcherpermittedkeys.size();j++){
                System.err.println("imatcherpermittedkeys"+matcherpermittedkeys.elementAt(j));
                if(directorpermittedkeys.elementAt(i).equalsIgnoreCase(matcherpermittedkeys.elementAt(j))){
                    sharednotes.addElement(directorpermittedkeys.elementAt(i));
                    System.err.println("SHAREDNOTE: "+directorpermittedkeys.elementAt(i));
                }
            }
        }
       
        Vector<String> directorNonSharedNotes = new Vector();
        for(int i=0;i<directorpermittedkeys.size();i++){
            if(!sharednotes.contains(directorpermittedkeys.elementAt(i))){
                 directorNonSharedNotes.addElement(directorpermittedkeys.elementAt(i));
            }
        }
        Vector<String> matcherNonSharedNotes = new Vector();
        for(int i=0;i<matcherpermittedkeys.size();i++){
            if(!sharednotes.contains(matcherpermittedkeys.elementAt(i))){
                 matcherNonSharedNotes.addElement(matcherpermittedkeys.elementAt(i));
            }
        }
       
        Vector allNewNotes = new Vector();
        
        
         //generateAllVariants(vsclone, "MSS");
        //generateAllVariants(vsclone, "MSN");
        //generateAllVariants(vsclone, "DS");
        //generateAllVariants(vsclone, "DMS");
        //generateAllVariants(vsclone, "DMD");
        
        
        for(int i=0;i<seq.size();i++){
              String s = seq.elementAt(i);
              
              boolean doSharedDirector = false;
              boolean doSharedMatcher = false;
              boolean doSharedSimultaneous = false;
              double rseed = r.nextDouble();
              
             
              
              
              if(rseed<probOfSharedNotes){
                  doSharedDirector = true;
                  doSharedMatcher = true;
                  doSharedSimultaneous = true;
              }
             
              //doSharedMatcher = true;
              
              if (s.equalsIgnoreCase("DS")){
                   if(doSharedDirector)allNewNotes.addElement(new MoveONLY(pcset, director, sharednotes.elementAt(r.nextInt(sharednotes.size()))));
                   else allNewNotes.addElement(new MoveONLY(pcset, director,directorNonSharedNotes.elementAt(r.nextInt(directorNonSharedNotes.size()))));
              }
              else if (s.equalsIgnoreCase("MS")){
                   if(doSharedMatcher){
                       allNewNotes.addElement(new MoveONLY(pcset, matcher, sharednotes.elementAt(r.nextInt(sharednotes.size()))));
                      
                   }
                   else allNewNotes.addElement(new MoveONLY(pcset, matcher, matcherNonSharedNotes.elementAt(r.nextInt(matcherNonSharedNotes.size()))));
              }
              else if (s.equalsIgnoreCase("DMS")){               
                  if(doSharedSimultaneous){
                       String directornote = sharednotes.elementAt(r.nextInt(sharednotes.size()));
                       String matchernote = sharednotes.elementAt(r.nextInt(sharednotes.size()));
                       if(directornote.equalsIgnoreCase(matchernote)){
                           allNewNotes.addElement(new MoveANDSAME(pcset, director, matcher,directornote));
                       }
                       else{
                           allNewNotes.addElement(new MoveANDDIFFERENT(pcset, director, directornote, matcher, matchernote));
                       }
                       
                  }
                  else{
                       String directornote = directorNonSharedNotes.elementAt(r.nextInt(directorNonSharedNotes.size()));
                       String matchernote = matcherNonSharedNotes.elementAt(r.nextInt(matcherNonSharedNotes.size()));
                       allNewNotes.addElement(new MoveANDDIFFERENT(pcset, director, directornote, matcher, matchernote));
                  }
                  
                  
              }
              
              
              
          }
        //System.err.println(this.allPossSequences.size()+": "+debugseqno+ " GENERATED NOTES: "+allNotes.size());
        debugseqno++;
        return allNewNotes;
    }
        
        int debugseqno = 0;
       
      
       
      
      
      
        
   
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        public void displayMovesOnClients(){
            this.displayMovesOnClients_DIRECTOR();
            this.displayMovesOnClientMATCHER();
        }
        
        
        
        
      
      
      
      
        public void displayMovesOnClients_DIRECTOR(){
           
          
           String pAOutput = "";
           String pBOutput = "";
           
         
           
           String output = "";// PARTNER "+"   YOU   "+"\n";
           for(int i=0;i<this.pcset.moves.size();i++){
               Move mve = (Move)this.pcset.moves.elementAt(i);
               if(mve instanceof MoveONLY){
                    MoveONLY mo = (MoveONLY)mve;
                    if(mo.getPerformer()==matcher){      
                         if(mo.isSolved())   output = output +   stringutf8(8594)+mo.getText().toUpperCase() + stringutf8(818);
                         else                output = output +   stringutf8(8674)+mo.getText().toLowerCase() + stringutf8(818);            
                         
                    }
                    else{
                        if(mo.isSolved()) output = output +stringutf8(8594) +mo.getText().toUpperCase();
                        else              output = output +stringutf8(8674) +mo.getText().toLowerCase();
                    }
                         
               }
               else if(mve instanceof MoveANDSAME){
                    MoveANDSAME mas = (MoveANDSAME)mve;     
                    
                    //...fix this
                    
                    
                    if(!mas.isPartiallySolved(director) && !mas.isPartiallySolved(matcher) ){
                        output = output +   stringutf8(8674)+ mas.getText().toLowerCase()+ mas.getText().toLowerCase()+ stringutf8(818);
                    }
                    else if(mas.isPartiallySolved(director) &! mas.isPartiallySolved(matcher) ){
                         output = output +   stringutf8(8674)+ mas.getText().toUpperCase()+ mas.getText().toLowerCase()+stringutf8(818);
                    }
                    else if(!mas.isPartiallySolved(director) && mas.isPartiallySolved(matcher) ){
                       output = output +  stringutf8(8674)+   mas.getText().toLowerCase()+ mas.getText().toUpperCase()+ stringutf8(818);
                    }
                    else{
                        output = output +  stringutf8(8594)+ mas.getText().toUpperCase()+ mas.getText().toUpperCase()+ stringutf8(818);
                    }
                    
                    
               }
               else if(mve instanceof MoveANDDIFFERENT){
                    MoveANDDIFFERENT mad = (MoveANDDIFFERENT)mve;     
                    
                    if(!mad.isPartiallySolved(director) && !mad.isPartiallySolved(matcher) ){
                        output = output +   stringutf8(8674)+mad.getText(director).toLowerCase() + mad.getText(matcher).toLowerCase()+stringutf8(818);
                        
                    }
       
                    else if(mad.isPartiallySolved(director) &! mad.isPartiallySolved(matcher) ){
                        output = output +   stringutf8(8674)+mad.getText(director).toUpperCase()+mad.getText(matcher).toLowerCase()+stringutf8(818);
                    }
                    else if(!mad.isPartiallySolved(director) && mad.isPartiallySolved(matcher) ){
                       output = output +   stringutf8(8674)+mad.getText(director).toLowerCase()+mad.getText(matcher).toUpperCase()+stringutf8(818);
                    }
                    else {
                        output = output +   stringutf8(8674)+mad.getText(director).toUpperCase()+mad.getText(matcher).toUpperCase()+stringutf8(818);
                    }
               }
               
           }
           
           
           
            if(pA==director){
               String outputTransformed = this.translateFromSystemToGUI(pA,output);
               cC.changePinnedMessage(pA,pAOutput+outputTransformed);
            }
             else{
               String outputTransformed = this.translateFromSystemToGUI(pB,output);
               cC.changePinnedMessage(pB,pBOutput+outputTransformed);
            }
                   
           
           
      }
        
       public void displayMovesOnClientMATCHER(){
           if(pA==director){
              
               if(ispracticestage){
                   cC.changePinnedMessage(pB,"Please wait while your partner practices");   
               }
               else{
                    cC.changePinnedMessage(pB,"Follow your partner`s instructions");
               }
               
              
           }
           else{
               
               if(ispracticestage){
                   cC.changePinnedMessage(pA,"Please wait while your partner practices");

               }
               else{  
                   cC.changePinnedMessage(pA,"Follow your partner`s instructions");
               }
           }
       } 
        
        
      
        
       public String renderMoveOLD2(String firstText, String secondText){
           return "         "+    "[ "+ firstText+" , "+ secondText+" ]";
       }
        
        
    
      
      
       public void displayMovesOnServer(){
           
           //cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:" ,true, matcher );  
           //cC.c.textOutputWindow_ChangeText("instructions", "This is the sequence you need to perform:\n\n" ,true, director );  
          String outputheader =  matcher.getParticipantID()+"_"+matcher.getUsername() +"____________"+ director.getParticipantID()+"_"+director.getUsername()+"\n";
          outputheader = outputheader + "matcher:"+ (long)PCTaskTG.htwdcCORRECTMINUSINCORRECT.getObject(matcher)+ "_________"+ "director:"+ (long)PCTaskTG.htwdcCORRECTMINUSINCORRECT.getObject(director)+"\n"  ;
          outputheader = outputheader+" PARTNER "+"   YOU   "+"\n";
          String output="";
           for(int i=0;i<this.pcset.moves.size();i++){
               Move mve = (Move)this.pcset.moves.elementAt(i);
               if(mve instanceof MoveONLY){
                    MoveONLY mo = (MoveONLY)mve;
                    if(mo.getPerformer()==matcher){
                         if(mo.isSolved())   output = output +  "   ("+mo.getText()+")   "  +"\n";
                         else                output = output +  "    "+mo.getText()+"    "  +"\n";
                    }
                    else{
                       if(mo.isSolved()) output = output + "         "   + "   ("+mo.getText()+")   "  +"\n";
                       else              output = output + "         "   + "    "+mo.getText()+"    "  +"\n";
                    }
                         
               }
               else if(mve instanceof MoveANDSAME){
                    MoveANDSAME mas = (MoveANDSAME)mve;     
                    
                    
                    if(mas.isPartiallySolved(matcher) && mas.isPartiallySolved(director) ){
                        output = output +  "   ("+mas.getText()+")   " +  "   ("+mas.getText()  +")\n";
                    }
                    else if(mas.isPartiallySolved(matcher) &! mas.isPartiallySolved(director) ){
                        output = output +  "   ("+mas.getText()+")   " +  "    "+mas.getText()  +"\n";
                    }
                    else if(!mas.isPartiallySolved(matcher) && mas.isPartiallySolved(director) ){
                        output = output +  "    "+mas.getText()+"    " +  "   ("+mas.getText()  +")\n";
                    }
                    else{
                        output = output +  "    "+mas.getText()+"    " +  "    "+mas.getText()  +"\n";
                    }
                    
                    
               }
               else if(mve instanceof MoveANDDIFFERENT){
                    MoveANDDIFFERENT mad = (MoveANDDIFFERENT)mve;     
                    if(mad.isPartiallySolved(matcher) && mad.isPartiallySolved(director) ){
                        output = output +  "   ("+mad.getText(matcher)+")   " +  "   ("+mad.getText(director)  +")\n";
                    }
                    else if(mad.isPartiallySolved(matcher) &! mad.isPartiallySolved(director) ){
                        output = output +  "   ("+mad.getText(matcher)+")   " +  "    "+mad.getText(director)  +"\n";
                    }
                    else if(!mad.isPartiallySolved(matcher) && mad.isPartiallySolved(director) ){
                        output = output +  "    "+mad.getText(matcher)+"    " +  "   ("+mad.getText(director)  +")\n";
                    }
                    else{
                        output = output +  "    "+mad.getText(matcher)+"    " +  "    "+mad.getText(director)  +"\n";
                    }
               }
               
           }
           //cC.c.textOutputWindow_ChangeText("instructions", output ,true, director );
           
           //String outputTranslated = this.translateFromSystemToGUI(this.director, output);
           ;
           
           this.jpct.displayText(outputheader +output);
      }
      
     
      
      
      
       public String[] createRandomSequence(String elements, long length){
          String[] output = new String[(int)length];
          for(int i=0;i<length;i++){
              int idx= r.nextInt(elements.length());
              char c = elements.charAt(idx);
              output[i]=""+c;
          }
          return output;
      }
    
      
      
      
      
      
      
      
      
      
      //level 1:  OTHER DOES MoveONLY SHARED     Size = 1
      //level 2:  OTHER Does MoveONLY SHARED     Size = 2
      //level 3:  OTHER Does MoveONLY SHARED  OR  Move
      
      
       //size 1
       //size 2
       //size 3
       //size 4
       
       
       
      
      //10.(5 each)..you need to get your participant to do a single one OVERLAPPING
       //10 (5 each)..you need to get your participant to do two   OVERLAPPING
       //6  (3 each)...you need to get your participant to two 

    @Override
    public synchronized void processNotification(String s) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
        if(s.equalsIgnoreCase("timeout")){
             //System.exit(-567);
             
             cC.changePinnedMessage(pA,"TIMEOUT. NEXT GAME!");
             cC.changePinnedMessage(pB,"TIMEOUT. NEXT GAME!");
             
             //cC.c.textOutputWindow_ChangeText("instructions", "TIMEOUT. NEXT GAME: " ,false, pA );   
             //cC.c.textOutputWindow_ChangeText("instructions", "TIMEOUT. NEXT GAME: " ,false, pB );   
            
            //cC.c.changeClientInterface_clearMainWindows(pA);
            //cC.c.changeClientInterface_clearMainWindows(pB);
            
             cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pA,"TIMEOUT! NEXT SET." );
             cC.c.telegram_sendInstructionToParticipant_MonospaceFont(pB,"TIMEOUT! NEXT SET" );
            
            
            this.decreaseScoresTimeout(pA,pB,this.pcset.moves.size());
            this.createNewSequence(false); 
        }
        else if (s.equalsIgnoreCase("next")){
            //cC.c.textOutputWindow_ChangeText("instructions", ": " ,false, pA );   
            //cC.c.textOutputWindow_ChangeText("instructions", ": " ,false, pB );   
            this.createNewSequence(false); 
        }
        
    }

    @Override
    public void changeClientProgressBars(int value, String text) {
         //cC.c.changeJProgressBar(pA, pAWhitelist, pAWhitelist, Color.red, value);
         //cC.c.changeJProgressBar(pA, "instructions",  text, Color.black, value);
         //cC.c.changeJProgressBar(pB, "instructions",  text, Color.black, value);
    }
      
      
    
      
      public Vector getAdditionalValues(Participant p){
        
          
          Vector avs = new Vector(); 
          long    pNumberOfTrials = (long)PCTaskTG.htwnumberOfSets.getObject(p);
            
          int setsize = this.pcset.moves.size();
          int currval = this.pcset.moves.size();
          Move currMove = null;
          for(int i=0;i<this.pcset.moves.size();i++){
              if(!this.pcset.moves.elementAt(i).isSolved()){
                  currval=i;
                  currMove = pcset.moves.elementAt(i);
                  break;
              }
          }
          AttribVal av0A = new AttribVal("level",this.level);
         
          
        
          
          AttribVal av1 = new AttribVal("numberofsets",pNumberOfTrials);
          AttribVal av2 = new AttribVal("setsize",setsize);
          AttribVal av3 = new AttribVal("currval",currval);
          AttribVal av4 =null;
          
          if(currMove==null) {
              av4 = new AttribVal("currmove","NULL" );
          }
          else{
              av4 = new AttribVal("currmove",currMove.getDesc() );
          }
          AttribVal av5 = new AttribVal("sequence",this.pcset.getSequenceDescription());
          avs.addElement(av0A);
         
          avs.addElement(av1);
          avs.addElement(av2);
          avs.addElement(av3);
          avs.addElement(av4);
          avs.addElement(av5);
          System.err.println("---------------------------------------------------------");
          System.err.println("Sequencedescription is: "+av5.getValAsString());
          return avs;
      }
      
      
      
      
      
      
      
      
      
      
         /*public void createRandom_MoveSequenceGOOD(){
            Participant director;
            Participant matcher;
            String directorWhitelist;
            String matcherWhitelist;
            if(mostRecentDirector==pB || mostRecentDirector==null){
                director=pA; matcher=pB;
                directorWhitelist = this.pAWhitelist;
                matcherWhitelist = this.pBWhitelist;
            }
            else{
                director=pB; matcher=pA;
                directorWhitelist = this.pBWhitelist;
                matcherWhitelist = this.pAWhitelist;
            }
            Vector<MoveONLY> numberONLYOtherBothSharedV = new Vector();
            Vector<MoveONLY> numberONLYOtherNotSharedV = new Vector();
            Vector<MoveONLY> numberONLYSelfV = new Vector();
            Vector<MoveANDSAME> numberANDSameV = new Vector();
            Vector numberANDDifferentV = new Vector(); 
            
            while(numberONLYOtherBothSharedV.size()<numberONLYOtherBothShared){
                //System.exit(-56);
                 int index = r.nextInt(this.sharedWhitelist.length());
                 String s = ""+sharedWhitelist.charAt(index);
                 MoveONLY mo = new MoveONLY(this.pcset,matcher,s);
                 numberONLYOtherBothSharedV.add(mo);
            }
            while(numberONLYOtherNotSharedV.size()<numberONLYOtherNotShared){
                 int index = r.nextInt(matcherWhitelist.length());
                 String s = ""+matcherWhitelist.charAt(index);
                 MoveONLY mo = new MoveONLY(this.pcset,matcher,s);
                 numberONLYOtherNotSharedV.add(mo);
            }
            while(numberONLYSelfV.size()<numberONLYSelf){
                 int index = r.nextInt(directorWhitelist.length());
                 String s = ""+directorWhitelist.charAt(index);
                 MoveONLY mo = new MoveONLY(this.pcset,director,s);
                 numberONLYSelfV.add(mo);
            }
            while(numberANDSameV.size()<numberANDSame){
                 int index = r.nextInt(sharedWhitelist.length());
                 String s = ""+sharedWhitelist.charAt(index);
                 MoveANDSAME mas = new MoveANDSAME(this.pcset,director,matcher,s);
                 numberANDSameV.add(mas);
            }
            while(numberANDDifferentV.size()<numberANDDifferent){
                 int indexD = r.nextInt(directorWhitelist.length());
                 String sD = ""+directorWhitelist.charAt(indexD);
                 
                 String matcherWhitelistFiltered = (matcherWhitelist+"").replace(sD, "");
                 int indexM = r.nextInt(matcherWhitelistFiltered.length());
                 String sM = ""+matcherWhitelistFiltered.charAt(indexM);
                 
                 MoveANDDIFFERENT mad = new MoveANDDIFFERENT(this.pcset,director, sD, matcher,sM);
                 numberANDDifferentV.add(mad);
            }
            
            Vector finalMoveSet = new Vector();
            for(int i=0;i< numberONLYOtherBothSharedV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYOtherBothSharedV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberONLYOtherNotSharedV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYOtherNotSharedV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberONLYSelfV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYSelfV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberANDSameV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberANDSameV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberANDDifferentV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberANDDifferentV.elementAt(i) ,index  );
            }
            this.pcset.moves=finalMoveSet;
            this.displayMovesOnClients(director, matcher);
            this.mostRecentDirector=director;
       }
        */
      
      
      
      public int numberONLYOtherBothShared =0;
      public int numberONLYOtherNotShared=0;
      public int numberONLYSelf=0;
      public int numberANDSame=0;
      public int numberANDDifferent=0;
      
      
       public void createRandom_MoveSequenceFromGUI(){
           
            String directorWhitelist;
            String matcherWhitelist;
            if(director==pB || director==null){
                director=pA; matcher=pB;
                directorWhitelist = this.pAWhitelist;
                matcherWhitelist = this.pBWhitelist;
            }
            else{
                director=pB; matcher=pA;
                directorWhitelist = this.pBWhitelist;
                matcherWhitelist = this.pAWhitelist;
            }
            Vector<MoveONLY> numberONLYOtherBothSharedV = new Vector();
            Vector<MoveONLY> numberONLYOtherNotSharedV = new Vector();
            Vector<MoveONLY> numberONLYSelfV = new Vector();
            Vector<MoveANDSAME> numberANDSameV = new Vector();
            Vector numberANDDifferentV = new Vector(); 
            
            while(numberONLYOtherBothSharedV.size()<numberONLYOtherBothShared){
                
                 int index = r.nextInt(this.sharedWhitelist.length());
                 String s = ""+sharedWhitelist.charAt(index);
                 MoveONLY mo = new MoveONLY(this.pcset,matcher,s);
                 numberONLYOtherBothSharedV.add(mo);
            }
            while(numberONLYOtherNotSharedV.size()<numberONLYOtherNotShared){
                 int index = r.nextInt(matcherWhitelist.length());
                 String s = ""+matcherWhitelist.charAt(index);
                 MoveONLY mo = new MoveONLY(this.pcset,matcher,s);
                 numberONLYOtherNotSharedV.add(mo);
            }
            while(numberONLYSelfV.size()<numberONLYSelf){
                 int index = r.nextInt(directorWhitelist.length());
                 String s = ""+directorWhitelist.charAt(index);
                 MoveONLY mo = new MoveONLY(this.pcset,director,s);
                 numberONLYSelfV.add(mo);
            }
            while(numberANDSameV.size()<numberANDSame){
                 int index = r.nextInt(sharedWhitelist.length());
                 String s = ""+sharedWhitelist.charAt(index);
                 MoveANDSAME mas = new MoveANDSAME(this.pcset,director,matcher,s);
                 numberANDSameV.add(mas);
            }
            while(numberANDDifferentV.size()<numberANDDifferent){
                 int indexD = r.nextInt(directorWhitelist.length());
                 String sD = ""+directorWhitelist.charAt(indexD);
                 
                 String matcherWhitelistFiltered = (matcherWhitelist+"").replace(sD, "");
                 int indexM = r.nextInt(matcherWhitelistFiltered.length());
                 String sM = ""+matcherWhitelistFiltered.charAt(indexM);
                 
                 MoveANDDIFFERENT mad = new MoveANDDIFFERENT(this.pcset,director, sD, matcher,sM);
                 numberANDDifferentV.add(mad);
            }
            
            Vector finalMoveSet = new Vector();
            for(int i=0;i< numberONLYOtherBothSharedV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYOtherBothSharedV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberONLYOtherNotSharedV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYOtherNotSharedV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberONLYSelfV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberONLYSelfV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberANDSameV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberANDSameV.elementAt(i) ,index  );
            }
            for(int i=0;i< numberANDDifferentV.size();i++){
                int index = r.nextInt(finalMoveSet.size()+1);
                finalMoveSet.insertElementAt(numberANDDifferentV.elementAt(i) ,index  );
            }
            this.pcset.moves=finalMoveSet;
            this.displayMovesOnClients();
            
       }
     
        
      
      
      public void kill(){
          this.jt.doLoop=false;
          this.jt.jtti=null;
          this.jt.setVisible(false);
          this.jpct.setVisible(false);
      }
    
      
      public static String stringutf8(int codePoint) {
            return new String(Character.toChars(codePoint));
     }
      
      
      
      
      private synchronized void checkForTimeouts(){
          while(true){
              try{
                  wait(500);
                  if(this.pcset!=null){
                      pcset.checkForTimeouts();
                  }
              }catch(Exception e){
                  e.printStackTrace();
                  Conversation.saveErr(e);
              }
            
          }
          
      }
      
      
      
      
}
