/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server;

import diet.debug.Debug;
import java.util.Vector;


import java.util.Hashtable;

/**
 *
 * Ensure that when participants connect to the server, all the participants are stored in this file
 * Ensure also that all the conversations that participants are assigned to are also recorded here
 *
 * @author Greg
 */
public class GroupAssignment {








    Vector allParticipants = new Vector();
    Hashtable participantsConversations = new Hashtable();



    
    
   



   public synchronized boolean assignParticipantToConversation(Vector conversations,String participantID,String userName,ParticipantConnection participConnection){
         //default behaviour: go through all conversations, checking if they are active and checking to see if
         //they are "filled".
        //System.exit(-5);
         for(int i=0;i<conversations.size();i++){
             Conversation c = (Conversation)conversations.elementAt(i);
             if(c.isConversationActive()){
                 boolean canAssign = false;
                 if(c.requestPermissionForNewParticipantToBeAddedToConversation(participantID))canAssign=true;
                 
                 
                 if(canAssign){
                     Participant p = new Participant(c, participantID, userName);
                     p.setConnection(participConnection);
                     c.addNewParticipant(p);

                     allParticipants.addElement(p);
                     participantsConversations.put(p, c);
                     //Conversation.printWSln("Main", "Added participant");
                     return true;
                 }              
             }
         }       
        //Conversation.printWSln("Main", "Didn't add participant");
        return false;
    }     
        
        
        

   
    

    public boolean isParticipantIDOK(Vector conversations, String participantID){
        for(int i=0;i<conversations.size();i++){
             Conversation c = (Conversation)conversations.elementAt(i);
             if(c.requestPermissionForNewParticipantToBeAddedToConversation(participantID))return true;
        }
        return false;
    }

    public Participant findUsernameForParticipantID(String participantID){
        for(int i=0;i<this.allParticipants.size();i++){
            Participant p = (Participant)allParticipants.elementAt(i);
            if(p.getParticipantID().equals(participantID)){
                return p;
            }
        }
        return null;
    }
    
    public synchronized boolean isParticipantNameAlreadyUsed(String name){
         for(int i=0;i<this.allParticipants.size();i++){
            Participant p = (Participant)allParticipants.elementAt(i);
            if(p.getUsername().equalsIgnoreCase(name))return true;
        }
        return false;
     }

    public synchronized boolean reConnectParticipant(ParticipantConnection participC, Participant p){
        Conversation.printWSln("Main", "LOGGING1");
        Conversation cP = (Conversation)this.participantsConversations.get(p);
        Conversation.printWSln("Main", "LOGGING2");
        
        cP.killClientOnRemoteMachine(p);
        ParticipantConnection oldConnection = p.getConnection();
         Conversation.printWSln("Main", "LOGGING3");
        if(oldConnection!=null){
             Conversation.printWSln("Main", "LOGGING4");
            oldConnection.dispose();
        }
        Conversation.printWSln("Main", "LOGGING5");
        p.setConnection(participC);
        participC.assignToParticipant(p);
        participC.setConnected(true);

        Conversation.printWSln("Main", "LOGGING6");
        cP.reactivateParticipant(p);
        Conversation.printWSln("Main", "LOGGING7");
        return true;
    }

}
