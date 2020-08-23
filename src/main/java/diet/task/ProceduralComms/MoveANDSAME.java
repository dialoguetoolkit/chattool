/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralComms;

import diet.server.Participant;
import java.util.Date;

/**
 *
 * @author gj
 */
public class MoveANDSAME extends Move{
    
     Participant pA;
     Participant pB;
     String name;
     long timeOfSelectionByPA = -1;
     long timeOfSelectionByPB = -1;
     
     long windowForJointSelection = 3000;
     
    public MoveANDSAME(PCSetOfMoves pcs, Participant pA, Participant pB, String name) {
        super(pcs);
        this.pA=pA;
        this.pB=pB;
        this.name = name;
    }
    
     
    
     public double evaluate(Participant p, String text){
         System.err.println("EVALUATING ANDSAME ANDSAME ANDSAME ANDSAME ANDSAME");
         System.err.println("EVALUATING ANDSAME ANDSAME ANDSAME ANDSAME ANDSAME");
         if(name.equalsIgnoreCase(text)){
             
             if(p==pA && timeOfSelectionByPA== -1 && timeOfSelectionByPB== -1){
                 timeOfSelectionByPA = new Date().getTime();  
                 System.err.println("EVALUATING ANDSAME__1A"); 
                 return 0.5;
             }
             else if(p==pA && timeOfSelectionByPA!= -1 ){
                 timeOfSelectionByPA = -1;  
                 timeOfSelectionByPB = -1;  
                 this.solved=false;
                  System.err.println("EVALUATING ANDSAME__1B"); 
                 return 0;
             }
             else if(p==pA && timeOfSelectionByPA== -1 && timeOfSelectionByPB!= -1){
                 timeOfSelectionByPA = new Date().getTime();  
                 if(timeOfSelectionByPA-timeOfSelectionByPB <=this.windowForJointSelection) {
                     this.solved=true;
                      System.err.println("EVALUATING ANDSAME__1C"); 
                     return 1;
                 }
                 else{
                      timeOfSelectionByPA = -1;  
                      timeOfSelectionByPB = -1;  
                      this.solved=false;
                       System.err.println("EVALUATING ANDSAME__1D"); 
                      return 0;
                 }
             }
             
             else if(p==pB && timeOfSelectionByPB== -1 && timeOfSelectionByPA== -1){
                 
                 timeOfSelectionByPB = new Date().getTime();  
                 System.err.println("EVALUATING ANDSAME__2A"); 
                 return 0.5;
             }
             else if(p==pB && timeOfSelectionByPB!= -1 ){
                 timeOfSelectionByPA = -1;  
                 timeOfSelectionByPB = -1;  
                 this.solved=false;
                  System.err.println("EVALUATING ANDSAME__2B"); 
                 return 0;
             }
             else if(p==pB && timeOfSelectionByPB== -1 && timeOfSelectionByPA!= -1){
                 timeOfSelectionByPB = new Date().getTime();  
                 if(timeOfSelectionByPB-timeOfSelectionByPA <=this.windowForJointSelection) {
                     this.solved=true;
                      System.err.println("EVALUATING ANDSAME__2C"); 
                     return 1;
                 }
                 else{
                      timeOfSelectionByPA = -1;  
                      timeOfSelectionByPB = -1;  
                      this.solved=false;
                       System.err.println("EVALUATING ANDSAME__2D"); 
                      return 0;
                 }
             }
             
             
         }
          System.err.println("EVALUATING ANDSAME__2E"); 
          timeOfSelectionByPA = -1;  
         timeOfSelectionByPB = -1;  
         this.solved=false;
         return 0;
     }

    public String getText() {
        return name;
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
               timeOfSelectionByPA = -1;  
               timeOfSelectionByPB = -1; 
               this.solved=false;
        }
        super.setSolved(solved);
    }
         
    public String getDesc() {
        return "ANDSAME:"+  this.name+"-"+timeOfSelectionByPA+"-"+timeOfSelectionByPB;
    }
    
    
}
