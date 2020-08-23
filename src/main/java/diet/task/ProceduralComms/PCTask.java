/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralComms;

import diet.attribval.AttribVal;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.utils.HashtableWithDefaultvalue;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class PCTask implements JTrialTimerActionRecipientInterface{
      
      JPCTask jpct;
      Random r =new Random();
    
      static HashtableWithDefaultvalue htwdcSCORE = new HashtableWithDefaultvalue((long)0);
      static HashtableWithDefaultvalue htwdcLARGESTSUCCESS = new HashtableWithDefaultvalue((long)0);
      static HashtableWithDefaultvalue htwdcCORRECTMINUSINCORRECT = new HashtableWithDefaultvalue((long)0);
      //static HashtableWithDefaultvalue htwdcLARGESTSETSIZETOBEASSIGNED = new HashtableWithDefaultvalue((long)1);
      static HashtableWithDefaultvalue htwnumberOfSets = new HashtableWithDefaultvalue((long)1);
      public  static HashtableWithDefaultvalue htCurrentLevel = new HashtableWithDefaultvalue((int)0);
      
      //static HashtableWithDefaultvalue htwdcLARGESTMOSTRECENTSUCCES = new HashtableWithDefaultvalue(0);
    
      public String sharedWhitelist = "fgh";
      public String pAWhitelist = "asd";
      public String pBWhitelist = "jkl";
      static public String allowedMetaChars =  CustomDialog.getString("What is the META character?", "?");//"?";//?yn"; 
    
      PCSetOfMoves pcset ;
      public Participant pA;
      public Participant pB;
      
      public Participant mostRecentDirector=null;
      DefaultConversationController cC;
      
      long durationOfTrial = 90 *1000;
      
      JTrialTimer jt;
      
      //model = only items that are in shared
      
      
      
      public PCTask(DefaultConversationController cC, Participant pA, Participant pB, boolean startTimer){
          this( cC,  pA,  pB);
          if(startTimer){
              jt.startTimer();
          }
      }
      
      
      public PCTask(DefaultConversationController cC, Participant pA, Participant pB){
          super(); 
          cC.c.textOutputWindow_ChangeText("instructions", "",false, pA );
          cC.c.textOutputWindow_ChangeText("instructions", "",false, pB );
          jt = new JTrialTimer("Timer: "+pA.getParticipantID()+","+pA.getUsername()+"--"+pB.getParticipantID()+","+pB.getUsername(),this, durationOfTrial);
      
          
          this.cC=cC;
         
          //boolean defaultSettings = CustomDialog.getBoolean("Do you want to use default settings?", "yes", "no");
          
          boolean defaultSettings=true;
          if(!defaultSettings){
                  ///  this.sharedWhitelist = CustomDialog.getString("SHAREDWHITELIST", this.sharedWhitelist);
                  ///  this.pAWhitelist= CustomDialog.getString("PAWHITELIST", pAWhitelist);
                  ///  this.pBWhitelist= CustomDialog.getString("PBWHITELIST", pBWhitelist);
                    this.allowedMetaChars = CustomDialog.getString("ALLOWEDMETACHARACTERS", this.allowedMetaChars);
                    
                    ///numberONLYOtherBothShared =  CustomDialog.getInteger("How many ONLY By Other shared?",  2);
                    ///numberONLYOtherNotShared= CustomDialog.getInteger("How many ONLY By Other not shared?",  2);
                    ///numberONLYSelf = CustomDialog.getInteger("How many ONLY By Self?",  2);
                    ///numberANDSame= CustomDialog.getInteger("How many AND Same",  2);
                    ///numberANDDifferent=CustomDialog.getInteger("How many AND Different?",  2);
          } 
            this.pA=pA;
            this.pB=pB;
            jpct = new JPCTask(this); 
      
            int pALevel = (int)PCTask.htCurrentLevel.getObject(pA);
            int pBLevel = (int)PCTask.htCurrentLevel.getObject(pB);
            
            int minLevel = Math.min(pALevel, pBLevel);
            this.level=(int)minLevel;
            this.jpct.setLevel((int)this.level);
          
          
          pcset = new PCSetOfMoves(cC);
          
          
          
          
          
          this.createNewSequence(false);
          displayMovesOnServer(pA, pB);     
      }
      
      boolean displayDebug = false;//CustomDialog.getBoolean("Display debug info");
      
      public synchronized void evaluate(Participant p,String text){
          
          synchronized(this.jt){
               
               boolean success = pcset.evaluate(p, text);
               if(this.mostRecentDirector==pA){
                   displayMovesOnServer(pA, pB);     
               }
               else{
                   displayMovesOnServer(pB, pA);   
               }
              
               if(success) {
                    
                    System.err.println("LEVEL:");
                    cC.c.changeClientInterface_clearMainWindows(pA);
                    cC.c.changeClientInterface_clearMainWindows(pB);
                    int sizesucc = pcset.moves.size();
                    updateScoresSuccess(pA,pB, sizesucc);
                    cC.c.sendInstructionToParticipant(pA,"CORRECT! Your score is: "+  (Long)PCTask.htwdcSCORE.getObject(pA) );
                    cC.c.sendInstructionToParticipant(pB,"CORRECT! Your score is: "+  (Long)PCTask.htwdcSCORE.getObject(pB) );

                     cC.c.textOutputWindow_ChangeText("instructions", "CORRECT: " ,false, pA );   
                     cC.c.textOutputWindow_ChangeText("instructions", "CORRECT: " ,false, pB );   
                    createNewSequence(true);
                    this.jt.nextTrial();
               }    
               else{
                   if(this.mostRecentDirector==pA){
                                              
                       
                       if(displayDebug){
                           cC.c.textOutputWindow_ChangeText("instructions", "" ,false, pA ); 
                           cC.c.textOutputWindow_ChangeText("instructions", "" ,false, pB ); 
                           this.displayMovesOnClients(pA, pB);
                       }
                   }
                   else{
                      
                       if(displayDebug){
                           cC.c.textOutputWindow_ChangeText("instructions", "" ,false, pA ); 
                           cC.c.textOutputWindow_ChangeText("instructions", "" ,false, pB ); 
                           this.displayMovesOnClients(pB, pA);
                       }
                   }
                   
               }
          }
              
      }
    
      
      
    
      
      
      
    
      public void updateScoresSuccess(Participant pAA, Participant pBB, long sizeOfSet){           
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
      
      
      static int numberOfLevelsToDecreaseOnError = CustomDialog.getInteger("How many levels to decrease on error?", 1);
      
      
      public void createNewSequence(boolean previousWasSuccess ){
            if(previousWasSuccess){
                this.level++;
                this.jpct.setLevel(this.level);
               
            }
            else{
                //this.level--;
                
                this.level=this.level-numberOfLevelsToDecreaseOnError;
                if(this.level<=0)this.level=0;
                this.jpct.setLevel(this.level);
            }
             
             PCTask.htCurrentLevel.putObject(pA, this.level);
             PCTask.htCurrentLevel.putObject(pB, this.level);
          
             long pAScore = (long)this.htwdcSCORE.getObject(pA);
             long pBScore = (long)this.htwdcSCORE.getObject(pB);
             
             System.err.println("SAVINGLEVELOF:"+pA.getUsername()+" is "+level);
             System.err.println("SAVINGLEVELOF:"+pB.getUsername()+" is "+level);
             
             
             cC.c.textOutputWindow_ChangeText("instructions", "Your score is: "+pAScore +"\n",true, pA );   
             cC.c.textOutputWindow_ChangeText("instructions", "Your score is: "+pBScore +"\n",true, pB );   
          
          
            //this.createRandom_MoveANDSequence();
             long pANumberOfTrials = (long)PCTask.htwnumberOfSets.getObject(pA)+1;
             long pBNumberOfTrials = (long)PCTask.htwnumberOfSets.getObject(pB)+1;
             
             
            PCTask.htwnumberOfSets.putObject(pA, pANumberOfTrials);
            PCTask.htwnumberOfSets.putObject(pB, pBNumberOfTrials);
             
            this.createRandom_MoveSequence();
            
            if(this.mostRecentDirector==pA){
                   displayMovesOnServer(pA, pB);     
               }
            else{
                   displayMovesOnServer(pB, pA);   
           }
            
           
      }
     
      
      //mode0 = Other does moves  that are shared by both
  
      //mode 1= Other also does moves that are not shared by both
      //mode 2 = only moves that are shared and not shared by both
      
      //mode 3 =  the AND are the same letters
      //mode 4 = the AND are different (complementary) letters
      
      
      //private  int maxNumberOfAND =0;
      //private int maxNumberOfONLY_OTHER = 1;
      
      
     // need to change the processing...
     // if there is an error then the sequence should check whether it is the legit first move of the sequence..
      
     /* public int numberONLYOtherBothShared =0;
      public int numberONLYOtherNotShared=0;
      public int numberONLYSelf=0;
      public int numberANDSame=0;
      public int numberANDDifferent=0;
      */
      

      
      int level =0;
      int[][] levelsettings = new int[][]  { 
          //size ...number of 
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1},
          {1,1}, // size 1....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type
          {2,1}, // size 2....1 type  
          {2,2}, // size 2....2types
          {2,2}, // size 2....2types
          {2,2}, // size 2....2types
          {2,2}, // size 2....2types
          {2,2}, // size 2....2types
          {2,2}, // size 2....2types
          {2,2}, // size 2....2types
          {3,1}, // size 3.....1 type
          {3,1}, // size 3.....1 type
          {3,2}, // size 3.....2 types
          {3,2}, // size 3.....2 types
          {3,3}, // size 3....3 types
          {3,3}, // size 3....3 types
          {4,1}, // size 4.....1 type
          {4,1}, // size 4.....1 type
          {4,2}, // size 4.....2 types
          {4,2}, // size 4.....2 types
          {4,3}, // size 4....3 types
          {4,3}, // size 4....3 types
          {4,4}, // size 4....4 types
          {4,4}, // size 4....4 types
          
          {5,1}, // size 5.....1 type
          {5,1}, // size 5.....1 type
          
          {5,2}, // size 5.....2 types
          {5,2}, // size 5.....2 types
          {5,3}, // size 5....3 types
          {5,3}, // size 5....3 types
          {5,4}, // size 5....4 types
          {5,4}, // size 5....4 types
          {5,5}, // size 5....5 types
          {5,5}, // size 5....5 types
          
          {6,1}, // size 6....1 types
          {6,1}, // size 6....1 types
          {6,2}, // size 6....2 types
          {6,3}, // size 6....3 types
          {6,3}, // size 6....3 types
          {6,4}, // size 6....4 types
          {6,4}, // size 6....4 types
          {6,5}, // size 6....5 types
          {6,5}, // size 6....5 types
        
          {7,1}, // size 7....1 types
          {7,1}, // size 7....1 types
          {7,2}, // size 7....2 types
          {7,2}, // size 7....2 types
          {7,3}, // size 7....3 types
          {7,3}, // size 7....3 types
          {7,4}, // size 7....4 types
          {7,4}, // size 7....4 types
          {7,5}, // size 7....5 types
          {7,5}, // size 7....5 types
        
          {8,1}, // size 8....1 types
          {8,1}, // size 8....1 types
          {8,2}, // size 8....2 types
          {8,2}, // size 8....2 types
          {8,3}, // size 8....3 types
          {8,3}, // size 8....3 types
          {8,4}, // size 8....4 types
          {8,4}, // size 8....4 types
          {8,5}, // size 8....4 types
          {8,5}, // size 8....4 types
         
        
          {9,1}, // size 9....1 types
          {9,1}, // size 9....1 types
      };
      
      public void setLevel(int lev){
          this.level=lev;
      }
     
      
     
      
      
      
      
        public void createRandom_MoveSequence(){
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
            
            
            //this.levelsettings[level][0];
            
            int level_setsize = this.levelsettings[level][0];
            int level_typesize =  this.levelsettings[level][1];
            
            
            System.err.println("NEXTSETNEXTSETNEXTSET:---------------");
            System.err.println("LevelTypeSize: "+level_typesize);
            System.err.println("LevelSetSize: "+level_setsize);
            
            int level_activetypes_count=0;
            boolean[] level_activetypes = {false,false,false,false,false};
            if(this.level<10){
                level_activetypes[0] = true;
                level_activetypes_count=1;
            }
            
            
            while(level_activetypes_count<level_typesize){
                 int activeIDX = r.nextInt(level_activetypes.length);
                 level_activetypes[activeIDX ]=true;
                 level_activetypes_count=0;
                 for(int i=0;i<level_activetypes.length;i++){
                    System.err.println("------------LEVELACTIVETYPES:---------------");
                    System.err.println(i+": "+level_activetypes[i]);
                    if(level_activetypes[i]) level_activetypes_count++;
                 }    
            }
            
            
            
            
            
           
            while(numberONLYOtherBothSharedV.size() + numberONLYOtherNotSharedV.size() + numberONLYSelfV.size()  +  numberANDSameV.size()  +     numberANDDifferentV.size()            <level_setsize){
                int indexType = r.nextInt(5);
                
                if(indexType==0 &&level_activetypes[0]){
                     int index = r.nextInt(this.sharedWhitelist.length());
                     String s = ""+sharedWhitelist.charAt(index);
                     MoveONLY mo = new MoveONLY(this.pcset,matcher,s);
                     numberONLYOtherBothSharedV.add(mo);
                }
                else if(indexType==1 &&level_activetypes[1]){
                    int index = r.nextInt(matcherWhitelist.length());
                    String s = ""+matcherWhitelist.charAt(index);
                    MoveONLY mo = new MoveONLY(this.pcset,matcher,s);
                    numberONLYOtherNotSharedV.add(mo);
                }
                else if(indexType==2 && level_activetypes[2]){
                    int index = r.nextInt(directorWhitelist.length());
                    String s = ""+directorWhitelist.charAt(index);
                    MoveONLY mo = new MoveONLY(this.pcset,director,s);
                    numberONLYSelfV.add(mo);
                 }
                else if(indexType==3 &&level_activetypes[3]&r.nextBoolean()&r.nextBoolean()){
                    int index = r.nextInt(sharedWhitelist.length());
                    String s = ""+sharedWhitelist.charAt(index);
                    MoveANDSAME mas = new MoveANDSAME(this.pcset,director,matcher,s);
                    numberANDSameV.add(mas);
                 }   
                else if(indexType==4 &&level_activetypes[4]&r.nextBoolean() &&r.nextBoolean()){
                    int indexD = r.nextInt(directorWhitelist.length());
                    String sD = ""+directorWhitelist.charAt(indexD);
                    String matcherWhitelistFiltered = (matcherWhitelist+"").replace(sD, "");
                    int indexM = r.nextInt(matcherWhitelistFiltered.length());
                    String sM = ""+matcherWhitelistFiltered.charAt(indexM);                
                    MoveANDDIFFERENT mad = new MoveANDDIFFERENT(this.pcset,director, sD, matcher,sM);
                    numberANDDifferentV.add(mad);
                  }
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
      
      
      
      
      
      
    
      
       boolean showSolved;
       
      public void displayMovesOnClients(Participant director, Participant matcher){
           
            
           
           cC.c.textOutputWindow_ChangeText("instructions", "You can only select these letters: "+this.pAWhitelist+ this.sharedWhitelist+"\n" ,true, pA );  
           cC.c.textOutputWindow_ChangeText("instructions", "Your partner can only select these letters: "+this.pBWhitelist+ this.sharedWhitelist+"\n" ,true, pA );  
           
           cC.c.textOutputWindow_ChangeText("instructions", "You can only select these letters: "+this.pBWhitelist+ this.sharedWhitelist+"\n" ,true, pB );  
           cC.c.textOutputWindow_ChangeText("instructions", "Your partner can only select these letters: "+this.pAWhitelist+ this.sharedWhitelist+"\n" ,true, pB );  
           
           if(this.allowedMetaChars.length()>0){
                cC.c.textOutputWindow_ChangeText("instructions", "You can also press these keys: "+this.allowedMetaChars+"\n" ,true, pA );  
                cC.c.textOutputWindow_ChangeText("instructions", "You can also press these keys: "+this.allowedMetaChars+"\n" ,true, pB );       
           }
           
          
           
            cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:\n" ,true, matcher );  
           cC.c.textOutputWindow_ChangeText("instructions", "This is the sequence you need to perform:\n\n" ,true, director );
           
           
           
           String output = " PARTNER "+"   YOU   "+"\n";
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
           cC.c.textOutputWindow_ChangeText("instructions", output ,true, director );  
      }
      
       public void displayMovesOnServer(Participant director, Participant matcher){
           
           //cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:" ,true, matcher );  
           //cC.c.textOutputWindow_ChangeText("instructions", "This is the sequence you need to perform:\n\n" ,true, director );  
          String output =  matcher.getParticipantID()+"_"+matcher.getUsername() +"____________"+ director.getParticipantID()+"_"+director.getUsername()+"\n";
          output = output + "matcher:"+ (long)PCTask.htwdcCORRECTMINUSINCORRECT.getObject(matcher)+ "_________"+ "director:"+ (long)PCTask.htwdcCORRECTMINUSINCORRECT.getObject(director)+"\n"  ;
          output = output+" PARTNER "+"   YOU   "+"\n";
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
           this.jpct.displayText(output);
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
    public void processNotification(String s) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
        if(s.equalsIgnoreCase("timeout")){
             //System.exit(-567);
            cC.c.textOutputWindow_ChangeText("instructions", "TIMEOUT. NEXT GAME: " ,false, pA );   
            cC.c.textOutputWindow_ChangeText("instructions", "TIMEOUT. NEXT GAME: " ,false, pB );   
            
            cC.c.changeClientInterface_clearMainWindows(pA);
            cC.c.changeClientInterface_clearMainWindows(pB);
            
             cC.c.sendInstructionToParticipant(pA,"TIMEOUT! NEXT SET." );
             cC.c.sendInstructionToParticipant(pB,"TIMEOUT! NEXT SET" );
            
            
            this.decreaseScoresTimeout(pA,pB,this.pcset.moves.size());
            this.createNewSequence(false); 
        }
        else if (s.equalsIgnoreCase("next")){
            cC.c.textOutputWindow_ChangeText("instructions", ": " ,false, pA );   
            cC.c.textOutputWindow_ChangeText("instructions", ": " ,false, pB );   
            this.createNewSequence(false); 
        }
        
    }

    @Override
    public void changeClientProgressBars(int value, String text) {
         //cC.c.changeJProgressBar(pA, pAWhitelist, pAWhitelist, Color.red, value);
         cC.c.changeJProgressBar(pA, "instructions",  text, Color.black, value);
          cC.c.changeJProgressBar(pB, "instructions",  text, Color.black, value);
    }
      
      
    
      
      public Vector getAdditionalValues(Participant p){
        
          
          Vector avs = new Vector(); 
          long    pNumberOfTrials = (long)PCTask.htwnumberOfSets.getObject(p);
            
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
         
          int levelsetsize = this.levelsettings[level][0];
          int levelsetvariety = this.levelsettings[level][1];
          AttribVal av0B = new AttribVal("levelsetsize",levelsetsize); 
          AttribVal av0C = new AttribVal("levelsetvariety",levelsetvariety); 
          
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
          avs.addElement(av0B);
          avs.addElement(av0C);
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
        
      
      
      public void kill(){
          this.jt.doLoop=false;
          this.jt.jtti=null;
          this.jt.setVisible(false);
          this.jpct.setVisible(false);
      }
    
      
      
}
