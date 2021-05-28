/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.autointervention;

/**
 *
 * @author LX1C
 */
public class interventionblock {
    
    public String interventionid;//="";
    public String participantID;//="";
    public String poscrit;//= new String[]{};
    public String negcrit;//=new String[]{};
   public String response;//="";

    public interventionblock(String interventionid, String participantID, String poscrit, String negcrit, String response) {
        this.interventionid = interventionid;
        this.participantID = participantID;
        this.poscrit = poscrit;
        this.negcrit = negcrit;
        this.response = response;
        
        if(interventionid==null)interventionid="";
        if(participantID==null)participantID="";
        if(poscrit==null)poscrit="";
        if(negcrit==null)negcrit="";
        if(this.response==null)this.response="";
        
    }

    
    public boolean blockTurn(String pID, String turn){
         if(!this.participantID.equalsIgnoreCase("")&! participantID.equalsIgnoreCase(pID)){
             return false;
         }
         if(turn.equalsIgnoreCase(this.poscrit)){
              if(this.negcrit.equalsIgnoreCase("")){
                  return true;
              }
              else{
                  return !turn.contains(negcrit);
              }
         }
         return false;
    }
    
    public String getResponse(){
        return this.response;
    }
    
    
    public String toString(){
        String retval ="" ;
        retval = retval + "id:"+this.interventionid +" "
                + "pid:"+this.participantID+ " "
                + "poscriteria:"+this.poscrit + " "
                + "negcriteria:"+this.negcrit + " "
                + "response: "+this.response;
        return retval;
    }

   

    

    

    

    
    
    
    
    
    
}
