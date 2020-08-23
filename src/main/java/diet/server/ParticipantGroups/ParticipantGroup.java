/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ParticipantGroups;

import diet.message.MessageChatTextFromClient;
import diet.server.Participant;
import diet.task.TaskControllerInterface;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author gj
 */
public class ParticipantGroup {
     
    ParticipantGroups pg;
    //JPanelParticipantGroup jpg ;
    
    TaskControllerInterface ti;
    
    ///For the purposes of experimental design it should be impossible for one participant to be in two subdialogues simultaneously!
    
    
     String id ="";
     Vector vps;
     
     public ParticipantGroup(ParticipantGroups pg, String id, Vector vps){
         this.pg =pg;
         this.id =id;
         this.vps=vps;
         //jpg = new JPanelParticipantGroup(this);
         
     }
     
     
     
     
     
    
     public Vector getParticipants(){
         return vps;
     }
     public String getID(){
         return this.id;
     }
     
     public Vector<Participant> getAllOtherParticipants(Participant p){
         Vector v = new Vector();
         for(int i=0;i<vps.size();i++){
             Participant vpsp = (Participant)vps.elementAt(i);
             if(vpsp!=p)v.add(vpsp);
         }
         return v;
     }
     
    
     public void processChatText(MessageChatTextFromClient mct){
         
     }
     
     
     
     
}
