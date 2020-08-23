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
abstract class Move {
 
    boolean solved = false;
    abstract public double evaluate(Participant p, String text);
    PCSetOfMoves pcs;
    
    public Move(PCSetOfMoves pcs){
        this.pcs=pcs;
    }
    
    
    public boolean isSolved(){
        return solved;
    }
    public void setSolved(boolean solved){
        this.solved=solved;
    }
    
    public String getDesc(){
        return "superclass";
    }
    
    
}
