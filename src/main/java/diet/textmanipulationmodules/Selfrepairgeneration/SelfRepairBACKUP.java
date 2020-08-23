/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.Selfrepairgeneration;

import diet.server.Conversation;
import diet.server.Participant;
import diet.utils.StringOperations;
import diet.utils.ThrowableOps;
import diet.utils.VectorToolkit;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class SelfRepairBACKUP {
    
    
    
    
        public Hashtable turnVersions = new Hashtable();
    
    
    public Vector getParticipantsTurnRevisions(Participant p){
        Object o = turnVersions.get(p);
        if(o==null){
            Vector v = new Vector();
            this.turnVersions.put(p, v);
            return v;
        }
        return (Vector)o;
    }
    
    public void addRevision(Participant p, String text ){
        text = text.replace("\n", "");
        Vector revisions = getParticipantsTurnRevisions(p);
        revisions.addElement(text);
        
        
    }
    
    public void setNewTurn (Participant p){
        turnVersions.put(p, new Vector());
    }
    
    public String getParticipantsTurnRevisionsAsString(Participant p){
        String listOfValues="";
        Vector v=getParticipantsTurnRevisions(p);
        for(int i=0;i<v.size();i++){
            String s = (String)v.elementAt(i);
            listOfValues = listOfValues+"("+i+") "+s+"\n";
        }
        return listOfValues;
    }
    
    
    
    boolean allowFullTurnDeletes = true;
    
    
    
    
    
    
    
    
    public String[] getFirstPart_SecondPart_SummaryWithErrorCheck(Participant p){
       
       
        
        String error = "ERROR:";
        try{
            return getFirstPart_SecondPart_Summary(p);
        }catch (Exception e){
            e.printStackTrace();
            error = error+ThrowableOps.getStackTraceAsString(e);
            Conversation.saveErr(e);
           
        }
        return new String[]{null,null, error};
    }
    
    private String[] getFirstPart_SecondPart_Summary(Participant p){
       
       String summary ="Summary of internal workings:\n";
        
        
        Vector v = this.getParticipantsTurnRevisions(p);
        if(v.size()<2){
            return new String[]{null,null,"The turn is much too short!"};
        }
        //int lengthOfProducedTurn = ((String)v.elementAt(v.size()-1)).length();
         
        
        summary = summary+ "Looking for the longest turn-versions (FIRST HALF OF REPAIR) that were typed before deleting:\n";
        
        String textAfterFinalDeleteBurst= null;
        ///Finding the longest turns
        Vector vCandidatesForLongest = new Vector();
        for(int i=2;i<v.size();i++){
            String s0 = (String)v.elementAt(i-2);
            String s1 = (String)v.elementAt(i-1);
            String s2 = (String)v.elementAt(i);
            if(s2.length()<s1.length()&& s1.length()>s0.length() ){
                            vCandidatesForLongest.add(s1); 
                            summary = summary+ "("+(i-1)+") "+s1+"\n";
            }
            if(s1.length()<s0.length() && s2.length()>s1.length()){
                textAfterFinalDeleteBurst=s1;
                System.err.println("DELETEBURST: "+s0);
                System.err.println("DELETEBURST: "+s1);
                System.err.println("DELETEBURST: "+s2);
                //System.exit(-4);
            }
            
            
        }
        
        
        summary =summary+"TEXT_AFTER_FINAL_DELETE_BURST: "+textAfterFinalDeleteBurst+"\n";
        
        if(textAfterFinalDeleteBurst == null){
            return new String[]{null,null, summary+ "EXITING...CAN'T FIND ANY TEXT AFTER FINAL DELETE BURST!\n"};
        }
       
        if(vCandidatesForLongest.size()==0){
             return new String[]{null,null, summary+ "EXITING...CAN'T FIND ANY SUITABLE CANDIDATES FOR FIRST HALF\n"};
           
        }
        
        
        
        //Need to choose the longest turn...
        String vlongestCandidateTurn = "";
        for(int i=0; i <vCandidatesForLongest.size();i++){
            String s = (String)vCandidatesForLongest.elementAt(i);
            if(s.length()>=vlongestCandidateTurn.length())vlongestCandidateTurn=s;
        }
        
        //Now need to go from the longest turn to the end to see where the leftmost deletes were made..
        int indexOfLongest = VectorToolkit.getINDEXOFOBJECT(vlongestCandidateTurn, v);
        
        String mostLeftmostDELETE = vlongestCandidateTurn;
        
       
        
       
        for(int i=indexOfLongest+1;i<=VectorToolkit.getINDEXOFOBJECT(textAfterFinalDeleteBurst,v);i++){
              String s = (String)v.elementAt(i);
              if(s.length()<=mostLeftmostDELETE.length()) mostLeftmostDELETE = s;
              System.err.println(i+"LOOKING FOR T ");
             
        }
       String lastVersionOfTurn = (String)v.lastElement();


       
       //Longest turn:    : WORD1 WORD2 WORD3 WORD4 WORD5
       //Leftmost delete  : WORD1 WORD2 WORD3 WORD4
       //Last turn version: WORD1 WORD2 WORD3 WORDA WORDB
       //Checking...did it delete past a space?
       //
       int lengthOfLeftMostDelete = mostLeftmostDELETE.length();
       String deletedPortion =  vlongestCandidateTurn.substring(lengthOfLeftMostDelete);
       
           
       boolean hasDeletedToTheBeginningOfTheTurn = false; if(mostLeftmostDELETE.length()==0)hasDeletedToTheBeginningOfTheTurn=true;
       boolean hasDeletedAPhysicalWhitespaceCharacter = false;   hasDeletedAPhysicalWhitespaceCharacter=StringOperations.doesStringContainWhitespace(StringOperations.stripEnd(deletedPortion, null));     
       
       if (hasDeletedToTheBeginningOfTheTurn) summary = summary + "Detected delete of entire turn\n";
       if (hasDeletedAPhysicalWhitespaceCharacter)   summary = summary + "Detected delete of whitespace character.\n";
       
      boolean hasDeletedToPositionPrecededByWhitespace=false;
      try{
           char c = vlongestCandidateTurn.charAt(lengthOfLeftMostDelete-1);
           if (Character.isWhitespace(c)) {
               hasDeletedToPositionPrecededByWhitespace = true;    
           } 
       }catch (Exception e){//Do not need any output here..it will only crash if they delete to the beginning of the turn...in which case
       }
      
      
      /*  boolean hasDeletedAndThenContinuedImmediatelyWithASpace = false;
       boolean hasDeletedAndThenContinuationHasASpaceInIt = false;
    
       try{
           char c = vlongestCandidateTurn.charAt(lengthOfLeftMostDelete-1);
           if (Character.isWhitespace(c)) {
               hasDeletedToPositionPrecededByWhitespace = true;    
           } 
       }catch (Exception e){//Do not need any output here..it will only crash if they delete to the beginning of the turn...in which case
       }
       
       try{
           char c = lastVersionOfTurn.charAt(mostLeftmostDELETE.length());
           if(Character.isWhitespace(c)) hasDeletedAndThenContinuedImmediatelyWithASpace=true;  
       }catch (Exception e){ }
       
       try{
           String lastVersionOfTurnToTheRightOfTheDeletePoint = lastVersionOfTurn.substring(mostLeftmostDELETE.length());
           if(StringOperations.doesStringContainWhitespace(lastVersionOfTurnToTheRightOfTheDeletePoint))  hasDeletedAndThenContinuationHasASpaceInIt =true;
           
       } catch (Exception e){ }
       
       
       if (hasDeletedToPositionPrecededByWhitespace) summary = summary + "Detected delete to position preceded by whitespace.\n";
       if (hasDeletedAndThenContinuedImmediatelyWithASpace) summary =summary + "Detected delete was followed immediately by a whitespace.\n";
       if (hasDeletedAndThenContinuationHasASpaceInIt) summary =summary + "Detected delete was followed by text that had a whitespace somewhere in it...\n";
        //if(! hasDeletedToPositionPrecededByWhitespace &! hasDeletedAndThenContinuedImmediatelyWithASpace &!hasDeletedAndThenContinuationHasASpaceInIt ){
       //    summary = summary + " EXITING: Didn't detect: (1) deleting of whitespace (2) deleting to position preceded by whitespace (3) immediately continuing after the delete with a whitespace (4) The continuation had no whitespace\n";   
       //    return new String[]{null,null, summary};
       //}
       */
       
       if(!allowFullTurnDeletes && hasDeletedToTheBeginningOfTheTurn ){
            summary = summary +"EXITING BECAUSE OF DELETE TO FULL TURN\n";
            return new String[]{null,null, summary};
       }
       else if(!hasDeletedAPhysicalWhitespaceCharacter & !hasDeletedToPositionPrecededByWhitespace){
            summary = summary +"EXITING BECAUSE IT DIDN'T DELETE A NON-TERMINAL WHITESPACE AND DIDN'T DELETE TO POSITION PRECEDED BY WHITESPACE\n";
            return new String[]{null,null, summary};
       }
       
       
       boolean textToTheRightOfLeftmostDeleteIsAllSpaces = false;
       
      // mostLeftmostDELETE
      
       
       //Longest turn:    : WORD1 WORD2 WORD3 WORD4 WORD5
       //Leftmost delete  : WORD1 WORD2 WORD3 |
       //Last turn version: WORD1 WORD2 WORD3 WORDA WORDB
          
       
       
       if( mostLeftmostDELETE.length()==0){
                 summary = summary + "It's a delete of a full turn. SAFE TO GENERATE!\n";
                 return  new String[] {vlongestCandidateTurn, lastVersionOfTurn.substring(mostLeftmostDELETE.length()), summary};
           
       }
       
       //The last character of leftmost delete = SPACE && This space character exists at the same position in the last turn version     (GOOD)       
       else if( Character.isWhitespace(mostLeftmostDELETE.charAt(mostLeftmostDELETE.length()-1))){
            if(Character.isWhitespace(vlongestCandidateTurn.charAt(mostLeftmostDELETE.length()-1))){
                 summary = summary + "The last character of leftmost delete is whitespace AND The same index in last turn is also whitespace. SAFE TO GENERATE!\n";
                 
                 System.err.println("DEX IS: "+mostLeftmostDELETE.length());
                 int iindex = mostLeftmostDELETE.length();
                 int llength = lastVersionOfTurn.length();
                  System.err.println("THE LENGTH IS: "+llength);
                 
                 System.err.println("THE CRASH IS AT:"+lastVersionOfTurn.substring(iindex));
                 System.err.println(getParticipantsTurnRevisionsAsString(p));
                 System.err.println(summary);
                 return  new String[] {vlongestCandidateTurn, lastVersionOfTurn.substring(mostLeftmostDELETE.length()), summary};
            }
       }
       
       
       
       
       
       

       //Longest turn:    : WORD1 WORD2 WORD3 WORD4 WORD5
       //Leftmost delete  : WORD1 WORD2 WORD3|
       //Last turn version: WORD1 WORD2 WORD3 WORDA WORDB
       
       
       //I am going to the shop
       //I am going to 
       //I am going to a cafe     
       
       boolean editingAtRightMostEdge = false;
       if( Character.isWhitespace(vlongestCandidateTurn.charAt(mostLeftmostDELETE.length()))){
           summary = summary + "Edit detected at rightmost boundary of a word\n";
           if(Character.isWhitespace(lastVersionOfTurn.charAt(mostLeftmostDELETE.length()))){
                summary = summary + "Edit detected at rightmost boundary of a word. The word was kept. Does NOT need to be repeated. SAFE TO GENERATE!\n";
                return  new String[] {vlongestCandidateTurn, lastVersionOfTurn.substring(mostLeftmostDELETE.length()), summary};
                
           }
           else{
                summary = summary + "Edit detected at rightmost boundary of a word. The word had text appended. New word needs to be extracted (USING NEW INDEX)!\n";
                int indexOfPreviousSpace = StringOperations.getIndexOfPrecedingWhiteSpace(lastVersionOfTurn,mostLeftmostDELETE.length()-1);
                indexOfPreviousSpace = indexOfPreviousSpace+1;
                return  new String[] {vlongestCandidateTurn, lastVersionOfTurn.substring(indexOfPreviousSpace), summary};        
           }
       }    
      
       
    
       summary = summary + "Edit detected within a word!\n";
       int indexOfPreviousSpace = StringOperations.getIndexOfPrecedingWhiteSpace(lastVersionOfTurn,mostLeftmostDELETE.length()-1);
       indexOfPreviousSpace = indexOfPreviousSpace+1;
       return  new String[] {vlongestCandidateTurn, lastVersionOfTurn.substring(indexOfPreviousSpace), summary};       
                
           
       //STILL NEED TO ADD THE FOLLOWING:
       ///Need to add check that leftmost delete is fully contained in the last turn version!! if it isn't - add an error message!
       //need to add error detection..so that IF THERE IS AN ERROR ANYWHERE IT SAVES THAT THERE WAS AN ERROR AND ABORTS IT AND SENDS THE CORRECT VERSION! And saves the error message to the output..
       ///need to save the internal workings to the data file
            
     
      
       
    }
    
}
