/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralComms;

import diet.server.Conversation;
import diet.server.Participant;
import java.util.Date;

/**
 *
 * @author gj
 */
public class MoveANDDIFFERENT extends Move{
    
     Participant pA;
     Participant pB;
     String name_pA;
     String name_pB;
     long timeOfSelectionByPA = -1;
     long timeOfSelectionByPB = -1;
     
     
     
    public MoveANDDIFFERENT(PCSetOfMoves pcs, Participant pA, String namePA, Participant pB,  String namePB) {
        super(pcs);
        this.pA=pA;
        this.pB=pB;
        this.name_pA=namePA;
        this.name_pB=namePB;
    }
    
     
    
     public double evaluate(Participant p, String text){
         
         
         
         
         System.err.println("EVALUATING ANDDDIFFERENTA");
         System.err.println("EVALUATING ANDDDIFFERENTA--user"+p.getUsername()+ " selected "+text);
         System.err.println("EVALUATING ANDDDIFFERENTA--This move has PA:"+pA.getUsername()+ " PA "+name_pA+ " pB:"+pB.getUsername()+" PB "+name_pB);
         System.err.println("EVALUATING ANDDDIFFERENTA--"+text+"-------"+name_pA+"-------------"+name_pB+"-----------");

         
         
         
         
         
         if(p==pA){
              System.err.println("EVALUATING ANDDDIFFERENTA-ISPA");
         }
         if(this.timeOfSelectionByPA == -1){
              System.err.println("EVALUATING ANDDDIFFERENTA-TIMEOFSELECTIONBYAIS-1");
         }
         if(this.timeOfSelectionByPB==-1){
               System.err.println("EVALUATING ANDDDIFFERENTA-TIMEOFSELECTIONBBAIS-1");
         }
         if(this.name_pA.equals(text)){
              System.err.println("EVALUATING ANDDDIFFERENTA-Text is equals");
         }
         //if(2<5)System.exit(-5);
         
         
         
         
         
         if(p==pA && this.timeOfSelectionByPA != -1) {
                timeOfSelectionByPA = -1;  
                timeOfSelectionByPB = -1;  
                this.solved=false;
                System.err.println("EVALUATING ANDDDIFFERENT2");
                return 0;
         }
         else if(p==pA && this.timeOfSelectionByPA == -1   && timeOfSelectionByPB ==-1  && this.name_pA.equals(text)) {
              timeOfSelectionByPA = new Date().getTime();
              System.err.println("EVALUATING ANDDDIFFERENT3");
               return 0.5;
         }
          else if(p==pA && this.timeOfSelectionByPA == -1   && timeOfSelectionByPB !=-1) {
              System.err.println("EVALUATING ANDDDIFFERENT4");
              timeOfSelectionByPA = new Date().getTime(); 
              if(timeOfSelectionByPA-timeOfSelectionByPB <=PCTaskTG.windowForJointSelection  && this.name_pA.equals(text)) {
                     this.solved=true;
                      System.err.println("EVALUATING ANDDDIFFERENT5");
                      return 1;
              }
              else{
                      timeOfSelectionByPA = -1;  
                      timeOfSelectionByPB = -1;  
                      this.solved=false;
                       System.err.println("EVALUATING ANDDDIFFERENT6");
                       return 0;
               }
             
         }
         
         if(p==pB && this.timeOfSelectionByPB != -1 ) {
                
                timeOfSelectionByPA = -1;  
                timeOfSelectionByPB = -1;  
                this.solved=false;
                System.err.println("EVALUATING ANDDDIFFERENT_B1");
                 return 0;
         }
         else if(p==pB && this.timeOfSelectionByPB == -1   && timeOfSelectionByPA ==-1  && this.name_pB.equals(text)) {
              timeOfSelectionByPB = new Date().getTime();
              System.err.println("EVALUATING ANDDDIFFERENT_B2");
               return 0.5;
         }
          else if(p==pB && this.timeOfSelectionByPB == -1   && timeOfSelectionByPA !=-1) {
              timeOfSelectionByPB = new Date().getTime(); 
              System.err.println("EVALUATING ANDDDIFFERENT_B3");
              if(timeOfSelectionByPB-timeOfSelectionByPA <=PCTaskTG.windowForJointSelection && this.name_pB.equals(text)) {
                     this.solved=true;
                      System.err.println("EVALUATING ANDDDIFFERENT_B4");
                     return 1 ;
                 }
                 else{
                      timeOfSelectionByPA = -1;  
                      timeOfSelectionByPB = -1;  
                      this.solved=false;
                       System.err.println("EVALUATING ANDDDIFFERENT_B5");
                        return 0;
                 }
             
         }
         System.err.println("EVALUATING ANDDDIFFERENT666666");
         timeOfSelectionByPA = -1;  
         timeOfSelectionByPB = -1;  
         this.solved=false;
          return 0;
         
         
         
     }

    public String getText(Participant p) {
        if(p==this.pA) return this.name_pA;
        if(p==this.pB) return this.name_pB;
        return "ERRRORERRORSHOULDNTHAPPEN";
    }
    
    public String getTextOTHER(Participant p) {
        if(p==this.pA) return this.name_pB;
        if(p==this.pB) return this.name_pA;
        return "ERRRORERRORSHOULDNTHAPPEN";
    }
         
     public boolean isPartiallySolved(Participant p){
        if(p==pA){
            if( timeOfSelectionByPA > -1) return true;
            return false;
        }
        else if(p==pB){
            if( timeOfSelectionByPB > -1) return true;
            return false;
        }
        
         return false;
    } 

    @Override
    public void setSolved(boolean solved) {
        if(!solved){
            this.timeOfSelectionByPA=-1;
            this.timeOfSelectionByPB=-1;
            this.solved=false;
        }
        super.setSolved(solved); //To change body of generated methods, choose Tools | Templates.
    }
    
     
     
     
     
     public String getDesc() {
         //return "ANNDDIFFERENT:"+this.name_pA+"-"+this.timeOfSelectionByPA+"-"+this.name_pB+"-"+this.timeOfSelectionByPB;
         String description = "ANDDIFFERENT "+pA.getParticipantID()+" "+pA.getUsername()+" "+this.name_pA+" "+this.timeOfSelectionByPA + " "+pB.getParticipantID()+" "+pB.getUsername() + " "+this.name_pB + " "+this.timeOfSelectionByPB;
         return description;
    }
    
     
     
     public boolean isTimedOut(){
        long currentTime = new Date().getTime();
        if((timeOfSelectionByPA >0  & timeOfSelectionByPB >0)){
            Conversation.saveErr("This really shouldn`t be called. PCSETOFMOVES is checking whether ANDDIFFERENT has timed out even though BOTH have already been selected");
        }
        
        
        
        if(timeOfSelectionByPA >0){
              if(currentTime - timeOfSelectionByPA > PCTaskTG.windowForJointSelection){
                  return true;
              }
        }
        if(timeOfSelectionByPB >0){
              if(currentTime - timeOfSelectionByPB > PCTaskTG.windowForJointSelection){
                  return true;
              }
        }
        return false;
        
    }
     
     
}
