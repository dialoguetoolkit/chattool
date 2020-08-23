/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.message.MessageButtonPressFromClient;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.IsTypingController.IsTypingOrNotTyping;
import diet.server.Participant;
import diet.task.FaceComms.FaceCommsTaskControllerDyadic;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class FaceComms2016_UCSC_TURNBYTURN_Dyadic extends TurnByTurn_Dyadic{

    
    FaceCommsTaskControllerDyadic fctc;
   
    Random r = new Random();
    int spooftypingmode = r.nextInt(3); // 0=nothing, 1= on pA, 2=BOTH
    
    
    long startTimeOfExperiment = new Date().getTime();
    long timeToStartWithFakeTyping = new Date().getTime();
    long durationOfFirstStage = 35 * 60 * 1000;
   
    
    boolean doDelayedTyping = r.nextBoolean();
    Participant originOfDelayedTyping = null;
    
    long typingDelayMax = 3500;
    long currentTurnDelay = (long)r.nextInt((int)typingDelayMax);
    long previousDelay = currentTurnDelay;
    
    public FaceComms2016_UCSC_TURNBYTURN_Dyadic(Conversation c) {
        super(c);
        this.setID("rapturnbyturn");
        sett.client_MainWindow_width=800;  
        fctc = new FaceCommsTaskControllerDyadic (this,5000);
        itnt.setInactivityThreshold(3500);
        
        
       
        
    }

    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
        super.processChatText(sender, mct); //To change body of generated methods, choose Tools | Templates.
        fctc.processChatText(sender, mct.getText());      
        itnt.removeSpoofTypingInfoAfterThreshold(sender, new Date().getTime());
        itnt.processTurnSentByClient(sender);
        
        
        if(sender==this.originOfDelayedTyping){
            previousDelay = currentTurnDelay;
            currentTurnDelay = (long)r.nextInt((int)typingDelayMax);
            Conversation.printWSln("Delay", "Setting current turn delay to: "+currentTurnDelay);
        }
       
    }
    
    
    
    

    @Override
    public synchronized void participantJoinedConversation(Participant p) {   
       // super.participantJoinedConversation(p);
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        if(c.getParticipants().getAllParticipants().size()==2){
            Participant pA = (Participant)c.getParticipants().getAllParticipants().elementAt(0);
            Participant pB = (Participant)c.getParticipants().getAllParticipants().elementAt(1);
            pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
            this.itnt.setWhoSeesEachOthersTyping( pp);
            CustomDialog.showDialog("PRESS OK TO START THE EXPERIMENT!");
            fctc.startTask(pA, pB);
            this.startTimeOfExperiment= new Date().getTime();
            
            if(doDelayedTyping){
                int recipIndex=r.nextInt(2);
                if(recipIndex==0){
                    this.originOfDelayedTyping=pA;
                }
                else{
                    this.originOfDelayedTyping=pB;
                }
            }
            
        }
        
    }

    
    
    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        c.textOutputWindow_Initialize(p, "instructions", "instructions", "", 500, 500, false, true);
        fctc.processChatText(p, "/d");
    }
    
    
    
    
    

    @Override
    public Vector getAdditionalInformationForParticipant(Participant p) {
        //return super.getAdditionalInformationForParticipant(p); //To change body of generated methods, choose Tools | Templates.
        
       
        
        Vector fctcadditionalvalues = this.fctc.getAdditionalValues(p);
        
        
       
        
        fctcadditionalvalues.addAll(super.getAdditionalInformationForParticipant(p));
        
        
        if(this.doDelayedTyping){
            AttribVal av = new AttribVal("condition","delayedtyping");
            fctcadditionalvalues.addElement(av);
            if(p==this.originOfDelayedTyping){
                AttribVal av2 = new AttribVal("participanttype","originofdelayedmessage");
                fctcadditionalvalues.addElement(av2);
                AttribVal av3 = new AttribVal("previousdelay",""+this.previousDelay);
                fctcadditionalvalues.addElement(av3);
                AttribVal av4 = new AttribVal("currentdelay",""+this.currentTurnDelay);
                fctcadditionalvalues.addElement(av4);
            }
            else{
                 AttribVal av2 = new AttribVal("participanttype","recipientofdelayedmessage");
                 fctcadditionalvalues.addElement(av2);
                  AttribVal av3 = new AttribVal("previousdelay",""+this.previousDelay);
                fctcadditionalvalues.addElement(av3);
                AttribVal av4 = new AttribVal("currentdelay",""+this.currentTurnDelay);
                fctcadditionalvalues.addElement(av4);
            }
        }
        else{
             AttribVal av = new AttribVal("condition","baseline");
             fctcadditionalvalues.addElement(av);
             AttribVal av2 = new AttribVal("participanttype","normalparticipant");
             fctcadditionalvalues.addElement(av2);
             AttribVal av3 = new AttribVal("previousdelay","NODELAY");
             fctcadditionalvalues.addElement(av3);
             AttribVal av4 = new AttribVal("currentdelay","NODELAY");
             fctcadditionalvalues.addElement(av4);
        }
        
        
       
        return fctcadditionalvalues;
    }

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         //super.processKeyPress(sender, mkp); //To change body of generated methods, choose Tools | Templates.
         
        
        
         
         
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
       // itnt.processDoingsByClient(sender);
       // this.determineIfToDoSpoofTyping(sender);
       // doSpoofTyping(sender);
         
        
    }

    @Override
    public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
      if (sender==this.originOfDelayedTyping) {
          Participant recip = pp.getRecipients(sender).firstElement();
          itnt.addSpoofTypingInfo(sender, new Date().getTime()+   ((long) this.currentTurnDelay));
      }
      else{
          itnt.processDoingsByClient(sender);
      }
        
        
       
       
        //doSpoofTyping(sender);
    }
    
   
   
    
    
    
    
    
    
    
    
    
    
    
    
    
     @Override
    public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
       // this.fctc.processChatText(sender, "/"+mbfc.buttonname.substring(1));
    }
    
    
    public static boolean showcCONGUI(){
        return false;
    }     
    
}
