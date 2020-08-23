/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server;

import diet.tg.TelegramParticipant;
import java.util.Vector;

/**
 *
 * @author LX1C
 */
public class ParticipantsAutoNameGenerator {

    Participants ps;
    
    public ParticipantsAutoNameGenerator(Participants ps) {
       this.ps=ps;
    }
    
    
    public boolean isThisCredentialFree(String id, String username){
        Vector<Participant> vtps =ps.getAllParticipants();
        //boolean found = false;
        for(int i=0;i<vtps.size();i++){
            Participant p = vtps.elementAt(i);
            System.err.println("Checking the following:"+p.getParticipantID()+ " "+p.getUsername());
            if(p.getParticipantID().equalsIgnoreCase(id)) return false;
            if(p.getUsername().equalsIgnoreCase(username)) return false;
             
        }
        return true;
    }
    
    public String[] generateNextIDUsername_Format_PREFIXPLUSNUMBER(String idprefix, String usernameprefix){
         int i = 0;
         boolean foundNamePair = false;
         while(true){
             i=i+1;
             String id = idprefix+ i;
             String username = usernameprefix +i;
             System.err.println("IS THIS CREDENTIAL FREE ID:"+id+ " USERNAME:"+username);
             if(isThisCredentialFree(id,username)){
                 return new String[]{id,username};
             }
              
         }
    }
    
    
    public String[] generateNextIDUsername_Format_PARTICIPANTIDPLUSNUMBER(String id, String usernameprefix){
        
        
         Vector<Participant> allP = ps.getAllParticipants();
         
         Participant p = ps.findParticipantWithEmail(id);
         
         if(p!=null) return null;
         int i = 0;
         boolean foundNamePair = false;
         while(true){
             i=i+1;
             String username = usernameprefix +i;
             System.err.println("IS THIS CREDENTIAL FREE ID:"+id+ " USERNAME:"+username);
             if(isThisCredentialFree(id,username)){
                 return new String[]{id,username};
             }
              
         }
    }
    
    
  
    
        
        
    
    
    
    
    
}
