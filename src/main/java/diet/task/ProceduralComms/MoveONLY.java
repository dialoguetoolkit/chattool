/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.ProceduralComms;

import diet.server.Participant;

/**
 *
 * @author gj
 */
public class MoveONLY extends Move{
    
     Participant pPerformer;
     String name;

    public MoveONLY(PCSetOfMoves pcs, Participant pPerformer, String name) {
        super(pcs);
        this.pPerformer = pPerformer;
        this.name = name;
    }
    
     
    
     public double evaluate(Participant p, String text){
         
         
         System.err.print("EVALUATING:" +text+ "  SENDER:"+ p.getParticipantID()+""+p.getUsername()+"-----ATTEMPTING TO MATCH:"+name+" FOR:"+pPerformer.getParticipantID()+pPerformer.getUsername());
             
         if(p==pPerformer && name.equalsIgnoreCase(text)) {
             this.solved=true;
             System.err.println("EVALUATING CORRECT");
             return 1;     
         }
         this.solved=false;
         System.err.println("EVALUATING INCORRECT");
         return 0;
     }
 
     public String getText(){
         return this.name;
     }
     public Participant getPerformer(){
         return this.pPerformer;
     }

    @Override
    public String getDesc() { 
        return "ONLY:"+this.name+"-"+this.solved;
    
    }
 
     
     
     
}
