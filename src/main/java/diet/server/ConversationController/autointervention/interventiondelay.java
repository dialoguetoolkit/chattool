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
public class interventiondelay {
    
    public String interventionid;//="";
    public String participantID;//="";
    public String poscrit;//= new String[]{};
    public String negcrit;//=new String[]{};
    public Long delay ;
    

    public interventiondelay(String interventionid, String participantID, String poscrit, String negcrit, String dely) {
        this.interventionid = interventionid;
        this.participantID = participantID;
        this.poscrit = poscrit;
        this.negcrit = negcrit;
       
        try{
            this.delay = Long.parseLong(dely);
        }catch(Exception e){
            this.delay=(long)0;
            System.err.println("Invalid delay time specified: "+delay);
        }
              
        if(interventionid==null)interventionid="";
        if(participantID==null)participantID="";
        if(poscrit==null)poscrit="";
        if(negcrit==null)negcrit="";
  
        
    }

    
    public boolean delayTurn(String pID, String turn){
         if(!this.participantID.equalsIgnoreCase("")&! participantID.equalsIgnoreCase(pID)){
             return false;
         }
         if(turn.contains(this.poscrit)){
              if(this.negcrit.equalsIgnoreCase("")){
                  return true;
              }
              else if( !turn.contains(negcrit)){
                   return true;      
              }
         }
         return false;
    }
    
    
    public long getDelay(){
        return this.delay;
    }
    
    
    
    
    public String toString(){
        String retval ="" ;
        retval = retval + "id:"+this.interventionid +" "
                + "pid:"+this.participantID+ " "
                + "poscriteria:"+this.poscrit + " "
                + "negcriteria:"+this.negcrit + " "
                + "delay: "+this.delay;
        return retval;
    }

   

    

    

    

    
    
    
    
    
    
}
