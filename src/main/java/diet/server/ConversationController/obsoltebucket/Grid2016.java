/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController.obsoltebucket;

import diet.message.MessageClientInterfaceEvent;
import diet.message.MessageTask;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;

import diet.server.Participant;
import diet.task.ProceduralCommsGRID.ProceduralCommsTaskController;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author gj
 */
public class Grid2016 extends DefaultConversationController {

    ProceduralCommsTaskController pctc = new ProceduralCommsTaskController(this);
    
    
    public Grid2016(Conversation c) {
        super(c);
    }

    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        if(c.getParticipants().getAllParticipants().size()==2){
            Participant a = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
            Participant b = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
            pctc.startGame(a,b);
        } 
        
        
                
                
    }

   
    @Override
    public void processTaskMove(MessageTask mt, Participant p) {
        super.processTaskMove(mt, p); //To change body of generated methods, choose Tools | Templates.
        pctc.processTaskMove(mt,p);
        
        //c.gridTextStimuli_ChangeSelection(p, new Date().getTime(), innerMostSelection, innerSelection);
       
        
    }
    
    public static boolean showcCONGUI() {
        return false;
        
        
       
    }

    @Override
    public void processClientEvent(Participant origin, MessageClientInterfaceEvent mce) {
        super.processClientEvent(origin, mce); //To change body of generated methods, choose Tools | Templates.
       // System.err.println("PROCESSING CLIENT EVENT");
        //System.err.println("THE TYPE OF EVENT IS: "+mce.getClientInterfaceEvent().getType());
        
    }
    
    
}
