/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.autointervention;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LX1C
 */
public class interventionmodify {
    
    public String interventionid;//="";
    public String participantID;//="";
    public Pattern poscrit;//= new String[]{};
    public Pattern negcrit;//=new String[]{};
    public Pattern stringToBeReplaced;//
    public String stringReplacement;
   
    

    public interventionmodify(String interventionid, String participantID, String strposcrit, String strnegcrit, String stToBeReplaced, String  stReplacement) {
        this.interventionid = interventionid;
        this.participantID = participantID;
        this.poscrit = Pattern.compile(strposcrit);
        this.negcrit = Pattern.compile(strnegcrit);
        this.stringToBeReplaced=Pattern.compile(stToBeReplaced);
        this.stringReplacement = stReplacement;
        
      
        if(interventionid==null)interventionid="";
        if(participantID==null)participantID="";
        if(stringReplacement==null)stringReplacement="";
        
        System.err.println("Initializing interventionmodify: poscrit:"+this.poscrit+ "---------negcrit "+this.negcrit+ "----------stringtobereplaced:"+stringToBeReplaced+"---stringreplacement"+stringReplacement);
        
    }

    public static void main(String[] args){
       interventionmodify imod = new interventionmodify("ID1","","[Oo][Kk]","","[Oo][Kk]", "REPLACED");
       
       boolean modifyturn = imod.modifyTurn("", "This is OK");
       //System.out.println(modifyturn);
       if(modifyturn){
           System.out.println(imod.getModifiedText("This is OK and so is this OK OK yes it is OK"));
       }
       
       
       modifyturn = imod.modifyTurn("", "This is really isn`t and so thre isn`t");
       //System.out.println(modifyturn);
       if(modifyturn){
           System.out.println(imod.getModifiedText("This is OK and so is this OK OK yes it is OK"));
       }
    }
    
    
    public static boolean doesStringContainRegEx(String haystack, Pattern regex){
        if(regex.pattern().equalsIgnoreCase(""))return false;
        Matcher matcher = regex.matcher(haystack);
        int count = 0;
        while(matcher.find()){
            System.err.println("Found regex:"+regex.pattern()+"----in haystack:"+haystack);
            return true;// true:
        }
        return false;
    }
    
    
    
    
    public boolean modifyTurn(String pID, String turn){
         if(!this.participantID.equalsIgnoreCase("")&! participantID.equalsIgnoreCase(pID)){
             System.err.println("MODIFY: Should not modify turn: "+turn);
             return false;
         }
         
         boolean turnHasNegCriteria = doesStringContainRegEx(turn,this.negcrit);
        

         if(turnHasNegCriteria){
              //System.err.println("MODIFY: Should not modify turn - NEGATIVE CRITERIA IDENTIFIED: "+turn);
             return false;
         }
         
         boolean turnHasPosCriteria = doesStringContainRegEx(turn,this.poscrit);
         if(turnHasPosCriteria){
             System.err.println("MODIFY: Should modify turn - positive criteria identified: "+turn);
             
         }
         else{
             //System.err.println("MODIFY: Should NOT modify turn - positive criteria NOT identified: "+turn);

         }
         return turnHasPosCriteria; 
         
        
    }
    
   /* public String getModifiedTextOTHER(String originalTurn){
         String currentModified = ""+originalTurn; 
        
         String nextModified = this.getModifiedTextRecursive(originalTurn);
         while(!currentModified.equalsIgnoreCase(nextModified)){
             currentModified = ""+nextModified;
             nextModified = this.getModifiedTextRecursive(nextModified);
             
         }
         return nextModified;
         
    }
    
    */
    
    public String getModifiedText(String originalTurn){
        // return  originalTurn.replace(this.stringToBeReplaced, stringReplacement);
        String modifiedTurn = stringToBeReplaced.matcher(originalTurn).replaceAll(stringReplacement);
        System.err.println("GETTING THE MODIFIED TEXT OF: "+originalTurn+ " Modified to: "+modifiedTurn);

        return modifiedTurn;
                
                
               
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
