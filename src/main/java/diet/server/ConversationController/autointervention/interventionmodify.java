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
public class interventionmodify {
    
    public String interventionid;//="";
    public String participantID;//="";
    public String poscrit;//= new String[]{};
    public String negcrit;//=new String[]{};
    public String stringToBeReplaced;//
    public String stringReplacement;
   
    

    public interventionmodify(String interventionid, String participantID, String poscrit, String negcrit, String stToBeReplaced, String  stReplacement) {
        this.interventionid = interventionid;
        this.participantID = participantID;
        this.poscrit = poscrit;
        this.negcrit = negcrit;
        this.stringToBeReplaced=stToBeReplaced;
        this.stringReplacement = stReplacement;
        
      
        if(interventionid==null)interventionid="";
        if(participantID==null)participantID="";
        if(poscrit==null)poscrit="";
        if(negcrit==null)negcrit="";
        if(stringToBeReplaced==null)stringToBeReplaced="";
        if(stringReplacement==null)stringReplacement="";
        
    }

    
    public boolean modifyTurn(String pID, String turn){
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
    
    public String getModifiedText(String originalTurn){
         return  originalTurn.replace(this.stringToBeReplaced, stringReplacement);
    }
    
    
    
    public String toString(){
        String retval ="" ;
        retval = retval + "id:"+this.interventionid +" "
                + "pid:"+this.participantID+ " "
                + "poscriteria:"+this.poscrit + " "
                + "negcriteria:"+this.negcrit + " "
                + "stringtobereplaced: "+this.stringToBeReplaced+ " "
                + "stringreplacement: "+this.stringReplacement + " ";
        return retval;
    }

   

    

    

    

    
    
    
    
    
    
}
