/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.IsTypingController;

import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.server.ParticipantGroups.ParticipantGroups;
import diet.utils.HashtableWithDefaultvalue;
import diet.utils.VectorHashtableEnforcingUniqueness;
import java.awt.Color;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class PathologicalIsTypingOrNotTyping extends Thread{
    
    VectorHashtableEnforcingUniqueness htPairsWhoAreInformedOfIsTyping = new VectorHashtableEnforcingUniqueness();
    
    final String constants_IsTyping = "ISTYPING";
    final String constants_NotTyping = "NOTTYPING";
    
    HashtableWithDefaultvalue htMode = new HashtableWithDefaultvalue(constants_NotTyping);
    HashtableWithDefaultvalue htLastTyping = new HashtableWithDefaultvalue(new Long(-1));
    
    //HashtableWithDefaultvalue htEndOfPreviousturn = new HashtableWithDefaultvalue(new Long(-1));
    //HashtableWithDefaultvalue htStartOfCurrentTurn = new HashtableWithDefaultvalue(new Long(-1));
    
    long inactivitythreshold = 1000;
    public boolean doActivity = true;
    
    DefaultConversationController cC;
    
    Color isDoingColour = Color.WHITE;
    Color isNotTypingColour = Color.BLACK;
    
    int isDoingPixels = 8;
    int isNotTypingPixels=8;
    
    
    
    public PathologicalIsTypingOrNotTyping(DefaultConversationController cCC){
        this.cC=cCC;
        this.start();
        
        
        
    }
    
    public PathologicalIsTypingOrNotTyping(DefaultConversationController cC,long inactivityThreshold){
        this.cC=cC;
        this.inactivitythreshold=inactivityThreshold;
        this.start();
    }
    
    
    
    public void setInactivityThreshold(long threshold){
      this.inactivitythreshold=threshold;
    }
    
    public void initializeClientInterfacesWithInactiveState(Participant p){
            this.informISNOTDOING(p);           
    }
    
    
    public void addPairWhoAreMutuallyInformedOfTyping(Participant a, Participant b){
       this.htPairsWhoAreInformedOfIsTyping.put(a, b);
       this.htPairsWhoAreInformedOfIsTyping.put(b, a);
    }
    
    public void setWhoSeesEachOthersTyping(ParticipantGroups pp){
        this.htPairsWhoAreInformedOfIsTyping = new VectorHashtableEnforcingUniqueness();
        Vector allParticipants = cC.c.getParticipants().getAllParticipants();
        for(int i=0;i<allParticipants.size();i++){
            Participant p = (Participant)allParticipants.elementAt(i);
            Vector pRecipients = cC.pp.getRecipients(p);
            for(int j=0;j<pRecipients.size();j++){
                Participant pRecip = (Participant)pRecipients.elementAt(j);
                this.htPairsWhoAreInformedOfIsTyping.put(p, pRecip);
                System.err.println("SETTING: "+p.getUsername()+" has keypress info sent to "+pRecip.getUsername());
            }
        }
        
        
    }
    
    
    
    public void run(){
        while (doActivity){
            try{
              Thread.sleep((long)inactivitythreshold/3);
              //System.err.println("ITNT - WAKING UP");
            }catch(Exception e){
                e.printStackTrace();
                Conversation.printWSlnLog("Main", constants_IsTyping);
            }
            examineInactivity();
        }
    }
    
    public synchronized void processDoingsByClient(Participant p){
        String mode = (String)htMode.getObject(p);
        this.htLastTyping.putObject(p, new Date().getTime());
        if(mode.equalsIgnoreCase(constants_IsTyping)) return;
        htMode.putObject(p, constants_IsTyping);
        this.informISDOING(p);
    }
    
    
    
    public synchronized void processTurnSentByClient(Participant p){
        String mode = (String)htMode.getObject(p);
        this.htLastTyping.putObject(p, (long)-99999);
        if(mode.equalsIgnoreCase(constants_NotTyping)) return;
        htMode.putObject(p, constants_NotTyping);
        this.informISNOTDOING(p);
        //System.exit(-4);
    }
    
    
    private synchronized void informISDOING(Participant p){   
        //cC.getC().sendArtificialTurnToRecipient(p, "IS TYPING", 0);
        //Vector v = cC.pp.getRecipients(p);
        Vector v = this.htPairsWhoAreInformedOfIsTyping.get(p);
        
        for(int i=0;i<v.size();i++){
            Participant pRecipient = (Participant)v.elementAt(i);
            cC.getC().changeClientInterface_changeBorderOnChatFrame(pRecipient, this.isDoingPixels, this.isDoingColour);
            
        }
    }
    
    private synchronized void examineInactivity(){
        Enumeration eee =htLastTyping.ht.keys();
         //System.err.println("EXAMINING ACTIVITY - 1");
        while(eee.hasMoreElements()){
            Participant p = (Participant)eee.nextElement();
            Long timeOfLastTyping = (Long)htLastTyping.getObject(p);
            long inactivity = new Date().getTime()-timeOfLastTyping;
            String mode = (String)htMode.getObject(p);
           // System.err.println("EXAMINING ACTIVITY - 2 "+p.getUsername()+ "inactivity: "+inactivity+" inactivitythreshold"+inactivitythreshold+"  mode:"+mode);
            if(inactivity>inactivitythreshold & mode.equalsIgnoreCase(this.constants_IsTyping)){
                System.err.println("ITNT - EXAMINING ACTIVITY - 3 "+p.getUsername());
                this.informISNOTDOING(p);
                this.htMode.putObject(p, this.constants_NotTyping);
            }
        }
        
    }
    private synchronized void informISNOTDOING(Participant p){
        //Vector v = cC.pp.getRecipients(p);
        Vector v = this.htPairsWhoAreInformedOfIsTyping.get(p);
        for(int i=0;i<v.size();i++){
            Participant pRecipient = (Participant)v.elementAt(i);
            cC.getC().changeClientInterface_changeBorderOnChatFrame(pRecipient, this.isNotTypingPixels, this.isNotTypingColour);
            
        }
    
    }
    
    
   // Vector all
    
    public synchronized void addSpoofTypingInfo(Participant p, long offset){
        
    }
    
    
    
    ////
    ////Need to be able to tell it that in X milliseconds do spoof is typing by participant
    /////////But need to be able to override that if participant types
    
    ////Need to be able to tell it that in X milliseconds, regardless of what happens, make it appear as if he has stopped typing 
    
    
    ////Need to be able to stop B from receiving updates about A's typing behaviour and make it seem as if A is typing
    ////Need to be able to stop B from receivng updates about A's typing and make it seem as if B is NOT typing
    
    
    
}
