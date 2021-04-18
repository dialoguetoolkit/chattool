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
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class PCTaskBACKUP implements JTrialTimerActionRecipientInterface{
      
      JPCTask jpct;
      Random r =new Random();
    
      static HashtableWithDefaultvalue htwdcSCORE = new HashtableWithDefaultvalue((long)0);
      static HashtableWithDefaultvalue htwdcLARGESTSUCCESS = new HashtableWithDefaultvalue((long)0);
      static HashtableWithDefaultvalue htwdcLARGESTSETSIZETOBEASSIGNED = new HashtableWithDefaultvalue((long)1);
      static HashtableWithDefaultvalue htwnumberOfSets = new HashtableWithDefaultvalue((long)1);
      //static HashtableWithDefaultvalue htwdcLARGESTMOSTRECENTSUCCES = new HashtableWithDefaultvalue(0);
    
      public String sharedWhitelist = "fgh";
      public String pAWhitelist = "asd";
      public String pBWhitelist = "jkl";
      public String allowedMetaChars =  "";//?yn"; 
    
      PCSetOfMoves pcset ;
      public Participant pA;
      public Participant pB;
      
      public Participant mostRecentDirector=null;
      DefaultConversationController cC;
      
      long durationOfTrial = 90 *1000;
      
      JTrialTimer jt = new JTrialTimer(this, durationOfTrial);
      
      
      //model = only items that are in shared
      
      
      
      
      
      
      public PCTaskBACKUP(DefaultConversationController cC, Participant pA, Participant pB){
          this.cC=cC;
         
          boolean defaultSettings = CustomDialog.getBoolean("Do you want to use default settings?", "yes", "no");
          if(!defaultSettings){
                    this.sharedWhitelist = CustomDialog.getString("SHAREDWHITELIST", this.sharedWhitelist);
                    this.pAWhitelist= CustomDialog.getString("PAWHITELIST", pAWhitelist);
                    this.pBWhitelist= CustomDialog.getString("PBWHITELIST", pBWhitelist);
                    this.allowedMetaChars = CustomDialog.getString("ALLOWEDMETACHARACTERS", this.allowedMetaChars);
                    
                    numberONLYOtherBothShared =  CustomDialog.getInteger("How many ONLY By Other shared?",  2);
                    numberONLYOtherNotShared= CustomDialog.getInteger("How many ONLY By Other not shared?",  2);
                    numberONLYSelf = CustomDialog.getInteger("How many ONLY By Self?",  2);
                    numberANDSame= CustomDialog.getInteger("How many AND Same",  2);
                    numberANDDifferent=CustomDialog.getInteger("How many AND Different?",  2);
          }
            jpct = new JPCTask(null); 
      
          
          
          
          //pcset = new PCSetOfMoves(cC);
          this.pA=pA;
          this.pB=pB;
          
          
          
          
          this.createNewSequence();
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
                    cC.c.changeClientInterface_clearMainWindows(pA);
                    cC.c.changeClientInterface_clearMainWindows(pB);
                    int sizesucc = pcset.moves.size();
                    increaseScoresSuccess(sizesucc);
                    cC.c.sendInstructionToParticipant(pA,"CORRECT! Your score is: "+  (Long)PCTaskBACKUP.htwdcSCORE.getObject(pA) );
                    cC.c.sendInstructionToParticipant(pB,"CORRECT! Your score is: "+  (Long)PCTaskBACKUP.htwdcSCORE.getObject(pB) );
                    
                    
                  
                     cC.c.textOutputWindow_ChangeText("instructions", "CORRECT: " ,false, pA );   
                     cC.c.textOutputWindow_ChangeText("instructions", "CORRECT: " ,false, pB );   
                    createNewSequence();
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
    
      
      
    
      
      
      
    
      public void increaseScoresSuccess(long sizeOfSet){           
             long pALargest = (long)htwdcLARGESTSUCCESS.getObject(pA);
             long pBLargest = (long)htwdcLARGESTSUCCESS.getObject(pB);
             
             htwdcLARGESTSUCCESS.putObject(pA, Math.max(pALargest, sizeOfSet));
             htwdcLARGESTSUCCESS.putObject(pB, Math.max(pBLargest, sizeOfSet));    
             
              htwdcLARGESTSETSIZETOBEASSIGNED.putObject(pA, (long)sizeOfSet+1);
              htwdcLARGESTSETSIZETOBEASSIGNED.putObject(pB, (long)sizeOfSet+1);
             
             long pAScore = (long)this.htwdcSCORE.getObject(pA); 
             long pBScore = (long)this.htwdcSCORE.getObject(pB);
             
             pAScore = (long)  (pAScore+ 10*(sizeOfSet));
             pBScore = (long)  (pBScore+ 10*(sizeOfSet));
             
             htwdcSCORE.putObject(pA, (long)pAScore);
             htwdcSCORE.putObject(pB, (long)pBScore);       
      }
      
      
       public void decreaseScoresTimeout(long sizeOfSet){           
             
              
             if(sizeOfSet>1)sizeOfSet=sizeOfSet-1;  
             
             
              htwdcLARGESTSETSIZETOBEASSIGNED.putObject(pA, (long)sizeOfSet);
              htwdcLARGESTSETSIZETOBEASSIGNED.putObject(pB, (long) sizeOfSet);
             //long pAScore = (long)this.htwdcSCORE.getObject(pA); 
            // long pBScore = (long)this.htwdcSCORE.getObject(pB);
             
            // pAScore = pAScore+ 10*(sizeOfSet);
            // pBScore = pBScore+ 10*(sizeOfSet);
             
             //htwdcSCORE.putObject(pA, (long)pAScore);
             //htwdcSCORE.putObject(pB, (long)pBScore);       
      }
      
      
      
      
      
      public void createNewSequence( ){
             long pAScore = (long)this.htwdcSCORE.getObject(pA);
             long pBScore = (long)this.htwdcSCORE.getObject(pB);
             
             cC.c.textOutputWindow_ChangeText("instructions", "Your score is: "+pAScore +"\n",true, pA );   
             cC.c.textOutputWindow_ChangeText("instructions", "Your score is: "+pBScore +"\n",true, pB );   
          
          
            //this.createRandom_MoveANDSequence();
             long pANumberOfTrials = (long)PCTaskBACKUP.htwnumberOfSets.getObject(pA)+1;
             long pBNumberOfTrials = (long)PCTaskBACKUP.htwnumberOfSets.getObject(pB)+1;
             
             
            PCTaskBACKUP.htwnumberOfSets.putObject(pA, pANumberOfTrials);
            PCTaskBACKUP.htwnumberOfSets.putObject(pB, pBNumberOfTrials);
             
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
      
      
     private  int maxNumberOfAND =0;
      private int maxNumberOfONLY_OTHER = 1;
      
      
     // need to change the processing...
     // if there is an error then the sequence should check whether it is the legit first move of the sequence..
      
      public int numberONLYOtherBothShared =1;
      public int numberONLYOtherNotShared=0;
      public int numberONLYSelf=0;
      public int numberANDSame=0;
      public int numberANDDifferent=0;
      
      
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
      
      
      public void displayMovesOLD(Participant director, Participant matcher){
           cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:" ,true, matcher );  
           cC.c.textOutputWindow_ChangeText("instructions", "This is the sequence you need to perform:\n\n" ,true, director );  
           
           String output = " PARTNER "+"   YOU   "+"\n";
           for(int i=0;i<this.pcset.moves.size();i++){
               Move mve = (Move)this.pcset.moves.elementAt(i);
               if(mve instanceof MoveONLY){
                    MoveONLY mo = (MoveONLY)mve;
                    if(mo.getPerformer()==matcher){
                         output = output +  "    "+mo.getText()+"    "  +"\n";
                         
                    }
                    else{
                        output = output + "         "   + "    "+mo.getText()+"    "  +"\n";
                    }
                         
               }
               else if(mve instanceof MoveANDSAME){
                    MoveANDSAME mas = (MoveANDSAME)mve;     
                    output = output +  "    "+mas.getText()+"    " +  "    "+mas.getText()  +"\n";
               }
               else if(mve instanceof MoveANDDIFFERENT){
                    MoveANDDIFFERENT mad = (MoveANDDIFFERENT)mve;     
                    output = output +  "    "+mad.getText(matcher)+"    " +  "    "+mad.getText(director)  +"\n";
               }
               
           }
           cC.c.textOutputWindow_ChangeText("instructions", output ,true, director );  
      }
       
       
      
      
      public void createRandom_MoveANDSequence(){
           long pALargest = (long)this.htwdcLARGESTSETSIZETOBEASSIGNED.getObject(pA);
           long pBLargest = (long)this.htwdcLARGESTSETSIZETOBEASSIGNED.getObject(pB);
           long largestmutual = Math.min(pALargest, pBLargest);
           
           if(largestmutual<1)largestmutual=1;
           //largestmutual =0;
           
           
            if( mostRecentDirector==pB  || mostRecentDirector==null){
                String[] sequenceONLY = this.createRandomSequence(pAWhitelist, largestmutual);
                String[] sequenceAND = this.createRandomSequence(this.sharedWhitelist, this.maxNumberOfAND);
                 
               // System.err.println("CREATED"+sequenceAND.length);
               //  System.exit(-5);
                
                Vector vSequenceOnly = new Vector();
                vSequenceOnly.addAll( Arrays.asList(sequenceONLY));
                Vector vSequenceOnlyCLONE = (Vector)vSequenceOnly.clone();
                
                 
                Vector vSequenceAnd = new Vector();
                vSequenceAnd.addAll( Arrays.asList(sequenceAND));
                Vector vSequenceAndCLONE = (Vector)vSequenceAnd.clone();
                
                
                Vector vOutput = new Vector();
                while(vSequenceOnly.size()>0 || vSequenceAnd.size()>0){
                    int index = r.nextInt(vSequenceOnly.size()+vSequenceAnd.size());
                    if(index<vSequenceOnly.size()){
                        Object o = vSequenceOnly.elementAt(index);
                        vOutput.insertElementAt(o, r.nextInt(vOutput.size()+1));
                        vSequenceOnly.removeElement(o);
                    }
                    else{
                        Object o = vSequenceAnd.elementAt(index-vSequenceOnly.size());
                         vOutput.insertElementAt(o, r.nextInt(vOutput.size()+1));
                         vSequenceAnd.removeElement(o);
                        
                    }
                }
                
                String[] sequence = new String[vOutput.size()];
                boolean[] seqboolANDSAME = new boolean[sequence.length];
                for(int i=0;i<vOutput.size();i++){
                    String s = (String)vOutput.elementAt(i);
                    sequence[i]=s;
                    if(vSequenceAndCLONE.contains(s)) seqboolANDSAME[i]=true;
                }
                
                
                
               
                
                pcset.createSequence_MoveANDSAME(pA, pB, sequence, seqboolANDSAME);
                
                
                String seqOTHER="";
                String seqSELF="";
                for(int i =0;i<sequence.length;i++){
                    if(seqboolANDSAME[i]){
                        seqOTHER = seqOTHER+sequence[i];
                        seqSELF=  seqSELF + sequence[i];
                    }
                    else{
                        seqOTHER = seqOTHER+sequence[i];
                        seqSELF=  seqSELF + " ";
                    }
                }
                
                
                
                cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:" ,true, pA );   
                cC.c.textOutputWindow_ChangeText("instructions", "Level 2\n",true,pB);
                cC.c.textOutputWindow_ChangeText("instructions", "Some letters you will need to select at the same time as your partner (within 3 secs)\n",true,pB);
                cC.c.textOutputWindow_ChangeText("instructions", "You need to perform the following sequence together:\n\nPartner: "+seqOTHER ,true, pB );
                cC.c.textOutputWindow_ChangeText("instructions", "\nYou:     "+seqSELF ,true, pB );
                mostRecentDirector=pA; 
           }
           else{
                String[] sequenceONLY = this.createRandomSequence(pBWhitelist, largestmutual);
                String[] sequenceAND = this.createRandomSequence(this.sharedWhitelist, this.maxNumberOfAND);
                 
               // System.err.println("CREATED"+sequenceAND.length);
               //  System.exit(-5);
                
                Vector vSequenceOnly = new Vector();
                vSequenceOnly.addAll( Arrays.asList(sequenceONLY));
                Vector vSequenceOnlyCLONE = (Vector)vSequenceOnly.clone();
                
                 
                Vector vSequenceAnd = new Vector();
                vSequenceAnd.addAll( Arrays.asList(sequenceAND));
                Vector vSequenceAndCLONE = (Vector)vSequenceAnd.clone();
                
                
                Vector vOutput = new Vector();
                while(vSequenceOnly.size()>0 || vSequenceAnd.size()>0){
                    int index = r.nextInt(vSequenceOnly.size()+vSequenceAnd.size());
                    if(index<vSequenceOnly.size()){
                        Object o = vSequenceOnly.elementAt(index);
                        vOutput.insertElementAt(o, r.nextInt(vOutput.size()+1));
                        vSequenceOnly.removeElement(o);
                    }
                    else{
                        Object o = vSequenceAnd.elementAt(index-vSequenceOnly.size());
                         vOutput.insertElementAt(o, r.nextInt(vOutput.size()+1));
                         vSequenceAnd.removeElement(o);
                        
                    }
                }
                
                String[] sequence = new String[vOutput.size()];
                boolean[] seqboolANDSAME = new boolean[sequence.length];
                for(int i=0;i<vOutput.size();i++){
                    String s = (String)vOutput.elementAt(i);
                    sequence[i]=s;
                    if(vSequenceAndCLONE.contains(s)) seqboolANDSAME[i]=true;
                }
                
                
                
               
                
                pcset.createSequence_MoveANDSAME(pB, pA, sequence, seqboolANDSAME);
                
                
                String seqOTHER="";
                String seqSELF="";
                for(int i =0;i<sequence.length;i++){
                    if(seqboolANDSAME[i]){
                        seqOTHER = seqOTHER+sequence[i];
                        seqSELF=  seqSELF + sequence[i];
                    }
                    else{
                        seqOTHER = seqOTHER+sequence[i];
                        seqSELF=  seqSELF + " ";
                    }
                }
                
                
                
                cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:" ,true, pB );   
                cC.c.textOutputWindow_ChangeText("instructions", "Together you need to perform the following sequence:\n\nPartner: "+seqOTHER ,true, pA );
                cC.c.textOutputWindow_ChangeText("instructions", "\nYou:     "+seqSELF ,true, pA );
                mostRecentDirector=pB; 
           }    
           
           
           
      }
      
       public void createRandom_MoveONLYSequence(){
           long pALargest = (long)this.htwdcLARGESTSETSIZETOBEASSIGNED.getObject(pA);
           long pBLargest = (long)this.htwdcLARGESTSETSIZETOBEASSIGNED.getObject(pB);
           long largestmutual = Math.min(pALargest, pBLargest);
           
           if(largestmutual<1)largestmutual=1;
           
           if( mostRecentDirector==pB  || mostRecentDirector==null){
                String[] sequence = this.createRandomSequence(pBWhitelist, largestmutual);
                pcset.createSequence_MoveONLY(pB, sequence);     
                String seq="";
                for(int i =0;i<sequence.length;i++){
                    seq = seq+sequence[i];
                }
                
                cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:" ,true, pA );   
                cC.c.textOutputWindow_ChangeText("instructions", "You must try and get your partner to do:\n\nPartner: "+seq ,true, pB );
                mostRecentDirector=pA; 
           }
           else{
                String[] sequence = this.createRandomSequence(pAWhitelist, largestmutual);
                pcset.createSequence_MoveONLY(pA, sequence);  
                String seq="";
                for(int i =0;i<sequence.length;i++){
                    seq = seq+sequence[i];
                } 
                cC.c.textOutputWindow_ChangeText("instructions", "You must try and get your partner to do:\n\nPartner: "+seq ,true, pA );
                cC.c.textOutputWindow_ChangeText("instructions", "Your partner is giving you instructions:",true, pB );
                mostRecentDirector=pB; 
           }      
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
    
      
      
      
      
      
      
      
      
      
      
      
      
      
      //10.(5 each)..you need to get your participant to do a single one OVERLAPPING
       //10 (5 each)..you need to get your participant to do two   OVERLAPPING
       //6  (3 each)...you need to get your participant to two 

    @Override
    public void processNotification(String s) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
        if(s.equalsIgnoreCase("timeout")){
             //System.exit(-567);
            cC.c.textOutputWindow_ChangeText("instructions", "TIMEOUT: " ,false, pA );   
            cC.c.textOutputWindow_ChangeText("instructions", "TIMEOUT: " ,false, pB );   
            this.decreaseScoresTimeout(this.pcset.moves.size());
            this.createNewSequence(); 
        }
        else if (s.equalsIgnoreCase("next")){
            cC.c.textOutputWindow_ChangeText("instructions", ": " ,false, pA );   
            cC.c.textOutputWindow_ChangeText("instructions", ": " ,false, pB );   
             this.createNewSequence(); 
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
          long    pNumberOfTrials = (long)PCTaskBACKUP.htwnumberOfSets.getObject(p);
            
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
          avs.addElement(av1);
          avs.addElement(av2);
          avs.addElement(av3);
          avs.addElement(av4);
          avs.addElement(av5);
          
          return avs;
      }
      
      
      
}
